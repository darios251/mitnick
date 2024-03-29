package com.mitnick.utils.dtos;

import org.hibernate.validator.constraints.MitnickField;
import org.hibernate.validator.constraints.MitnickField.FieldType;

import com.mitnick.utils.Validator;

public class ClienteDto extends BaseDto {

	private static final long serialVersionUID = 1L;

	@MitnickField(required=true, fieldType=FieldType.NAME, min=1, max=250)
	private String nombre;
	
	@MitnickField(fieldType=FieldType.NAME, min=3, max=30)
	private String actividad;
	
	@MitnickField(min=8, max=10, fieldType=FieldType.INTEGER)
	private String documento;
	
	@MitnickField(min=12, max=13, fieldType=FieldType.CUIT)
	private String cuit;
	
	@MitnickField(min=5, max=40, fieldType=FieldType.PHONE_NUMBER)
	private String telefono;
	
	@MitnickField(min=5, max=40, fieldType=FieldType.PHONE_NUMBER)
	private String celular;
	
	@MitnickField(min=3, max=40, fieldType=FieldType.EMAIL)
	private String email;
	
	@MitnickField(fieldType=FieldType.DATE)
	private String fechaNacimiento;
		
	private DireccionDto direccion;
	
	private String ultimoMovimiento = "";
	
	public String getCelular() {	
		if (Validator.isNull(celular))
			return "";
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	private String tipoComprador;
	
	private int cantidadComprobantes=0;

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

	public String getTipoComprador() {
		return tipoComprador;
	}

	public void setTipoComprador(String tipoComprador) {
		this.tipoComprador = tipoComprador;
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

	public String getFechaNacimiento() {
		if (Validator.isNull(fechaNacimiento))
			return "";
		return fechaNacimiento;
	}

	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public DireccionDto getDireccion() {
		return direccion;
	}

	public void setDireccion(DireccionDto direccion) {
		this.direccion = direccion;
	}

	public String getActividad() {
		return actividad;
	}

	public void setActividad(String actividad) {
		this.actividad = actividad;
	}

	public int getCantidadComprobantes() {
		cantidadComprobantes++;
		return cantidadComprobantes;
	}

	public void setCantidadComprobantes(int cantidadComprobantes) {
		this.cantidadComprobantes = cantidadComprobantes;
	}

	public String getUltimoMovimiento() {
		return ultimoMovimiento;
	}

	public void setUltimoMovimiento(String ultimoMovimiento) {
		this.ultimoMovimiento = ultimoMovimiento;
	}

	@Override
	public String toString() {
		return "ClienteDto [nombre=" + nombre + ", actividad=" + actividad
				+ ", documento=" + documento + ", cuit=" + cuit + ", telefono="
				+ telefono + ", celular=" + celular + ", email=" + email
				+ ", fechaNacimiento=" + fechaNacimiento + ", direccion="
				+ direccion + ", ultimoMovimiento=" + ultimoMovimiento
				+ ", tipoComprador=" + tipoComprador
				+ ", cantidadComprobantes=" + cantidadComprobantes + "]";
	}
	
}
