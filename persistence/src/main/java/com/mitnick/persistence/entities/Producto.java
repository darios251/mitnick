package com.mitnick.persistence.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;

import org.appfuse.model.BaseObject;

@Entity(name = "Producto")
public class Producto extends BaseObject implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.AUTO) 
	private Long id;
	
	@Column(name = "descripcion", length = 255, nullable = false)
	private String descripcion;
	
	@Column(name = "codigo", length = 255, nullable = false)
	private String codigo;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn(name = "marca_id")
	private Marca marca;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn(name = "tipo_id")
	private Tipo tipo;
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "movimiento_id")
	private List<Movimiento> movimientos;
	
	@Column(name = "stock", nullable = false)
	private int stock;
	
	@Column(name = "precio", nullable = false)
	private Long precio;

	@Column(name = "iva", nullable = false)
	private Long iva;
	
	@Column(name = "stockMinimo", nullable = false)
	private int stockMinimo=-1;
	
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

	public Marca getMarca() {
		return marca;
	}

	public void setMarca(Marca marca) {
		this.marca = marca;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public List<Movimiento> getMovimientos() {
		if (movimientos==null)
			movimientos = new ArrayList<Movimiento>();
		return movimientos;
	}

	public void setMovimientos(List<Movimiento> movimientos) {
		this.movimientos = movimientos;
	}
	
	public void addMovimientos(Movimiento movimiento) {
		getMovimientos().add(movimiento);
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public Long getPrecio() {
		return precio;
	}

	public void setPrecio(Long precio) {
		this.precio = precio;
	}

	public int getStockMinimo() {
		return stockMinimo;
	}

	public void setStockMinimo(int stockMinimo) {
		this.stockMinimo = stockMinimo;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Long getIva() {
		return iva;
	}

	public void setIva(Long iva) {
		this.iva = iva;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result
				+ ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((iva == null) ? 0 : iva.hashCode());
		result = prime * result + ((marca == null) ? 0 : marca.hashCode());
		result = prime * result
				+ ((movimientos == null) ? 0 : movimientos.hashCode());
		result = prime * result + ((precio == null) ? 0 : precio.hashCode());
		result = prime * result + stock;
		result = prime * result + stockMinimo;
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
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
		if (!(obj instanceof Producto)) {
			return false;
		}
		Producto other = (Producto) obj;
		if (codigo == null) {
			if (other.codigo != null) {
				return false;
			}
		} else if (!codigo.equals(other.codigo)) {
			return false;
		}
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
		if (iva == null) {
			if (other.iva != null) {
				return false;
			}
		} else if (!iva.equals(other.iva)) {
			return false;
		}
		if (marca == null) {
			if (other.marca != null) {
				return false;
			}
		} else if (!marca.equals(other.marca)) {
			return false;
		}
		if (movimientos == null) {
			if (other.movimientos != null) {
				return false;
			}
		} else if (!movimientos.equals(other.movimientos)) {
			return false;
		}
		if (precio == null) {
			if (other.precio != null) {
				return false;
			}
		} else if (!precio.equals(other.precio)) {
			return false;
		}
		if (stock != other.stock) {
			return false;
		}
		if (stockMinimo != other.stockMinimo) {
			return false;
		}
		if (tipo == null) {
			if (other.tipo != null) {
				return false;
			}
		} else if (!tipo.equals(other.tipo)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Producto [id=" + id + ", descripcion=" + descripcion
				+ ", codigo=" + codigo + ", marca=" + marca + ", tipo=" + tipo
				+ ", movimientos=" + movimientos + ", stock=" + stock
				+ ", precio=" + precio + ", iva=" + iva + ", stockMinimo="
				+ stockMinimo + "]";
	}

	

}
