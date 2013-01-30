package com.mitnick.persistence.daos;

import java.util.List;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.mitnick.persistence.entities.MedioPago;
import com.mitnick.utils.MitnickConstants;

@Repository("medioPagoDao")
public class MedioPagoDAO extends GenericDaoHibernate<MedioPago, Long>  implements IMedioPagoDAO {

	public MedioPagoDAO() {
		super(MedioPago.class);
	}

	@SuppressWarnings("unchecked")
	public List<MedioPago> obtenerMediosPagosCuentaCorriente() {
		DetachedCriteria criteria = DetachedCriteria.forClass(MedioPago.class);
		criteria.add(Restrictions.not(Restrictions.ilike("codigo", MitnickConstants.Medio_Pago.CUENTA_CORRIENTE)));
		return getHibernateTemplate().findByCriteria(criteria);
	}
	
	public MedioPago getByCode(String code) {
		DetachedCriteria criteria = DetachedCriteria.forClass(MedioPago.class);
		criteria.add(Restrictions.ilike("codigo", code));
		List<MedioPago> medioPagos = getHibernateTemplate().findByCriteria(criteria);
		if (medioPagos!=null && !medioPagos.isEmpty())
			return medioPagos.get(0);
		else 
			return null;
	}

}
