package com.mitnick.persistence.daos;

import java.util.List;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
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
			criteria.add(Restrictions.ge("fecha", filtro.getFechaInicio()));
		}
		if(Validator.isNotNull(filtro.getFechaFin())){
			criteria.add(Restrictions.le("fecha", filtro.getFechaFin()));
		}
		criteria.createAlias("producto", "p");
		if(Validator.isNotNull(filtro.getProducto())){
			criteria.add(Restrictions.eq("p.id", filtro.getProducto().getId()));
		}
		criteria.add(Restrictions.eq("p.eliminado", false));
		criteria.add(Restrictions.eq("eliminado", false));
//		criteria.addOrder(Order.desc("fecha"));
		criteria.addOrder(Order.asc("p.codigo"));
		return getHibernateTemplate().findByCriteria(criteria);
	}
	
	

	@SuppressWarnings("unchecked")
	public List<Movimiento> findByFiltro(ReporteMovimientosDto filtro) { 
		DetachedCriteria criteria = DetachedCriteria.forClass(Movimiento.class);

		if(Validator.isNotNull(filtro.getFechaInicio())){
			criteria.add(Restrictions.ge("fecha", filtro.getFechaInicio()));
		}
		if(Validator.isNotNull(filtro.getFechaFin())){
			criteria.add(Restrictions.le("fecha", filtro.getFechaFin()));
		}
		criteria.createAlias("producto", "p");
		if(Validator.isNotBlankOrNull(filtro.getCodigo())){
			criteria.add(Restrictions.ilike("p.codigo", filtro.getCodigo(), MatchMode.START));
		}
		if(Validator.isNotBlankOrNull(filtro.getDescripcion())){
			criteria.add(Restrictions.ilike("p.descripcion", filtro.getDescripcion(), MatchMode.ANYWHERE));
		}
		if(Validator.isNotNull(filtro.getMarca()) && filtro.getMarca().getId()>=0){
			criteria.add(Restrictions.eq("p.marca.id", filtro.getMarca().getId()));
		}
		if(Validator.isNotNull(filtro.getTipo()) && filtro.getTipo().getId()>=0){
			criteria.add(Restrictions.eq("p.tipo.id", filtro.getTipo().getId()));
		}
		criteria.add(Restrictions.eq("p.eliminado", false));
		criteria.add(Restrictions.eq("eliminado", false));
		
//		criteria.addOrder(Order.desc("fecha"));
		criteria.addOrder(Order.asc("p.codigo"));
		return getHibernateTemplate().findByCriteria(criteria);
	}
	
	public Movimiento saveOrUpdate(Movimiento movimiento){
		getHibernateTemplate().saveOrUpdate(movimiento);
		return movimiento;
	}
	
	@SuppressWarnings("unchecked")
	public void removeAll(Long idProducto){
		DetachedCriteria criteria = DetachedCriteria.forClass(Movimiento.class);
		criteria.createAlias("producto", "p");
		criteria.add(Restrictions.eq("p.id", idProducto));
		criteria.add(Restrictions.eq("p.eliminado", false));
		criteria.add(Restrictions.eq("eliminado", false));
		
		List<Movimiento> movimientos = getHibernateTemplate().findByCriteria(criteria);
		for (Movimiento movimiento : movimientos) {
			movimiento.setEliminado(true);
			getHibernateTemplate().saveOrUpdate(movimiento);
		}
			
	}
}
