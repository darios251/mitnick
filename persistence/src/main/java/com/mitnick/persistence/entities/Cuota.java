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

@Entity(name = "Cuota")
public class Cuota extends BaseObject implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.AUTO) 
	private Long id;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "fecha")
	private Date fecha_pagar;
	
	@Column(name = "total", nullable = false)
	private BigDecimal total;
	
	@ManyToOne
	@PrimaryKeyJoinColumn(name = "venta_id")
	private Venta venta;
	
	@OneToMany (cascade = {CascadeType.ALL})
	@JoinColumn(name = "cuota_id")
	private List<Pago> pagos;
	
	@Column(name = "numero_cuota", nullable = false)
	private int nroCuota;
	
	@ManyToOne
	@PrimaryKeyJoinColumn(name = "cliente_id")
	private Cliente cliente;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "fechaPago", nullable = true)
	private Date fechaPago;
	
	@Column(name = "faltaPagar", nullable = false)
	private BigDecimal faltaPagar;

	@Column(name = "pagado", nullable = false)
	private boolean pagado = false;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFecha_pagar() {
		return fecha_pagar;
	}

	public void setFecha_pagar(Date fecha_pagar) {
		this.fecha_pagar = fecha_pagar;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public Venta getVenta() {
		return venta;
	}

	public void setVenta(Venta venta) {
		this.venta = venta;
	}

	public List<Pago> getPagos() {
		return pagos;
	}

	public void setPagos(List<Pago> pagos) {
		this.pagos = pagos;
	}

	public int getNroCuota() {
		return nroCuota;
	}

	public void setNroCuota(int nroCuota) {
		this.nroCuota = nroCuota;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public boolean isPagado() {
		return pagado;
	}

	public void setPagado(boolean pagado) {
		this.pagado = pagado;
	}

	public Date getFechaPago() {
		return fechaPago;
	}

	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}

	public BigDecimal getFaltaPagar() {
		return faltaPagar;
	}

	public void setFaltaPagar(BigDecimal faltaPagar) {
		this.faltaPagar = faltaPagar;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
		result = prime * result
				+ ((faltaPagar == null) ? 0 : faltaPagar.hashCode());
		result = prime * result
				+ ((fechaPago == null) ? 0 : fechaPago.hashCode());
		result = prime * result
				+ ((fecha_pagar == null) ? 0 : fecha_pagar.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + nroCuota;
		result = prime * result + (pagado ? 1231 : 1237);
		result = prime * result + ((pagos == null) ? 0 : pagos.hashCode());
		result = prime * result + ((total == null) ? 0 : total.hashCode());
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
		if (!(obj instanceof Cuota)) {
			return false;
		}
		Cuota other = (Cuota) obj;
		if (cliente == null) {
			if (other.cliente != null) {
				return false;
			}
		} else if (!cliente.equals(other.cliente)) {
			return false;
		}
		if (faltaPagar == null) {
			if (other.faltaPagar != null) {
				return false;
			}
		} else if (!faltaPagar.equals(other.faltaPagar)) {
			return false;
		}
		if (fechaPago == null) {
			if (other.fechaPago != null) {
				return false;
			}
		} else if (!fechaPago.equals(other.fechaPago)) {
			return false;
		}
		if (fecha_pagar == null) {
			if (other.fecha_pagar != null) {
				return false;
			}
		} else if (!fecha_pagar.equals(other.fecha_pagar)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (nroCuota != other.nroCuota) {
			return false;
		}
		if (pagado != other.pagado) {
			return false;
		}
		if (pagos == null) {
			if (other.pagos != null) {
				return false;
			}
		} else if (!pagos.equals(other.pagos)) {
			return false;
		}
		if (total == null) {
			if (other.total != null) {
				return false;
			}
		} else if (!total.equals(other.total)) {
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
		return "Cuota [id=" + id + ", fecha_pagar=" + fecha_pagar + ", total="
				+ total + ", venta=" + venta + ", pagos=" + pagos
				+ ", nroCuota=" + nroCuota + ", cliente=" + cliente
				+ ", fechaPago=" + fechaPago + ", faltaPagar=" + faltaPagar
				+ ", pagado=" + pagado + "]";
	}

	
	
}
