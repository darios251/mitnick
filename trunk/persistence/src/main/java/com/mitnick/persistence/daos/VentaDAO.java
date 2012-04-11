package com.mitnick.persistence.daos;

import java.util.List;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.mitnick.persistence.entities.Venta;
import com.mitnick.servicio.servicios.dtos.ReporteVentaDto;
import com.mitnick.utils.Validator;

@Repository("ventaDao")
public class VentaDAO extends GenericDaoHibernate<Venta, Long>  implements IVentaDAO {

	public VentaDAO() {
		super(Venta.class);
	}

	@Override
	public List<Venta> findByFiltro(ReporteVentaDto filtro) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Venta.class);

		if(Validator.isNotNull(Validator.isNotNull(filtro.getFechaInicio()))){
			criteria.add(Restrictions.gt("fecha", filtro.getFechaInicio()));
		}
		if(Validator.isNotNull(filtro.getFechaFin())){
			criteria.add(Restrictions.le("fecha", filtro.getFechaFin()));
		}
		
		criteria.addOrder(Order.desc("fecha"));
		return getHibernateTemplate().findByCriteria(criteria);
	}

	public Venta saveOrUpdate(Venta venta){
		getHibernateTemplate().saveOrUpdate(venta);
		return venta;
	}
}
