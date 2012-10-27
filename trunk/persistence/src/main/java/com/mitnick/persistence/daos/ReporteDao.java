package com.mitnick.persistence.daos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.appfuse.model.BaseObject;
import org.springframework.stereotype.Repository;

import com.mitnick.persistence.entities.Producto;
import com.mitnick.persistence.entities.ProductoVenta;
import com.mitnick.persistence.entities.Venta;
import com.mitnick.servicio.servicios.dtos.ReporteVentaArticuloDTO;
import com.mitnick.servicio.servicios.dtos.ReportesDto;

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
}
