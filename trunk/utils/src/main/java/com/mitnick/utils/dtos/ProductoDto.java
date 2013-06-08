package com.mitnick.utils.dtos;

import java.math.BigDecimal;

import com.mitnick.utils.VentaHelper;

public class ProductoDto extends BaseDto {

	private static final long serialVersionUID = 1L;

	private String codigo;
	
	private String descripcion;
	
	private TipoDto tipo;
	
	private String talle;
	
	private MarcaDto marca;
	
	private BigDecimal precioVenta;
	
	private BigDecimal precioCompra;
	
	private BigDecimal descuento;
	
	private int stock;
	
	private int stockMinimo=-1;
	
	private int stockCompra;
	
	private ProveedorDto proveedor;
	
	private BigDecimal iva;

	public BigDecimal getIva() {
		return iva;
	}

	public void setIva(BigDecimal iva) {
		this.iva = iva;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public TipoDto getTipo() {
		return tipo;
	}

	public void setTipo(TipoDto tipo) {
		this.tipo = tipo;
	}

	public String getTalle() {
		return talle;
	}

	public void setTalle(String talle) {
		this.talle = talle;
	}

	public MarcaDto getMarca() {
		return marca;
	}

	public void setMarca(MarcaDto marca) {
		this.marca = marca;
	}

	public BigDecimal getPrecioVenta() {
		return precioVenta;
	}

	public void setPrecioVenta(BigDecimal precioVenta) {
		this.precioVenta = precioVenta;
	}

	public BigDecimal getPrecioCompra() {
		return precioCompra;
	}

	public void setPrecioCompra(BigDecimal precioCompra) {
		this.precioCompra = precioCompra;
	}

	public BigDecimal getDescuento() {
		return descuento;
	}

	public void setDescuento(BigDecimal descuento) {
		this.descuento = descuento;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
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
	
	public void setProveedor(ProveedorDto proveedorDto) {
		this.proveedor = proveedorDto;
	}
	
	public ProveedorDto getProveedor() {
		return proveedor;
	}
	
	public BigDecimal getPrecioVentaConIva() {
		return VentaHelper.calcularPrecioFinal(precioVenta);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + ((descuento == null) ? 0 : descuento.hashCode());
		result = prime * result + ((marca == null) ? 0 : marca.hashCode());
		result = prime * result + ((precioCompra == null) ? 0 : precioCompra.hashCode());
		result = prime * result + ((precioVenta == null) ? 0 : precioVenta.hashCode());
		result = prime * result + ((proveedor == null) ? 0 : proveedor.hashCode());
		result = prime * result + stock;
		result = prime * result + stockCompra;
		result = prime * result + stockMinimo;
		result = prime * result + ((talle == null) ? 0 : talle.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
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
		if (!(obj instanceof ProductoDto)) {
			return false;
		}
		ProductoDto other = (ProductoDto) obj;
		if (codigo == null) {
			if (other.codigo != null) {
				return false;
			}
		} else if (!codigo.equals(other.codigo)) {
			return false;
		}
		if (descripcion == null) {
			if (other.descripcion != null) {
				return false;
			}
		} else if (!descripcion.equals(other.descripcion)) {
			return false;
		}
		if (descuento == null) {
			if (other.descuento != null) {
				return false;
			}
		} else if (!descuento.equals(other.descuento)) {
			return false;
		}
		if (marca == null) {
			if (other.marca != null) {
				return false;
			}
		} else if (!marca.equals(other.marca)) {
			return false;
		}
		if (precioCompra == null) {
			if (other.precioCompra != null) {
				return false;
			}
		} else if (!precioCompra.equals(other.precioCompra)) {
			return false;
		}
		if (precioVenta == null) {
			if (other.precioVenta != null) {
				return false;
			}
		} else if (!precioVenta.equals(other.precioVenta)) {
			return false;
		}
		if (proveedor == null) {
			if (other.proveedor != null) {
				return false;
			}
		} else if (!proveedor.equals(other.proveedor)) {
			return false;
		}
		if (stock != other.stock) {
			return false;
		}
		if (stockCompra != other.stockCompra) {
			return false;
		}
		if (stockMinimo != other.stockMinimo) {
			return false;
		}
		if (talle == null) {
			if (other.talle != null) {
				return false;
			}
		} else if (!talle.equals(other.talle)) {
			return false;
		}
		if (tipo == null) {
			if (other.tipo != null) {
				return false;
			}
		} else if (!tipo.equals(other.tipo)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "ProductoDto [codigo=" + codigo + ", descripcion=" + descripcion + ", tipo=" + tipo + ", talle=" + talle
				+ ", marca=" + marca + ", precioVenta=" + precioVenta + ", precioCompra=" + precioCompra + ", descuento="
				+ descuento + ", stock=" + stock + ", stockMinimo=" + stockMinimo + ", stockCompra=" + stockCompra
				+ ", proveedor=" + proveedor + "]";
	}

}
