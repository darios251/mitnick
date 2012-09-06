package com.mitnick.persistence.entities;

import java.io.Serializable;
import java.math.BigDecimal;
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
	
	@OneToMany (cascade = {CascadeType.ALL})
	@JoinColumn(name = "venta_id")
	private List<ProductoVenta> productos;
	
	@OneToMany (cascade = {CascadeType.ALL})
	@JoinColumn(name = "venta_id")
	private List<Pago> pagos;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "fecha")
	private Date fecha;
	
	@Column(name = "subtotal", nullable = false)
	private BigDecimal subtotal;
	
	@Column(name = "descuento", nullable = false)
	private BigDecimal descuento;
	
	@Column(name = "impuesto", nullable = false)
	private BigDecimal impuesto;
	
	@Column(name = "total", nullable = false)
	private BigDecimal total;
	
	@ManyToOne
	@PrimaryKeyJoinColumn(name = "cliente_id")
	private Cliente cliente;
	
	@OneToMany (cascade = {CascadeType.ALL})
	@JoinColumn(name = "venta_id")
	private List<Cuota> cuotas;
	
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

	public BigDecimal getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

	public BigDecimal getDescuento() {
		return descuento;
	}

	public void setDescuento(BigDecimal descuento) {
		this.descuento = descuento;
	}

	public BigDecimal getImpuesto() {
		return impuesto;
	}

	public void setImpuesto(BigDecimal impuesto) {
		this.impuesto = impuesto;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
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

	public List<Cuota> getCuotas() {
		return cuotas;
	}

	public void setCuotas(List<Cuota> cuotas) {
		this.cuotas = cuotas;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
		result = prime * result + ((cuotas == null) ? 0 : cuotas.hashCode());
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
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Venta)) {
			return false;
		}
		Venta other = (Venta) obj;
		if (cliente == null) {
			if (other.cliente != null) {
				return false;
			}
		} else if (!cliente.equals(other.cliente)) {
			return false;
		}
		if (cuotas == null) {
			if (other.cuotas != null) {
				return false;
			}
		} else if (!cuotas.equals(other.cuotas)) {
			return false;
		}
		if (descuento == null) {
			if (other.descuento != null) {
				return false;
			}
		} else if (!descuento.equals(other.descuento)) {
			return false;
		}
		if (discriminacionIVA == null) {
			if (other.discriminacionIVA != null) {
				return false;
			}
		} else if (!discriminacionIVA.equals(other.discriminacionIVA)) {
			return false;
		}
		if (fecha == null) {
			if (other.fecha != null) {
				return false;
			}
		} else if (!fecha.equals(other.fecha)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (impuesto == null) {
			if (other.impuesto != null) {
				return false;
			}
		} else if (!impuesto.equals(other.impuesto)) {
			return false;
		}
		if (pagos == null) {
			if (other.pagos != null) {
				return false;
			}
		} else if (!pagos.equals(other.pagos)) {
			return false;
		}
		if (productos == null) {
			if (other.productos != null) {
				return false;
			}
		} else if (!productos.equals(other.productos)) {
			return false;
		}
		if (subtotal == null) {
			if (other.subtotal != null) {
				return false;
			}
		} else if (!subtotal.equals(other.subtotal)) {
			return false;
		}
		if (total == null) {
			if (other.total != null) {
				return false;
			}
		} else if (!total.equals(other.total)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Venta [id=" + id + ", productos=" + productos + ", pagos="
				+ pagos + ", fecha=" + fecha + ", subtotal=" + subtotal
				+ ", descuento=" + descuento + ", impuesto=" + impuesto
				+ ", total=" + total + ", cliente=" + cliente + ", cuotas="
				+ cuotas + ", discriminacionIVA=" + discriminacionIVA + "]";
	}

	

}
