package com.mitnick.persistence.daos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.appfuse.model.BaseObject;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.mitnick.persistence.entities.Producto;
import com.mitnick.persistence.entities.ProductoVenta;
import com.mitnick.persistence.entities.Venta;
import com.mitnick.servicio.servicios.dtos.ReporteCompraSugeridaDTO;
import com.mitnick.servicio.servicios.dtos.ReporteMovimientosDto;
import com.mitnick.servicio.servicios.dtos.ReporteVentaArticuloDTO;
import com.mitnick.servicio.servicios.dtos.ReportesDto;
import com.mitnick.utils.PropertiesManager;
import com.mitnick.utils.Validator;

@Repository("reporteDao")
public class ReporteDao extends GenericDaoHibernate<BaseObject, Serializable> implements IReporteDao {

	public ReporteDao() {
		super(BaseObject.class);
	}

	public List<ReporteVentaArticuloDTO> consultarVentaPorArticulo(ReportesDto filtro) {
		String hql = "SELECT p.codigo, p.descripcion, sum(pv.cantidad), sum(pv.precio), v.fecha from " +
				Producto.class.getName() + " as p," + ProductoVenta.class.getName() + " as pv, " +
				Venta.class.getName() + " as v where pv.producto.id=p.id and pv.venta.id=v.id " +
				" group by p.codigo, p.descripcion, pv.cantidad, pv.precio, v.fecha ";
		
		@SuppressWarnings("unchecked")
		List<Object[]> items = getHibernateTemplate().find(hql);
		List<ReporteVentaArticuloDTO> resultado = new ArrayList<ReporteVentaArticuloDTO>();
		if (items!=null && !items.isEmpty()){
			for (Object[] item: items){
				ReporteVentaArticuloDTO dto = getDTOFecha(resultado, item);
				int cantidad = dto.getCantidad();
				dto.setCantidad(cantidad + Integer.parseInt(item[2].toString()));
				Double total = dto.getTotal();
				dto.setTotal(total + Double.parseDouble(item[3].toString()));
			}
			
		}
		
		return resultado;
	}	
	
	public List<ReporteVentaArticuloDTO> consultarVentaPorZapatillas(ReportesDto filtro) {
		String hql = "SELECT p.codigo, p.descripcion, p.talle, sum(pv.cantidad), sum(pv.precio) from " +
				Producto.class.getName() + " as p," + ProductoVenta.class.getName() + " as pv, " +
				Venta.class.getName() + " as v where pv.producto.id=p.id and pv.venta.id=v.id  and p.tipo.id=4" +
				" group by p.codigo, p.descripcion, p.talle, pv.cantidad, pv.precio";
		
		@SuppressWarnings("unchecked")
		List<Object[]> items = getHibernateTemplate().find(hql);
		List<ReporteVentaArticuloDTO> resultado = new ArrayList<ReporteVentaArticuloDTO>();
		if (items!=null && !items.isEmpty()){
			for (Object[] item: items){
				ReporteVentaArticuloDTO dto = getDTOZapatilla(resultado, item);				
				int cantidad = dto.getCantidad();
				dto.setCantidad(cantidad + Integer.parseInt(item[3].toString()));
				Double total = dto.getTotal();
				dto.setTotal(total + Double.parseDouble(item[4].toString()));
				
			}
			
		}
		
		return resultado;
	}
	
	private ReporteVentaArticuloDTO getDTOFecha(List<ReporteVentaArticuloDTO> items, Object[] item){
		for (ReporteVentaArticuloDTO dto: items){
			if (dto.getFecha().equals((Date)item[4]) && dto.getProductoCodigo().equals(item[0].toString()))
				return dto;
		}		
		ReporteVentaArticuloDTO dto = new ReporteVentaArticuloDTO();
		dto.setProductoCodigo(item[0].toString());
		dto.setProductoDescripcion(item[1].toString());
		dto.setCantidad(0);
		dto.setTotal(new Double(0));
		dto.setFecha((Date)item[4]);
		items.add(dto);
		return dto;
	}
	
	private ReporteVentaArticuloDTO getDTOZapatilla(List<ReporteVentaArticuloDTO> items, Object[] item){
		for (ReporteVentaArticuloDTO dto: items){
			if (dto.getProductoCodigo().equals(item[0].toString()))
				return dto;
		}		
		ReporteVentaArticuloDTO dto = new ReporteVentaArticuloDTO();
		dto.setProductoCodigo(item[0].toString());
		dto.setProductoDescripcion(item[1].toString());
		dto.setTalle(item[2].toString());
		dto.setCantidad(0);
		dto.setTotal(new Double(0));		
		items.add(dto);
		return dto;
	}
	
	public List<ReporteCompraSugeridaDTO> consultarCompraSugerida(ReporteMovimientosDto dto) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Producto.class);

		if(!Validator.isBlankOrNull(dto.getCodigo())){
			criteria.add(Restrictions.eq("codigo", dto.getCodigo()));
		}
		if(!Validator.isBlankOrNull(dto.getDescripcion())){
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
