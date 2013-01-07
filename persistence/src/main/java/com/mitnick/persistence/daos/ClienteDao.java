package com.mitnick.persistence.daos;

import java.awt.Desktop;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mitnick.exceptions.PersistenceException;
import com.mitnick.persistence.entities.Cliente;
import com.mitnick.persistence.entities.Empresa;
import com.mitnick.servicio.servicios.dtos.ConsultaClienteDto;
import com.mitnick.utils.ConstraintValidationHelper;
import com.mitnick.utils.Validator;
import com.mitnick.utils.dtos.ClienteDto;
import com.mitnick.utils.dtos.CuotaDto;
import com.mitnick.utils.dtos.PagoDto;

/**
 * Esta clase tiene la responsabilidad de representar el administrador
 * de accesos a datos persistentes de clientes.
 * 
 * @author Lucas Garc�a
 *
 */
@Repository("clienteDao")
public class ClienteDao extends GenericDaoHibernate<Cliente, Long> implements IClienteDao {
	
	protected javax.validation.Validator entityValidator = Validation.buildDefaultValidatorFactory().getValidator();

	@Autowired
	protected ICuotaDao cuotaDao;

	public ClienteDao() {
		super(Cliente.class);
	}

	@SuppressWarnings("unchecked")
	public List<Cliente> findByDocumento(String documento) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Cliente.class);
		
		if(!Validator.isBlankOrNull(documento)){
			criteria.add(Restrictions.ilike("documento", documento));
		}
		criteria.add(Restrictions.eq("eliminado", false));
		criteria.addOrder(Order.desc("nombre"));
		return getHibernateTemplate().findByCriteria(criteria);
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<Cliente> findByFiltro(ConsultaClienteDto filtro) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Cliente.class);
		
		if(!Validator.isBlankOrNull(filtro.getDocumento())){
			criteria.add(Restrictions.ilike("documento", filtro.getDocumento().trim()));
		}
		
		if(!Validator.isBlankOrNull(filtro.getNombre())){
			criteria.add(Restrictions.ilike("nombre", "%" + filtro.getNombre().trim() + "%"));
		}

		criteria.add(Restrictions.eq("eliminado", false));
		criteria.addOrder(Order.desc("nombre"));
		return getHibernateTemplate().findByCriteria(criteria);
	}
	
	public Cliente saveOrUpdate(Cliente cliente){
		Set<ConstraintViolation<Cliente>> constraintViolations = entityValidator.validate(cliente);
		if(Validator.isNotEmptyOrNull(constraintViolations))
			throw new PersistenceException(new ConstraintValidationHelper<Cliente>().getMessage(constraintViolations));
		
		getHibernateTemplate().saveOrUpdate(cliente);
		return cliente;
	}

	@Override
	public void cargarReporte() {
		try {
			JasperReport reporte = (JasperReport) JRLoader.loadObject(this.getClass().getResourceAsStream("/reports/listaClientes.jasper"));
			
			HashMap<String, Object> parameters = new HashMap<String, Object>();
		    			
			@SuppressWarnings("deprecation")
			JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parameters, super.getHibernateTemplate().getSessionFactory().getCurrentSession().connection());
			
			String fileName = System.currentTimeMillis() + "-clientes.pdf";
			
			JRExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint); 
			exporter.setParameter(JRExporterParameter.OUTPUT_FILE,new java.io.File(fileName));
			exporter.exportReport();
			
			File file = new File(fileName);
			Desktop.getDesktop().open(file);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	public void generarComprobante(List<CuotaDto> cuotas) {
		try {
			JasperReport reporte = (JasperReport) JRLoader.loadObject(this.getClass().getResourceAsStream("/reports/comprobante.jasper"));
			DetachedCriteria criteria = DetachedCriteria.forClass(Empresa.class);
			criteria.add(Restrictions.idEq(new Long(1)));
			ClienteDto cliente = cuotas.get(0).getClienteDto();
			List<PagoDto> pagosComprobante = new ArrayList<PagoDto>();
			BigDecimal pagoComprobante = new BigDecimal(0);
			
			for (int i = 0; i < cuotas.size(); i++) {
				pagosComprobante.addAll(cuotas.get(i).getPagosComprobante());	
				pagoComprobante= pagoComprobante.add(cuotas.get(i).getPagoComprobante());
			}
			
			JRDataSource dr = new JRBeanCollectionDataSource(clean(pagosComprobante));
			
			Empresa empresa = (Empresa) getHibernateTemplate().findByCriteria(criteria).get(0);
			
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("nombreEmpresa", empresa.getNombre());
			parameters.put("empresaDireccion", empresa.getDireccion().getDomicilio() + "(" + empresa.getDireccion().getCodigoPostal() + ")" 
					+ empresa.getDireccion().getCiudad().getDescripcion() + "\n Tel" + empresa.getTelefono());
			parameters.put("tipoResponsable", empresa.getTipoResponsable());
			parameters.put("cuitEmpresa", empresa.getCuit());
			parameters.put("iibbEmpresa", empresa.getNmIngresosBrutos());
			parameters.put("fechaInicioActividadEmpresa", "01/12/1988");
			parameters.put("tipoIva", "Consumidor Final");
			parameters.put("nombreCliente",cliente.getNombre());
			parameters.put("direccionCliente", cliente.getDireccion().getDomicilio() + " " + cliente.getDireccion().getCiudad().getDescripcion());
			
			BigDecimal saldoTotal = getSaldoDeudor(cliente);
			
			
			BigDecimal saldoPendiente = saldoTotal.subtract(pagoComprobante);
			
			parameters.put("saldoTotal", saldoTotal.toString());
			parameters.put("total", pagoComprobante.toString());
			parameters.put("saldoPendiente", saldoPendiente.toString());
			
			super.getHibernateTemplate().flush();
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parameters, dr);
			
			JRExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint); 
			exporter.setParameter(JRExporterParameter.OUTPUT_FILE,new java.io.File(cuotas.get(0).getId() + "-comprobante.pdf"));
			exporter.exportReport();
			
			File file = new File(cuotas.get(0).getId() + "-comprobante.pdf");
			Desktop.getDesktop().open(file);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	private List<PagoDto> clean(List<PagoDto> pagos){
		List<PagoDto> pagosLimpios = new ArrayList<PagoDto>();
		Iterator<PagoDto> pagosIt = pagos.iterator();
		while (pagosIt.hasNext()){
			PagoDto pago = pagosIt.next();
			PagoDto pagoDto = getPagoMedioPago(pagosLimpios, pago);
			if (pagoDto==null)
				pagosLimpios.add(pago);
			else 
				pagoDto.setMonto(pagoDto.getMonto().add(pago.getMonto()));				
		}
		return pagosLimpios;
	}
	
	private PagoDto getPagoMedioPago(List<PagoDto> pagos, PagoDto pago){
		for (int i = 0; i < pagos.size(); i++) {
			if (pagos.get(i).getMedioPago().equals(pago.getMedioPago()))
				return pagos.get(i);
		}
		return null;
	}
	
	private BigDecimal getSaldoDeudor(ClienteDto cliente){
		
		return cuotaDao.getSaldoPendiente(cliente.getId());
	}

}
