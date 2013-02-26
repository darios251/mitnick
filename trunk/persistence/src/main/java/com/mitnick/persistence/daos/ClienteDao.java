package com.mitnick.persistence.daos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mitnick.exceptions.PersistenceException;
import com.mitnick.persistence.entities.Cliente;
import com.mitnick.persistence.entities.Comprobante;
import com.mitnick.persistence.entities.Credito;
import com.mitnick.persistence.entities.Empresa;
import com.mitnick.persistence.entities.Venta;
import com.mitnick.servicio.servicios.dtos.ConsultaClienteDto;
import com.mitnick.servicio.servicios.dtos.ReporteMovimientoClienteDto;
import com.mitnick.utils.ConstraintValidationHelper;
import com.mitnick.utils.MitnickConstants;
import com.mitnick.utils.Validator;
import com.mitnick.utils.dtos.ClienteDto;
import com.mitnick.utils.dtos.CuotaDto;
import com.mitnick.utils.dtos.PagoDto;

/**
 * Esta clase tiene la responsabilidad de representar el administrador de
 * accesos a datos persistentes de clientes.
 * 
 * @author Lucas Garc�a
 * 
 */
@Repository("clienteDao")
public class ClienteDao extends GenericDaoHibernate<Cliente, Long> implements
		IClienteDao {

	protected javax.validation.Validator entityValidator = Validation
			.buildDefaultValidatorFactory().getValidator();

	@Autowired
	protected ICuotaDao cuotaDao;

	@Autowired
	protected IVentaDAO ventaDao;

	public ClienteDao() {
		super(Cliente.class);
	}

	@SuppressWarnings("unchecked")
	public List<Cliente> findByDocumento(String documento) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Cliente.class);

		if (!Validator.isBlankOrNull(documento)) {
			criteria.add(Restrictions.ilike("documento", documento));
		}
		criteria.add(Restrictions.eq("eliminado", false));
		criteria.addOrder(Order.desc("nombre"));
		return getHibernateTemplate().findByCriteria(criteria);
	}

	@SuppressWarnings("unchecked")
	public Cliente findById(Long id) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Cliente.class);

		if (Validator.isNotNull(id)) {
			criteria.add(Restrictions.eq("id", id));
		}
		criteria.add(Restrictions.eq("eliminado", false));
		List<Cliente> clientes = getHibernateTemplate()
				.findByCriteria(criteria);
		if (clientes != null && !clientes.isEmpty())
			return clientes.get(0);
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<Comprobante> findComprobantesByClienteId(Long id) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Cliente.class);

		if (Validator.isNotNull(id)) {
			criteria.add(Restrictions.eq("id", id));
		}
		criteria.add(Restrictions.eq("eliminado", false));
		List<Cliente> clientes = getHibernateTemplate()
				.findByCriteria(criteria);
		if (clientes != null && !clientes.isEmpty())
			return clientes.get(0).getComprobantes();
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Cliente> findByFiltro(ConsultaClienteDto filtro) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Cliente.class);

		if (!Validator.isBlankOrNull(filtro.getDocumento())) {
			criteria.add(Restrictions.ilike("documento", filtro.getDocumento()
					.trim()));
		}

		if (!Validator.isBlankOrNull(filtro.getNombre())) {
			criteria.add(Restrictions.ilike("nombre", "%"
					+ filtro.getNombre().trim() + "%"));
		}

		criteria.add(Restrictions.eq("eliminado", false));
		criteria.addOrder(Order.desc("nombre"));
		return getHibernateTemplate().findByCriteria(criteria);
	}

	public Cliente saveOrUpdate(Cliente cliente) {
		Set<ConstraintViolation<Cliente>> constraintViolations = entityValidator
				.validate(cliente);
		if (Validator.isNotEmptyOrNull(constraintViolations))
			throw new PersistenceException(
					new ConstraintValidationHelper<Cliente>()
							.getMessage(constraintViolations));

		getHibernateTemplate().saveOrUpdate(cliente);
		return cliente;
	}

	@Override
	public void cargarReporte() {
		try {
			JasperReport reporte = (JasperReport) JRLoader.loadObject(this
					.getClass().getResourceAsStream(
							"/reports/listaClientes.jasper"));

			HashMap<String, Object> parameters = new HashMap<String, Object>();

			@SuppressWarnings("deprecation")
			JasperPrint jasperPrint = JasperFillManager.fillReport(reporte,
					parameters, super.getHibernateTemplate()
							.getSessionFactory().getCurrentSession()
							.connection());

			JasperViewer.viewReport(jasperPrint,false);

		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public Comprobante generarComprobante(List<CuotaDto> cuotas) {
		try {
			JasperReport reporte = (JasperReport) JRLoader.loadObject(this
					.getClass().getResourceAsStream(
							"/reports/comprobante.jasper"));
			DetachedCriteria criteria = DetachedCriteria
					.forClass(Empresa.class);
			criteria.add(Restrictions.idEq(new Long(1)));
			ClienteDto cliente = cuotas.get(0).getClienteDto();
			List<PagoDto> pagosComprobante = new ArrayList<PagoDto>();
			BigDecimal pagoComprobante = new BigDecimal(0);

			for (int i = 0; i < cuotas.size(); i++) {
				pagosComprobante.addAll(cuotas.get(i).getPagosComprobante());
				pagoComprobante = pagoComprobante.add(cuotas.get(i)
						.getPagoComprobante());
			}

			JRDataSource dr = new JRBeanCollectionDataSource(
					clean(pagosComprobante));

			Empresa empresa = (Empresa) getHibernateTemplate().findByCriteria(
					criteria).get(0);

			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("nombreEmpresa", empresa.getNombre());
			parameters.put("empresaDireccion", empresa.getDireccion()
					.getDomicilio()
					+ "("
					+ empresa.getDireccion().getCodigoPostal()
					+ ")"
					+ empresa.getDireccion().getCiudad().getDescripcion()
					+ "\n Tel" + empresa.getTelefono());
			parameters.put("tipoResponsable", empresa.getTipoResponsable());
			parameters.put("cuitEmpresa", empresa.getCuit());
			parameters.put("iibbEmpresa", empresa.getNmIngresosBrutos());
			parameters.put("fechaInicioActividadEmpresa", "01/12/1988");
			parameters.put("tipoIva", "Consumidor Final");
			parameters.put("nombreCliente", cliente.getNombre());
			parameters.put("direccionCliente", cliente.getDireccion()
					.getDomicilio()
					+ " "
					+ cliente.getDireccion().getCiudad().getDescripcion());

			BigDecimal saldoTotal = getSaldoDeudor(cliente);

			BigDecimal saldoPendiente = saldoTotal.subtract(pagoComprobante);

			parameters.put("saldoTotal", saldoTotal.toString());
			parameters.put("total", pagoComprobante.toString());
			parameters.put("saldoPendiente", saldoPendiente.toString());

			Comprobante comprobante = new Comprobante();
			String id = String.valueOf(cliente.getId()).concat(String.valueOf(cliente.getCantidadComprobantes()));
			comprobante.setId(id);
			comprobante.setFecha(new Date());
			comprobante.setTotal(pagoComprobante);
			Cliente clienteObject = findById(cliente.getId());
			clienteObject.addComprobante(comprobante);
			clienteObject = saveOrUpdate(clienteObject);
			super.getHibernateTemplate().flush();
			parameters.put("nroComprobante", id);

			JasperPrint jasperPrint = JasperFillManager.fillReport(reporte,
					parameters, dr);

			JasperViewer.viewReport(jasperPrint,false);
			
			return comprobante;
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return null;
	}

	public Comprobante saveOrUpdate(Comprobante comprobante){
		getHibernateTemplate().saveOrUpdate(comprobante);
		return comprobante;
	}
	
	private List<PagoDto> clean(List<PagoDto> pagos) {
		List<PagoDto> pagosLimpios = new ArrayList<PagoDto>();
		Iterator<PagoDto> pagosIt = pagos.iterator();
		while (pagosIt.hasNext()) {
			PagoDto pago = pagosIt.next();
			PagoDto auxiliar = new PagoDto();
			auxiliar.setMedioPago(pago.getMedioPago());
			auxiliar.setMonto(pago.getMonto());			
			PagoDto pagoDto = getPagoMedioPago(pagosLimpios, auxiliar);
			if (pagoDto == null)
				pagosLimpios.add(auxiliar);
			else
				pagoDto.setMonto(pagoDto.getMonto().add(pago.getMonto()));
		}
		return pagosLimpios;
	}

	private PagoDto getPagoMedioPago(List<PagoDto> pagos, PagoDto pago) {
		for (int i = 0; i < pagos.size(); i++) {
			if (pagos.get(i).getMedioPago().getCodigo()
					.equals(pago.getMedioPago().getCodigo()))
				return pagos.get(i);
		}
		return null;
	}

	private BigDecimal getSaldoDeudor(ClienteDto cliente) {

		return cuotaDao.getSaldoPendiente(cliente.getId());
	}
	
	private BigDecimal getSaldoFavor(ClienteDto cliente) {

		DetachedCriteria criteria = DetachedCriteria.forClass(Credito.class);
		BigDecimal aFavor = new BigDecimal(0); 
		criteria.createAlias("cliente", "c");
		if(Validator.isNotNull(cliente)){
			criteria.add(Restrictions.eq("c.id", cliente.getId()));
		}	
		
		List<Credito> creditos = getHibernateTemplate().findByCriteria(criteria);
		if (creditos!=null && !creditos.isEmpty()){
			for (int i = 0; i < creditos.size(); i++) {
				Credito credito = creditos.get(i);
				aFavor = aFavor.add(credito.getDisponible());
			}
		}
			
		return aFavor;
	}

	public void reporteMovimientosCliente(ClienteDto cliente) {
		try {
			List<Venta> ventas = ventaDao.findByClient(cliente.getId());
			List<ReporteMovimientoClienteDto> movimientos = new ArrayList<ReporteMovimientoClienteDto>();
			for (int i = 0; i < ventas.size(); i++) {
				Venta venta = ventas.get(i);
				ReporteMovimientoClienteDto movimiento = new ReporteMovimientoClienteDto();
				movimiento.setMonto(venta.getTotal());
				String nro = venta.getNumeroTicket();
				movimiento.setFecha(venta.getFecha());
				if (venta.getTipo()== MitnickConstants.VENTA){
					movimiento.setNroComprobante("Fact-" + nro);
					movimiento.setDebe(venta.getPagoCuenta());
					BigDecimal pago = venta.getPagoContado();
					pago = pago.add(venta.getPagoNC());
					movimiento.setHaber(pago);
				} else {
					movimiento.setNroComprobante("NC-" + nro);
					movimiento.setDebe(new BigDecimal(0));
					movimiento.setHaber(venta.getTotal());
				}
				movimientos.add(movimiento);
			}
			List<Comprobante> comprobantes = findComprobantesByClienteId(cliente.getId());
			for (int j = 0; j < comprobantes.size(); j++) {
				Comprobante comprobante = comprobantes.get(j);
				ReporteMovimientoClienteDto movimiento = new ReporteMovimientoClienteDto();
				movimiento.setMonto(comprobante.getTotal());
				String nro = comprobante.getId().toString();
				movimiento.setNroComprobante("Comp-" + nro);
				movimiento.setFecha(comprobante.getFecha());
				movimiento.setDebe(comprobante.getPagoCuenta());
				BigDecimal pago = comprobante.getPagoContado();
				pago = pago.add(comprobante.getPagoNC());
				movimiento.setHaber(pago);
				movimientos.add(movimiento);
			}

			JasperReport reporte = (JasperReport) JRLoader.loadObject(this
					.getClass().getResourceAsStream(
							"/reports/movimientosCliente.jasper"));
			
			JRDataSource dr = new JRBeanCollectionDataSource(
					orderByDate(movimientos));

			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("saldoDeudor", getSaldoDeudor(cliente).toString());
			parameters.put("saldoAFavor", getSaldoFavor(cliente).toString());
			parameters.put("nombreCliente", cliente.getNombre());
			parameters.put("direccionCliente", cliente.getDireccion()
					.getDomicilio()
					+ " "
					+ cliente.getDireccion().getCiudad().getDescripcion());

			JasperPrint jasperPrint = JasperFillManager.fillReport(reporte,
					parameters, dr);

			JasperViewer.viewReport(jasperPrint,false);

		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private List<ReporteMovimientoClienteDto> orderByDate(
			List<ReporteMovimientoClienteDto> movimientos) {
		return movimientos;
	}

}
