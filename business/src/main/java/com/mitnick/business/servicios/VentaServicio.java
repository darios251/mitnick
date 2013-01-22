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
import com.mitnick.utils.PrinterService;
import com.mitnick.utils.VentaHelper;
import com.mitnick.utils.dtos.ClienteDto;
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
	protected PrinterService printerService;
	
	@Override
	public VentaDto agregarProducto(ProductoDto producto, VentaDto venta) {
		ProductoVentaDto productoVenta = getProductoVentaDto(producto, venta);
		if (productoVenta == null) {
			productoVenta = new ProductoVentaDto();
			
			productoVenta.setProducto(producto);
			productoVenta.setCantidad(1);
			productoVenta.setPrecioTotal(producto.getPrecioVenta());
			venta.addProducto(productoVenta);
		} else
			productoVenta.setCantidad(productoVenta.getCantidad()+1);
		
		VentaHelper.calcularTotales(venta);
		
		return venta;
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
	public VentaDto modificarCantidad(ProductoVentaDto producto, int cantidad, VentaDto venta) {
		ProductoVentaDto pv = getProductoVentaDto(producto.getProducto(), venta);
		if (pv!=null) {
			pv.setCantidad(cantidad);
			VentaHelper.calcularTotales(venta);
		} else {
			throw new BusinessException("error.productoServicio.producto.nulo", "Se modifica la cantidad de un producto no agregado a la venta");	
		}			

		return venta;
	}

	@Override
	public VentaDto agregarCliente(ClienteDto cliente, VentaDto venta) {
		venta.setCliente(cliente);
		return venta;
	}

	@Override
	public VentaDto quitarCliente(VentaDto venta) {
		venta.setCliente(null);
		return venta;
	}

	//TODO: validar medio de pago sin vuelto
	//restar el vuelto al pago - el pago deberia ser siempre el total 
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
		VentaHelper.calcularTotales(ventaDto);
		
		if (ventaDto.getTipo()==MitnickConstants.VENTA && !ventaDto.isPagado()){
			throw new BusinessException("error.ventaServicio.facturar", "No se puede facturar la venta ya que no se pago el total");
		}
		Venta venta = guardarVenta(ventaDto);
		
		//TODO NOTA DE CREDITO SI VENTA.TIPO = MitnickConstants.DEVOLUCION
//		if(!venta.isPrinted()) {
//			
//			if(venta.getCliente() == null && !printerService.imprimirTicket(ventaDto))
//				throw new BusinessException("error.ventaServicio.facturar.impresion", "Ocurrió un error durante la impresión");
//			else if(venta.getCliente() != null && !printerService.imprimirTicketFactura(ventaDto))
//				throw new BusinessException("error.ventaServicio.facturar.impresion", "Ocurrió un error durante la impresión");
//		}
//		else
//			venta.setPrinted(true);
		
		venta.setNumeroTicket(ventaDto.getNumeroTicket());
		venta.setTipoTicket(ventaDto.getTipoTicket());
		
		ventaDao.saveOrUpdate(venta);
		//ventaDao.generarFactura(venta);
		
		return ventaDto;

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
	private Venta guardarVenta(VentaDto ventaDto){
		VentaHelper.calcularTotales(ventaDto);
		try {
			@SuppressWarnings("unchecked")
			Venta venta = (Venta) entityDTOParser.getEntityFromDto(ventaDto);
			//Actualizacion de stock
			Iterator<ProductoVenta> productos = venta.getProductos().iterator();
			while (productos.hasNext()) {
				actualizarStock(productos.next(), venta.getTipo());
			}
			ventaDao.saveOrUpdate(venta);
			
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

		stock = stock - productoVenta.getCantidad();
		//se modifica el stock del producto
		producto.setStock(stock);
		
		movimiento.setCantidad(productoVenta.getCantidad());
		movimiento.setFecha(new Date());
		
		if (tipo == MitnickConstants.VENTA)
			movimiento.setTipo(Movimiento.VENTA);
		if (tipo == MitnickConstants.DEVOLUCION)
			movimiento.setTipo(Movimiento.DEVOLUCION);
		
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
		@SuppressWarnings("unchecked")
		Venta venta = (Venta) entityDTOParser.getEntityFromDto(ventaDto);
		
		ventaDao.saveOrUpdate(venta);
	}
	
	public VentaDto getVentaByNroFactura(String nroTicket){
		Venta venta = ventaDao.findByNumeroFactura(nroTicket);
		if (venta!=null)
			return (VentaDto) entityDTOParser.getDtoFromEntity(venta);
		return null;
	}
}

