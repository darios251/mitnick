package com.mitnick.business.servicios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mitnick.exceptions.BusinessException;
import com.mitnick.exceptions.PersistenceException;
import com.mitnick.persistence.daos.IMedioPagoDAO;
import com.mitnick.servicio.servicios.IMedioPagoServicio;
import com.mitnick.utils.dtos.MedioPagoDto;

@SuppressWarnings("rawtypes")
@Service("medioPagoServicio")
public class MedioPagoServicio extends ServicioBase implements IMedioPagoServicio {

	@Autowired
	protected IMedioPagoDAO medioPagoDao;
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	@Override
	public List<MedioPagoDto> obtenerMediosPagos() {
		List<MedioPagoDto> resultado = new ArrayList<MedioPagoDto>();
		try {
			resultado.addAll(entityDTOParser.getDtosFromEntities(medioPagoDao.getAll()));
		}
		catch(PersistenceException e) {
			throw new BusinessException(e, "Error al intentar obtener medios de pagos");
		}
		catch (Exception e) {
			throw new BusinessException("error.persistence", "Error en capa de persistencia de  cliente", e);
		}
		return resultado;
	}

	public void setMedioPagoDao(IMedioPagoDAO medioPagoDao) {
		this.medioPagoDao = medioPagoDao;
	}

}
