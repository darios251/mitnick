package com.mitnick.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.mitnick.servicio.servicios.dtos.DescuentoDto;
import com.mitnick.utils.dtos.CuotaDto;
import com.mitnick.utils.dtos.PagoDto;
import com.mitnick.utils.dtos.ProductoVentaDto;
import com.mitnick.utils.dtos.VentaDto;

public class VentaHelper {

	public static BigDecimal getDescuentoTotal(VentaDto ventaDto) {
		DescuentoDto descuento = ventaDto.getDescuento();
		BigDecimal monto = BigDecimal.ZERO;
		if (Validator.isNotNull(descuento)) {
			return descuento.getDescuento();
		}
		return monto.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public static BigDecimal getDescuentoTotal(List<ProductoVentaDto> productos) {
		BigDecimal monto = BigDecimal.ZERO;
		for (ProductoVentaDto producto : productos){
			if (Validator.isNotNull(producto.getDescuento())){
				DescuentoDto descuento = producto.getDescuento();
				monto = monto.add(descuento.getDescuento());
			}
		}
		return monto.setScale(2, BigDecimal.ROUND_HALF_UP);
	}
	
	public static BigDecimal getDescuentoTotal(ProductoVentaDto productoVentaDto) {
		DescuentoDto descuento = productoVentaDto.getDescuento();
		BigDecimal monto = BigDecimal.ZERO;
		if (Validator.isNotNull(descuento)) {
			return descuento.getDescuento();
		}
		return monto.setScale(2, BigDecimal.ROUND_HALF_UP);

	}
	
	public static List<ProductoVentaDto> getProductosPrecioVendido(VentaDto ventaDto) {
		List<ProductoVentaDto> productos = new ArrayList<ProductoVentaDto>();
		for(ProductoVentaDto productoVenta : ventaDto.getProductos()) {
			productoVenta.setId(null);
			BigDecimal precioCantidad = productoVenta.getProducto().getPrecioVenta().multiply(new BigDecimal(productoVenta.getCantidad()));
			BigDecimal precioTotal = calcularPrecioFinal(precioCantidad);
			//si se modificó el precio del producto en la venta original
			if (!productoVenta.getPrecioTotal().equals(precioTotal)){
				BigDecimal precioTotalSinIva = calcularPrecioSinIva(productoVenta.getPrecioTotal()) ;
				BigDecimal precioUnitarioSinIva = precioTotalSinIva.divide(new BigDecimal(productoVenta.getCantidad()),2, RoundingMode.HALF_UP);
				productoVenta.getProducto().setPrecioVenta(precioUnitarioSinIva);
				BigDecimal precioUnitarioConIva = calcularPrecioFinal(precioUnitarioSinIva);
				productoVenta.getProducto().setIva(precioUnitarioConIva.subtract(precioUnitarioSinIva));
			}
			productos.add(productoVenta);
		}
		return productos;
	}
	
	/**
	 * la impresora fiscal calcula de este modo:
	 * precioprod * 1.21
	 * suma de todos los precios prod
	 * @param ventaDto
	 */
	private static void calcularTotalesB(VentaDto ventaDto) {
		BigDecimal subTotal = BigDecimal.ZERO;
		BigDecimal impuestos = BigDecimal.ZERO;
		BigDecimal total = BigDecimal.ZERO;
		
		for(ProductoVentaDto producto : ventaDto.getProductos()) {
			BigDecimal precioCantidad = producto.getProducto().getPrecioVenta().multiply(new BigDecimal(producto.getCantidad()));
			BigDecimal precioFinal = calcularPrecioFinal(precioCantidad);
			BigDecimal iva = precioFinal.subtract(precioCantidad);
			producto.setPrecioTotal(precioFinal);
			subTotal = subTotal.add(precioFinal);
			producto.setIva(iva);
			impuestos = impuestos.add(producto.getIva());
		}
		total = subTotal;
		
		// se incluyen los impuestos
		ventaDto.setSubTotal(subTotal);
		ventaDto.setImpuesto(impuestos);
		// se incluyen los impuestos
		ventaDto.setTotal(total);
		ventaDto.setAjusteRedondeo(new BigDecimal(0));
	}

	/**
	 * la impresora fiscal calcula de este modo:
	 * suma de todos los precios prod 
	 * suma total obtenida * 1.21
	 * @param ventaDto
	 */
	private static void calcularTotalesA(VentaDto ventaDto) {
		BigDecimal subTotal = BigDecimal.ZERO;
		BigDecimal impuestos = BigDecimal.ZERO;
		BigDecimal total = BigDecimal.ZERO;
		
		for(ProductoVentaDto producto : ventaDto.getProductos()) {
			BigDecimal precioCantidad = producto.getProducto().getPrecioVenta().multiply(new BigDecimal(producto.getCantidad()));
			
			subTotal = subTotal.add(precioCantidad);
			
			BigDecimal precioFinal = calcularPrecioFinal(precioCantidad);
			BigDecimal iva = precioFinal.subtract(precioCantidad);
			producto.setPrecioTotal(precioFinal);
			
			producto.setIva(iva);
			impuestos = impuestos.add(producto.getIva());
		}
		
		
		// se incluyen los impuestos
		subTotal = calcularPrecioFinal(subTotal);
		total = subTotal;
		
		ventaDto.setSubTotal(subTotal);
		ventaDto.setImpuesto(impuestos);
		// se incluyen los impuestos
		ventaDto.setTotal(total);
		ventaDto.setAjusteRedondeo(new BigDecimal(0));
	}

	public static void calcularTotales(VentaDto ventaDto) {

		String tipoFactura = PropertiesManager.getProperty("application.tipoComprador.consumidorFinal");
		if (!Validator.isBlankOrNull(tipoFactura))
			tipoFactura = "F";
		if (Validator.isNotNull(ventaDto.getTipoResponsabilidad()) && !tipoFactura.equals(ventaDto.getTipoResponsabilidad().getTipoComprador()))
			calcularTotalesA(ventaDto);
		else
			calcularTotalesB(ventaDto);
		
		// suma de todos los pagos
		if (ventaDto.isVenta()){
			BigDecimal montoPagado = BigDecimal.ZERO;
			Iterator<PagoDto> pagos = ventaDto.getPagos().iterator();
			while (pagos.hasNext()) {
				montoPagado = montoPagado.add(pagos.next().getMonto());
			}

			boolean pagado = montoPagado.compareTo(ventaDto.getTotal()) >= 0;
			ventaDto.setPagado(pagado);

			ventaDto.setTotalPagado(montoPagado);

			if (pagado) {
				ventaDto.setFaltaPagar(BigDecimal.ZERO);
				ventaDto.setVuelto(montoPagado.subtract(ventaDto.getTotal()));
			} else {
				ventaDto.setFaltaPagar(ventaDto.getTotal().subtract(montoPagado));
				ventaDto.setVuelto(null);
			}			
		}

	}
	
	/**
	 * El calculo del precio final se hace sobre el precio base del producto sin iva.
	 * @param precioProducto
	 * @return
	 */
	public static BigDecimal calcularPrecioFinal(BigDecimal precioProducto) {
		BigDecimal impuesto = BigDecimal.ZERO;
		String ivaString = PropertiesManager.getProperty("applicationConfiguration.impuesto.porcentaje");
		BigDecimal precio = precioProducto; 
		if (Validator.isNotBlankOrNull(ivaString)){
			impuesto = new BigDecimal(ivaString).setScale(2, BigDecimal.ROUND_HALF_UP).divide(new BigDecimal(100));
			precio = precioProducto.setScale(2, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.ONE.add(impuesto));
		}
		
		return precio.setScale(2, BigDecimal.ROUND_HALF_UP);
	}
	
	/**
	 * El cálculo se hace sobre el precio final.
	 * @param precioProducto
	 * @return
	 */
	public static BigDecimal calcularPrecioSinIva(BigDecimal precioProducto) {
		BigDecimal impuesto = BigDecimal.ZERO;
		BigDecimal precio = BigDecimal.ZERO;
		String ivaString = PropertiesManager.getProperty("applicationConfiguration.impuesto.porcentaje");
		if (Validator.isNotBlankOrNull(ivaString)) {
			impuesto = new BigDecimal(ivaString).setScale(2, BigDecimal.ROUND_HALF_UP).divide(new BigDecimal(100));
			precio = precioProducto.setScale(2, BigDecimal.ROUND_HALF_UP).divide(BigDecimal.ONE.add(impuesto),2, RoundingMode.HALF_UP);
		}
		return precio;
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
