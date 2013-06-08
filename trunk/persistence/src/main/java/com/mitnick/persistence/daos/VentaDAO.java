package com.mitnick.persistence.daos;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import org.apache.commons.lang.StringUtils;
import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.mitnick.exceptions.PersistenceException;
import com.mitnick.persistence.entities.Credito;
import com.mitnick.persistence.entities.Empresa;
import com.mitnick.persistence.entities.Venta;
import com.mitnick.servicio.servicios.dtos.ReportesDto;
import com.mitnick.utils.DateHelper;
import com.mitnick.utils.MitnickConstants;
import com.mitnick.utils.Validator;
import com.mitnick.utils.VentaHelper;
import com.mitnick.utils.dtos.CuotaDto;
import com.mitnick.utils.dtos.PagoDto;
import com.mitnick.utils.dtos.ProductoVentaDto;
import com.mitnick.utils.dtos.VentaDto;

@Repository("ventaDao")
public class VentaDAO extends GenericDaoHibernate<Venta, Long>  implements IVentaDAO {

	public VentaDAO() {
		super(Venta.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Venta> findByFiltro(ReportesDto filtro) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Venta.class);

		if(Validator.isNotNull(Validator.isNotNull(filtro.getFechaInicio()))){
			criteria.add(Restrictions.ge("fecha", filtro.getFechaInicio()));
		}
		if(Validator.isNotNull(filtro.getFechaFin())){
			criteria.add(Restrictions.le("fecha", filtro.getFechaFin()));
		}

		criteria.add(Restrictions.eq("canceled", false));
		
		criteria.addOrder(Order.desc("fecha"));
		return getHibernateTemplate().findByCriteria(criteria);
	}

	public Venta saveOrUpdate(Venta venta){
		
		if (!venta.isCanceled() && venta.isDevolucion()){
			//SE CREA LA NOTA DE CREDITO
			Credito credito = new Credito();
			credito.setFecha(new Date());			
			credito.setMonto(venta.getPagoContado());
			credito.setMontoUsado(new BigDecimal(0));
			credito.setNumeroTicket(venta.getNumeroTicketOriginal());
			credito.setNumeroNC(venta.getNumeroTicket());
			getHibernateTemplate().saveOrUpdate(credito);
		}
		
		getHibernateTemplate().saveOrUpdate(venta);
		return venta;
	}
	
	public void actualizarCreditos(VentaDto venta) {
		if (!venta.getPagoNotaCredito().isEmpty()){
			Iterator<PagoDto> creditosUsados = venta.getPagoNotaCredito().iterator();
			while (creditosUsados.hasNext()){
				PagoDto pago = creditosUsados.next();
				usarCredito(pago.getNroNC(), pago.getMonto());
			}
		}
	}
	
	public void actualizarCreditos(List<CuotaDto> cuotas) {
		for (CuotaDto cuota : cuotas){
			for (PagoDto pago : cuota.getPagos()){
					if (!pago.isComprobante() && MitnickConstants.Medio_Pago.NOTA_CREDITO.equals(pago.getMedioPago().getCodigo())) 
						usarCredito(pago.getNroNC(), pago.getMonto());					
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public void usarCredito(String nroCredito, BigDecimal montoUsado) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Credito.class);
		
		criteria.add(Restrictions.ilike("numeroNC", nroCredito));	
		List<Credito> creditos = getHibernateTemplate().findByCriteria(criteria);
		if (creditos!=null && !creditos.isEmpty()) {
			Credito credito = creditos.get(0);
			BigDecimal montoUsadoAntes=credito.getMontoUsado();			
			credito.setMontoUsado(montoUsado.add(montoUsadoAntes));
			getHibernateTemplate().saveOrUpdate(credito);	
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public Credito getCredito(String nroCredito) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Credito.class);
		
		criteria.add(Restrictions.ilike("numeroNC", nroCredito));	
		List<Credito> creditos = getHibernateTemplate().findByCriteria(criteria);
		if (creditos!=null && !creditos.isEmpty())
			return creditos.get(0);
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<Venta> findByClient(Long cliente){
		DetachedCriteria criteria = DetachedCriteria.forClass(Venta.class);
		
		criteria.createAlias("cliente", "c");
		if(Validator.isNotNull(cliente)){
			criteria.add(Restrictions.eq("c.id", cliente));
		}		
		criteria.add(Restrictions.eq("canceled", false));

		return getHibernateTemplate().findByCriteria(criteria);
	}
	
	@SuppressWarnings("unchecked")
	public Venta findLastByClient(Long cliente) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Venta.class);
		
		criteria.createAlias("cliente", "c");
		if(Validator.isNotNull(cliente)){
			criteria.add(Restrictions.eq("c.id", cliente));
		}		
		criteria.add(Restrictions.eq("canceled", false));
		criteria.addOrder(Order.desc("fecha"));
		List<Venta> ventas = getHibernateTemplate().findByCriteria(criteria);
		if (Validator.isNotEmptyOrNull(ventas))
			return ventas.get(0);
		return null;
	}
	
	
	@SuppressWarnings("unchecked")
	public Venta findByNumeroFactura(String numeroTicket) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Venta.class);
		
		criteria.add(Restrictions.ilike("numeroTicket", numeroTicket));	
		criteria.add(Restrictions.eq("canceled", false));
		criteria.add(Restrictions.eq("tipo", MitnickConstants.VENTA));

		List<Venta> ventas = getHibernateTemplate().findByCriteria(criteria);
		if (ventas==null || ventas.isEmpty())
				return null;
		return ventas.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public Venta findTransactionByNumeroFactura(String numeroTicket) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Venta.class);
		
		criteria.add(Restrictions.ilike("numeroTicket", numeroTicket));	
		criteria.add(Restrictions.eq("canceled", false));

		List<Venta> ventas = getHibernateTemplate().findByCriteria(criteria);
		if (ventas==null || ventas.isEmpty())
				return null;
		return ventas.get(0);
	}
	
	public void generarFactura(VentaDto venta, boolean duplicado) {
		try {
			JasperReport reporte = (JasperReport) JRLoader.loadObject(this.getClass().getResourceAsStream("/reports/facturaDuplicado.jasper"));
			DetachedCriteria criteria = DetachedCriteria.forClass(Empresa.class);
			criteria.add(Restrictions.idEq(new Long(1)));
			
			Empresa empresa = (Empresa) getHibernateTemplate().findByCriteria(criteria).get(0);
			int nroFactActual = empresa.getNumeroFacturaActual();
			boolean valido = true;
			String nroTRX = venta.getNumeroTicket();
			String nroFactura = StringUtils.leftPad(empresa.getNumeroPrefijo() + "", 4, "0"); 
			if (Validator.isBlankOrNull(nroTRX)){
				nroFactActual = nroFactActual + 1;
				nroTRX = Integer.toString(nroFactActual);
				empresa.setNumeroFacturaActual(nroFactActual);
				getHibernateTemplate().save(empresa);
				valido = false;
			}
			nroFactura = nroFactura.concat("-");
			nroFactura = nroFactura.concat(StringUtils.leftPad(nroTRX+ "", 8, "0"));
					
			venta.setNumeroTicket(String.valueOf(nroFactActual));
			
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("nombreEmpresa", empresa.getNombre());
			parameters.put("empresaDireccion", empresa.getDireccion().getDomicilio() + "(" + empresa.getDireccion().getCodigoPostal() + ")" 
					+ empresa.getDireccion().getCiudad().getDescripcion() + "\n Tel" + empresa.getTelefono());
			parameters.put("tipoResponsable", empresa.getTipoResponsable());
			parameters.put("nroFactura", nroFactura);
			parameters.put("cuitEmpresa", empresa.getCuit());
			parameters.put("ingBrutos", empresa.getIngBrutos());
			parameters.put("fechaInicioActividadEmpresa", empresa.getFechaInicioActividad());
			
			parameters.put("tipoIva", venta.getTipoResponsabilidad().getDescripcion());
			
			if (Validator.isNotNull(venta.getFecha()))
				parameters.put("fechaTrx", DateHelper.getFecha(venta.getFecha()));
			else
				parameters.put("fechaTrx", DateHelper.getFecha(new Date()));
			
			if (!valido)
				parameters.put("leyenda", "Comprobante no válido como Factura");
			else
				parameters.put("leyenda", "");
			boolean consumidorFinal = venta.getTipoResponsabilidad().getTipoComprador().equals(MitnickConstants.TipoComprador.CONSUMIDOR_FINAL);
			String nombre = "";
			String direccion = "";
			String cuit = "";
			if (Validator.isNotNull(venta.getCliente())){
				nombre = venta.getCliente().getNombre();
				if (Validator.isNotNull(venta.getCliente().getDireccion())){
					direccion = venta.getCliente().getDireccion().getDomicilio();
					if (Validator.isNotNull(venta.getCliente().getDireccion().getCiudad()))
						direccion = direccion.concat(" ").concat(venta.getCliente().getDireccion().getCiudad().getDescripcion());
				}
				if (Validator.isNotNull(venta.getCliente().getCuit()))
					cuit =  venta.getCliente().getCuit().replaceAll("-", "").trim();	
				if (Validator.isNotBlankOrNull(cuit)) 
					cuit = venta.getCliente().getCuit();
				else if (Validator.isNotBlankOrNull(venta.getCliente().getDocumento()))
					cuit = venta.getCliente().getDocumento();
			}
			parameters.put("nombreCliente", nombre);
			parameters.put("direccionCliente", direccion);
			parameters.put("cuitCliente", cuit);
			String leyenda = "Factura";
			
			if (consumidorFinal){
				venta.setTipoTicket("B");
				parameters.put("tipoLetra", "B");
			} else {
				venta.setTipoTicket("A");
				parameters.put("tipoLetra", "A");
			}

			List<ProductoVentaDto> productos = null;
			if (venta.isDevolucion())
				leyenda = "Nota de Crédito";
			
			if (duplicado){
				leyenda = "Duplicado - ".concat(leyenda).concat(" - ").concat("No válido");
				productos = VentaHelper.getProductosPrecioVendido(venta);				
			} else
				productos = venta.getProductos();
				
			parameters.put("leyenda", leyenda);
			parameters.put("totalVenta", venta.getTotal().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
			JRDataSource dr = new JRBeanCollectionDataSource(productos);
										
			JasperPrint jasperPrint = JasperFillManager.fillReport(reporte,parameters, dr);
			JasperViewer.viewReport(jasperPrint,false);

		} catch (Exception e1) {
			throw new PersistenceException("error.reporte.factura.Cliente","Error al generar la factura del cliente.",e1);
		}	
	}
}
