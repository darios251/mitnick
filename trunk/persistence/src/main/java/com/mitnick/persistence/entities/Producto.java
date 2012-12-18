package com.mitnick.persistence.entities;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import org.appfuse.model.BaseObject;

@Entity(name = "Producto")
public class Producto extends BaseObject implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.AUTO) 
	private Long id;
	
	@Column(name = "descripcion", length = 255)
	private String descripcion;
	
	@Column(name = "codigo", length = 255)
	private String codigo;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn(name = "marca_id")
	private Marca marca;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn(name = "tipo_id")
	private Tipo tipo;
	
	@Column(name = "stock")
	private int stock;
	
	@Column(name = "stockMinimo")
	private int stockMinimo = -1;
	
	@Column(name = "stockCompra")
	private int stockCompra = -1;
	
	@Column(name = "precioVenta")
	private BigDecimal precioVenta;
	
	@Column(name = "precioCompra")
	private BigDecimal precioCompra;

	@Column(name = "iva")
	private BigDecimal iva;
	
	@Column(name = "eliminado", nullable = false)
	private boolean eliminado = false;
	
	@ManyToOne
	@PrimaryKeyJoinColumn(name = "proveedor_id")
	private Proveedor proveedor;

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Marca getMarca() {
		return marca;
	}

	public void setMarca(Marca marca) {
		this.marca = marca;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
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

	public BigDecimal getIva() {
		return iva;
	}

	public void setIva(BigDecimal iva) {
		this.iva = iva;
	}

	public boolean isEliminado() {
		return eliminado;
	}

	public void setEliminado(boolean eliminado) {
		this.eliminado = eliminado;
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public Long getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result
				+ ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + (eliminado ? 1231 : 1237);
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((iva == null) ? 0 : iva.hashCode());
		result = prime * result + ((marca == null) ? 0 : marca.hashCode());
		result = prime * result
				+ ((precioCompra == null) ? 0 : precioCompra.hashCode());
		result = prime * result
				+ ((precioVenta == null) ? 0 : precioVenta.hashCode());
		result = prime * result
				+ ((proveedor == null) ? 0 : proveedor.hashCode());
		result = prime * result + stock;
		result = prime * result + stockCompra;
		result = prime * result + stockMinimo;
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Producto other = (Producto) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (eliminado != other.eliminado)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (iva == null) {
			if (other.iva != null)
				return false;
		} else if (!iva.equals(other.iva))
			return false;
		if (marca == null) {
			if (other.marca != null)
				return false;
		} else if (!marca.equals(other.marca))
			return false;
		if (precioCompra == null) {
			if (other.precioCompra != null)
				return false;
		} else if (!precioCompra.equals(other.precioCompra))
			return false;
		if (precioVenta == null) {
			if (other.precioVenta != null)
				return false;
		} else if (!precioVenta.equals(other.precioVenta))
			return false;
		if (proveedor == null) {
			if (other.proveedor != null)
				return false;
		} else if (!proveedor.equals(other.proveedor))
			return false;
		if (stock != other.stock)
			return false;
		if (stockCompra != other.stockCompra)
			return false;
		if (stockMinimo != other.stockMinimo)
			return false;
		if (tipo == null) {
			if (other.tipo != null)
				return false;
		} else if (!tipo.equals(other.tipo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Producto [id=" + id + ", descripcion=" + descripcion
				+ ", codigo=" + codigo + ", marca=" + marca + ", tipo=" + tipo
				+ ", stock=" + stock + ", stockMinimo=" + stockMinimo
				+ ", stockCompra=" + stockCompra + ", precioVenta="
				+ precioVenta + ", precioCompra=" + precioCompra + ", iva="
				+ iva + ", eliminado=" + eliminado + ", proveedor=" + proveedor
				+ "]";
	}
	
}
