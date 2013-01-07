package com.mitnick.persistence.entities;

import java.io.Serializable;
import java.util.ArrayList;
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

@Entity(name = "Cliente")
public class Cliente extends BaseObject implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.AUTO) 
	private Long id;
	
	@Column(name = "nombre", length = 30, nullable = false)
	private String nombre;
	
	@Column(name = "actividad", length = 30, nullable = false)
	private String actividad;
	
	@Column(name = "documento", length = 10, nullable = true, unique = true)
	private String documento;
	
	@Column(name = "cuit", length = 13, nullable = true, unique = true)
	private String cuit;

	@Column(name = "telefono", length = 40, nullable = true)
	private String telefono;
	
	@Column(name = "email", length = 40, nullable = true)
	private String email;
	
	@Column(name = "eliminado", nullable = false)
	private boolean eliminado = false;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "fecha_nacimiento", nullable = true)
	private Date fechaNacimiento;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn(name = "direccion_id")
	private Direccion direccion;
	
	@OneToMany (cascade = {CascadeType.ALL})
	@JoinColumn(name = "comprobante_id")
	private List<Comprobante> comprobantes;
	
	public List<Comprobante> getComprobantes() {
		if (comprobantes==null)
			comprobantes = new ArrayList<Comprobante>();
		return comprobantes;
	}

	public void setComprobantes(List<Comprobante> comprobantes) {
		this.comprobantes = comprobantes;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
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

	public boolean isEliminado() {
		return eliminado;
	}

	public void setEliminado(boolean eliminado) {
		this.eliminado = eliminado;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public Direccion getDireccion() {
		return direccion;
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}

	public String getActividad() {
		return actividad;
	}

	public void setActividad(String actividad) {
		this.actividad = actividad;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((actividad == null) ? 0 : actividad.hashCode());
		result = prime * result
				+ ((comprobantes == null) ? 0 : comprobantes.hashCode());
		result = prime * result + ((cuit == null) ? 0 : cuit.hashCode());
		result = prime * result
				+ ((direccion == null) ? 0 : direccion.hashCode());
		result = prime * result
				+ ((documento == null) ? 0 : documento.hashCode());
		result = prime * result + (eliminado ? 1231 : 1237);
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result
				+ ((fechaNacimiento == null) ? 0 : fechaNacimiento.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result
				+ ((telefono == null) ? 0 : telefono.hashCode());
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
		if (!(obj instanceof Cliente)) {
			return false;
		}
		Cliente other = (Cliente) obj;
		if (actividad == null) {
			if (other.actividad != null) {
				return false;
			}
		} else if (!actividad.equals(other.actividad)) {
			return false;
		}
		if (comprobantes == null) {
			if (other.comprobantes != null) {
				return false;
			}
		} else if (!comprobantes.equals(other.comprobantes)) {
			return false;
		}
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
		if (documento == null) {
			if (other.documento != null) {
				return false;
			}
		} else if (!documento.equals(other.documento)) {
			return false;
		}
		if (eliminado != other.eliminado) {
			return false;
		}
		if (email == null) {
			if (other.email != null) {
				return false;
			}
		} else if (!email.equals(other.email)) {
			return false;
		}
		if (fechaNacimiento == null) {
			if (other.fechaNacimiento != null) {
				return false;
			}
		} else if (!fechaNacimiento.equals(other.fechaNacimiento)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (nombre == null) {
			if (other.nombre != null) {
				return false;
			}
		} else if (!nombre.equals(other.nombre)) {
			return false;
		}
		if (telefono == null) {
			if (other.telefono != null) {
				return false;
			}
		} else if (!telefono.equals(other.telefono)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Cliente [id=" + id + ", nombre=" + nombre + ", actividad="
				+ actividad + ", documento=" + documento + ", cuit=" + cuit
				+ ", telefono=" + telefono + ", email=" + email
				+ ", eliminado=" + eliminado + ", fechaNacimiento="
				+ fechaNacimiento + ", direccion=" + direccion
				+ ", comprobantes=" + comprobantes + "]";
	}

	public void addComprobante(Comprobante comprobante){
		getComprobantes().add(comprobante);
		
	}
	
}
