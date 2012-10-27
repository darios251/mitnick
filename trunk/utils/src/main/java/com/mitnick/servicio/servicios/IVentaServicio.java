package com.mitnick.servicio.servicios;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.security.access.annotation.Secured;

import com.mitnick.servicio.servicios.dtos.DescuentoDto;
import com.mitnick.utils.dtos.ClienteDto;
import com.mitnick.utils.dtos.CuotaDto;
import com.mitnick.utils.dtos.PagoDto;
import com.mitnick.utils.dtos.ProductoDto;
import com.mitnick.utils.dtos.ProductoVentaDto;
import com.mitnick.utils.dtos.VentaDto;

public interface IVentaServicio {

	@Secured(value={"ROLE_USER", "ROLE_ADMIN"})
	VentaDto agregarProducto(ProductoDto producto, VentaDto venta);
	
	@Secured(value={"ROLE_USER", "ROLE_ADMIN"})
	VentaDto quitarProducto(ProductoVentaDto producto, VentaDto venta);
	
	@Secured(value={"ROLE_USER", "ROLE_ADMIN"})
	VentaDto agregarDescuento(DescuentoDto descuento, VentaDto venta);
	
	@Secured(value={"ROLE_USER", "ROLE_ADMIN"})
	VentaDto quitarDescuento(VentaDto venta);
	
	@Secured(value={"ROLE_USER", "ROLE_ADMIN"})
	VentaDto modificarCantidad(ProductoVentaDto producto, int cantidad, VentaDto venta);
	
	@Secured(value={"ROLE_USER", "ROLE_ADMIN"})
	VentaDto agregarCliente(ClienteDto cliente, VentaDto venta);
	
	@Secured(value={"ROLE_USER", "ROLE_ADMIN"})
	VentaDto quitarCliente(VentaDto venta);
	
	@Secured(value={"ROLE_USER", "ROLE_ADMIN"})
	VentaDto agregarPago(PagoDto pago, VentaDto venta);
	
	@Secured(value={"ROLE_USER", "ROLE_ADMIN"})
	VentaDto quitarPago(PagoDto pago, VentaDto venta);
	
	@Secured(value={"ROLE_USER", "ROLE_ADMIN"})
	VentaDto facturar(VentaDto venta);
	
	@Secured(value={"ROLE_USER", "ROLE_ADMIN"})
	List<CuotaDto> generarCuotas(int cuotas, BigDecimal total, ClienteDto cliente);
	
	@Secured(value={"ROLE_USER", "ROLE_ADMIN"})
	void guardarCuotas(VentaDto venta, List<CuotaDto> cuotas);

	@Secured(value={"ROLE_USER", "ROLE_ADMIN"})
	void cancelar(VentaDto ventaDto);
	
}
