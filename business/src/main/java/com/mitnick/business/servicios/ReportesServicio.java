package com.mitnick.business.servicios;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mitnick.exceptions.BusinessException;
import com.mitnick.exceptions.PersistenceException;
import com.mitnick.persistence.daos.IMovimientoDao;
import com.mitnick.persistence.daos.IReporteDao;
import com.mitnick.persistence.daos.IVentaDAO;
import com.mitnick.persistence.entities.Movimiento;
import com.mitnick.persistence.entities.Pago;
import com.mitnick.persistence.entities.Venta;
import com.mitnick.servicio.servicios.IReportesServicio;
import com.mitnick.servicio.servicios.dtos.ReporteDetalleMovimientosDto;
import com.mitnick.servicio.servicios.dtos.ReporteMovimientosDto;
import com.mitnick.servicio.servicios.dtos.ReporteVentaArticuloDTO;
import com.mitnick.servicio.servicios.dtos.ReporteVentasResultadoDTO;
import com.mitnick.servicio.servicios.dtos.ReportesDto;
import com.mitnick.utils.MitnickConstants;
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
			} else {
				movimientoDto.setVentas(movimientoDto.getVentas() - movimiento.getCantidad());	
				movimientoDto.setStockFinal(movimientoDto.getStockFinal() - movimiento.getCantidad());
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
	
	@SuppressWarnings("unchecked")
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
				dto.setTotal(venta.getTotal().longValue());
				
				for (Pago pago: venta.getPagos()){
					total = total.add(venta.getTotal());
					dto.setTotal(venta.getTotal().longValue());
					
					if (MitnickConstants.Medio_Pago.EFECTIVO.equals(pago.getMedioPago().getCodigo())){
						dto.setTotalEfectivo(pago.getPago());
						totalEfectivo = totalEfectivo.add(new BigDecimal(pago.getPago()));
					}
						
					if (MitnickConstants.Medio_Pago.DEBITO.equals(pago.getMedioPago().getCodigo())) {
						dto.setTotalDebito(pago.getPago());
						totalDebito = totalDebito.add(new BigDecimal(pago.getPago()));
					}
						
					if (MitnickConstants.Medio_Pago.CREDITO.equals(pago.getMedioPago().getCodigo())) {
						dto.setTotalCredito(pago.getPago());
						totalCredito = totalCredito.add(new BigDecimal(pago.getPago()));
					}
						
					if (MitnickConstants.Medio_Pago.CUENTA_CORRIENTE.equals(pago.getMedioPago().getCodigo())) {
						dto.setTotalCC(pago.getPago());
						totalCC = totalCC.add(new BigDecimal(pago.getPago()));
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
			
			JRExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint); 
			exporter.setParameter(JRExporterParameter.OUTPUT_FILE,new java.io.File("ventas.pdf"));
			exporter.exportReport();
			
			File file = new File("ventas.pdf");
			Desktop.getDesktop().open(file);

			
		}
		catch(PersistenceException e) {
			throw new BusinessException(e, "Error al intentar obtener el reporte de ventas");
		} catch (JRException e) {
			throw new BusinessException("Error al intentar obtener el reporte de ventas");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	@SuppressWarnings("unchecked")
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
				Long totalRegistro = dto.getTotal().longValue() + venta.getTotal().longValue();
				dto.setTotal(totalRegistro);
				
				for (Pago pago: venta.getPagos()){
					total = total.add(venta.getTotal());
					dto.setTotal(venta.getTotal().longValue());
					
					if (MitnickConstants.Medio_Pago.EFECTIVO.equals(pago.getMedioPago().getCodigo())){
						dto.setTotalEfectivo(pago.getPago());
						totalEfectivo = totalEfectivo.add(new BigDecimal(pago.getPago()));
					}
						
					if (MitnickConstants.Medio_Pago.DEBITO.equals(pago.getMedioPago().getCodigo())) {
						dto.setTotalDebito(pago.getPago());
						totalDebito = totalDebito.add(new BigDecimal(pago.getPago()));
					}
						
					if (MitnickConstants.Medio_Pago.CREDITO.equals(pago.getMedioPago().getCodigo())) {
						dto.setTotalCredito(pago.getPago());
						totalCredito = totalCredito.add(new BigDecimal(pago.getPago()));
					}
						
					if (MitnickConstants.Medio_Pago.CUENTA_CORRIENTE.equals(pago.getMedioPago().getCodigo())) {
						dto.setTotalCC(pago.getPago());
						totalCC = totalCC.add(new BigDecimal(pago.getPago()));
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
			
			JRExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint); 
			exporter.setParameter(JRExporterParameter.OUTPUT_FILE,new java.io.File("ventas.pdf"));
			exporter.exportReport();
			
			File file = new File("ventas.pdf");
			Desktop.getDesktop().open(file);

			
		}
		catch(PersistenceException e) {
			throw new BusinessException(e, "Error al intentar obtener el reporte de ventas");
		} catch (JRException e) {
			throw new BusinessException("Error al intentar obtener el reporte de ventas");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	private ReporteVentasResultadoDTO getDTOFecha(List<ReporteVentasResultadoDTO> ingresos, Date fecha){
		for (ReporteVentasResultadoDTO dto: ingresos){
			if (dto.getFecha().equals(fecha))
				return dto;
		}		
		ReporteVentasResultadoDTO dto = new ReporteVentasResultadoDTO();
		dto.setFecha(fecha);
		dto.setTotal(new Long(0));
		ingresos.add(dto);
		return dto;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	@Override
	public void consultarListadoDeControl(ReportesDto filtro) {
		
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	@Override
	public void consultarEstadoCuentas(ReportesDto filtro) {
		
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	@Override
	public void consultarVentaPorArticulo(ReportesDto filtro) {
		
		try{
			List<ReporteVentaArticuloDTO> articulos = reporteDao.consultarVentaPorArticulo(filtro);
			
			JasperReport reporte = (JasperReport) JRLoader.loadObject(this.getClass().getResourceAsStream("/reports/ventasProducto.jasper"));
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			
			JRDataSource dr = new JRBeanCollectionDataSource(articulos);
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parameters, dr);
			
			JRExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint); 
			exporter.setParameter(JRExporterParameter.OUTPUT_FILE,new java.io.File("ventasProducto.pdf"));
			exporter.exportReport();
			
			File file = new File("ventasProducto.pdf");
			Desktop.getDesktop().open(file);

			
		}
		catch(PersistenceException e) {
			throw new BusinessException(e, "Error al intentar obtener el reporte de ventas");
		} catch (JRException e) {
			throw new BusinessException("Error al intentar obtener el reporte de ventas");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	@Override
	public void consultarStockArticulo(ReportesDto filtro) {
		
	}
	
	
}
