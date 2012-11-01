package com.mitnick.persistence.daos;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.mitnick.exceptions.PersistenceException;
import com.mitnick.persistence.entities.Cuota;
import com.mitnick.servicio.servicios.dtos.ReportesDto;
import com.mitnick.utils.ConstraintValidationHelper;
import com.mitnick.utils.Validator;
import com.mitnick.utils.dtos.CuotaDto;

@Repository("cuotaDao")
public class CuotaDao extends GenericDaoHibernate<Cuota, Long>  implements ICuotaDao {

	protected javax.validation.Validator entityValidator = Validation.buildDefaultValidatorFactory().getValidator();
	
	public CuotaDao() {
		super(Cuota.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<Cuota> getCuotaByClienteId(Long cliente){
		DetachedCriteria criteria = DetachedCriteria.forClass(Cuota.class);

		criteria.createAlias("cliente", "c");
		if(Validator.isNotNull(cliente)){
			criteria.add(Restrictions.eq("c.id", cliente));
		}
		criteria.add(Restrictions.eq("pagado", false));
		
		criteria.addOrder(Order.asc("fecha_pagar"));
		return getHibernateTemplate().findByCriteria(criteria);
	}

	@SuppressWarnings("unchecked")
	public List<Cuota> getCuotaPagas(ReportesDto filtro){
		DetachedCriteria criteria = DetachedCriteria.forClass(Cuota.class);

		if(Validator.isNotNull(Validator.isNotNull(filtro.getFechaInicio()))){
			criteria.add(Restrictions.gt("fechaPago", filtro.getFechaInicio()));
		}
		if(Validator.isNotNull(filtro.getFechaFin())){
			criteria.add(Restrictions.le("fechaPago", filtro.getFechaFin()));
		}
		
		criteria.add(Restrictions.eq("pagado", true));
		criteria.addOrder(Order.desc("fechaPago"));
		
		return getHibernateTemplate().findByCriteria(criteria);
	}
	
	public void eliminarCuota(CuotaDto cuotaDto){
		Cuota cuota = getHibernateTemplate().get(Cuota.class, cuotaDto.getId());
		getHibernateTemplate().delete(cuota);
	}
	
	public Cuota saveOrUpdate(Cuota cuota){
		Set<ConstraintViolation<Cuota>> constraintViolations = entityValidator.validate(cuota);
		if(Validator.isNotEmptyOrNull(constraintViolations))
			throw new PersistenceException(new ConstraintValidationHelper<Cuota>().getMessage(constraintViolations));
		
		getHibernateTemplate().saveOrUpdate(cuota);
		return cuota;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Cuota> findByFiltro(ReportesDto filtro) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Cuota.class);

		if(Validator.isNotNull(Validator.isNotNull(filtro.getFechaInicio()))){
			criteria.add(Restrictions.gt("fecha_pagar", filtro.getFechaInicio()));
		}
		if(Validator.isNotNull(filtro.getFechaFin())){
			criteria.add(Restrictions.le("fecha_pagar", filtro.getFechaFin()));
		}
		
		criteria.add(Restrictions.eq("pagado", false));
		criteria.addOrder(Order.asc("fecha_pagar"));
		return getHibernateTemplate().findByCriteria(criteria);
	}

}