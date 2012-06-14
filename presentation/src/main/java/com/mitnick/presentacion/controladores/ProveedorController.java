package com.mitnick.presentacion.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.mitnick.exceptions.BusinessException;
import com.mitnick.exceptions.PresentationException;
import com.mitnick.presentacion.vistas.ProveedorView;
import com.mitnick.presentacion.vistas.paneles.ProveedorNuevoPanel;
import com.mitnick.presentacion.vistas.paneles.ProveedorPanel;
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
	
	public ProveedorController() {
		
	}
	
	public void mostrarProveedorNuevoPanel() {
		logger.info("Mostrando el panel de proveedor nuevo");
		proveedorPanel.setVisible(false);
		proveedorNuevoPanel.setVisible(true);
		proveedorNuevoPanel.actualizarPantalla();
	}

	public void mostrarProveedorPanel() {
		logger.info("Mostrando el panel de proveedores");
		proveedorNuevoPanel.setVisible(false);
		proveedorPanel.setVisible(true);
		proveedorPanel.actualizarPantalla();
	}
	
	public List<ProveedorDto> getAllProveedores() {
		try {
			return proveedorServicio.obtenerProveedores();
		}
		catch(BusinessException e) {
			throw new PresentationException(e.getMessage(), "Hubo un error al intentar obtener los proveedores");
		}
		catch(Exception e) {
			throw new PresentationException("error.unkwon", e.getMessage());
		}
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
		catch(Exception e) {
			throw new PresentationException("error.unkwon", e.getMessage());
		}
		
		// chequeo si se encontro o no algo en la busqueda
		if(resultado == null || resultado.isEmpty())
			throw new PresentationException("error.proveedor.consulta.noExiste");
		
		return resultado;
	}
	
	public void guardarProveedor(ProveedorDto proveedor, String codigo, String nombre, String telefono) {
		if(Validator.isBlankOrNull(codigo))
			throw new PresentationException("error.proveedor.nuevo.codigo.null");
		if(Validator.isBlankOrNull(nombre))
			throw new PresentationException("error.proveedor.nuevo.nombre.null");
		if(Validator.isBlankOrNull(telefono))
			throw new PresentationException("error.proveedor.nuevo.telefono.null");
		
		if(Validator.isNull(proveedor))
			proveedor = new ProveedorDto();
		
		proveedor.setCodigo(codigo);
		proveedor.setNombre(nombre);
		proveedor.setTelefono(telefono);
		
		try {
			proveedorServicio.guardarProveedor(proveedor);
		}
		catch(BusinessException e) {
			throw new PresentationException(e.getMessage(), "Hubo un error al intentar dar del alta el producto: " + proveedor);
		}
		catch(Exception e) {
			throw new PresentationException("error.unkwon", e.getMessage());
		}
	}
	
	public void eliminarProveedor() {
		ProveedorDto proveedorDto = null;
		try {
			int index = getProveedorPanel().getTable().getSelectedRow();
			proveedorDto = getProveedorPanel().getTableModel().getProveedor(index);
		}
		catch (IndexOutOfBoundsException exception) {
			if(getProveedorPanel().getTableModel().getRowCount() == 0) {
				throw new PresentationException("error.proveedorPanel.proveedor.vacio");
			}
			else {
				throw new PresentationException("error.proveedorPanel.proveedor.noSeleccionado");
			}
		}
		
		try {
			proveedorServicio.bajaProveedor(proveedorDto);
		}
		catch(BusinessException e) {
			throw new PresentationException(e.getMessage(), "Hubo un error al intentar eliminar el proveedor");
		}
		catch(Exception e) {
			throw new PresentationException("error.unkwon", e.getMessage());
		}
	}
	
	public void editarProveedor() {
		ProveedorDto proveedorDto = null;
		try {
			int index = getProveedorPanel().getTable().getSelectedRow();
			proveedorDto = getProveedorPanel().getTableModel().getProveedor(index);
		}
		catch (IndexOutOfBoundsException exception) {
			if(getProveedorPanel().getTableModel().getRowCount() == 0) {
				throw new PresentationException("error.proveedorPanel.proveedor.vacio");
			}
			else {
				throw new PresentationException("error.proveedorPanel.proveedor.noSeleccionado");
			}
		}
		
		try {
			proveedorNuevoPanel.setProducto(proveedorDto);
			mostrarProveedorNuevoPanel();
		}
		catch(BusinessException e) {
			throw new PresentationException(e.getMessage(), "Hubo un error al intentar editar el proveedor");
		}
		catch(Exception e) {
			throw new PresentationException("error.unkwon", e.getMessage());
		}
	}
	
	public List<ProveedorDto> obtenerProveedores() {
		try {
			return proveedorServicio.obtenerProveedores();
		}
		catch(BusinessException e) {
			throw new PresentationException(e.getMessage(), "Hubo un error al intentar obtener los proveedores");
		}
		catch(Exception e) {
			throw new PresentationException("error.unkwon", e.getMessage());
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

}