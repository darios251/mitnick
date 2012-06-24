package com.mitnick.utils.dtos;

import java.util.Date;

import org.hibernate.validator.constraints.MitnickTextField;
import org.hibernate.validator.constraints.MitnickTextField.FieldType;
import org.hibernate.validator.constraints.Required;

public class ClienteDto extends BaseDto {

	private static final long serialVersionUID = 1L;

	@MitnickTextField(required=true, fieldType=FieldType.NAME, min=3, max=30)
	private String nombre;
	
	@MitnickTextField(required=true, fieldType=FieldType.NAME, min=3, max=30)
	private String apellido;
	
	@MitnickTextField(required=true, min=8, max=10, fieldType=FieldType.INTEGER)
	private String documento;
	
	@MitnickTextField(required=true, min=12, max=13, fieldType=FieldType.CUIT)
	private String cuit;
	
	@MitnickTextField(min=5, max=40, fieldType=FieldType.PHONE_NUMBER)
	private String telefono;
	
	@MitnickTextField(min=3, max=40, fieldType=FieldType.EMAIL)
	private String email;
	
	private Date fechaNacimiento;
	
	@Required
	private DireccionDto direccion;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
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

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public DireccionDto getDireccion() {
		return direccion;
	}

	public void setDireccion(DireccionDto direccion) {
		this.direccion = direccion;
	}
}
