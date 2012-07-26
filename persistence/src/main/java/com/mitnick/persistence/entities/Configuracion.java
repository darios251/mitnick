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
	
	@Column(name = "mgcvl", length = 80, nullable = false)
	private String magicaValue;
	
	@Column(name = "salt", length = 20, nullable = false)
	private String salt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMagicValue() {
		return magicaValue;
	}

	public void setMagicValue(String magicValue) {
		this.magicaValue = magicValue;
	}
	
	public void setSalt(String salt) {
		this.salt = salt;
	}
	
	public String getSalt() {
		return salt;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((magicaValue == null) ? 0 : magicaValue.hashCode());
		result = prime * result + ((salt == null) ? 0 : salt.hashCode());
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
		if (magicaValue == null) {
			if (other.magicaValue != null)
				return false;
		} else if (!magicaValue.equals(other.magicaValue))
			return false;
		if (salt == null) {
			if (other.salt != null)
				return false;
		} else if (!salt.equals(other.salt))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Configuracion [id=" + id + ", magicaValue=" + magicaValue
				+ ", salt=" + salt + "]";
	}

}
