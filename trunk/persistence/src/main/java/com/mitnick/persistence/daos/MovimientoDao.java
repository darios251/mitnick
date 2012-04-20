package com.mitnick.persistence.daos;

import java.util.List;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.mitnick.persistence.entities.Movimiento;
import com.mitnick.servicio.servicios.dtos.ReporteDetalleMovimientosDto;
import com.mitnick.servicio.servicios.dtos.ReporteMovimientosDto;
import com.mitnick.utils.Validator;

@Repository("movimientoDao")
public class MovimientoDao extends GenericDaoHibernate<Movimiento, Long> implements IMovimientoDao {

	public MovimientoDao() {
		super(Movimiento.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Movimiento> findByFiltro(ReporteDetalleMovimientosDto filtro) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Movimiento.class);

		if(Validator.isNotNull(filtro.getFechaInicio())){
			criteria.add(Restrictions.gt("fecha", filtro.getFechaInicio()));
		}
		if(Validator.isNotNull(filtro.getFechaFin())){
			criteria.add(Restrictions.le("fecha", filtro.getFechaFin()));
		}
		if(Validator.isNotNull(filtro.getProducto())){
			criteria.add(Restrictions.eq("producto.id", filtro.getProducto().getId()));
		}
		criteria.add(Restrictions.eq("eliminado", false));
		
		criteria.addOrder(Order.desc("fecha"));
		return getHibernateTemplate().findByCriteria(criteria);
	}

	@SuppressWarnings("unchecked")
	public List<Movimiento> findByFiltro(ReporteMovimientosDto filtro) { 
		DetachedCriteria criteria = DetachedCriteria.forClass(Movimiento.class);

		if(Validator.isNotNull(filtro.getFechaInicio())){
			criteria.add(Restrictions.gt("fecha", filtro.getFechaInicio()));
		}
		if(Validator.isNotNull(filtro.getFechaFin())){
			criteria.add(Restrictions.le("fecha", filtro.getFechaFin()));
		}
		criteria.createAlias("producto", "p");
		if(!Validator.isBlankOrNull(filtro.getCodigo())){
			criteria.add(Restrictions.eq("p.codigo", filtro.getCodigo()));
		}
		if(!Validator.isBlankOrNull(filtro.getDescripcion())){
			criteria.add(Restrictions.ilike("p.descripcion", "%" + filtro.getDescripcion() + "%"));
		}
		if(Validator.isNotNull(filtro.getMarca()) && filtro.getMarca().getId()>=0){
			criteria.add(Restrictions.eq("p.marca.id", filtro.getMarca().getId()));
		}
		if(Validator.isNotNull(filtro.getTipo()) && filtro.getTipo().getId()>=0){
			criteria.add(Restrictions.eq("p.tipo.id", filtro.getTipo().getId()));
		}
		criteria.add(Restrictions.eq("p.eliminado", false));
		
		criteria.addOrder(Order.desc("fecha"));
		return getHibernateTemplate().findByCriteria(criteria);
	}
	
	public Movimiento saveOrUpdate(Movimiento movimiento){
		getHibernateTemplate().saveOrUpdate(movimiento);
		return movimiento;
	}
}
