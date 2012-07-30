package com.mitnick.persistence.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.appfuse.model.BaseObject;

import com.mitnick.exceptions.BusinessException;

@Entity(name = "Parametro")
public class Parametro extends BaseObject implements Serializable {
	private static final long serialVersionUID = 1L;

	private static int STRING_TYPE = 1;
	private static int INTEGER_TYPE = 2;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "descripcion", length = 80, nullable = false)
	private String descripcion;

	@Column(name = "nombre", length = 80, nullable = false)
	private String nombre;

	@Column(name = "valor", length = 120, nullable = false)
	private String valor;

	@Column(name = "tipo", nullable = false)
	private Long tipo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public Long getTipo() {
		return tipo;
	}

	public void setTipo(Long tipo) {
		this.tipo = tipo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		result = prime * result + ((valor == null) ? 0 : valor.hashCode());
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
		if (!(obj instanceof Parametro)) {
			return false;
		}
		Parametro other = (Parametro) obj;
		if (descripcion == null) {
			if (other.descripcion != null) {
				return false;
			}
		} else if (!descripcion.equals(other.descripcion)) {
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
		if (tipo == null) {
			if (other.tipo != null) {
				return false;
			}
		} else if (!tipo.equals(other.tipo)) {
			return false;
		}
		if (valor == null) {
			if (other.valor != null) {
				return false;
			}
		} else if (!valor.equals(other.valor)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Parametro [id=" + id + ", descripcion=" + descripcion
				+ ", nombre=" + nombre + ", valor=" + valor + ", tipo=" + tipo
				+ "]";
	}

	public int getIntValor() {
		if (this.getTipo().intValue() == INTEGER_TYPE)
			try {
				return Integer.parseInt(this.getValor());
			} catch (NumberFormatException e) {
				throw new BusinessException("configuracion.parametro.error", e);
			}
		else
			throw new BusinessException("configuracion.parametro.error");
	}

	public String getStringValor() {
		if (this.getTipo().intValue() == STRING_TYPE)
			return this.getValor();
		else
			throw new BusinessException("configuracion.parametro.error");
	}

}
