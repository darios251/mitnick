package com.mitnick.business.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mitnick.persistence.daos.ICierreZDao;
import com.mitnick.persistence.entities.CierreZ;
import com.mitnick.servicio.servicios.ICierreZServicio;
import com.mitnick.utils.dtos.CierreZDto;


@SuppressWarnings("rawtypes")
@Service("cirreZServicio")
public class CierreZServicio extends ServicioBase implements ICierreZServicio {
	
	@Autowired
	private ICierreZDao cierreZDao;

	@Override
	public void guardarCierre(CierreZDto cierreZDto) {
		CierreZ cierreZ = new CierreZ();
		cierreZ.setNumero(Integer.parseInt(cierreZDto.getNumero()));
		cierreZ.setFecha(cierreZDto.getFecha());
		cierreZ.setNumeroCaja(cierreZDto.getNumeroCaja());
		
		cierreZDao.save(cierreZ);
	}

}
