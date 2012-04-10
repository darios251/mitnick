package com.mitnick.servicio.servicios;

import java.util.List;

import com.mitnick.servicio.servicios.dtos.ReporteMovimientosDto;
import com.mitnick.servicio.servicios.dtos.ReporteVentaDto;
import com.mitnick.utils.dtos.MovimientoDto;
import com.mitnick.utils.dtos.VentaDto;

public interface IReportesServicio {
	
	public List<MovimientoDto> reporteMovimientos(ReporteMovimientosDto filtro);
	
	public List<VentaDto> reporteVentas(ReporteVentaDto filtro);

}
