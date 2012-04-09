package com.mitnick.business.servicios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mitnick.business.exceptions.BusinessException;
import com.mitnick.business.services.ServicioBase;
import com.mitnick.persistence.daos.IMedioPagoDAO;
import com.mitnick.persistence.entities.MedioPago;
import com.mitnick.servicio.servicios.IMedioPagoServicio;
import com.mitnick.utils.dtos.MedioPagoDto;

@Service("medioPagoServicio")
public class MedioPagoServicio extends ServicioBase implements IMedioPagoServicio {

	@Autowired
	IMedioPagoDAO medioPagoDao;
	
	@Override
	public List<MedioPagoDto> obtenerMediosPagos() {
		List<MedioPagoDto> resultado = new ArrayList<MedioPagoDto>();
		List<MedioPago> mediosPago = null;
		try {
			mediosPago = medioPagoDao.getAll();
		} catch (Exception e) {
			throw new BusinessException("error.persistence", "Error en capa de persistencia de  cliente", e);
		}
		for (int i = 0; i < mediosPago.size(); i++) {
			resultado.add(getMedioPagoDtoFromMedioPago(mediosPago.get(i)));
		}
		return resultado;
	}


	private MedioPagoDto getMedioPagoDtoFromMedioPago(MedioPago medioPago){
		MedioPagoDto medioPagoDto = new MedioPagoDto();
		medioPagoDto.setDescripcion(medioPago.getDescripcion());
		medioPagoDto.setId(medioPago.getId());
		return medioPagoDto;
	}
	
	public IMedioPagoDAO getMedioPagoDao() {
		return medioPagoDao;
	}

	public void setMedioPagoDao(IMedioPagoDAO medioPagoDao) {
		this.medioPagoDao = medioPagoDao;
	}

}
