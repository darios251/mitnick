package com.mitnick.persistence.entities;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import org.appfuse.model.BaseObject;

import com.mitnick.utils.Validator;

@Entity(name = "Producto_Venta")
public class ProductoVenta extends BaseObject implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.AUTO) 
	private Long id;
	
	@Column(name = "descripcion", length = 255)
	private String descripcion = "";
	
	@ManyToOne
	@PrimaryKeyJoinColumn(name = "producto_id")
	private Producto producto;
	
	@Column(name = "precio", nullable = false)
	private BigDecimal precio;
	
	@Column(name = "iva", nullable = false)
	private BigDecimal iva;
	
	@Column(name = "cantidad", nullable = false)
	private int cantidad;
	
	@Column(name = "descuento", nullable = false)
	private BigDecimal descuento;

	public BigDecimal getDescuento() {
		return descuento;
	}

	public void setDescuento(BigDecimal descuento) {
		this.descuento = descuento;
	}

	@ManyToOne
	@PrimaryKeyJoinColumn(name = "venta_id")
	private Venta venta;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public BigDecimal getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public BigDecimal getIva() {
		return iva;
	}

	public void setIva(BigDecimal iva) {
		this.iva = iva;
	}

	public Venta getVenta() {
		return venta;
	}

	public void setVenta(Venta venta) {
		this.venta = venta;
	}

	public String getDescripcion() {
		if (Validator.isBlankOrNull(descripcion))
			if (Validator.isNotNull(producto))
				descripcion = producto.getDescripcion();
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cantidad;
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + ((descuento == null) ? 0 : descuento.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((iva == null) ? 0 : iva.hashCode());
		result = prime * result + ((precio == null) ? 0 : precio.hashCode());
		result = prime * result + ((producto == null) ? 0 : producto.hashCode());
		result = prime * result + ((venta == null) ? 0 : venta.hashCode());
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
		if (!(obj instanceof ProductoVenta)) {
			return false;
		}
		ProductoVenta other = (ProductoVenta) obj;
		if (cantidad != other.cantidad) {
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
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (iva == null) {
			if (other.iva != null) {
				return false;
			}
		} else if (!iva.equals(other.iva)) {
			return false;
		}
		if (precio == null) {
			if (other.precio != null) {
				return false;
			}
		} else if (!precio.equals(other.precio)) {
			return false;
		}
		if (producto == null) {
			if (other.producto != null) {
				return false;
			}
		} else if (!producto.equals(other.producto)) {
			return false;
		}
		if (venta == null) {
			if (other.venta != null) {
				return false;
			}
		} else if (!venta.equals(other.venta)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "ProductoVenta [id=" + id + ", descripcion=" + descripcion + ", producto=" + producto + ", precio=" + precio
				+ ", iva=" + iva + ", cantidad=" + cantidad + ", descuento=" + descuento + ", venta=" + venta + "]";
	}

	
	

}
