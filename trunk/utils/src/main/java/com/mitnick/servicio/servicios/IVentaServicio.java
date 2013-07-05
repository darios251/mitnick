package com.mitnick.servicio.servicios;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.security.access.annotation.Secured;

import com.mitnick.servicio.servicios.dtos.DescuentoDto;
import com.mitnick.utils.dtos.ClienteDto;
import com.mitnick.utils.dtos.CreditoDto;
import com.mitnick.utils.dtos.CuotaDto;
import com.mitnick.utils.dtos.PagoDto;
import com.mitnick.utils.dtos.ProductoVentaDto;
import com.mitnick.utils.dtos.VentaDto;

public interface IVentaServicio {

	@Secured(value={"ROLE_USER", "ROLE_ADMIN"})
	VentaDto agregarProducto(String productoCode, VentaDto venta); 
	
	@Secured(value={"ROLE_USER", "ROLE_ADMIN"})
	VentaDto quitarProducto(ProductoVentaDto producto, VentaDto venta);
	
	@Secured(value={"ROLE_USER", "ROLE_ADMIN"})
	VentaDto agregarDescuento(DescuentoDto descuento, VentaDto venta);
	
	@Secured(value={"ROLE_USER", "ROLE_ADMIN"})
	VentaDto agregarDescuento(DescuentoDto descuento, VentaDto venta, ProductoVentaDto productoVenta);
	
	@Secured(value={"ROLE_USER", "ROLE_ADMIN"})
	VentaDto quitarDescuento(VentaDto venta);
	
	@Secured(value={"ROLE_USER", "ROLE_ADMIN"})
	VentaDto quitarDescuento(VentaDto venta, ProductoVentaDto productoVenta);
	
	@Secured(value={"ROLE_USER", "ROLE_ADMIN"})
	VentaDto modificarCantidad(ProductoVentaDto producto, int cantidad, VentaDto venta);
	
	@Secured(value={"ROLE_USER", "ROLE_ADMIN"})
	VentaDto modificarPrecioUnitario(ProductoVentaDto producto, BigDecimal precioUnitario, VentaDto venta);
	
	@Secured(value={"ROLE_USER", "ROLE_ADMIN"})
	VentaDto agregarCliente(ClienteDto cliente, VentaDto venta);
	
	@Secured(value={"ROLE_USER", "ROLE_ADMIN"})
	void validarCliente(ClienteDto cliente);
	
	@Secured(value={"ROLE_USER", "ROLE_ADMIN"})
	VentaDto desagregarCliente(VentaDto venta);
	
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
	
	@Secured(value={"ROLE_USER", "ROLE_ADMIN"})
	public VentaDto getVentaByNroFactura(String nroTicket);
	
	@Secured(value={"ROLE_USER", "ROLE_ADMIN"})
	public BigDecimal getSaldoDeudorCliente(VentaDto venta);
	
	@Secured(value={"ROLE_USER", "ROLE_ADMIN"})
	public CreditoDto obtenerCredito(String nroNC);
	
	@Secured(value={"ROLE_USER", "ROLE_ADMIN"})
	public void cancelarVenta(VentaDto ventaDto);

	@Secured(value={"ROLE_USER", "ROLE_ADMIN"})
	public VentaDto getVentaByNroFacturaTipo(String numero, String tipo, int numeroCaja);
	
	@Secured(value={"ROLE_USER", "ROLE_ADMIN"})
	public void generarReporteFactura(VentaDto venta, boolean duplicado);
	
	@Secured(value={"ROLE_USER", "ROLE_ADMIN"})
	public void consultarTransaccion(String nroTrx, String tipo, String factura, int numeroCaja);
	
	@Secured(value={"ROLE_USER", "ROLE_ADMIN"})
	public void getDevolucionFromVenta(VentaDto venta, VentaDto devolucion);
	
}
