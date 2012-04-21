package com.mitnick.business.servicios;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mitnick.exceptions.BusinessException;
import com.mitnick.exceptions.PersistenceException;
import com.mitnick.persistence.daos.IMovimientoDao;
import com.mitnick.persistence.daos.IVentaDAO;
import com.mitnick.persistence.entities.Movimiento;
import com.mitnick.servicio.servicios.IReportesServicio;
import com.mitnick.servicio.servicios.dtos.ReporteDetalleMovimientosDto;
import com.mitnick.servicio.servicios.dtos.ReporteMovimientosDto;
import com.mitnick.servicio.servicios.dtos.ReporteVentaDto;
import com.mitnick.utils.dtos.MovimientoDto;
import com.mitnick.utils.dtos.MovimientoProductoDto;
import com.mitnick.utils.dtos.ProductoDto;
import com.mitnick.utils.dtos.VentaDto;

@SuppressWarnings("rawtypes")
@Service("reportesServicio")
public class ReportesServicio extends ServicioBase implements IReportesServicio {
	
	@Autowired
	protected IMovimientoDao movimientoDao;
	
	protected IVentaDAO ventaDao;
	
	@Transactional(readOnly=true)
	@Override
	public List<MovimientoProductoDto> reporteMovimientosAgrupadosPorProducto(ReporteMovimientosDto filtro) {
		try{
			return agruparPorProducto(movimientoDao.findByFiltro(filtro));
		} 
		catch(PersistenceException e) {
			throw new BusinessException(e, "Error al intentar obtener el reporte de movimientos agrupados por producto");
		}
		catch (Exception e) {
			throw new BusinessException("error.persistence", "Error en capa de persistencia de  movimiento", e);
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
		catch (Exception e) {
			throw new BusinessException("error.persistence", "Error en capa de persistencia de  movimiento", e);
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
	public List<VentaDto> reporteVentas(ReporteVentaDto filtro) {
		List<VentaDto> ventas = new ArrayList<VentaDto>();
		try{
			ventas.addAll(entityDTOParser.getDtosFromEntities(ventaDao.findByFiltro(filtro)));
		}
		catch(PersistenceException e) {
			throw new BusinessException(e, "Error al intentar obtener el reporte de ventas");
		}
		catch (Exception e) {
			throw new BusinessException("error.persistence", "Error en capa de persistencia de  cliente", e);
		}
		
		return ventas;
		
	}

	public void setMovimientoDao(IMovimientoDao movimientoDao) {
		this.movimientoDao = movimientoDao;
	}


	public void setVentaDao(IVentaDAO ventaDao) {
		this.ventaDao = ventaDao;
	}
	
}
