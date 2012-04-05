package com.mitnick.business.services.dummies;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.mitnick.business.exceptions.BusinessException;
import com.mitnick.business.services.ServicioBase;
import com.mitnick.servicio.servicios.IVentaServicio;
import com.mitnick.servicio.servicios.dtos.DescuentoDto;
import com.mitnick.utils.dtos.ClienteDto;
import com.mitnick.utils.dtos.PagoDto;
import com.mitnick.utils.dtos.ProductoDto;
import com.mitnick.utils.dtos.ProductoVentaDto;
import com.mitnick.utils.dtos.VentaDto;

@Service("ventaServicio")
public class VentaServicioDummy extends ServicioBase implements IVentaServicio {

	@Override
	public void guardarVenta(VentaDto venta) {

	}

	@Override
	public VentaDto agregarProducto(ProductoDto producto, VentaDto venta) {
		ProductoVentaDto productoVenta = new ProductoVentaDto();
		productoVenta.setCantidad(1);
		productoVenta.setPrecioTotal(new BigDecimal(100));
		productoVenta.setProducto(producto);
		venta.getProductos().add(productoVenta);
		venta.setSubTotal(new BigDecimal(100));
		venta.setTotal(new BigDecimal(100));
		venta.setFaltaPagar(new BigDecimal(100));
		venta.setTotalPagado(new BigDecimal(0));
		return venta;
	}

	@Override
	public VentaDto quitarProducto(ProductoVentaDto producto, VentaDto venta) {
		boolean eliminado = venta.getProductos().remove(producto);
		
		if(!eliminado)
			throw new BusinessException("Error al eliminar el producto. Consulte con el administrador");
		return venta;
	}

	@Override
	public VentaDto agregarDescuento(DescuentoDto descuento, VentaDto venta) {
		venta.setDescuento(descuento.getDescuento());
		return venta;
	}

	@Override
	public VentaDto quitarDescuento(VentaDto venta) {
		venta.setDescuento(new BigDecimal(0));
		return venta;
	}

	@Override
	public VentaDto modificarCantidad(ProductoVentaDto producto, int cantidad, VentaDto venta) {
		venta.getProductos().get(venta.getProductos().indexOf(producto)).setCantidad(cantidad);
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
		venta.getPagos().add(pago);
		if(pago.getMonto().compareTo(venta.getFaltaPagar()) >= 0)
			venta.setPagado(true);
		venta.setTotalPagado(pago.getMonto());
		return venta;
	}

	@Override
	public VentaDto quitarPago(PagoDto pago, VentaDto venta) {
		venta.getPagos().remove(pago);
		venta.setTotalPagado(venta.getTotalPagado().subtract(pago.getMonto()));
		venta.setFaltaPagar(venta.getFaltaPagar().add(pago.getMonto()));
		return venta;
	}

	@Override
	public void facturar(VentaDto venta) {

	}

}
