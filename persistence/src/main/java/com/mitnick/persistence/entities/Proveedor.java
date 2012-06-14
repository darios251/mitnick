package com.mitnick.persistence.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.appfuse.model.BaseObject;

@Entity(name = "Proveedor")
public class Proveedor extends BaseObject implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.AUTO) 
	private Long id;
	
	@NotNull(message="{error.entity.proveedor.codigo.null}")
	@Size(min=1, max=20, message="{error.entity.proveedor.codigo.size}")
	@Column(name = "codigo", length = 20, nullable = false)
	private String codigo;
	
	@NotNull(message="{error.entity.proveedor.nombre.null}")
	@Size(min=3, max=50, message="{error.entity.proveedor.nombre.size}")
	@Pattern(regexp="^[a-zA-Záéíóúñ1234567890 ]*$", message="{error.entity.proveedor.nombre.regexp}")
	@Column(name = "nombre", length = 255, nullable = false)
	private String nombre;
	
	@Size(min=10, max=40, message="{error.entity.proveedor.telefono.size}")
	@Column(name = "telefono", length = 40, nullable = true)
	private String telefono;
	
	@Column(name = "eliminado", nullable = false)
	private boolean eliminado = false;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	public void setEliminado(boolean eliminado) {
		this.eliminado = eliminado;
	}
	
	public boolean isEliminado() {
		return eliminado;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result + (eliminado ? 1231 : 1237);
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result
				+ ((telefono == null) ? 0 : telefono.hashCode());
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
		Proveedor other = (Proveedor) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		if (eliminado != other.eliminado)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (telefono == null) {
			if (other.telefono != null)
				return false;
		} else if (!telefono.equals(other.telefono))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Proveedor [id=" + id + ", codigo=" + codigo + ", nombre="
				+ nombre + ", telefono=" + telefono + ", eliminado="
				+ eliminado + "]";
	}

}
