package com.mitnick.business.servicios;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mitnick.exceptions.BusinessException;
import com.mitnick.exceptions.PersistenceException;
import com.mitnick.persistence.daos.IVendedorDao;
import com.mitnick.persistence.entities.Vendedor;
import com.mitnick.servicio.servicios.IVendedorServicio;
import com.mitnick.servicio.servicios.dtos.ConsultaVendedorDto;
import com.mitnick.utils.Validator;
import com.mitnick.utils.dtos.VendedorDto;

@SuppressWarnings("rawtypes")
@Service("vendedorServicio")
public class VendedorServicio extends ServicioBase implements IVendedorServicio {

	@Autowired
	protected IVendedorDao vendedorDao;
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	@Override
	public List<VendedorDto> consultaVendedor(ConsultaVendedorDto dto) {
		try{
			return entityDTOParser.getDtosFromEntities(vendedorDao.findByFiltro(dto));
		}
		catch(PersistenceException e) {
			throw new BusinessException(e, "Error al intentar consultar vendedores");
		}
	}

	@Transactional
	@Override
	public VendedorDto guardarVendedor(VendedorDto dto) {
		@SuppressWarnings("unchecked")
		Vendedor vendedor = (Vendedor) entityDTOParser.getEntityFromDto(dto);
		Set<ConstraintViolation<Vendedor>> constraintViolations = entityValidator.validate(vendedor);
		
		if(Validator.isNotEmptyOrNull(constraintViolations)) {
			StringBuffer buffer = new StringBuffer();
			for(ConstraintViolation<Vendedor> constraint : constraintViolations) {
				buffer.append(constraint.getMessage()).append("\n");
			}
			throw new BusinessException(buffer.toString());
		}
		
		try {
			vendedor = vendedorDao.saveOrUpdate(vendedor);
			dto.setId(vendedor.getId());
		}
		catch(PersistenceException e) {
			throw new BusinessException(e, "Error al intentar guardar el cliente");
		}
		return dto;
		
	}

	@Transactional
	@Override
	public void bajaVendedor(VendedorDto dto) {
		if (dto.getId() == null) {
			throw new BusinessException("error.vendedorServicio.id.nulo", "Se invoca la eliminación de un vendedor que no existe en la base de datos ya que no se brinda el ID");
		}
		try {
			@SuppressWarnings("unchecked")
			Vendedor vendedor = (Vendedor) entityDTOParser.getEntityFromDto(dto);
			vendedor.setEliminado(true);
			vendedorDao.saveOrUpdate(vendedor);
		}
		catch(PersistenceException e) {
			throw new BusinessException(e, "Error al intentar eliminar el vendedor");
		}
		
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	@Override
	public List<VendedorDto> obtenerVendedores() {
		return entityDTOParser.getDtosFromEntities(vendedorDao.getAllNoEliminado());
	}

}
