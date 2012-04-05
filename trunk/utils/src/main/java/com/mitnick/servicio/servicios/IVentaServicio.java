package com.mitnick.servicio.servicios;

import com.mitnick.servicio.servicios.dtos.DescuentoDto;
import com.mitnick.utils.dtos.ClienteDto;
import com.mitnick.utils.dtos.PagoDto;
import com.mitnick.utils.dtos.ProductoDto;
import com.mitnick.utils.dtos.ProductoVentaDto;
import com.mitnick.utils.dtos.VentaDto;

public interface IVentaServicio {

	VentaDto agregarProducto(ProductoDto producto, VentaDto venta);
	
	VentaDto quitarProducto(ProductoVentaDto producto, VentaDto venta);
	
	VentaDto agregarDescuento(DescuentoDto descuento, VentaDto venta);
	
	VentaDto quitarDescuento(VentaDto venta);
	
	VentaDto modificarCantidad(ProductoVentaDto producto, int cantidad, VentaDto venta);
	
	VentaDto agregarCliente(ClienteDto cliente, VentaDto venta);
	
	VentaDto quitarCliente(VentaDto venta);
	
	VentaDto agregarPago(PagoDto pago, VentaDto venta);
	
	VentaDto quitarPago(PagoDto pago, VentaDto venta);
	
	VentaDto facturar(VentaDto venta);
}
