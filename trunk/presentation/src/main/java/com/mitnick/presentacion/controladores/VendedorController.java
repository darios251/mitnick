package com.mitnick.presentacion.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.mitnick.exceptions.BusinessException;
import com.mitnick.exceptions.PresentationException;
import com.mitnick.presentacion.vistas.VendedorView;
import com.mitnick.presentacion.vistas.paneles.VendedorNuevoPanel;
import com.mitnick.presentacion.vistas.paneles.VendedorPanel;
import com.mitnick.servicio.servicios.IVendedorServicio;
import com.mitnick.servicio.servicios.dtos.ConsultaVendedorDto;
import com.mitnick.utils.Validator;
import com.mitnick.utils.dtos.VendedorDto;

@Controller("vendedorController")
public class VendedorController extends BaseController {
	
	@Autowired
	private VendedorView vendedorView;
	@Autowired
	private VendedorPanel vendedorPanel;
	@Autowired
	private VendedorNuevoPanel vendedorNuevoPanel;

	@Autowired
	private IVendedorServicio vendedorServicio;
	
	public VendedorController() {
		
	}
	
	public void mostrarVendedorNuevoPanel() {
		logger.info("Mostrando el panel de vendedor nuevo");
		ultimoPanelMostrado = vendedorNuevoPanel;
		vendedorPanel.setVisible(false);
		vendedorNuevoPanel.setVisible(true);
		vendedorNuevoPanel.actualizarPantalla();
	}

	public void mostrarVendedorPanel() {
		logger.info("Mostrando el panel de vendedores");
		ultimoPanelMostrado = vendedorPanel;
		vendedorNuevoPanel.setVisible(false);
		vendedorPanel.setVisible(true);
		vendedorPanel.actualizarPantalla();
	}
	
	public List<VendedorDto> getVendedoresByFilter(ConsultaVendedorDto dto) {
		logger.debug("Entrando al método getVendedorByFilter con: " + dto);
		
		
		List<VendedorDto> resultado = null;
		try {
			resultado = vendedorServicio.consultaVendedor(dto);
		}
		catch(BusinessException e) {
			throw new PresentationException(e);
		}
		
		// chequeo si se encontro o no algo en la busqueda
		if(resultado == null || resultado.isEmpty())
			throw new PresentationException("error.vendedor.consulta.noExiste");
		
		return resultado;
	}
	
	public void guardarVendedor(VendedorDto vendedor, String codigo, String nombre) {
		if(Validator.isNull(vendedor))
			vendedor = new VendedorDto();
		
		vendedor.setCodigo(codigo);
		vendedor.setNombre(nombre);
		
		validateDto(vendedor);
		
		try {
			vendedorServicio.guardarVendedor(vendedor);
		}
		catch(BusinessException e) {
			throw new PresentationException(e.getMessage(), "Hubo un error al intentar dar del alta el vendedor: " + vendedor);
		}
	}
	
	public void eliminarVendedor() {
		VendedorDto vendedorDto = null;
		try {
			int index = getVendedorPanel().getTable().getSelectedRow();
			if (index>-1)
				index = getVendedorPanel().getTable().convertRowIndexToModel(index);
			vendedorDto = getVendedorPanel().getTableModel().getVendedor(index);
		}
		catch (IndexOutOfBoundsException exception) {
			if(getVendedorPanel().getTableModel().getRowCount() == 0) {
				throw new PresentationException("error.vendedorPanel.vendedor.eliminar.vacio");
			}
			else {
				throw new PresentationException("error.vendedorPanel.vendedor.eliminar.noSeleccionado");
			}
		}
		
		try {
			vendedorServicio.bajaVendedor(vendedorDto);
		}
		catch(BusinessException e) {
			throw new PresentationException(e.getMessage(), "Hubo un error al intentar eliminar el vendedor");
		}
	}
	
	public void nuevoVendedor() {
		vendedorNuevoPanel.setVendedor(null);
		mostrarVendedorNuevoPanel();
	}
	
	public void editarVendedor() {
		VendedorDto vendedorDto = null;
		try {
			int index = getVendedorPanel().getTable().getSelectedRow();
			if (index>-1)
				index = getVendedorPanel().getTable().convertRowIndexToModel(index);
			vendedorDto = getVendedorPanel().getTableModel().getVendedor(index);
		}
		catch (IndexOutOfBoundsException exception) {
			if(getVendedorPanel().getTableModel().getRowCount() == 0) {
				throw new PresentationException("error.vendedorPanel.vendedor.editar.vacio");
			}
			else {
				throw new PresentationException("error.vendedorPanel.vendedor.editar.noSeleccionado");
			}
		}
		
		try {
			vendedorNuevoPanel.setVendedor(vendedorDto);
			mostrarVendedorNuevoPanel();
		}
		catch(BusinessException e) {
			throw new PresentationException(e.getMessage(), "Hubo un error al intentar editar el vendedor");
		}
	}
	
	public List<VendedorDto> obtenerVendedores() {
		try {
			return vendedorServicio.obtenerVendedores();
		}
		catch(BusinessException e) {
			throw new PresentationException(e.getMessage(), "Hubo un error al intentar obtener los vendedores");
		}
	}
	
	public VendedorView getVendedorView() {
		return vendedorView;
	}

	public VendedorPanel getVendedorPanel() {
		return vendedorPanel;
	}

	public VendedorNuevoPanel getVendedorNuevoPanel() {
		return vendedorNuevoPanel;
	}
	
	@Override
	public void mostrarUltimoPanelMostrado() {
		// TODO Auto-generated method stub
	}

}