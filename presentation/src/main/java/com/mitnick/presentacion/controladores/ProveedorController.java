package com.mitnick.presentacion.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.mitnick.exceptions.BusinessException;
import com.mitnick.exceptions.PresentationException;
import com.mitnick.presentacion.vistas.ProveedorView;
import com.mitnick.presentacion.vistas.paneles.ProveedorNuevoPanel;
import com.mitnick.presentacion.vistas.paneles.ProveedorPanel;
import com.mitnick.presentacion.vistas.paneles.ProveedorProductoPanel;
import com.mitnick.servicio.servicios.IProveedorServicio;
import com.mitnick.servicio.servicios.dtos.ConsultaProveedorDto;
import com.mitnick.utils.Validator;
import com.mitnick.utils.dtos.ProveedorDto;

@Controller("proveedorController")
public class ProveedorController extends BaseController {
	
	@Autowired
	private ProveedorView proveedorView;
	@Autowired
	private ProveedorPanel proveedorPanel;
	@Autowired
	private ProveedorNuevoPanel proveedorNuevoPanel;
	@Autowired
	private IProveedorServicio proveedorServicio;
	@Autowired
	private ProveedorProductoPanel proveedorProductoPanel;
	
	public ProveedorController() {
		
	}
	
	public void mostrarProveedorNuevoPanel() {
		logger.info("Mostrando el panel de proveedor nuevo");
		ultimoPanelMostrado = proveedorNuevoPanel;
		proveedorPanel.setVisible(false);
		proveedorProductoPanel.setVisible(false);
		proveedorNuevoPanel.setVisible(true);
		proveedorNuevoPanel.actualizarPantalla();
	}

	public void mostrarProveedorPanel() {
		logger.info("Mostrando el panel de proveedores");
		ultimoPanelMostrado = proveedorPanel;
		proveedorNuevoPanel.setVisible(false);
		proveedorProductoPanel.setVisible(false);
		proveedorPanel.setVisible(true);
		proveedorPanel.actualizarPantalla();
	}
	
	public void mostrarProveedorProductoPanel() {
		logger.info("Mostrando el panel de productos de proveedor");
		ultimoPanelMostrado = proveedorProductoPanel;
		proveedorNuevoPanel.setVisible(false);
		proveedorPanel.setVisible(false);
		proveedorProductoPanel.setVisible(true);
		proveedorProductoPanel.actualizarPantalla();
	}
	
	public List<ProveedorDto> getProveedorsByFilter(ConsultaProveedorDto dto) {
		logger.debug("Entrando al método getProveedorByFilter con: " + dto);
		
		
		List<ProveedorDto> resultado = null;
		try {
			resultado = proveedorServicio.consultaProveedor(dto);
		}
		catch(BusinessException e) {
			throw new PresentationException(e);
		}
		
		// chequeo si se encontro o no algo en la busqueda
		if(resultado == null || resultado.isEmpty())
			throw new PresentationException("error.proveedor.consulta.noExiste");
		
		return resultado;
	}
	
	public void guardarProveedor(ProveedorDto proveedor, String codigo, String nombre, String telefono) {
		if(Validator.isNull(proveedor))
			proveedor = new ProveedorDto();
		
		proveedor.setCodigo(codigo);
		proveedor.setNombre(nombre);
		proveedor.setTelefono(telefono);
		
		validateDto(proveedor);
		
		try {
			proveedorServicio.guardarProveedor(proveedor);
		}
		catch(BusinessException e) {
			throw new PresentationException(e.getMessage(), "Hubo un error al intentar dar del alta el producto: " + proveedor);
		}
	}
	
	public void eliminarProveedor() {
		ProveedorDto proveedorDto = null;
		try {
			int index = getProveedorPanel().getTable().getSelectedRow();
			if (index>-1)
				index = getProveedorPanel().getTable().convertRowIndexToModel(index);
			proveedorDto = getProveedorPanel().getTableModel().getProveedor(index);
		}
		catch (IndexOutOfBoundsException exception) {
			if(getProveedorPanel().getTableModel().getRowCount() == 0) {
				throw new PresentationException("error.proveedorPanel.proveedor.eliminar.vacio");
			}
			else {
				throw new PresentationException("error.proveedorPanel.proveedor.eliminar.noSeleccionado");
			}
		}
		
		try {
			proveedorServicio.bajaProveedor(proveedorDto);
		}
		catch(BusinessException e) {
			throw new PresentationException(e.getMessage(), "Hubo un error al intentar eliminar el proveedor");
		}
	}
	
	public void nuevoProveedor() {
		proveedorNuevoPanel.setProveedor(null);
		mostrarProveedorNuevoPanel();
	}
	
	public void editarProveedor() {
		ProveedorDto proveedorDto = null;
		try {
			int index = getProveedorPanel().getTable().getSelectedRow();
			if (index>-1)
				index = getProveedorPanel().getTable().convertRowIndexToModel(index);
			proveedorDto = getProveedorPanel().getTableModel().getProveedor(index);
		}
		catch (IndexOutOfBoundsException exception) {
			if(getProveedorPanel().getTableModel().getRowCount() == 0) {
				throw new PresentationException("error.proveedorPanel.proveedor.editar.vacio");
			}
			else {
				throw new PresentationException("error.proveedorPanel.proveedor.editar.noSeleccionado");
			}
		}
		
		try {
			proveedorNuevoPanel.setProveedor(proveedorDto);
			mostrarProveedorNuevoPanel();
		}
		catch(BusinessException e) {
			throw new PresentationException(e.getMessage(), "Hubo un error al intentar editar el proveedor");
		}
	}
	
	public void verProductosProveedor() {
		ProveedorDto proveedorDto = null;
		try {
			int index = getProveedorPanel().getTable().getSelectedRow();
			proveedorDto = getProveedorPanel().getTableModel().getProveedor(index);
		}
		catch (IndexOutOfBoundsException exception) {
			if(getProveedorPanel().getTableModel().getRowCount() == 0) {
				throw new PresentationException("error.proveedorPanel.proveedor.verProductos.vacio");
			}
			else {
				throw new PresentationException("error.proveedorPanel.proveedor.verProductos.noSeleccionado");
			}
		}
		
		getProveedorProductoPanel().setProveedor(proveedorDto);
		mostrarProveedorProductoPanel();
	}
	
	public List<ProveedorDto> obtenerProveedores() {
		try {
			return proveedorServicio.obtenerProveedores();
		}
		catch(BusinessException e) {
			throw new PresentationException(e.getMessage(), "Hubo un error al intentar obtener los proveedores");
		}
	}
	
	public ProveedorView getProveedorView() {
		return proveedorView;
	}

	public ProveedorPanel getProveedorPanel() {
		return proveedorPanel;
	}

	public ProveedorNuevoPanel getProveedorNuevoPanel() {
		return proveedorNuevoPanel;
	}
	
	public ProveedorProductoPanel getProveedorProductoPanel() {
		return proveedorProductoPanel;
	}

	@Override
	public void mostrarUltimoPanelMostrado() {
		// TODO Auto-generated method stub
	}

}