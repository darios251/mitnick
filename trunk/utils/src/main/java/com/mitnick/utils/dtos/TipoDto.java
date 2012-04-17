package com.mitnick.utils.dtos;

import com.mitnick.utils.PropertiesManager;

public class TipoDto extends BaseDto {

	private static final long serialVersionUID = 1L;

	private String descripcion;

	public TipoDto() {
	}
	
	public TipoDto(Long id){
		setId(id);
		setDescripcion(PropertiesManager.getProperty("cmb.label.todos"));
	}
	
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	@Override
	public String toString() {
		return this.descripcion;
	}
}
