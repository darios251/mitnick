package com.mitnick.utils.dtos;

import com.mitnick.utils.MitnickConstants;

public class TipoCompradorDto extends BaseDto {

	private static final long serialVersionUID = 1L;

	private String tipoComprador;
	
	private String descripcion;

	public TipoCompradorDto(String tipoComprador, String descripcion) {
		this.tipoComprador = tipoComprador;
		this.descripcion = descripcion;
	}
	
	public String getTipoComprador() {
		return tipoComprador;
	}

	public void setTipoComprador(String tipoComprador) {
		this.tipoComprador = tipoComprador;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	@Override
	public String toString() {
		return descripcion;
	}
	
	public static TipoCompradorDto getTipoCompradorDto(String tipoComprador){
		if (MitnickConstants.TipoComprador.CONSUMIDOR_FINAL.equals(tipoComprador) )
			return new TipoCompradorDto(MitnickConstants.TipoComprador.CONSUMIDOR_FINAL, MitnickConstants.TipoComprador.CONSUMIDOR_FINAL_DESC);
		if (MitnickConstants.TipoComprador.CONTRIBUYENTE_EVENTUAL.equals(tipoComprador) )
			return new TipoCompradorDto(MitnickConstants.TipoComprador.CONTRIBUYENTE_EVENTUAL, MitnickConstants.TipoComprador.CONTRIBUYENTE_EVENTUAL_DESC);
		if (MitnickConstants.TipoComprador.CONTRIBUYENTE_EVENTUAL_SOCIAL.equals(tipoComprador) )
			return new TipoCompradorDto(MitnickConstants.TipoComprador.CONTRIBUYENTE_EVENTUAL_SOCIAL, MitnickConstants.TipoComprador.CONTRIBUYENTE_EVENTUAL_SOCIAL_DESC);
		if (MitnickConstants.TipoComprador.EXENTO.equals(tipoComprador) )
			return new TipoCompradorDto(MitnickConstants.TipoComprador.EXENTO, MitnickConstants.TipoComprador.EXENTO_DESC);
		if (MitnickConstants.TipoComprador.MONOTRIBUTISTA.equals(tipoComprador) )
			return new TipoCompradorDto(MitnickConstants.TipoComprador.MONOTRIBUTISTA, MitnickConstants.TipoComprador.MONOTRIBUTISTA_DESC);
		if (MitnickConstants.TipoComprador.MONOTRIBUTISTA_SOCIAL.equals(tipoComprador) )
			return new TipoCompradorDto(MitnickConstants.TipoComprador.MONOTRIBUTISTA_SOCIAL, MitnickConstants.TipoComprador.MONOTRIBUTISTA_SOCIAL_DESC);
		if (MitnickConstants.TipoComprador.NO_CATEGORIZADO.equals(tipoComprador) )
			return new TipoCompradorDto(MitnickConstants.TipoComprador.NO_CATEGORIZADO, MitnickConstants.TipoComprador.NO_CATEGORIZADO_DESC);
		if (MitnickConstants.TipoComprador.NO_RESPONSABLE.equals(tipoComprador) )
			return new TipoCompradorDto(MitnickConstants.TipoComprador.NO_RESPONSABLE, MitnickConstants.TipoComprador.NO_RESPONSABLE_DESC);
		if (MitnickConstants.TipoComprador.RESPONSABLE_INSCRIPTO.equals(tipoComprador) )
			return new TipoCompradorDto(MitnickConstants.TipoComprador.RESPONSABLE_INSCRIPTO, MitnickConstants.TipoComprador.RESPONSABLE_INSCRIPTO_DESC);
		else
			return null;
	}
	
}
