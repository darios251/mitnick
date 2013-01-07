package com.mitnick.persistence.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import org.appfuse.model.BaseObject;

@Entity(name = "Pago")
public class Pago extends BaseObject implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.AUTO) 
	private Long id;
	
	@ManyToOne
	@PrimaryKeyJoinColumn(name = "medio_pago_id")
	private MedioPago medioPago;
	
	@Column(name = "pago", nullable = false)
	private Long pago;
	
	@Column(name = "comprobante", nullable = false)
	private boolean comprobante = false;
	
	@Column(name = "fecha", nullable = true)
	private Date fecha;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public MedioPago getMedioPago() {
		return medioPago;
	}

	public void setMedioPago(MedioPago medioPago) {
		this.medioPago = medioPago;
	}

	public Long getPago() {
		return pago;
	}

	public void setPago(Long pago) {
		this.pago = pago;
	}

	public boolean isComprobante() {
		return comprobante;
	}

	public void setComprobante(boolean comprobante) {
		this.comprobante = comprobante;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (comprobante ? 1231 : 1237);
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((medioPago == null) ? 0 : medioPago.hashCode());
		result = prime * result + ((pago == null) ? 0 : pago.hashCode());
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
		if (!(obj instanceof Pago)) {
			return false;
		}
		Pago other = (Pago) obj;
		if (comprobante != other.comprobante) {
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
		if (medioPago == null) {
			if (other.medioPago != null) {
				return false;
			}
		} else if (!medioPago.equals(other.medioPago)) {
			return false;
		}
		if (pago == null) {
			if (other.pago != null) {
				return false;
			}
		} else if (!pago.equals(other.pago)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Pago [id=" + id + ", medioPago=" + medioPago + ", pago=" + pago
				+ ", comprobante=" + comprobante + ", fecha=" + fecha + "]";
	}

}
