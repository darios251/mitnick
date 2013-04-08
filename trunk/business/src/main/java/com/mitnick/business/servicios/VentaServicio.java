package com.mitnick.business.servicios;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mitnick.exceptions.BusinessException;
import com.mitnick.exceptions.PersistenceException;
import com.mitnick.persistence.daos.ICuotaDao;
import com.mitnick.persistence.daos.IMovimientoDao;
import com.mitnick.persistence.daos.IProductoDAO;
import com.mitnick.persistence.daos.IVentaDAO;
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
	protected PrinterService printerService;
	
	
	private VentaDto agregarProducto(ProductoDto producto, VentaDto venta) {
		ProductoVentaDto productoVenta = getProductoVentaDto(producto, venta);
		if (productoVenta == null) {
			productoVenta = new ProductoVentaDto();
			
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
		venta.setDescuento(descuento);
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
	public VentaDto modificarCantidad(ProductoVentaDto productoVenta, int cantidad, VentaDto venta) {
		productoVenta.setCantidad(cantidad);
		VentaHelper.calcularTotales(venta);

		return venta;
	}

	@Override
	public VentaDto modificarPrecioUnitario(ProductoVentaDto productoVenta, BigDecimal precioUnitario, VentaDto venta) {
		BigDecimal ivaProducto = VentaHelper.calcularImpuesto(precioUnitario);
		productoVenta.getProducto().setPrecioVenta(precioUnitario.subtract(ivaProducto));
		productoVenta.getProducto().setIva(ivaProducto);
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
		if (Validator.isBlankOrNull(cliente.getCuit()))
			throw new BusinessException("error.venta.cliente.cuit.null", "El cuit del cliente es nulo.");
		if (Validator.isBlankOrNull(cliente.getDocumento()))
			throw new BusinessException("error.venta.cliente.documento.null", "El documento del cliente es nulo.");
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
		
		if (ventaDto.getTipo()==MitnickConstants.VENTA && !ventaDto.isPagado()){
			throw new BusinessException("error.ventaServicio.facturar", "No se puede facturar la venta ya que no se pago el total");
		}
		
		VentaHelper.calcularTotales(ventaDto);
		actualizarPagoEFTVuelto(ventaDto);
		@SuppressWarnings("unchecked")
		Venta venta = (Venta) entityDTOParser.getEntityFromDto(ventaDto);
				
//		if(!venta.isPrinted()) {
//			if(!printerService.imprimirTicketFactura(ventaDto))
//				throw new BusinessException("error.ventaServicio.facturar.impresion", "Ocurrió un error durante la impresión");
//		}
//		else
			venta.setPrinted(true);		
		actualizarStock(venta);
		venta.setNumeroTicket(ventaDto.getNumeroTicket());
		venta.setTipoTicket(ventaDto.getTipoTicket());
		
		//este metodo se invoca solo si es una venta - la devolucion nunca puede tener pagos realizados con notas de credito.
		if (MitnickConstants.DEVOLUCION != venta.getTipo())
			ventaDao.actualizarCreditos(ventaDto);
		ventaDao.saveOrUpdate(venta);
		ventaDto.setId(venta.getId());
		
		return ventaDto;

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
		ventaDto.setCancelada(true);
		//se eliminan las cuotas que estuvieran asociadas a la venta
		ventaDto.setCuotas(new ArrayList<CuotaDto>());
		@SuppressWarnings("unchecked")
		Venta venta = (Venta) entityDTOParser.getEntityFromDto(ventaDto);
		
		ventaDao.saveOrUpdate(venta);
	}
	
	@SuppressWarnings("unchecked")
	public VentaDto getVentaByNroFactura(String nroTicket){
		Venta venta = ventaDao.findByNumeroFactura(nroTicket);
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
	
}

