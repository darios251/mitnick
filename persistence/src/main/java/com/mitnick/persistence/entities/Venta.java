package com.mitnick.persistence.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.appfuse.model.BaseObject;

@Entity(name = "Venta")
public class Venta extends BaseObject implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.AUTO) 
	private Long id;
	
	@OneToMany
	@JoinColumn(name = "producto_id")
	private List<ProductoVenta> productos;
	
	@OneToMany
	@JoinColumn(name = "pagos_id")
	private List<Pago> pagos;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "fecha")
	private Date fecha;
	
	@Column(name = "subtotal", nullable = false)
	private Long subtotal;
	
	@Column(name = "descuento", nullable = false)
	private Long descuento;
	
	@Column(name = "impuesto", nullable = false)
	private Long impuesto;
	
	@Column(name = "total", nullable = false)
	private Long total;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn(name = "cliente_id")
	private Cliente cliente;
	
	private DiscriminacionIVA discriminacionIVA; 
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<ProductoVenta> getProductos() {
		return productos;
	}

	public void setProductos(List<ProductoVenta> productos) {
		this.productos = productos;
	}

	public List<Pago> getPagos() {
		return pagos;
	}

	public void setPagos(List<Pago> pagos) {
		this.pagos = pagos;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Long getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(Long subtotal) {
		this.subtotal = subtotal;
	}

	public Long getDescuento() {
		return descuento;
	}

	public void setDescuento(Long descuento) {
		this.descuento = descuento;
	}

	public Long getImpuesto() {
		return impuesto;
	}

	public void setImpuesto(Long impuesto) {
		this.impuesto = impuesto;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public DiscriminacionIVA getDiscriminacionIVA() {
		return discriminacionIVA;
	}

	public void setDiscriminacionIVA(DiscriminacionIVA discriminacionIVA) {
		this.discriminacionIVA = discriminacionIVA;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
		result = prime * result
				+ ((descuento == null) ? 0 : descuento.hashCode());
		result = prime
				* result
				+ ((discriminacionIVA == null) ? 0 : discriminacionIVA
						.hashCode());
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((impuesto == null) ? 0 : impuesto.hashCode());
		result = prime * result + ((pagos == null) ? 0 : pagos.hashCode());
		result = prime * result
				+ ((productos == null) ? 0 : productos.hashCode());
		result = prime * result
				+ ((subtotal == null) ? 0 : subtotal.hashCode());
		result = prime * result + ((total == null) ? 0 : total.hashCode());
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
		Venta other = (Venta) obj;
		if (cliente == null) {
			if (other.cliente != null)
				return false;
		} else if (!cliente.equals(other.cliente))
			return false;
		if (descuento == null) {
			if (other.descuento != null)
				return false;
		} else if (!descuento.equals(other.descuento))
			return false;
		if (discriminacionIVA == null) {
			if (other.discriminacionIVA != null)
				return false;
		} else if (!discriminacionIVA.equals(other.discriminacionIVA))
			return false;
		if (fecha == null) {
			if (other.fecha != null)
				return false;
		} else if (!fecha.equals(other.fecha))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (impuesto == null) {
			if (other.impuesto != null)
				return false;
		} else if (!impuesto.equals(other.impuesto))
			return false;
		if (pagos == null) {
			if (other.pagos != null)
				return false;
		} else if (!pagos.equals(other.pagos))
			return false;
		if (productos == null) {
			if (other.productos != null)
				return false;
		} else if (!productos.equals(other.productos))
			return false;
		if (subtotal == null) {
			if (other.subtotal != null)
				return false;
		} else if (!subtotal.equals(other.subtotal))
			return false;
		if (total == null) {
			if (other.total != null)
				return false;
		} else if (!total.equals(other.total))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Venta [id=" + id + ", productos=" + productos + ", pagos="
				+ pagos + ", fecha=" + fecha + ", subtotal=" + subtotal
				+ ", descuento=" + descuento + ", impuesto=" + impuesto
				+ ", total=" + total + ", cliente=" + cliente
				+ ", discriminacionIVA=" + discriminacionIVA + "]";
	}

}
