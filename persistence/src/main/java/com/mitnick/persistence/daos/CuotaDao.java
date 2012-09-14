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
import com.mitnick.utils.ConstraintValidationHelper;
import com.mitnick.utils.Validator;
import com.mitnick.utils.dtos.CuotaDto;

@Repository("CuotaDao")
public class CuotaDao extends GenericDaoHibernate<Cuota, Long>  implements ICuotaDao {

	protected javax.validation.Validator entityValidator = Validation.buildDefaultValidatorFactory().getValidator();
	
	public CuotaDao() {
		super(Cuota.class);
	}
	
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
}
