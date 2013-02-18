package com.mitnick.utils.dtos;

import org.hibernate.validator.constraints.MitnickField;
import org.hibernate.validator.constraints.MitnickField.FieldType;
import org.hibernate.validator.constraints.Required;

public class ProductoNuevoDto extends BaseDto {

	private static final long serialVersionUID = 1L;

	@MitnickField(required=true, fieldType=FieldType.INTEGER, min=3, max=20)
	private String codigo;
	
	@MitnickField(required=true, fieldType=FieldType.NAME, min=3, max=255)
	private String descripcion;
	
	@Required
	private TipoDto tipo;
		
	private String talle;
	
	@Required
	private MarcaDto marca;
	
	@MitnickField(required=true, fieldType=FieldType.BIGDECIMAL)
	private String precioVenta;
	
	@MitnickField(required=true, fieldType=FieldType.BIGDECIMAL)
	private String precioCompra;
	
	@MitnickField(fieldType=FieldType.BIGDECIMAL)
	private String iva;
	
	@MitnickField(fieldType=FieldType.BIGDECIMAL)
	private String descuento;
	
	@MitnickField(required=true, fieldType=FieldType.INTEGER)
	private String stock;
	
	@MitnickField(required=true, fieldType=FieldType.INTEGER)
	private String stockMinimo="-1";
	
	@MitnickField(required=true, fieldType=FieldType.INTEGER)
	private String stockCompra;
	
	@Required
	private ProveedorDto proveedor;
	
	private boolean confirmado = false;

	public String getCodigo() {
		return codigo;
	}

	public boolean isConfirmado() {
		return confirmado;
	}

	public void setConfirmado(boolean confirmado) {
		this.confirmado = confirmado;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public TipoDto getTipo() {
		return tipo;
	}

	public void setTipo(TipoDto tipo) {
		this.tipo = tipo;
	}

	public String getTalle() {
		return talle;
	}

	public void setTalle(String talle) {
		this.talle = talle;
	}

	public MarcaDto getMarca() {
		return marca;
	}

	public void setMarca(MarcaDto marca) {
		this.marca = marca;
	}

	public String getPrecioVenta() {
		return precioVenta;
	}

	public void setPrecioVenta(String precioVenta) {
		this.precioVenta = precioVenta;
	}

	public String getPrecioCompra() {
		return precioCompra;
	}

	public void setPrecioCompra(String precioCompra) {
		this.precioCompra = precioCompra;
	}

	public String getIva() {
		return iva;
	}

	public void setIva(String iva) {
		this.iva = iva;
	}

	public String getDescuento() {
		return descuento;
	}

	public void setDescuento(String descuento) {
		this.descuento = descuento;
	}

	public String getStock() {
		return stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
	}

	public String getStockMinimo() {
		return stockMinimo;
	}

	public void setStockMinimo(String stockMinimo) {
		this.stockMinimo = stockMinimo;
	}

	public String getStockCompra() {
		return stockCompra;
	}

	public void setStockCompra(String stockCompra) {
		this.stockCompra = stockCompra;
	}
	
	public void setProveedor(ProveedorDto proveedorDto) {
		this.proveedor = proveedorDto;
	}
	
	public ProveedorDto getProveedor() {
		return proveedor;
	}

	@Override
	public String toString() {
		return "ProductoDto [codigo=" + codigo + ", descripcion=" + descripcion
				+ ", tipo=" + tipo + ", talle=" + talle + ", marca=" + marca
				+ ", precioVenta=" + precioVenta + ", precioCompra="
				+ precioCompra + ", iva=" + iva + ", descuento=" + descuento
				+ ", stock=" + stock + ", stockMinimo=" + stockMinimo
				+ ", stockCompra=" + stockCompra + ", proveedorDto="
				+ proveedor + "]";
	}

}
