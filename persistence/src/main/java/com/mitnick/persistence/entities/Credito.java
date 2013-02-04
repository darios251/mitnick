package com.mitnick.persistence.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.appfuse.model.BaseObject;

@Entity(name = "Credito")
public class Credito extends BaseObject implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO) 
	private Long id;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "fecha", nullable = true)
	private Date fecha;
	
	@ManyToOne
	@PrimaryKeyJoinColumn(name = "cliente_id")
	private Cliente cliente;
	
	@Column(name = "monto", nullable = false)
	private BigDecimal monto;

	@Column(name = "montoUsado", nullable = false)
	private BigDecimal montoUsado;
	
	@Column(name = "numeroNC")
	private String numeroNC;
	
	@Column(name = "numeroTicket")
	private String numeroTicket;

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public BigDecimal getMonto() {
		return monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	public BigDecimal getMontoUsado() {
		return montoUsado;
	}

	public void setMontoUsado(BigDecimal montoUsado) {
		this.montoUsado = montoUsado;
	}

	public String getNumeroNC() {
		return numeroNC;
	}

	public void setNumeroNC(String numeroNC) {
		this.numeroNC = numeroNC;
	}

	public String getNumeroTicket() {
		return numeroTicket;
	}

	public void setNumeroTicket(String numeroTicket) {
		this.numeroTicket = numeroTicket;
	}

	public BigDecimal getDisponible(){
		return monto.subtract(montoUsado);
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((monto == null) ? 0 : monto.hashCode());
		result = prime * result
				+ ((montoUsado == null) ? 0 : montoUsado.hashCode());
		result = prime * result
				+ ((numeroNC == null) ? 0 : numeroNC.hashCode());
		result = prime * result
				+ ((numeroTicket == null) ? 0 : numeroTicket.hashCode());
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
		if (!(obj instanceof Credito)) {
			return false;
		}
		Credito other = (Credito) obj;
		if (cliente == null) {
			if (other.cliente != null) {
				return false;
			}
		} else if (!cliente.equals(other.cliente)) {
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
		if (monto == null) {
			if (other.monto != null) {
				return false;
			}
		} else if (!monto.equals(other.monto)) {
			return false;
		}
		if (montoUsado == null) {
			if (other.montoUsado != null) {
				return false;
			}
		} else if (!montoUsado.equals(other.montoUsado)) {
			return false;
		}
		if (numeroNC == null) {
			if (other.numeroNC != null) {
				return false;
			}
		} else if (!numeroNC.equals(other.numeroNC)) {
			return false;
		}
		if (numeroTicket == null) {
			if (other.numeroTicket != null) {
				return false;
			}
		} else if (!numeroTicket.equals(other.numeroTicket)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Credito [id=" + id + ", fecha=" + fecha + ", cliente="
				+ cliente + ", monto=" + monto + ", montoUsado=" + montoUsado
				+ ", numeroNC=" + numeroNC + ", numeroTicket=" + numeroTicket
				+ "]";
	}

}
