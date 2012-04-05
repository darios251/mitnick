package com.mitnick.business.servicios;

import com.mitnick.business.services.ServicioBase;
import com.mitnick.servicio.servicios.IVentaServicio;
import com.mitnick.servicio.servicios.dtos.DescuentoDto;
import com.mitnick.utils.dtos.ClienteDto;
import com.mitnick.utils.dtos.PagoDto;
import com.mitnick.utils.dtos.ProductoDto;
import com.mitnick.utils.dtos.ProductoVentaDto;
import com.mitnick.utils.dtos.VentaDto;

public class VentaServicio extends ServicioBase implements IVentaServicio {

	@Override
	public void guardarVenta(VentaDto venta) {
		// TODO Auto-generated method stub

	}

	@Override
	public VentaDto agregarProducto(ProductoDto producto, VentaDto venta) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VentaDto quitarProducto(ProductoVentaDto producto, VentaDto venta) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VentaDto agregarDescuento(DescuentoDto descuento, VentaDto venta) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VentaDto quitarDescuento(VentaDto venta) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VentaDto modificarCantidad(ProductoVentaDto producto, int cantidad,
			VentaDto venta) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VentaDto agregarCliente(ClienteDto cliente, VentaDto venta) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VentaDto quitarCliente(VentaDto venta) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VentaDto agregarPago(PagoDto pago, VentaDto venta) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VentaDto quitarPago(PagoDto pago, VentaDto venta) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void facturar(VentaDto venta) {
		// TODO Auto-generated method stub

	}

}
