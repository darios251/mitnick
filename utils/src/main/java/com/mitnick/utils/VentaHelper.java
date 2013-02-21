package com.mitnick.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.mitnick.servicio.servicios.dtos.DescuentoDto;
import com.mitnick.utils.dtos.CuotaDto;
import com.mitnick.utils.dtos.PagoDto;
import com.mitnick.utils.dtos.ProductoDto;
import com.mitnick.utils.dtos.ProductoNuevoDto;
import com.mitnick.utils.dtos.ProductoVentaDto;
import com.mitnick.utils.dtos.VentaDto;

public class VentaHelper {

	public static BigDecimal getDescuentoTotal(VentaDto ventaDto) {
		DescuentoDto descuento = ventaDto.getDescuento();
		BigDecimal monto = BigDecimal.ZERO;
		if (Validator.isNotNull(descuento)) {
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

	public static List<ProductoVentaDto> getProductosDevolucion(VentaDto ventaDto) {
		List<ProductoVentaDto> productos = new ArrayList<ProductoVentaDto>();
		for(ProductoVentaDto producto : ventaDto.getProductos()) {
			producto.setId(null);
			productos.add(producto);
		}
		return productos;
	}
	
	public static void calcularTotales(VentaDto ventaDto) {

		// suma de todos los productos
		BigDecimal subTotal = BigDecimal.ZERO;
		BigDecimal impuestos = BigDecimal.ZERO;
		
		for(ProductoVentaDto producto : ventaDto.getProductos()) {
			BigDecimal precioTotal = producto.getProducto().getPrecioVentaConIva().multiply(new BigDecimal(producto.getCantidad()));
			producto.setPrecioTotal(precioTotal);
			subTotal = subTotal.add(precioTotal);
			producto.setIva(calcularImpuesto(producto));
			impuestos = impuestos.add(producto.getIva());
		}

		// incluye los impuestos
		ventaDto.setSubTotal(subTotal);
		
		ventaDto.setImpuesto(impuestos);
		
		BigDecimal descuentos = VentaHelper.getDescuentoTotal(ventaDto);
		BigDecimal total = subTotal.subtract(descuentos);
		
		ventaDto.setAjusteRedondeo(calcularAjusteRedondeo(ventaDto));

		ventaDto.setTotal(total);

		if (ventaDto.getTipo() == MitnickConstants.VENTA){
			// suma de todos los pagos
			BigDecimal montoPagado = BigDecimal.ZERO;
			Iterator<PagoDto> pagos = ventaDto.getPagos().iterator();
			while (pagos.hasNext()) {
				montoPagado = montoPagado.add(pagos.next().getMonto());
			}

			boolean pagado = montoPagado.compareTo(total) >= 0;
			ventaDto.setPagado(pagado);

			ventaDto.setTotalPagado(montoPagado);

			if (pagado) {
				ventaDto.setFaltaPagar(BigDecimal.ZERO);
				ventaDto.setVuelto(montoPagado.subtract(total));
			} else {
				ventaDto.setFaltaPagar(total.subtract(montoPagado));
				ventaDto.setVuelto(null);
			}			
		}

	}

	private static BigDecimal calcularAjusteRedondeo(VentaDto ventaDto) {
		BigDecimal subTotal = new BigDecimal(0); 
		
		for(ProductoVentaDto producto : ventaDto.getProductos()) {
			subTotal = subTotal.add(calcularPrecioSinIva(producto.getPrecioTotal()));
			subTotal = subTotal.add(calcularImpuesto(producto.getPrecioTotal()));
		}
		
		BigDecimal result = ventaDto.getSubTotal();
		
		if(ventaDto.getDescuento() != null) 
			result = result.subtract(ventaDto.getDescuento().getDescuento());
		return result.subtract(subTotal);
	}

	public static BigDecimal calcularImpuesto(BigDecimal precioProducto) {
		BigDecimal impuesto = BigDecimal.ZERO;
		BigDecimal iva = BigDecimal.ZERO;
		String ivaString = PropertiesManager.getProperty("applicationConfiguration.impuesto.porcentaje");
		if (!Validator.isBlankOrNull(ivaString)) {
			impuesto = new BigDecimal(ivaString).setScale(2, BigDecimal.ROUND_HALF_UP).divide(new BigDecimal(100));
			BigDecimal precio = precioProducto.setScale(2, BigDecimal.ROUND_HALF_UP).divide(BigDecimal.ONE.add(impuesto),2, RoundingMode.HALF_UP);
			iva = precio.multiply(impuesto).setScale(2, BigDecimal.ROUND_HALF_UP);
		}
		return iva;
	}
	
	public static BigDecimal calcularPrecioSinIva(BigDecimal precioProducto) {
		BigDecimal impuesto = BigDecimal.ZERO;
//		BigDecimal iva = BigDecimal.ZERO;
		BigDecimal precio = BigDecimal.ZERO;
		String ivaString = PropertiesManager.getProperty("applicationConfiguration.impuesto.porcentaje");
		if (!Validator.isBlankOrNull(ivaString)) {
			impuesto = new BigDecimal(ivaString).setScale(2, BigDecimal.ROUND_HALF_UP).divide(new BigDecimal(100));
			precio = precioProducto.setScale(2, BigDecimal.ROUND_HALF_UP).divide(BigDecimal.ONE.add(impuesto),2, RoundingMode.HALF_UP);
//			iva = precio.multiply(impuesto).setScale(2, BigDecimal.ROUND_HALF_UP);
		}
		return precio;
	}

	
	public static BigDecimal calcularImpuesto(ProductoNuevoDto productoDto) {
		return calcularImpuesto(new BigDecimal(productoDto.getPrecioVenta()));
	}
	
	public static BigDecimal calcularImpuesto(ProductoDto productoDto) {
		return calcularImpuesto(productoDto.getPrecioVenta());
	}
	
	public static BigDecimal calcularImpuesto(ProductoVentaDto productoDto) {
		return calcularImpuesto(productoDto.getPrecioTotal());
	}
	
	public static void calcularTotales(CuotaDto cuotaDto) {
		// suma de todos los pagos
		BigDecimal montoPagado = BigDecimal.ZERO;
		Iterator<PagoDto> pagos = cuotaDto.getPagos().iterator();
		while (pagos.hasNext()) {
			montoPagado = montoPagado.add(pagos.next().getMonto());
		}

		BigDecimal total = cuotaDto.getTotal();
		boolean pagado = montoPagado.compareTo(total) >= 0;
		cuotaDto.setPagado(pagado);

		cuotaDto.setTotalPagado(montoPagado);

		if (pagado) {
			cuotaDto.setFaltaPagar(BigDecimal.ZERO);
		} else {
			cuotaDto.setFaltaPagar(total.subtract(montoPagado));
		}
	}
}
