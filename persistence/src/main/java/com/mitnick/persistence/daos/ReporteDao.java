package com.mitnick.persistence.daos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.appfuse.model.BaseObject;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.mitnick.persistence.entities.Producto;
import com.mitnick.persistence.entities.ProductoVenta;
import com.mitnick.servicio.servicios.dtos.ReporteCompraSugeridaDTO;
import com.mitnick.servicio.servicios.dtos.ReporteMovimientosDto;
import com.mitnick.utils.PropertiesManager;
import com.mitnick.utils.Validator;

@Repository("reporteDao")
public class ReporteDao extends GenericDaoHibernate<BaseObject, Serializable> implements IReporteDao {

	public ReporteDao() {
		super(BaseObject.class);
	}

	@SuppressWarnings("unchecked")
	public List<ProductoVenta> consultarVentaPorArticulo(ReporteMovimientosDto dto) {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(ProductoVenta.class);

		criteria.createAlias("producto", "p");
		
		if(Validator.isNotBlankOrNull(dto.getCodigo())){
			criteria.add(Restrictions.ilike("p.codigo", dto.getCodigo(), MatchMode.START));
		}
		if(Validator.isNotBlankOrNull(dto.getDescripcion())){
			criteria.add(Restrictions.ilike("p.descripcion", dto.getDescripcion(), MatchMode.ANYWHERE));
		}
		if(Validator.isNotNull(dto.getMarca()) && dto.getMarca().getId()>=0){
			criteria.add(Restrictions.eq("p.marca.id", dto.getMarca().getId()));
		}
		if(Validator.isNotNull(dto.getTipo()) && dto.getTipo().getId()>=0){
			criteria.add(Restrictions.eq("p.tipo.id", dto.getTipo().getId()));
		}
		
		criteria.createAlias("venta", "v");
		
		if(Validator.isNotNull(dto.getFechaInicio())){
			criteria.add(Restrictions.ge("v.fecha", dto.getFechaInicio()));
		}
		if(Validator.isNotNull(dto.getFechaFin())){
			criteria.add(Restrictions.le("v.fecha", dto.getFechaFin()));
		}
		
		criteria.add(Restrictions.ne("p.codigo", PropertiesManager.getProperty("application.producto.comodin")));
		criteria.add(Restrictions.eq("p.eliminado", false));
		criteria.add(Restrictions.eq("v.canceled", false));
		
		List<ProductoVenta> productos = getHibernateTemplate().findByCriteria(criteria); 
		
		return productos;
	}	
	
	@SuppressWarnings("unchecked")
	public List<ReporteCompraSugeridaDTO> consultarCompraSugerida(ReporteMovimientosDto dto) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Producto.class);

		if(Validator.isNotBlankOrNull(dto.getCodigo())){
			criteria.add(Restrictions.ilike("codigo", dto.getCodigo(), MatchMode.START));
		}
		if(Validator.isNotBlankOrNull(dto.getDescripcion())){
			criteria.add(Restrictions.ilike("descripcion", "%" + dto.getDescripcion() + "%"));
		}
		if(Validator.isNotNull(dto.getMarca()) && dto.getMarca().getId()>=0){
			criteria.add(Restrictions.eq("marca.id", dto.getMarca().getId()));
		}
		if(Validator.isNotNull(dto.getTipo()) && dto.getTipo().getId()>=0){
			criteria.add(Restrictions.eq("tipo.id", dto.getTipo().getId()));
		}

		criteria.add(Restrictions.ne("codigo", PropertiesManager.getProperty("application.producto.comodin")));
		criteria.add(Restrictions.eq("eliminado", false));
		List<Producto> productos = getHibernateTemplate().findByCriteria(criteria); 
		List<ReporteCompraSugeridaDTO> resultado = new ArrayList<ReporteCompraSugeridaDTO>();
		for (Producto producto : productos){
			ReporteCompraSugeridaDTO compra = new ReporteCompraSugeridaDTO();
			compra.setProductoCodigo(producto.getCodigo());
			compra.setProductoDescripcion(producto.getDescripcion());
			compra.setStockActual(producto.getStock());
			compra.setStockMinimo(producto.getStockMinimo());
			compra.setStockCompra(producto.getStockCompra());
			int sugerido = producto.getStockCompra() - producto.getStock();
			if (sugerido>0)
				compra.setCompraSugerida(sugerido);
			else
				compra.setCompraSugerida(0);
			resultado.add(compra);
		}
		return resultado;
	}
}
