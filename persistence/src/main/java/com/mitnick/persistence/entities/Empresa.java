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
import javax.validation.constraints.NotNull;

import org.appfuse.model.BaseObject;
import org.hibernate.validator.constraints.MitnickField;
import org.hibernate.validator.constraints.MitnickField.FieldType;

@Entity(name = "Empresa")
public class Empresa extends BaseObject implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.AUTO) 
	private Long id;
	
	@MitnickField(required=true, fieldType=FieldType.NAME, min=3, max=30)
	@Column(name = "nombre", length = 30, nullable = false)
	private String nombre;
	
	@MitnickField(required=true, min=12, max=13, fieldType=FieldType.CUIT)
	@Column(name = "cuit", length = 13, nullable = false, unique = true)
	private String cuit;
	
	@MitnickField(min=5, max=40, fieldType=FieldType.PHONE_NUMBER)
	@Column(name = "telefono", length = 40, nullable = true)
	private String telefono;
	
	@MitnickField(min=3, max=40, fieldType=FieldType.EMAIL)
	@Column(name = "email", length = 40, nullable = true)
	private String email;
	
	@Column(name = "fechaInicioActividad", nullable = true)
	private String fechaInicioActividad;
	
	@MitnickField(required=true, min=12, max=50)
	@Column(name = "tipoResponsable", length = 50, nullable = false)
	private String tipoResponsable;
	
	@MitnickField(required=true, min=12, max=50)
	@Column(name = "ingBrutos", length = 50, nullable = false)
	private String ingBrutos;

	@NotNull
	@Column(name = "numeroFacturaActual", length = 8, nullable = false)
	private int numeroFacturaActual;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn(name = "direccion_id")
	private Direccion direccion;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIngBrutos() {
		return ingBrutos;
	}

	public void setIngBrutos(String ingBrutos) {
		this.ingBrutos = ingBrutos;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCuit() {
		return cuit;
	}

	public void setCuit(String cuit) {
		this.cuit = cuit;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getFechaInicioActividad() {
		return fechaInicioActividad;
	}

	public void setFechaInicioActividad(String fechaInicioActividad) {
		this.fechaInicioActividad = fechaInicioActividad;
	}

	public String getTipoResponsable() {
		return tipoResponsable;
	}

	public void setTipoResponsable(String tipoResponsable) {
		this.tipoResponsable = tipoResponsable;
	}

	public int getNumeroFacturaActual() {
		return numeroFacturaActual;
	}

	public void setNumeroFacturaActual(int numeroFacturaActual) {
		this.numeroFacturaActual = numeroFacturaActual;
	}

	public Direccion getDireccion() {
		return direccion;
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cuit == null) ? 0 : cuit.hashCode());
		result = prime * result + ((direccion == null) ? 0 : direccion.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((fechaInicioActividad == null) ? 0 : fechaInicioActividad.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((ingBrutos == null) ? 0 : ingBrutos.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + numeroFacturaActual;
		result = prime * result + ((telefono == null) ? 0 : telefono.hashCode());
		result = prime * result + ((tipoResponsable == null) ? 0 : tipoResponsable.hashCode());
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
		if (!(obj instanceof Empresa)) {
			return false;
		}
		Empresa other = (Empresa) obj;
		if (cuit == null) {
			if (other.cuit != null) {
				return false;
			}
		} else if (!cuit.equals(other.cuit)) {
			return false;
		}
		if (direccion == null) {
			if (other.direccion != null) {
				return false;
			}
		} else if (!direccion.equals(other.direccion)) {
			return false;
		}
		if (email == null) {
			if (other.email != null) {
				return false;
			}
		} else if (!email.equals(other.email)) {
			return false;
		}
		if (fechaInicioActividad == null) {
			if (other.fechaInicioActividad != null) {
				return false;
			}
		} else if (!fechaInicioActividad.equals(other.fechaInicioActividad)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (ingBrutos == null) {
			if (other.ingBrutos != null) {
				return false;
			}
		} else if (!ingBrutos.equals(other.ingBrutos)) {
			return false;
		}
		if (nombre == null) {
			if (other.nombre != null) {
				return false;
			}
		} else if (!nombre.equals(other.nombre)) {
			return false;
		}
		if (numeroFacturaActual != other.numeroFacturaActual) {
			return false;
		}
		if (telefono == null) {
			if (other.telefono != null) {
				return false;
			}
		} else if (!telefono.equals(other.telefono)) {
			return false;
		}
		if (tipoResponsable == null) {
			if (other.tipoResponsable != null) {
				return false;
			}
		} else if (!tipoResponsable.equals(other.tipoResponsable)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Empresa [id=" + id + ", nombre=" + nombre + ", cuit=" + cuit + ", telefono=" + telefono + ", email=" + email
				+ ", fechaInicioActividad=" + fechaInicioActividad + ", tipoResponsable=" + tipoResponsable + ", ingBrutos="
				+ ingBrutos + ", numeroFacturaActual=" + numeroFacturaActual + ", direccion=" + direccion + "]";
	}

}
