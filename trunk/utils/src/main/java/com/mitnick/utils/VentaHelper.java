package com.mitnick.utils;

import java.math.BigDecimal;
import java.util.Iterator;

import com.mitnick.servicio.servicios.dtos.DescuentoDto;
import com.mitnick.utils.dtos.PagoDto;
import com.mitnick.utils.dtos.ProductoDto;
import com.mitnick.utils.dtos.ProductoNuevoDto;
import com.mitnick.utils.dtos.ProductoVentaDto;
import com.mitnick.utils.dtos.VentaDto;

public class VentaHelper {

	public static BigDecimal getDescuentoTotal(VentaDto ventaDto) {
		DescuentoDto descuento = ventaDto.getDescuento();
		BigDecimal monto = new BigDecimal(0);
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

	public static void calcularTotales(VentaDto ventaDto) {

		// suma de todos los productos
		BigDecimal subTotal = new BigDecimal(0);
		BigDecimal impuestos = new BigDecimal(0);
		Iterator<ProductoVentaDto> productos = ventaDto.getProductos()
				.iterator();
		while (productos.hasNext()) {
			ProductoVentaDto producto = productos.next();
			BigDecimal precioTotal = producto.getProducto().getPrecioVenta().multiply(new BigDecimal(producto.getCantidad()));
			producto.setPrecioTotal(precioTotal);
			subTotal = subTotal.add(precioTotal);
			producto.setIva(CalcularImpuesto(producto));
			impuestos = impuestos.add(producto.getIva());
		}

		// incluye los impuestos
		ventaDto.setSubTotal(subTotal);

		ventaDto.setImpuesto(impuestos);

		BigDecimal descuentos = VentaHelper.getDescuentoTotal(ventaDto);
		BigDecimal total = subTotal.subtract(descuentos);

		ventaDto.setTotal(total);

		// suma de todos los pagos
		BigDecimal montoPagado = new BigDecimal(0);
		Iterator<PagoDto> pagos = ventaDto.getPagos().iterator();
		while (pagos.hasNext()) {
			montoPagado = montoPagado.add(pagos.next().getMonto());
		}

		boolean pagado = montoPagado.compareTo(total) >= 0;
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

	public static BigDecimal CalcularImpuesto(ProductoNuevoDto productoDto) {
		BigDecimal impuesto = new BigDecimal(0);
		String ivaString = PropertiesManager.getProperty("applicationConfiguration.impuesto.porcentaje");
		if (!Validator.isBlankOrNull(ivaString)) {
			BigDecimal iva = new BigDecimal(ivaString).divide(new BigDecimal(100));
			impuesto = new BigDecimal(productoDto.getPrecioVenta()).multiply(iva);
		}
		return impuesto;
	}
	
	public static BigDecimal CalcularImpuesto(ProductoDto productoDto) {
		BigDecimal impuesto = new BigDecimal(0);
		String ivaString = PropertiesManager
				.getProperty("applicationConfiguration.impuesto.porcentaje");
		if (!Validator.isBlankOrNull(ivaString)) {
			BigDecimal iva = new BigDecimal(ivaString).divide(new BigDecimal(100));
			impuesto = productoDto.getPrecioVenta().multiply(iva);
		}
		return impuesto;
	}
	
	public static BigDecimal CalcularImpuesto(ProductoVentaDto productoDto) {
		BigDecimal impuesto = new BigDecimal(0);
		String ivaString = PropertiesManager
				.getProperty("applicationConfiguration.impuesto.porcentaje");
		if (!Validator.isBlankOrNull(ivaString)) {
			BigDecimal iva = new BigDecimal(ivaString).divide(new BigDecimal(
					100));
			impuesto = productoDto.getPrecioTotal().multiply(iva);
		}
		return impuesto;
	}
}
