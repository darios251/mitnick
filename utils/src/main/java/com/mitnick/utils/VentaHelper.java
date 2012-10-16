package com.mitnick.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;

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

	public static BigDecimal CalcularImpuesto(BigDecimal precioProducto) {
		BigDecimal impuesto = new BigDecimal(0);
		BigDecimal iva = new BigDecimal(0);
		String ivaString = PropertiesManager.getProperty("applicationConfiguration.impuesto.porcentaje");
		if (!Validator.isBlankOrNull(ivaString)) {
			impuesto = new BigDecimal(ivaString).setScale(2, BigDecimal.ROUND_HALF_UP).divide(new BigDecimal(100));
			BigDecimal p1 = precioProducto.setScale(2, BigDecimal.ROUND_HALF_UP);
			BigDecimal p2 = BigDecimal.ONE.add(impuesto);
			BigDecimal p3 = p1.divide(p2,2, RoundingMode.HALF_UP);
			BigDecimal precio = p3;
			iva = precio.multiply(impuesto).setScale(2, BigDecimal.ROUND_HALF_UP);
			
		}
		return iva;
	}

	
	public static BigDecimal CalcularImpuesto(ProductoNuevoDto productoDto) {
		return CalcularImpuesto(new BigDecimal(productoDto.getPrecioVenta()));
	}
	
	public static BigDecimal CalcularImpuesto(ProductoDto productoDto) {
		return CalcularImpuesto(productoDto.getPrecioVenta());
	}
	
	public static BigDecimal CalcularImpuesto(ProductoVentaDto productoDto) {
		return CalcularImpuesto(productoDto.getPrecioTotal());
	}
	
	public static void calcularTotales(CuotaDto cuotaDto) {
		// suma de todos los pagos
		BigDecimal montoPagado = new BigDecimal(0);
		Iterator<PagoDto> pagos = cuotaDto.getPagos().iterator();
		while (pagos.hasNext()) {
			montoPagado = montoPagado.add(pagos.next().getMonto());
		}

		BigDecimal total = cuotaDto.getTotal();
		boolean pagado = montoPagado.compareTo(total) >= 0;
		cuotaDto.setPagado(pagado);

		cuotaDto.setTotalPagado(montoPagado);

		if (pagado) {
			cuotaDto.setFaltaPagar(new BigDecimal(0));
		} else {
			cuotaDto.setFaltaPagar(total.subtract(montoPagado));
		}
	}
}
