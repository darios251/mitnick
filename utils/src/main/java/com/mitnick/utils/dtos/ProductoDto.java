package com.mitnick.utils.dtos;

import java.math.BigDecimal;

public class ProductoDto extends BaseDto {

	private static final long serialVersionUID = 1L;

	private String codigo;
	
	private String descripcion;
	
	private TipoDto tipo;
	
	private String talle;
	
	private MarcaDto marca;
	
	private BigDecimal precioVenta;
	
	private BigDecimal precioCompra;
	
	private BigDecimal iva;
	
	private BigDecimal descuento;
	
	private int stock;
	
	private int stockMinimo=-1;
	
	private int stockCompra;
	
	private ProveedorDto proveedor;

	public String getCodigo() {
		return codigo;
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

	public BigDecimal getPrecioVenta() {
		return precioVenta;
	}

	public void setPrecioVenta(BigDecimal precioVenta) {
		this.precioVenta = precioVenta;
	}

	public BigDecimal getPrecioCompra() {
		return precioCompra;
	}

	public void setPrecioCompra(BigDecimal precioCompra) {
		this.precioCompra = precioCompra;
	}

	public BigDecimal getIva() {
		return iva;
	}

	public void setIva(BigDecimal iva) {
		this.iva = iva;
	}

	public BigDecimal getDescuento() {
		return descuento;
	}

	public void setDescuento(BigDecimal descuento) {
		this.descuento = descuento;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public int getStockMinimo() {
		return stockMinimo;
	}

	public void setStockMinimo(int stockMinimo) {
		this.stockMinimo = stockMinimo;
	}

	public int getStockCompra() {
		return stockCompra;
	}

	public void setStockCompra(int stockCompra) {
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
