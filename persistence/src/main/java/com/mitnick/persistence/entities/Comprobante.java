package com.mitnick.persistence.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.appfuse.model.BaseObject;

import com.mitnick.utils.MitnickConstants;

@Entity(name = "Comprobante")
public class Comprobante extends BaseObject implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id 
	private String id;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "fecha")
	private Date fecha;
	
	@Column(name = "total", nullable = false)
	private BigDecimal total;
	
	@OneToMany (cascade = {CascadeType.ALL})
	@JoinColumn(name = "comprobante_id")
	private List<Pago> pagos;
	
	@ManyToOne
	@PrimaryKeyJoinColumn(name = "cliente_id")
	private Cliente cliente;
	
	@Column(name= "numeroCaja", nullable = false)
	private int numeroCaja;

	public Comprobante(){
		
	}
	
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public int getNumeroCaja() {
		return numeroCaja;
	}

	public void setNumeroCaja(int numeroCaja) {
		this.numeroCaja = numeroCaja;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public List<Pago> getPagos() {
		return pagos;
	}

	public void setPagos(List<Pago> pagos) {
		this.pagos = pagos;
	}

	public void addPago(Pago pago){
		if (pagos==null)
			pagos = new ArrayList<Pago>();
		pagos.add(pago);
	}
	
	public BigDecimal getPagoContado(){
		BigDecimal total = new BigDecimal(0);
		Iterator<Pago> pagos = getPagos().iterator();
		while (pagos.hasNext()){
			Pago pago = pagos.next();
			if (!MitnickConstants.Medio_Pago.CUENTA_CORRIENTE.equals(pago.getMedioPago().getCodigo()) && !MitnickConstants.Medio_Pago.NOTA_CREDITO.equals(pago.getMedioPago().getCodigo()))
				total = total.add(pago.getPago());
		}
		return total;
	}
	
	public BigDecimal getPagoNC(){
		BigDecimal total = new BigDecimal(0);
		Iterator<Pago> pagos = getPagos().iterator();
		while (pagos.hasNext()){
			Pago pago = pagos.next();
			if (MitnickConstants.Medio_Pago.NOTA_CREDITO.equals(pago.getMedioPago().getCodigo()))
				total = total.add(pago.getPago());			
		}
		total = total.negate();
		return total;
	}
	
	public BigDecimal getPagoCuenta(){
		return new BigDecimal(0);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + numeroCaja;
		result = prime * result + ((pagos == null) ? 0 : pagos.hashCode());
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
		if (!(obj instanceof Comprobante)) {
			return false;
		}
		Comprobante other = (Comprobante) obj;
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
		if (numeroCaja != other.numeroCaja) {
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
		return true;
	}

	@Override
	public String toString() {
		return "Comprobante [id=" + id + ", fecha=" + fecha + ", total=" + total + ", pagos=" + pagos + ", cliente=" + cliente
				+ ", numeroCaja=" + numeroCaja + "]";
	}
	

}
