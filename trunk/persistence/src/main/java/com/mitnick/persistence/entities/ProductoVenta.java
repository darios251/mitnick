package com.mitnick.persistence.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import org.appfuse.model.BaseObject;

@Entity(name = "Producto_Venta")
public class ProductoVenta extends BaseObject implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.AUTO) 
	private Long id;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn(name = "producto_id")
	private Producto producto;
	
	@Column(name = "precio", nullable = false)
	private Long precio;
	
	@Column(name = "cantidad", nullable = false)
	private int cantidad;
	
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

	public Long getPrecio() {
		return precio;
	}

	public void setPrecio(Long precio) {
		this.precio = precio;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cantidad;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((precio == null) ? 0 : precio.hashCode());
		result = prime * result
				+ ((producto == null) ? 0 : producto.hashCode());
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
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
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
		return true;
	}

	@Override
	public String toString() {
		return "ProductoVenta [id=" + id + ", producto=" + producto
				+ ", precio=" + precio + ", cantidad=" + cantidad + "]";
	}

}
