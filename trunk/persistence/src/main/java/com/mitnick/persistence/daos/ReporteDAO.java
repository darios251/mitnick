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
			
		
		List<Object[]> items = getHibernateTemplate().find(hql);
		List<ReporteVentaArticuloDTO> resultado = new ArrayList<ReporteVentaArticuloDTO>();
		if (items!=null && !items.isEmpty()){
			for (Object[] item: items){
				ReporteVentaArticuloDTO dto = new ReporteVentaArticuloDTO();
				dto.setProductoCodigo(item[0].toString());
				dto.setProductoDescripcion(item[1].toString());
				dto.setCantidad(Integer.parseInt(item[2].toString()));
				dto.setTotal(Double.parseDouble(item[3].toString()));
				dto.setFecha((Date)item[4]);
				resultado.add(dto);
			}
			
		}
		
		return resultado;
	}
}
