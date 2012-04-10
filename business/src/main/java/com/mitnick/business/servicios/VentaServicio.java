package com.mitnick.business.servicios;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mitnick.business.exceptions.BusinessException;
import com.mitnick.business.services.ServicioBase;
import com.mitnick.persistence.daos.IClienteDao;
import com.mitnick.persistence.daos.IMedioPagoDAO;
import com.mitnick.persistence.daos.IProductoDAO;
import com.mitnick.persistence.daos.IVentaDAO;
import com.mitnick.persistence.entities.Movimiento;
import com.mitnick.persistence.entities.Pago;
import com.mitnick.persistence.entities.Producto;
import com.mitnick.persistence.entities.ProductoVenta;
import com.mitnick.persistence.entities.Venta;
import com.mitnick.servicio.servicios.IVentaServicio;
import com.mitnick.servicio.servicios.dtos.DescuentoDto;
import com.mitnick.utils.Validator;
import com.mitnick.utils.dtos.ClienteDto;
import com.mitnick.utils.dtos.PagoDto;
import com.mitnick.utils.dtos.ProductoDto;
import com.mitnick.utils.dtos.ProductoVentaDto;
import com.mitnick.utils.dtos.VentaDto;

@Service("ventaServicio")
public class VentaServicio extends ServicioBase implements IVentaServicio {

	@Autowired
	IVentaDAO ventaDao;
	
	@Autowired
	IClienteDao clienteDao;
	
	@Autowired
	IProductoDAO productoDao;
	
	@Autowired
	IMedioPagoDAO medioPagoDao;
	
	@Override
	public VentaDto agregarProducto(ProductoDto producto, VentaDto venta) {
		ProductoVentaDto productoVenta = getProductoVentaDto(producto, venta);
		if (productoVenta == null) {
			productoVenta = new ProductoVentaDto();
			
			productoVenta.setProducto(producto);
			productoVenta.setCantidad(1);
			productoVenta.setPrecioTotal(producto.getPrecio());
			venta.addProducto(productoVenta);
		} else
			productoVenta.setCantidad(productoVenta.getCantidad()+1);
		
		calcularTotales(venta);
		
		return venta;
		
	}

	@Override
	public VentaDto quitarProducto(ProductoVentaDto producto, VentaDto venta) {
		venta.getProductos().remove(producto);
		calcularTotales(venta);
		
		return venta;
	}

	@Override
	public VentaDto agregarDescuento(DescuentoDto descuento, VentaDto venta) {
		venta.setDescuento(descuento);
		calcularTotales(venta);
		
		return venta;
	}

	@Override
	public VentaDto quitarDescuento(VentaDto venta) {
		venta.setDescuento(null);
		calcularTotales(venta);
		
		return venta;
	}

	@Override
	public VentaDto modificarCantidad(ProductoVentaDto producto, int cantidad,
			VentaDto venta) {
		Iterator<ProductoVentaDto> productos = venta.getProductos().iterator();
		boolean find = false;
		while (productos.hasNext()){
			ProductoVentaDto pv = productos.next(); 
			if (pv.getProducto().getCodigo().equals(producto.getProducto().getCodigo())){
				pv.setCantidad(cantidad);
				find = true;
			}				
		}
		if (find)
			calcularTotales(venta);
		else {
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

	@Override
	public VentaDto agregarPago(PagoDto pago, VentaDto venta) {
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
		
		calcularTotales(venta);
		return venta;
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
		calcularTotales(venta);
		return venta;
	}

	//TODO: se guarda la venta, se envia a la impresora fiscal
	public VentaDto facturar(VentaDto ventaDto) {
		calcularTotales(ventaDto);
		if (!ventaDto.isPagado()){
			throw new BusinessException("error.ventaServicio.facturar", "No se puede facturar la venta ya que no se pago el total");
		}
		guardarVenta(ventaDto);
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
	
	private void guardarVenta(VentaDto ventaDto){
		Venta venta = getVentaFromVentaDto(ventaDto);
		try {
			//Actualizacion de stock
			Iterator<ProductoVenta> productos = venta.getProductos().iterator();
			while (productos.hasNext()) {
				actualizarStock(productos.next());
			}
			ventaDao.save(venta);
		} catch (Exception e) {
			throw new BusinessException("error.persistence", "Error en capa de persistencia de  cliente", e);
		}
		
	}
	
	private void actualizarStock(ProductoVenta productoVenta){
		Producto producto = null; 
		try {
			producto = productoDao.get(productoVenta.getProducto().getId());
		} catch (Exception e) {
			throw new BusinessException("error.persistence", "Error en capa de persistencia de  cliente", e);
		}
		int stock = producto.getStock();
		stock = stock - productoVenta.getCantidad();
		//se modifica el stock del producto
		producto.setStock(stock);
		
		//se generan los movimientos
		Movimiento movimiento = new Movimiento();
		movimiento.setCantidad(productoVenta.getCantidad());
		movimiento.setFecha(new Date());
		movimiento.setTipo(Movimiento.VENTA);
		producto.addMovimientos(movimiento);
		try {
			productoDao.save(producto);
		} catch (Exception e) {
			throw new BusinessException("error.persistence", "Error en capa de persistencia de  cliente", e);
		}

		
	}

	private Venta getVentaFromVentaDto(VentaDto ventaDto){
		
		calcularTotales(ventaDto);
		
		Venta venta = new Venta();
		if (Validator.isNotNull(ventaDto.getCliente()))
			try {
				venta.setCliente(clienteDao.get(ventaDto.getCliente().getId()));
			} catch (Exception e) {
				throw new BusinessException("error.persistence", "Error en capa de persistencia de  cliente", e);
			}
		venta.setDescuento(new Long(getDescuentoTotal(ventaDto).longValue()));
		venta.setFecha(new Date());
		
		List<ProductoVenta> productos=new ArrayList<ProductoVenta>();
		List<ProductoVentaDto> productosDto = ventaDto.getProductos();
		for (int i = 0; i < productosDto.size(); i++) {
			productos.add(getProductoVentaFromProductoVentaDto(productosDto.get(i)));
		}
		venta.setProductos(productos);
		
		List<Pago> pagos = new ArrayList<Pago>();
		List<PagoDto> pagosDto = ventaDto.getPagos();
		for (int i = 0; i < pagosDto.size(); i++) {
			pagos.add(getPagoFromPagoDto(pagosDto.get(i)));
		}
		venta.setPagos(pagos);
		
		//TODO: tipo de cliente
		venta.setDiscriminacionIVA(null); 
		
		venta.setImpuesto(new Long(ventaDto.getImpuesto().longValue()));
		venta.setSubtotal(new Long(ventaDto.getSubTotal().longValue()));
		venta.setTotal(new Long(ventaDto.getTotal().longValue()));
		
		return venta;
	}

	private ProductoVenta getProductoVentaFromProductoVentaDto(ProductoVentaDto productoVentaDto){
		ProductoVenta productoVenta = new ProductoVenta();
		try{
			productoVenta.setProducto(productoDao.get(productoVentaDto.getProducto().getId()));
		} catch (Exception e) {
			throw new BusinessException("error.persistence", "Error en capa de persistencia de  cliente", e);
		}
		productoVenta.setCantidad(productoVentaDto.getCantidad());
		productoVenta.setPrecio(new Long(productoVentaDto.getPrecioTotal().longValue()));
		return productoVenta;
	}
	

	private Pago getPagoFromPagoDto(PagoDto pagoDto){
		Pago pago = new Pago();
		try {
			pago.setMedioPago(medioPagoDao.get(pagoDto.getMedioPago().getId()));
		} catch (Exception e) {
			throw new BusinessException("error.persistence", "Error en capa de persistencia de  cliente", e);
		}
		pago.setPago(new Long(pagoDto.getMonto().longValue()));
		return pago;
	}
	
	private BigDecimal getDescuentoTotal(VentaDto ventaDto){
		DescuentoDto descuento = ventaDto.getDescuento();
		BigDecimal monto = new BigDecimal(0);
		if (Validator.isNotNull(descuento)){
			if (descuento.getTipo() == DescuentoDto.MONTO)
				monto = descuento.getDescuento();
			else {
				BigDecimal perc = descuento.getDescuento();
				perc = perc.divide(new BigDecimal(100));
				BigDecimal subtotal = ventaDto.getSubTotal();
				monto = subtotal.multiply(perc);
			}
		}
		return monto;
	}
	
	private void calcularTotales(VentaDto ventaDto){
		
		//suma de todos los productos
		BigDecimal subTotal = new BigDecimal(0);
		BigDecimal impuestos = new BigDecimal(0);
		Iterator<ProductoVentaDto> productos = ventaDto.getProductos().iterator();
		while (productos.hasNext()) {
			ProductoVentaDto producto = productos.next();
			BigDecimal precioTotal = producto.getProducto().getPrecio().multiply(new BigDecimal(producto.getCantidad()));
			producto.setPrecioTotal(precioTotal);
			
			
			subTotal = subTotal.add(precioTotal);
			
			impuestos = impuestos.add(producto.getProducto().getIva());
		}
		
		//incluye los impuestos
		ventaDto.setSubTotal(subTotal);
		
		ventaDto.setImpuesto(impuestos); 
		
		BigDecimal descuentos = getDescuentoTotal(ventaDto);
		BigDecimal total = subTotal.subtract(descuentos); 
		
		ventaDto.setTotal(total);
		
		//suma de todos los pagos
		BigDecimal montoPagado = new BigDecimal(0);
		Iterator<PagoDto> pagos = ventaDto.getPagos().iterator();
		while (pagos.hasNext()) {
			montoPagado = montoPagado.add(pagos.next().getMonto());
		}
		
		boolean pagado = montoPagado.compareTo(total)>=0;
		ventaDto.setPagado(pagado);
		
		ventaDto.setTotalPagado(montoPagado);
		
		if (pagado) {
			ventaDto.setFaltaPagar(new BigDecimal(0));
			ventaDto.setVuelto(montoPagado.subtract(total));
		} else {
			ventaDto.setFaltaPagar(total.subtract(montoPagado));
			ventaDto.setVuelto(null);
		}

	}

	public IVentaDAO getVentaDao() {
		return ventaDao;
	}

	public void setVentaDao(IVentaDAO ventaDao) {
		this.ventaDao = ventaDao;
	}

	public IClienteDao getClienteDao() {
		return clienteDao;
	}

	public void setClienteDao(IClienteDao clienteDao) {
		this.clienteDao = clienteDao;
	}

	public IProductoDAO getProductoDao() {
		return productoDao;
	}

	public void setProductoDao(IProductoDAO productoDao) {
		this.productoDao = productoDao;
	}

	public IMedioPagoDAO getMedioPagoDao() {
		return medioPagoDao;
	}

	public void setMedioPagoDao(IMedioPagoDAO medioPagoDao) {
		this.medioPagoDao = medioPagoDao;
	}
	
}
