package com.mitnick.persistence.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.appfuse.model.BaseObject;

@Entity(name = "Configuracion")
public class Configuracion extends BaseObject implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.AUTO) 
	private Long id;
	
	@Column(name = "nmsd", length = 30, nullable = false)
	private String numeroSerieDisco;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumeroSerieDisco() {
		return numeroSerieDisco;
	}

	public void setNumeroSerieDisco(String numeroSerieDisco) {
		this.numeroSerieDisco = numeroSerieDisco;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime
				* result
				+ ((numeroSerieDisco == null) ? 0 : numeroSerieDisco.hashCode());
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
		Configuracion other = (Configuracion) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (numeroSerieDisco == null) {
			if (other.numeroSerieDisco != null)
				return false;
		} else if (!numeroSerieDisco.equals(other.numeroSerieDisco))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Configuracion [id=" + id + ", numeroSerieDisco="
				+ numeroSerieDisco + "]";
	}
	
}
