package com.mitnick.business.servicios;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mitnick.exceptions.BusinessException;
import com.mitnick.exceptions.PersistenceException;
import com.mitnick.persistence.daos.ICuotaDao;
import com.mitnick.persistence.daos.IEmpresaDao;
import com.mitnick.persistence.daos.IMovimientoDao;
import com.mitnick.persistence.daos.IProductoDAO;
import com.mitnick.persistence.daos.IVentaDAO;
import com.mitnick.persistence.entities.Empresa;
import com.mitnick.persistence.entities.Movimiento;
import com.mitnick.persistence.entities.Producto;
import com.mitnick.persistence.entities.ProductoVenta;
import com.mitnick.persistence.entities.Venta;
import com.mitnick.servicio.servicios.IVentaServicio;
import com.mitnick.servicio.servicios.dtos.DescuentoDto;
import com.mitnick.utils.DateHelper;
import com.mitnick.utils.MitnickConstants;
import com.mitnick.utils.PropertiesManager;
import com.mitnick.utils.Validator;
import com.mitnick.utils.VentaHelper;
import com.mitnick.utils.dtos.ClienteDto;
import com.mitnick.utils.dtos.CreditoDto;
import com.mitnick.utils.dtos.CuotaDto;
import com.mitnick.utils.dtos.PagoDto;
import com.mitnick.utils.dtos.ProductoDto;
import com.mitnick.utils.dtos.ProductoVentaDto;
import com.mitnick.utils.dtos.VentaDto;

@SuppressWarnings("rawtypes")
@Service("ventaServicio")
public class VentaServicio extends ServicioBase implements IVentaServicio {

	@Autowired
	protected IVentaDAO ventaDao;
	@Autowired
	protected IProductoDAO productoDao;
	@Autowired
	protected IMovimientoDao movimientoDao;
	@Autowired
	protected ICuotaDao cuotaDao;
	@Autowired
	protected IEmpresaDao empresaDao;
	
	@Autowired
	protected PrinterService printerService;
	
	@SuppressWarnings("unchecked")
	public VentaDto getVentaByNroFacturaTipo(String numero, String tipo, int numeroCaja){
		Venta venta = ventaDao.findTransactionByNumeroTipoFactura(numero, PropertiesManager.getProperty("dialog.consultarTransacciones.filter.venta"), tipo, numeroCaja);
		if (Validator.isNotNull(venta)){
			VentaDto ventaDto = (VentaDto) entityDTOParser.getDtoFromEntity(venta);
			return ventaDto;
		} 
		return null;
	}
	
	private VentaDto agregarProducto(ProductoDto producto, VentaDto venta) {
		ProductoVentaDto productoVenta = getProductoVentaDto(producto, venta);
		if (productoVenta == null) {
			productoVenta = new ProductoVentaDto();
			productoVenta.setDescripcion(producto.getDescripcion());
			productoVenta.setProducto(producto);
			productoVenta.setCantidad(1);
			productoVenta.setPrecioTotal(producto.getPrecioVentaConIva());
			venta.addProducto(productoVenta);
		} else
			productoVenta.setCantidad(productoVenta.getCantidad()+1);
		
		VentaHelper.calcularTotales(venta);
		
		return venta;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public VentaDto agregarProducto(String productoCode, VentaDto venta) {
		ProductoDto productoDTO = (ProductoDto)entityDTOParser.getDtoFromEntity(productoDao.findByCode(productoCode));
		if (productoDTO==null)
			throw new BusinessException("error.venta.agregarProducto.productoNoEncontrado", "El producto no se encuentra");
		return agregarProducto(productoDTO, venta);
	}

	@Override
	public VentaDto quitarProducto(ProductoVentaDto producto, VentaDto venta) {
		venta.getProductos().remove(producto);
		VentaHelper.calcularTotales(venta);
		
		return venta;
	}

	@Override
	public VentaDto agregarDescuento(DescuentoDto descuento, VentaDto venta) {		
		if (Validator.isNotNull(descuento)) {
			if (descuento.getTipo() == DescuentoDto.PORCENTAJE){
				BigDecimal monto = BigDecimal.ZERO;
				BigDecimal perc = descuento.getDescuento();
				perc = perc.divide(new BigDecimal(100));
				BigDecimal subtotal = venta.getSubTotal();
				monto = subtotal.multiply(perc);
				descuento.setTipo(DescuentoDto.MONTO);
				descuento.setDescuento(monto);
			}
		}
		venta.setDescuento(descuento);
		VentaHelper.calcularTotales(venta);
		
		return venta;
	}
	
	@Override
	public VentaDto agregarDescuento(DescuentoDto descuento, VentaDto venta, ProductoVentaDto productoVenta) {
		if (Validator.isNotNull(descuento)) {
			if (descuento.getTipo() == DescuentoDto.PORCENTAJE){
				BigDecimal monto = BigDecimal.ZERO;
				BigDecimal perc = descuento.getDescuento();
				perc = perc.divide(new BigDecimal(100));
				BigDecimal subtotal = productoVenta.getPrecioTotal();
				monto = subtotal.multiply(perc);
				descuento.setTipo(DescuentoDto.MONTO);
				descuento.setDescuento(monto);
			}
		}
		productoVenta.setDescuento(descuento);
		VentaHelper.calcularTotales(venta);
		
		return venta;
	}

	@Override
	public VentaDto quitarDescuento(VentaDto venta) {
		venta.setDescuento(null);
		VentaHelper.calcularTotales(venta);
		
		return venta;
	}

	@Override
	public VentaDto quitarDescuento(VentaDto venta, ProductoVentaDto productoVenta) {
		productoVenta.setDescuento(null);
		VentaHelper.calcularTotales(venta);
		
		return venta;
	}
	
	@Override
	public VentaDto modificarCantidad(ProductoVentaDto productoVenta, int cantidad, VentaDto venta) {
		productoVenta.setCantidad(cantidad);
		VentaHelper.calcularTotales(venta);

		return venta;
	}

	@Override
	public VentaDto modificarPrecioUnitario(ProductoVentaDto productoVenta, BigDecimal precioUnitario, VentaDto venta) {
		BigDecimal precioSinIva = VentaHelper.calcularPrecioSinIva(precioUnitario);
		productoVenta.getProducto().setPrecioVenta(precioSinIva);
		VentaHelper.calcularTotales(venta);

		return venta;
	}
	
	@Override
	public VentaDto agregarCliente(ClienteDto cliente, VentaDto venta) {
		venta.setCliente(cliente);
		return venta;
	}
	
	@Override
	public VentaDto desagregarCliente(VentaDto venta) {
		venta.setCliente(null);
		return venta;
	}

	@Override
	public void validarCliente(ClienteDto cliente) {
		if (Validator.isBlankOrNullCuit(cliente.getCuit()) && Validator.isBlankOrNull(cliente.getDocumento()))
			throw new BusinessException("error.venta.cliente.cuit.document.null", "El cuit y el documento del cliente es nulo.");					
		if (Validator.isNull(cliente.getDireccion())||Validator.isBlankOrNull(cliente.getDireccion().getDomicilio()))
			throw new BusinessException("error.venta.cliente.domicilio.null", "El domicilio del cliente es nulo.");
	}
	
	@Override
	public VentaDto quitarCliente(VentaDto venta) {
		venta.setCliente(null);
		return venta;
	}
	
	@Override
	public VentaDto agregarPago(PagoDto pago, VentaDto venta) {
		validarPago(pago, venta);
		
		PagoDto pagoDto = getPagoDto(pago, venta);
		
		if (pagoDto == null)
			//agrego el nuevo pago
			venta.getPagos().add(pago);
		else{
			//actualizo el pago existente en la venta
			BigDecimal pagado = pagoDto.getMonto();
			BigDecimal pagando = pago.getMonto();
			pagoDto.setMonto(pagado.add(pagando));
		}
		
		VentaHelper.calcularTotales(venta);
		return venta;
	}

	private void validarPago(PagoDto pago, VentaDto venta){
		if (!MitnickConstants.Medio_Pago.EFECTIVO.equals(pago.getMedioPago().getCodigo())){
			if (venta.getFaltaPagar().compareTo(pago.getMonto())<0)
				throw new BusinessException("error.ventaServicio.pagoSinVuelto", "El medio de pago no admite vuelto. Pague por el total restante.");
		}
		//si es cuenta se debe tener un cliente asociado
		if (MitnickConstants.Medio_Pago.CUENTA_CORRIENTE.equals(pago.getMedioPago().getCodigo())
				&& venta.getCliente()==null)
			throw new BusinessException("error.ventaServicio.cuentaSinCliente", "Debe asociar un cliente a la venta para pagar con cuenta corriente");
	}
	
	public PagoDto getPagoDto(PagoDto pago, VentaDto venta){
		Iterator<PagoDto> pagos = venta.getPagos().iterator();
		PagoDto pagoDto = null;
		while (pagos.hasNext()) {
			PagoDto pDto = pagos.next();
			if (pDto.getMedioPago().getId().equals(pago.getMedioPago().getId()))
				pagoDto = pDto;
		}
		return pagoDto;
	}
	
	@Override
	public VentaDto quitarPago(PagoDto pago, VentaDto venta) {
		venta.getPagos().remove(pago);
		VentaHelper.calcularTotales(venta);
		return venta;
	}

	@Transactional
	@Override
	public VentaDto facturar(VentaDto ventaDto) {
		
		if (ventaDto.isVenta() && !ventaDto.isPagado()){
			throw new BusinessException("error.ventaServicio.facturar", "No se puede facturar la venta ya que no se pago el total");
		}
		
		VentaHelper.calcularTotales(ventaDto);
		actualizarPagoEFTVuelto(ventaDto);
		@SuppressWarnings("unchecked")
		Venta venta = (Venta) entityDTOParser.getEntityFromDto(ventaDto);
		logger.info("Facturando venta con monto: " + venta.getTotal());
		if(!ventaDto.isPrinted()) {
			if(!printerService.imprimirTicketFactura(ventaDto))
				throw new BusinessException("error.ventaServicio.facturar.impresion", "Ocurrió un error durante la impresión");
		} 	
		//las siguientes dos lineas se descomentan para probar sin impresora fiscal
//		ventaDto.setNumeroTicket(String.valueOf(System.currentTimeMillis()));
//		ventaDto.setTipoTicket("B");
		
		logger.info("Venta facturada: " + ventaDto.getNumeroTicket());
		venta.setPrinted(true);
		ventaDto.setPrinted(true);
		
		actualizarStock(venta);
		venta.setNumeroTicket(ventaDto.getNumeroTicket());
		
		venta.setTipoTicket(ventaDto.getTipoTicket());
		
		//este metodo se invoca solo si es una venta - la devolucion nunca puede tener pagos realizados con notas de credito.
		if (venta.isVenta())
			ventaDao.actualizarCreditos(ventaDto);
		ventaDao.saveOrUpdate(venta);
		ventaDto.setId(venta.getId());
		logger.info("Venta guardada: " +venta.getId() + " - nro ticket: " + ventaDto.getNumeroTicket());
		return ventaDto;

	}
	
	@Transactional
	@Override
	public void cancelarVenta(VentaDto ventaDto) {
		
		@SuppressWarnings("unchecked")
		Venta venta = (Venta) entityDTOParser.getEntityFromDto(ventaDto);
		//se transforma en devolucion para que se genera la nota de credito y se cancele la venta.
		venta.setId(null);
		ventaDto.setTipo(MitnickConstants.DEVOLUCION);		
		venta.setTipo(MitnickConstants.DEVOLUCION);
		if(!ventaDto.isPrinted()) {
			if(!printerService.imprimirTicketFactura(ventaDto))
				throw new BusinessException("error.ventaServicio.facturar.impresion", "Ocurrió un error durante la impresión");
		}
		
		venta.setPrinted(true);		
		ventaDto.setPrinted(true);
		
		actualizarStock(venta);
		ventaDao.saveOrUpdate(venta);
		ventaDto.setId(venta.getId());
				
	}
	
	private void actualizarPagoEFTVuelto(VentaDto venta){
		for (PagoDto pago : venta.getPagos()){
			if (pago.isEfectivo()){
				pago.setMonto(pago.getMonto().subtract(venta.getVuelto()));
			}
				
		}
	}
	
	private ProductoVentaDto getProductoVentaDto(ProductoDto productoDto, VentaDto venta){
		Iterator<ProductoVentaDto> productos = venta.getProductos().iterator();
		ProductoVentaDto producto = null;
		while (productos.hasNext()) {
			ProductoVentaDto pvDto = productos.next();
			if (pvDto.getProducto().getCodigo().equals(productoDto.getCodigo()))
				producto = pvDto;
		}
		return producto;
	}
	
	@Transactional
	private Venta actualizarStock(Venta venta){
		try {
			//Actualizacion de stock
			Iterator<ProductoVenta> productos = venta.getProductos().iterator();
			while (productos.hasNext()) {
				ProductoVenta productoVenta = productos.next();
				if (!PropertiesManager.getProperty("application.producto.comodin").equals(productoVenta.getProducto().getCodigo()))
					actualizarStock(productoVenta, venta.getTipo());
			}
			
			return venta;
		}
		catch(PersistenceException e) {
			throw new BusinessException(e, "Error al intentar guardar el cliente");
		}
		catch (Exception e) {
			throw new BusinessException("error.persistence", "Error en capa de persistencia de  cliente", e);
		}
	}
	
	@Transactional
	private void actualizarStock(ProductoVenta productoVenta, int tipo){
		Producto producto = productoVenta.getProducto(); 
		int stock = producto.getStock();
		
		//se generan los movimientos
		Movimiento movimiento = new Movimiento();
		//el stock a la fecha anterior a aplicar el movimento
		movimiento.setStockAlaFecha(stock);

		
		if (tipo == MitnickConstants.VENTA){
			movimiento.setTipo(Movimiento.VENTA);
			stock = stock - productoVenta.getCantidad();
		}
			
		if (tipo == MitnickConstants.DEVOLUCION){
			movimiento.setTipo(Movimiento.DEVOLUCION);
			stock = stock + productoVenta.getCantidad();
		}
			
		//se modifica el stock del producto
		producto.setStock(stock);
		
		movimiento.setCantidad(productoVenta.getCantidad());
		movimiento.setFecha(new Date());
		
		movimiento.setProducto(producto);
		
		productoDao.saveOrUpdate(producto);
		movimientoDao.saveOrUpdate(movimiento);
	}
	
	@Transactional
	public List<CuotaDto> generarCuotas(int cantidadCuotas, BigDecimal total, ClienteDto cliente) {
		List<CuotaDto> cuotas = new ArrayList<CuotaDto>();
		BigDecimal cantidad = new BigDecimal(cantidadCuotas);
		BigDecimal valorCuota = total.divide(cantidad, 0, BigDecimal.ROUND_DOWN);

		BigDecimal paga = valorCuota.multiply(cantidad);
		
		Date fecha = new Date();
		GregorianCalendar calendar = (GregorianCalendar) Calendar.getInstance();
		//primer fecha a pagar debe ser 1 mes posterior a la fecha actual
		calendar.setTime(fecha);
		calendar.add(Calendar.MONTH, 1);
		fecha = calendar.getTime();
		
		CuotaDto cuota = null; 
		for (int i = 0; i < cantidadCuotas; i++) {
			cuota = new CuotaDto();
			cuota.setClienteDto(cliente);
			cuota.setNroCuota(i + 1);
			cuota.setTotal(valorCuota);
			cuota.setFaltaPagar(valorCuota);
			cuota.setFecha_pagar(DateHelper.getFecha(fecha));
			calendar.setTime(fecha);
			calendar.add(Calendar.MONTH, 1);
			fecha = calendar.getTime();
			cuotas.add(cuota);
		}
		
		if (cuota!=null){
			BigDecimal restante = total.subtract(paga);
			cuota.setTotal(cuota.getTotal().add(restante));
			cuota.setFaltaPagar(cuota.getFaltaPagar().add(restante));
		}
		
		return cuotas;
	}	

	@Transactional
	public void guardarCuotas(VentaDto venta, List<CuotaDto> cuotas) {
		venta.setCuotas(cuotas);
	}
	
	@Transactional
	public void cancelar(VentaDto ventaDto) {
		logger.info("Cancelando venta con monto: " + ventaDto.getTotal());
		ventaDto.setCancelada(true);
		//se eliminan las cuotas que estuvieran asociadas a la venta
		ventaDto.setCuotas(new ArrayList<CuotaDto>());
		@SuppressWarnings("unchecked")
		Venta venta = (Venta) entityDTOParser.getEntityFromDto(ventaDto);
		
		ventaDao.saveOrUpdate(venta);
		logger.info("Venta facturada (ticket impreso): " + ventaDto.isPrinted());
		logger.info("Cancelando venta facturada nro: " + ventaDto.getNumeroTicket());
		logger.info("Venta cancelada id: " + venta.getId());
	}
	
	@SuppressWarnings("unchecked")
	public VentaDto getVentaByNroFactura(String nroTicket){
		Venta venta = ventaDao.findByNumeroFactura(nroTicket, PropertiesManager.getPropertyAsInteger("application.caja.numero"));
		if (venta!=null)
			return (VentaDto) entityDTOParser.getDtoFromEntity(venta);
		return null;
	}
	
	public BigDecimal getSaldoDeudorCliente(VentaDto venta) {
		return cuotaDao.getSaldoPendiente(venta.getCliente().getId());
	}
		
	@SuppressWarnings("unchecked")
	public CreditoDto obtenerCredito(String nroNC){
		return (CreditoDto) entityDTOParser.getDtoFromEntity(ventaDao.getCredito(nroNC));
	}
	
	@SuppressWarnings("unchecked")
	public void consultarTransaccion(String nroTrx, String tipo, String factura, int numeroCaja) {
		Venta venta = ventaDao.findTransactionByNumeroTipoFactura(nroTrx, tipo, factura, numeroCaja);
		if (Validator.isNull(venta))
			throw new BusinessException("error.consultarTransaccion.noExiste","No se encuentra una transacciï¿½n con el nï¿½mero ingresado");
		try {
			VentaDto ventaDto = (VentaDto) entityDTOParser.getDtoFromEntity(venta);
			generarReporteFactura(ventaDto, true);
		} catch (PersistenceException e) {
			throw new BusinessException(e,
					"Error al intentar obtener el comprobante de la transacciï¿½n");
		}
	}
	
	/**
	 * 
	 * Genera un reporte con los datos de la venta recibida por parametro.
	 * Si duplicado es true la factura tiene la leyenda de duplicado.
	 * @param venta
	 * @param duplicado
	 */
	public void generarReporteFactura(VentaDto venta, boolean duplicado) {
		try {
			
			JasperReport reporte = null;
			if (Validator.isNotNull(PropertiesManager.getPropertyAsBoolean("application.discount")) && PropertiesManager.getPropertyAsBoolean("application.discount").booleanValue()) {
				reporte = (JasperReport) JRLoader.loadObject(this.getClass().getResourceAsStream("/reports/facturaDescuentosDuplicado.jasper"));
			} else {
				reporte = (JasperReport) JRLoader.loadObject(this.getClass().getResourceAsStream("/reports/facturaDuplicado.jasper"));
			}
				
				
			Empresa empresa = empresaDao.getEmpresa();
			
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("nombreEmpresa", empresa.getNombre());
			parameters.put("empresaDireccion", empresa.getDireccion().getDomicilio() + "(" + empresa.getDireccion().getCodigoPostal() + ")" 
					+ empresa.getDireccion().getCiudad().getDescripcion() + "\n Tel" + empresa.getTelefono());
			parameters.put("tipoResponsable", empresa.getTipoResponsable());
			String nroFactura = StringUtils.leftPad(venta.getNumeroCaja() + "", 4, "0"); 
			nroFactura = nroFactura.concat("-");
			nroFactura = nroFactura.concat(StringUtils.leftPad(venta.getNumeroTicket()+ "", 8, "0"));
			
			parameters.put("nroFactura", nroFactura);
			parameters.put("cuitEmpresa", empresa.getCuit());
			parameters.put("ingBrutos", empresa.getIngBrutos());
			parameters.put("fechaInicioActividadEmpresa", empresa.getFechaInicioActividad());
			
			parameters.put("tipoIva", venta.getTipoResponsabilidad().getDescripcion());
			
			if (Validator.isNotNull(venta.getFecha()))
				parameters.put("fechaTrx", DateHelper.getFecha(venta.getFecha()));
			else
				parameters.put("fechaTrx", DateHelper.getFecha(new Date()));
			
			if (duplicado)
				parameters.put("leyenda", "Comprobante no válido como Factura");
			else
				parameters.put("leyenda", "");
			boolean consumidorFinal = venta.getTipoResponsabilidad().getTipoComprador().equals(MitnickConstants.TipoComprador.CONSUMIDOR_FINAL);
			String nombre = "";
			String direccion = "";
			String cuit = "";
			if (Validator.isNotNull(venta.getCliente())){
				nombre = venta.getCliente().getNombre();
				if (Validator.isNotNull(venta.getCliente().getDireccion())){
					direccion = venta.getCliente().getDireccion().getDomicilio();
					if (Validator.isNotNull(venta.getCliente().getDireccion().getCiudad()))
						direccion = direccion.concat(" ").concat(venta.getCliente().getDireccion().getCiudad().getDescripcion());
				}
				if (Validator.isNotNull(venta.getCliente().getCuit()))
					cuit =  venta.getCliente().getCuit().replaceAll("-", "").trim();	
				if (Validator.isNotBlankOrNull(cuit)) 
					cuit = venta.getCliente().getCuit();
				else if (Validator.isNotBlankOrNull(venta.getCliente().getDocumento()))
					cuit = venta.getCliente().getDocumento();
			}
			parameters.put("nombreCliente", nombre);
			parameters.put("direccionCliente", direccion);
			parameters.put("cuitCliente", cuit);
			String leyenda = "Factura";
			
			if (consumidorFinal){
				venta.setTipoTicket("B");
				parameters.put("tipoLetra", "B");
			} else {
				venta.setTipoTicket("A");
				parameters.put("tipoLetra", "A");
			}

			List<ProductoVentaDto> productos = null;
			if (venta.isDevolucion())
				leyenda = "Nota de Crédito";
			
			if (duplicado){
				leyenda = "Duplicado - ".concat(leyenda).concat(" - ").concat("No válido");
				productos = VentaHelper.getProductosPrecioVendido(venta);				
			} else
				productos = venta.getProductos();
				
			parameters.put("ventaDescuentos", venta.getDescuentoVenta().toString());
			parameters.put("prodDescuentos", venta.getDescuentoProductos().toString());
			
			parameters.put("leyenda", leyenda);
			parameters.put("totalVenta", venta.getTotal().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
			parameters.put("pagos", venta.getPagos());
			JRDataSource dr = new JRBeanCollectionDataSource(productos);
										
			JasperPrint jasperPrint = JasperFillManager.fillReport(reporte,parameters, dr);
			JasperViewer.viewReport(jasperPrint,false);

		} catch (Exception e1) {
			throw new PersistenceException("error.reporte.factura.Cliente","Error al generar la factura del cliente.",e1);
		}	
	}
	
	@Override
	public void getDevolucionFromVenta(VentaDto venta, VentaDto devolucion) {
		ClienteDto cliente = venta.getCliente();
		List<PagoDto> pagos = venta.getPagos();
		List<ProductoVentaDto> productos = VentaHelper.getProductosPrecioVendido(venta);						
		devolucion.setCliente(cliente);
		for (PagoDto pago : pagos){
			pago.setId(null);
		}
		devolucion.setPagos(pagos);
		devolucion.setProductos(productos);
		devolucion.setNumeroTicketOriginal(venta.getNumeroTicket());
		devolucion.setDescuento(venta.getDescuento());
		
		VentaHelper.calcularTotales(devolucion);
	}
}

