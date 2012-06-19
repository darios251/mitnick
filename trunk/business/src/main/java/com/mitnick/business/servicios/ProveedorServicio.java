package com.mitnick.business.servicios;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mitnick.exceptions.BusinessException;
import com.mitnick.exceptions.PersistenceException;
import com.mitnick.persistence.daos.IProveedorDAO;
import com.mitnick.persistence.entities.Proveedor;
import com.mitnick.servicio.servicios.IProveedorServicio;
import com.mitnick.servicio.servicios.dtos.ConsultaProveedorDto;
import com.mitnick.utils.Validator;
import com.mitnick.utils.dtos.ProveedorDto;

@SuppressWarnings("rawtypes")
@Service("proveedorServicio")
public class ProveedorServicio extends ServicioBase implements IProveedorServicio {

	@Autowired
	protected IProveedorDAO proveedorDao;

	@Transactional
	@Override
	public ProveedorDto guardarProveedor(ProveedorDto proveedorDto) {
		@SuppressWarnings("unchecked")
		Proveedor proveedor = (Proveedor) entityDTOParser.getEntityFromDto(proveedorDto);
		Set<ConstraintViolation<Proveedor>> constraintViolations = entityValidator.validate(proveedor);
		
		if(Validator.isNotEmptyOrNull(constraintViolations)) {
			StringBuffer buffer = new StringBuffer();
			for(ConstraintViolation<Proveedor> constraint : constraintViolations) {
				buffer.append(constraint.getMessage()).append("\n");
			}
			throw new BusinessException(buffer.toString());
		}
		
		try {
			proveedor = proveedorDao.saveOrUpdate(proveedor);
			proveedorDto.setId(proveedor.getId());
		}
		catch(PersistenceException e) {
			throw new BusinessException(e, "Error al intentar guardar el cliente");
		}
		return proveedorDto;
	}

	@Transactional
	@Override
	public void bajaProveedor(ProveedorDto proveedorDto) {
		if (proveedorDto.getId() == null) {
			throw new BusinessException("error.proveedorServicio.id.nulo", "Se invoca la eliminación de un proveedor que no existe en la base de datos ya que no se brinda el ID");
		}
		try {
			@SuppressWarnings("unchecked")
			Proveedor proveedor = (Proveedor) entityDTOParser.getEntityFromDto(proveedorDto);
			proveedor.setEliminado(true);
			proveedorDao.saveOrUpdate(proveedor);
		}
		catch(PersistenceException e) {
			throw new BusinessException(e, "Error al intentar eliminar el proveedor");
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	@Override
	public List<ProveedorDto> obtenerProveedores() {
		return entityDTOParser.getDtosFromEntities(proveedorDao.getAllNoEliminado());
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	@Override
	public List<ProveedorDto> consultaProveedor(ConsultaProveedorDto filtro) {
		try{
			return entityDTOParser.getDtosFromEntities(proveedorDao.findByFiltro(filtro));
		}
		catch(PersistenceException e) {
			throw new BusinessException(e, "Error al intentar consultar productos");
		}
	}
	
}
