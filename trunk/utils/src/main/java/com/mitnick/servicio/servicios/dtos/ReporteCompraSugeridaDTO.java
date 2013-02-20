package com.mitnick.servicio.servicios.dtos;

public class ReporteCompraSugeridaDTO extends ServicioBaseDto {
	
	private static final long serialVersionUID = 1L;

	private String productoCodigo;
	private String productoDescripcion;
	private int stockActual;
	private int stockMinimo;
	private int stockCompra;
	private int compraSugerida;
	public String getProductoCodigo() {
		return productoCodigo;
	}
	public void setProductoCodigo(String productoCodigo) {
		this.productoCodigo = productoCodigo;
	}
	public String getProductoDescripcion() {
		return productoDescripcion;
	}
	public void setProductoDescripcion(String productoDescripcion) {
		this.productoDescripcion = productoDescripcion;
	}
	public int getStockActual() {
		return stockActual;
	}
	public void setStockActual(int stockActual) {
		this.stockActual = stockActual;
	}
	public int getStockMinimo() {
		return stockMinimo;
	}
	public void setStockMinimo(int stockMinimo) {
		this.stockMinimo = stockMinimo;
	}
	public int getStockCompra() {
		return stockCompra;
	}
	public void setStockCompra(int stockCompra) {
		this.stockCompra = stockCompra;
	}
	public int getCompraSugerida() {
		return compraSugerida;
	}
	public void setCompraSugerida(int compraSugerida) {
		this.compraSugerida = compraSugerida;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + compraSugerida;
		result = prime * result
				+ ((productoCodigo == null) ? 0 : productoCodigo.hashCode());
		result = prime
				* result
				+ ((productoDescripcion == null) ? 0 : productoDescripcion
						.hashCode());
		result = prime * result + stockActual;
		result = prime * result + stockCompra;
		result = prime * result + stockMinimo;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof ReporteCompraSugeridaDTO)) {
			return false;
		}
		ReporteCompraSugeridaDTO other = (ReporteCompraSugeridaDTO) obj;
		if (compraSugerida != other.compraSugerida) {
			return false;
		}
		if (productoCodigo == null) {
			if (other.productoCodigo != null) {
				return false;
			}
		} else if (!productoCodigo.equals(other.productoCodigo)) {
			return false;
		}
		if (productoDescripcion == null) {
			if (other.productoDescripcion != null) {
				return false;
			}
		} else if (!productoDescripcion.equals(other.productoDescripcion)) {
			return false;
		}
		if (stockActual != other.stockActual) {
			return false;
		}
		if (stockCompra != other.stockCompra) {
			return false;
		}
		if (stockMinimo != other.stockMinimo) {
			return false;
		}
		return true;
	}
	@Override
	public String toString() {
		return "ReporteCompraSugeridaDTO [productoCodigo=" + productoCodigo
				+ ", productoDescripcion=" + productoDescripcion
				+ ", stockActual=" + stockActual + ", stockMinimo="
				+ stockMinimo + ", stockCompra=" + stockCompra
				+ ", compraSugerida=" + compraSugerida + "]";
	}

	

}
