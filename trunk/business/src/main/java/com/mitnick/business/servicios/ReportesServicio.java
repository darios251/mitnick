package com.mitnick.business.servicios;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mitnick.exceptions.BusinessException;
import com.mitnick.exceptions.PersistenceException;
import com.mitnick.persistence.daos.ICuotaDao;
import com.mitnick.persistence.daos.IMovimientoDao;
import com.mitnick.persistence.daos.IReporteDao;
import com.mitnick.persistence.daos.IVentaDAO;
import com.mitnick.persistence.entities.Cuota;
import com.mitnick.persistence.entities.Movimiento;
import com.mitnick.persistence.entities.Pago;
import com.mitnick.persistence.entities.Venta;
import com.mitnick.servicio.servicios.IReportesServicio;
import com.mitnick.servicio.servicios.dtos.ReporteCompraSugeridaDTO;
import com.mitnick.servicio.servicios.dtos.ReporteDetalleMovimientosDto;
import com.mitnick.servicio.servicios.dtos.ReporteMovimientosDto;
import com.mitnick.servicio.servicios.dtos.ReporteVentaArticuloDTO;
import com.mitnick.servicio.servicios.dtos.ReporteVentasResultadoDTO;
import com.mitnick.servicio.servicios.dtos.ReportesDto;
import com.mitnick.utils.DateHelper;
import com.mitnick.utils.MitnickConstants;
import com.mitnick.utils.dtos.CuotaDto;
import com.mitnick.utils.dtos.MovimientoDto;
import com.mitnick.utils.dtos.MovimientoProductoDto;
import com.mitnick.utils.dtos.ProductoDto;
import com.mitnick.utils.dtos.VentaDto;

@SuppressWarnings("rawtypes")
@Service("reportesServicio")
public class ReportesServicio extends ServicioBase implements IReportesServicio {
	
	@Autowired
	protected IMovimientoDao movimientoDao;
	
	@Autowired
	protected IVentaDAO ventaDao;
	
	@Autowired
	protected IReporteDao reporteDao;
	
	@Autowired
	protected ICuotaDao cuotaDao;
	
	@Transactional(readOnly=true)
	@Override
	public List<MovimientoProductoDto> reporteMovimientosAgrupadosPorProducto(ReporteMovimientosDto filtro) {
		try{
			return agruparPorProducto(movimientoDao.findByFiltro(filtro));
		} 
		catch(PersistenceException e) {
			throw new BusinessException(e, "Error al intentar obtener el reporte de movimientos agrupados por producto");
		}
	}
	
	@Transactional(readOnly=true)
	@Override
	public void exportarMovimientosAgrupadosPorProducto(List<MovimientoProductoDto> movimientos) {
		try{
			JasperReport reporte = (JasperReport) JRLoader.loadObject(this.getClass().getResourceAsStream("/reports/movimientoProductos.jasper"));
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			
			JRDataSource dr = new JRBeanCollectionDataSource(movimientos);
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parameters, dr);			
			JasperViewer.viewReport(jasperPrint,false);

		} 
		catch(PersistenceException e) {
			throw new BusinessException(e, "Error al intentar obtener el reporte de movimientos agrupados por producto");
		} catch (JRException e) {
			throw new BusinessException("Error al intentar obtener el reporte de ventas");
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	@Override
	public List<MovimientoDto> reporteMovimientosDeProducto(ReporteDetalleMovimientosDto filtro) {
		try{
			return entityDTOParser.getDtosFromEntities(movimientoDao.findByFiltro(filtro));
		}
		catch(PersistenceException e) {
			throw new BusinessException(e, "Error al intentar obtener el reporte de movimientos de producto");
		}
	} 
	
	@Transactional(readOnly=true)
	@Override
	public void exportarMovimientosDeProducto(List<MovimientoDto> movimientos, ProductoDto producto, String stockOriginal, String stockFinal) {
		try{																										
			JasperReport reporte = (JasperReport) JRLoader.loadObject(this.getClass().getResourceAsStream("/reports/detalleMovimientoProducto.jasper"));
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			
			parameters.put("codigo", producto.getCodigo());
			parameters.put("descripcion", producto.getDescripcion());
			parameters.put("stockOriginal", stockOriginal);
			parameters.put("stockFinal", stockFinal);
			
			JRDataSource dr = new JRBeanCollectionDataSource(movimientos);
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parameters, dr);
			
			JasperViewer.viewReport(jasperPrint,false);

		} 
		catch(PersistenceException e) {
			throw new BusinessException(e, "Error al intentar obtener el reporte de movimientos agrupados por producto");
		} catch (JRException e) {
			throw new BusinessException("Error al intentar obtener el reporte de ventas");
		} 
	}
	
	/**
	 * Obtiene la lista de productos con sus movimientos sumarizados.
	 * @param movimientos
	 * @return
	 */
	private List<MovimientoProductoDto> agruparPorProducto(List<Movimiento> movimientos){
		List<MovimientoProductoDto> productos = new ArrayList<MovimientoProductoDto>();
		for (Movimiento movimiento: movimientos){
			MovimientoProductoDto movimientoDto = getDetallePorProducto(movimiento, productos);
			if (movimiento.getTipo() == Movimiento.AJUSTE) {
				movimientoDto.setAjustes(movimientoDto.getAjustes() + movimiento.getCantidad());
				movimientoDto.setStockFinal(movimientoDto.getStockFinal() + movimiento.getCantidad());
			} else  if (movimiento.getTipo() == Movimiento.VENTA){
				movimientoDto.setVentas(movimientoDto.getVentas() - movimiento.getCantidad());	
				movimientoDto.setStockFinal(movimientoDto.getStockFinal() - movimiento.getCantidad());
			}  else  if (movimiento.getTipo() == Movimiento.DEVOLUCION){
				movimientoDto.setVentas(movimientoDto.getVentas() + movimiento.getCantidad());	
				movimientoDto.setStockFinal(movimientoDto.getStockFinal() + movimiento.getCantidad());
			}
		}
		return productos;		
	}
	
	/**
	 * Obtiene el detalle de movimientos del producto para el movimiento pasado por parametro.
	 * @param movimiento
	 * @param productos
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private MovimientoProductoDto getDetallePorProducto(Movimiento movimiento, List<MovimientoProductoDto> productos){
		Iterator<MovimientoProductoDto> movProductos = productos.iterator();
		MovimientoProductoDto movimientoDto = null;
		while (movimientoDto == null && movProductos.hasNext()) {
			MovimientoProductoDto movimientoProducto = movProductos.next();
			if (movimientoProducto.getProducto().getCodigo().equals(movimiento.getProducto().getCodigo()))
				movimientoDto = movimientoProducto;
		}
		if (movimientoDto == null) {
			movimientoDto = new MovimientoProductoDto();
			movimientoDto.setAjustes(0);
			movimientoDto.setVentas(0);
			movimientoDto.setStockFinal(movimiento.getStockAlaFecha());
			movimientoDto.setStockOriginal(movimiento.getStockAlaFecha());
			movimientoDto.setProducto((ProductoDto) entityDTOParser.getDtoFromEntity(movimiento.getProducto()));
			productos.add(movimientoDto);
		}
		
		return movimientoDto;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	@Override
	public List<VentaDto> reporteVentas(ReportesDto filtro) {
		List<VentaDto> ventas = new ArrayList<VentaDto>();
		try{
			ventas.addAll(entityDTOParser.getDtosFromEntities(ventaDao.findByFiltro(filtro)));
		}
		catch(PersistenceException e) {
			throw new BusinessException(e, "Error al intentar obtener el reporte de ventas");
		}
		
		return ventas;
		
	}
	
	@Transactional(readOnly=true)
	@Override
	public void reporteIngresos(ReportesDto filtro) {
		List<ReporteVentasResultadoDTO> ingresos= new ArrayList<ReporteVentasResultadoDTO>();
		BigDecimal totalEfectivo = new BigDecimal(0);
		BigDecimal totalDebito = new BigDecimal(0);
		BigDecimal totalCredito = new BigDecimal(0);
		BigDecimal totalCC = new BigDecimal(0);
		BigDecimal total = new BigDecimal(0);
		try{
			List<Venta> ventas = ventaDao.findByFiltro(filtro);
			for (Venta venta: ventas){
				ReporteVentasResultadoDTO dto = new ReporteVentasResultadoDTO();
				dto.setFecha(venta.getFecha());
				total = total.add(venta.getTotal());
				dto.setTotal(venta.getTotal());
				
				for (Pago pago: venta.getPagos()){
					BigDecimal parcialTotal = pago.getPago();
					
					if (MitnickConstants.Medio_Pago.EFECTIVO.equals(pago.getMedioPago().getCodigo())){
						parcialTotal = parcialTotal.add(dto.getTotalEfectivo());
						dto.setTotalEfectivo(parcialTotal);
						totalEfectivo = totalEfectivo.add(pago.getPago());
					}
						
					if (MitnickConstants.Medio_Pago.DEBITO.equals(pago.getMedioPago().getCodigo())) {
						parcialTotal = parcialTotal.add(dto.getTotalDebito());
						dto.setTotalDebito(parcialTotal);
						totalDebito = totalDebito.add(pago.getPago());
					}
						
					if (MitnickConstants.Medio_Pago.CREDITO.equals(pago.getMedioPago().getCodigo())) {
						parcialTotal = parcialTotal.add(dto.getTotalCredito());
						dto.setTotalCredito(parcialTotal);
						totalCredito = totalCredito.add(pago.getPago());
					}
					
					
					if (MitnickConstants.Medio_Pago.CUENTA_CORRIENTE.equals(pago.getMedioPago().getCodigo())) {
						parcialTotal = parcialTotal.add(dto.getTotalCC());
						dto.setTotalCC(parcialTotal);
						totalCC = totalCC.add(pago.getPago());
					}				
						
				}
				ingresos.add(dto);				
			}
			JasperReport reporte = (JasperReport) JRLoader.loadObject(this.getClass().getResourceAsStream("/reports/ventas.jasper"));
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("totalEfectivo", totalEfectivo.toString());
			parameters.put("totalDebito", totalDebito.toString());
			parameters.put("totalCredito", totalCredito.toString());
			parameters.put("totalCC", totalCC.toString());
			parameters.put("total", total.toString());
			
			JRDataSource dr = new JRBeanCollectionDataSource(ingresos);
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parameters, dr);
			JasperViewer.viewReport(jasperPrint,false);
			
		}
		catch(PersistenceException e) {
			throw new BusinessException(e, "Error al intentar obtener el reporte de ventas");
		} catch (JRException e) {
			throw new BusinessException("Error al intentar obtener el reporte de ventas");
		} 
		
	}
	
	@Transactional(readOnly=true)
	@Override
	public void reporteIngresosAgrupados(ReportesDto filtro) {
		List<ReporteVentasResultadoDTO> ingresos= new ArrayList<ReporteVentasResultadoDTO>();
		BigDecimal totalEfectivo = new BigDecimal(0);
		BigDecimal totalDebito = new BigDecimal(0);
		BigDecimal totalCredito = new BigDecimal(0);
		BigDecimal totalCC = new BigDecimal(0);
		BigDecimal total = new BigDecimal(0);
		try{
			List<Venta> ventas = ventaDao.findByFiltro(filtro);
			for (Venta venta: ventas){
				ReporteVentasResultadoDTO dto = getDTOFecha(ingresos, venta.getFecha());
				BigDecimal totalRegistro = dto.getTotal().add(venta.getTotal());
				dto.setTotal(totalRegistro);
				total = total.add(venta.getTotal());
				for (Pago pago: venta.getPagos()){
					BigDecimal parcialTotal = pago.getPago();
					
					if (MitnickConstants.Medio_Pago.EFECTIVO.equals(pago.getMedioPago().getCodigo())){
						parcialTotal = parcialTotal.add(dto.getTotalEfectivo());
						dto.setTotalEfectivo(parcialTotal);
						totalEfectivo = totalEfectivo.add(pago.getPago());
					}
						
					if (MitnickConstants.Medio_Pago.DEBITO.equals(pago.getMedioPago().getCodigo())) {
						parcialTotal = parcialTotal.add(dto.getTotalDebito());
						dto.setTotalDebito(parcialTotal);
						totalDebito = totalDebito.add(pago.getPago());
					}
						
					if (MitnickConstants.Medio_Pago.CREDITO.equals(pago.getMedioPago().getCodigo())) {
						parcialTotal = parcialTotal.add(dto.getTotalCredito());
						dto.setTotalCredito(parcialTotal);
						totalCredito = totalCredito.add(pago.getPago());
					}
					
					
					if (MitnickConstants.Medio_Pago.CUENTA_CORRIENTE.equals(pago.getMedioPago().getCodigo())) {
						parcialTotal = parcialTotal.add(dto.getTotalCC());
						dto.setTotalCC(parcialTotal);
						totalCC = totalCC.add(pago.getPago());
					}
				}
						
			}
			JasperReport reporte = (JasperReport) JRLoader.loadObject(this.getClass().getResourceAsStream("/reports/ventas.jasper"));
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("totalEfectivo", totalEfectivo.toString());
			parameters.put("totalDebito", totalDebito.toString());
			parameters.put("totalCredito", totalCredito.toString());
			parameters.put("totalCC", totalCC.toString());
			parameters.put("total", total.toString());
			
			JRDataSource dr = new JRBeanCollectionDataSource(ingresos);
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parameters, dr);
			
			JasperViewer.viewReport(jasperPrint,false);
			
		}
		catch(PersistenceException e) {
			throw new BusinessException(e, "Error al intentar obtener el reporte de ventas");
		} catch (JRException e) {
			throw new BusinessException("Error al intentar obtener el reporte de ventas");
		}
	}
	
	
	@Transactional(readOnly=true)
	@Override
	public void reporteIngresosAnual(ReportesDto filtro) {
		List<ReporteVentasResultadoDTO> ingresos= new ArrayList<ReporteVentasResultadoDTO>();
		BigDecimal totalEfectivo = new BigDecimal(0);
		BigDecimal totalDebito = new BigDecimal(0);
		BigDecimal totalCredito = new BigDecimal(0);
		BigDecimal totalCC = new BigDecimal(0);
		BigDecimal total = new BigDecimal(0);
		try{
			List<Venta> ventas = ventaDao.findByFiltro(filtro);
			for (Venta venta: ventas){
				ReporteVentasResultadoDTO dto = getDTOMes(ingresos, venta.getFecha());
				BigDecimal totalRegistro = dto.getTotal().add(venta.getTotal());
				dto.setTotal(totalRegistro);
				total = total.add(venta.getTotal());
				for (Pago pago: venta.getPagos()){
					BigDecimal parcialTotal = pago.getPago();
					
					if (MitnickConstants.Medio_Pago.EFECTIVO.equals(pago.getMedioPago().getCodigo())){
						parcialTotal = parcialTotal.add(dto.getTotalEfectivo());
						dto.setTotalEfectivo(parcialTotal);
						totalEfectivo = totalEfectivo.add(pago.getPago());
					}
						
					if (MitnickConstants.Medio_Pago.DEBITO.equals(pago.getMedioPago().getCodigo())) {
						parcialTotal = parcialTotal.add(dto.getTotalDebito());
						dto.setTotalDebito(parcialTotal);
						totalDebito = totalDebito.add(pago.getPago());
					}
						
					if (MitnickConstants.Medio_Pago.CREDITO.equals(pago.getMedioPago().getCodigo())) {
						parcialTotal = parcialTotal.add(dto.getTotalCredito());
						dto.setTotalCredito(parcialTotal);
						totalCredito = totalCredito.add(pago.getPago());
					}
					
					
					if (MitnickConstants.Medio_Pago.CUENTA_CORRIENTE.equals(pago.getMedioPago().getCodigo())) {
						parcialTotal = parcialTotal.add(dto.getTotalCC());
						dto.setTotalCC(parcialTotal);
						totalCC = totalCC.add(pago.getPago());
					}
				}
						
			}
			JasperReport reporte = (JasperReport) JRLoader.loadObject(this.getClass().getResourceAsStream("/reports/ventasAnual.jasper"));
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("totalEfectivo", totalEfectivo.toString());
			parameters.put("totalDebito", totalDebito.toString());
			parameters.put("totalCredito", totalCredito.toString());
			parameters.put("totalCC", totalCC.toString());
			parameters.put("total", total.toString());
			
			JRDataSource dr = new JRBeanCollectionDataSource(ingresos);
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parameters, dr);
			
			JasperViewer.viewReport(jasperPrint,false);
			
		}
		catch(PersistenceException e) {
			throw new BusinessException(e, "Error al intentar obtener el reporte de ventas");
		} catch (JRException e) {
			throw new BusinessException("Error al intentar obtener el reporte de ventas");
		} 
	}

	private ReporteVentasResultadoDTO getDTOFecha(List<ReporteVentasResultadoDTO> ingresos, Date fecha){
		for (ReporteVentasResultadoDTO dto: ingresos){
			String fechaA = DateHelper.getFecha(fecha);
			String fechaB = DateHelper.getFecha(dto.getFecha());
			if (fechaA.equals(fechaB))
				return dto;
		}		
		ReporteVentasResultadoDTO dto = new ReporteVentasResultadoDTO();
		dto.setFecha(fecha);
		dto.setTotal(new BigDecimal(0));
		ingresos.add(dto);
		return dto;
	}
	
	private ReporteVentasResultadoDTO getDTOMes(List<ReporteVentasResultadoDTO> ingresos, Date fecha){
		for (ReporteVentasResultadoDTO dto: ingresos){
			Calendar calendario = Calendar.getInstance();
			calendario.setTime(fecha);
			int month = calendario.get(Calendar.MONTH);
			int year = calendario.get(Calendar.YEAR); 
			Calendar calendario2 = Calendar.getInstance();
			calendario2.setTime(dto.getFecha());
			int month2 = calendario2.get(Calendar.MONTH);
			int year2 = calendario2.get(Calendar.YEAR); 

			if (month==month2 && year==year2)
				return dto;
		}		
		ReporteVentasResultadoDTO dto = new ReporteVentasResultadoDTO();
		dto.setFecha(fecha);
		dto.setTotal(new BigDecimal(0));
		ingresos.add(dto);
		return dto;
	}
	
	
	@Transactional(readOnly=true)
	@Override
	public void consultarListadoDeControl(ReportesDto filtro) {
		List<Cuota> cuotas = cuotaDao.getCuotaPagas(filtro);
		List<ReporteVentasResultadoDTO> ingresos= new ArrayList<ReporteVentasResultadoDTO>();
		BigDecimal totalEfectivo = new BigDecimal(0);
		BigDecimal totalDebito = new BigDecimal(0);
		BigDecimal totalCredito = new BigDecimal(0);
		BigDecimal total = new BigDecimal(0);
		try{
			for (Cuota cuota: cuotas){
				ReporteVentasResultadoDTO dto = getDTOFecha(ingresos, cuota.getFechaPago());
				BigDecimal totalRegistro = dto.getTotal().add(cuota.getTotal());
				dto.setTotal(totalRegistro);
				total = total.add(cuota.getTotal());
				for (Pago pago: cuota.getPagos()){
					BigDecimal parcialTotal = pago.getPago();
					
					if (MitnickConstants.Medio_Pago.EFECTIVO.equals(pago.getMedioPago().getCodigo())){
						parcialTotal = parcialTotal.add(dto.getTotalEfectivo());
						dto.setTotalEfectivo(parcialTotal);
						totalEfectivo = totalEfectivo.add(pago.getPago());
					}
						
					if (MitnickConstants.Medio_Pago.DEBITO.equals(pago.getMedioPago().getCodigo())) {
						parcialTotal = parcialTotal.add(dto.getTotalDebito());
						dto.setTotalDebito(parcialTotal);
						totalDebito = totalDebito.add(pago.getPago());
					}
						
					if (MitnickConstants.Medio_Pago.CREDITO.equals(pago.getMedioPago().getCodigo())) {
						parcialTotal = parcialTotal.add(dto.getTotalCredito());
						dto.setTotalCredito(parcialTotal);
						totalCredito = totalCredito.add(pago.getPago());
					}
				}
						
			}
			JasperReport reporte = (JasperReport) JRLoader.loadObject(this.getClass().getResourceAsStream("/reports/listadoControl.jasper"));
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("totalEfectivo", totalEfectivo.toString());
			parameters.put("totalDebito", totalDebito.toString());
			parameters.put("totalCredito", totalCredito.toString());
			parameters.put("total", total.toString());
			
			JRDataSource dr = new JRBeanCollectionDataSource(ingresos);
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parameters, dr);
			
			JasperViewer.viewReport(jasperPrint,false);

		}
		catch(PersistenceException e) {
			throw new BusinessException(e, "Error al intentar obtener el reporte de ventas");
		} catch (JRException e) {
			throw new BusinessException("Error al intentar obtener el reporte de ventas");
		} 
		
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	@Override
	public void consultarEstadoCuentas(ReportesDto filtro) {
		List<CuotaDto> cuotas = new ArrayList<CuotaDto>();
		try{
			cuotas.addAll(entityDTOParser.getDtosFromEntities(cuotaDao.findByFiltro(filtro)));
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			
			JasperReport reporte = (JasperReport) JRLoader.loadObject(this.getClass().getResourceAsStream("/reports/estadoCuenta.jasper"));
			
			JRDataSource dr = new JRBeanCollectionDataSource(cuotas);
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parameters, dr);
			
			JasperViewer.viewReport(jasperPrint,false);

		}
		catch(PersistenceException e) {
			throw new BusinessException(e, "Error al intentar obtener el reporte de ventas");
		} catch (JRException e) {
			throw new BusinessException("Error al intentar obtener el reporte de ventas");
		} 
	}
	
	
	@Transactional(readOnly=true)
	@Override
	public void consultarVentaPorArticulo(ReportesDto filtro) {
		
		try{
			List<ReporteVentaArticuloDTO> articulos = reporteDao.consultarVentaPorArticulo(filtro);
			
			JasperReport reporte = (JasperReport) JRLoader.loadObject(this.getClass().getResourceAsStream("/reports/ventasProducto.jasper"));
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			
			JRDataSource dr = new JRBeanCollectionDataSource(articulos);
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parameters, dr);
			
			JasperViewer.viewReport(jasperPrint,false);
	
		}
		catch(PersistenceException e) {
			throw new BusinessException(e, "Error al intentar obtener el reporte de ventas");
		} catch (JRException e) {
			throw new BusinessException("Error al intentar obtener el reporte de ventas");
		}
	}
	
	@Transactional(readOnly=true)
	@Override
	public void consultarVentaZapatillaPorTalle(ReportesDto filtro) {
		
		try{
			List<ReporteVentaArticuloDTO> articulos = reporteDao.consultarVentaPorZapatillas(filtro);
			
			JasperReport reporte = (JasperReport) JRLoader.loadObject(this.getClass().getResourceAsStream("/reports/ventasZapatillas.jasper"));
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			
			JRDataSource dr = new JRBeanCollectionDataSource(articulos);
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parameters, dr);
			
			JasperViewer.viewReport(jasperPrint,false);
		
		}
		catch(PersistenceException e) {
			throw new BusinessException(e, "Error al intentar obtener el reporte de ventas");
		} catch (JRException e) {
			throw new BusinessException("Error al intentar obtener el reporte de ventas");
		} 	
	}
	
	public void exportarCompraSugerida(ReporteMovimientosDto dto) {
		try{
			List<ReporteCompraSugeridaDTO> articulos = reporteDao.consultarCompraSugerida(dto);
			
			JasperReport reporte = (JasperReport) JRLoader.loadObject(this.getClass().getResourceAsStream("/reports/compraSugerida.jasper"));
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			
			JRDataSource dr = new JRBeanCollectionDataSource(articulos);
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parameters, dr);
			
			JasperViewer.viewReport(jasperPrint,false);
		
		}
		catch(PersistenceException e) {
			throw new BusinessException(e, "Error al intentar obtener el reporte de ventas");
		} catch (JRException e) {
			throw new BusinessException("Error al intentar obtener el reporte de ventas");
		} 		
	}
		
}
