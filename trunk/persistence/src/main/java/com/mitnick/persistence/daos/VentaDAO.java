package com.mitnick.persistence.daos;

import java.awt.Desktop;
import java.io.File;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.commons.lang.StringUtils;
import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.mitnick.persistence.entities.Empresa;
import com.mitnick.persistence.entities.Venta;
import com.mitnick.servicio.servicios.dtos.ReporteVentaDto;
import com.mitnick.utils.Validator;

@Repository("ventaDao")
public class VentaDAO extends GenericDaoHibernate<Venta, Long>  implements IVentaDAO {

	public VentaDAO() {
		super(Venta.class);
	}

	@SuppressWarnings("unchecked")
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
	
	public void generarFactura(Venta venta) {
		try {
			JasperReport reporte = (JasperReport) JRLoader.loadObject(this.getClass().getResourceAsStream("/reports/factura.jasper"));
			DetachedCriteria criteria = DetachedCriteria.forClass(Empresa.class);
			criteria.add(Restrictions.idEq(new Long(1)));
			
			Empresa empresa = (Empresa) getHibernateTemplate().findByCriteria(criteria).get(0);
			
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("nombreEmpresa", empresa.getNombre());
			parameters.put("empresaDireccion", empresa.getDireccion().getDomicilio() + "(" + empresa.getDireccion().getCodigoPostal() + ")" 
					+ empresa.getDireccion().getCiudad().getDescripcion() + "\n Tel" + empresa.getTelefono());
			parameters.put("tipoResponsable", empresa.getTipoResponsable());
			parameters.put("facturaNumero1", StringUtils.leftPad(empresa.getNumeroPrefijoFacturaActual() + "", 4, "0") );
			parameters.put("facturaNumero2", StringUtils.leftPad(empresa.getNumeroFacturaActual() + "", 8, "0"));
			parameters.put("cuitEmpresa", empresa.getCuit());
			parameters.put("iibbEmpresa", empresa.getNmIngresosBrutos());
			parameters.put("fechaInicioActividadEmpresa", "01/12/1988");
			parameters.put("tipoIva", "Consumidor Final");
			parameters.put("nombreCliente", venta.getCliente().getApellido() + " " + venta.getCliente().getNombre());
			parameters.put("direccionCliente", venta.getCliente().getDireccion().getDomicilio() + " " + venta.getCliente().getDireccion().getCiudad().getDescripcion());
			parameters.put("cuitCliente", venta.getCliente().getCuit());
			parameters.put("ventaId", venta.getId().toString());
			parameters.put("totalVenta", venta.getTotal().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
			
			super.getHibernateTemplate().flush();
			
			@SuppressWarnings("deprecation")
			JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parameters, super.getHibernateTemplate().getSessionFactory().getCurrentSession().connection());
			
			JRExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint); 
			exporter.setParameter(JRExporterParameter.OUTPUT_FILE,new java.io.File(venta.getId() + "-factura.pdf"));
			exporter.exportReport();
			
			File file = new File(venta.getId() + "-factura.pdf");
			Desktop.getDesktop().open(file);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}
