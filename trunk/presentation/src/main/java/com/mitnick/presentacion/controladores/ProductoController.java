package com.mitnick.presentacion.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.mitnick.exceptions.BusinessException;
import com.mitnick.exceptions.PresentationException;
import com.mitnick.presentacion.vistas.PrincipalView;
import com.mitnick.presentacion.vistas.ProductoView;
import com.mitnick.presentacion.vistas.paneles.ProductoNuevoPanel;
import com.mitnick.presentacion.vistas.paneles.ProductoPanel;
import com.mitnick.servicio.servicios.IProductoServicio;
import com.mitnick.servicio.servicios.dtos.ConsultaProductoDto;
import com.mitnick.utils.MitnickConstants;
import com.mitnick.utils.Validator;
import com.mitnick.utils.anotaciones.AuthorizationRequired;
import com.mitnick.utils.dtos.MarcaDto;
import com.mitnick.utils.dtos.ProductoDto;
import com.mitnick.utils.dtos.ProductoNuevoDto;
import com.mitnick.utils.dtos.ProveedorDto;
import com.mitnick.utils.dtos.TipoDto;

@Controller("productoController")
public class ProductoController extends BaseController {
	
	@Autowired
	private ProductoView productoView;
	@Autowired
	private ProductoPanel productoPanel;
	@Autowired
	private ProductoNuevoPanel productoNuevoPanel;
	@Autowired
	private IProductoServicio productoServicio;
	@Autowired
	private PrincipalView principalView;
	
	public ProductoController() {
		
	}
	
	@AuthorizationRequired(role = MitnickConstants.Role.ADMIN)
	public void resetearStock() {
		productoServicio.resetearMovimiento();
	}
	
	@AuthorizationRequired(role = MitnickConstants.Role.ADMIN)
	public void actualizarStock() {
		principalView.actualizarStock();
	}
	
	public void mostrarProductoNuevoPanel() {
		logger.info("Mostrando el panel de producto nuevo");
		ultimoPanelMostrado = productoNuevoPanel;
		productoPanel.setVisible(false);
		productoNuevoPanel.setVisible(true);
		productoNuevoPanel.actualizarPantalla();
	}

	public void mostrarProductosPanel() {
		logger.info("Mostrando el panel de productos");
		ultimoPanelMostrado = productoPanel;
		productoNuevoPanel.setVisible(false);
		productoPanel.setVisible(true);
		productoPanel.actualizarPantalla();
	}
	
	public List<ProductoDto> getProductosByFilter(ConsultaProductoDto dto) {
		logger.debug("Entrando al método getProductosByFilter con: " + dto);
		
		List<ProductoDto> resultado = null;
		try {
			resultado = getProductoServicio().consultaProducto(dto);
		}
		catch(BusinessException e) {
			throw new PresentationException(e);
		}
		
		// chequeo si se encontro o no algo en la busqueda
		if(resultado == null || resultado.isEmpty())
			throw new PresentationException("error.venta.producto.noExiste");
		
		return resultado;
	}
	
	public List<MarcaDto> obtenerMarcas() {
		try {
			return productoServicio.obtenerMarcas();
		}
		catch(BusinessException e) {
			throw new PresentationException(e.getMessage(), "Hubo un error al intentar obtener las marcas");
		}
	}
	
	public List<TipoDto> obtenerTipos() {
		try {
			return productoServicio.obtenerTipos();
		}
		catch(BusinessException e) {
			throw new PresentationException(e.getMessage(), "Hubo un error al intentar obtener los tipos");
		}
	}
	
	public List<ProductoDto> getAllProductos() {
		try {
			return productoServicio.obtenerProductos();
		}
		catch(BusinessException e) {
			throw new PresentationException(e.getMessage(), "Hubo un error al intentar obtener los productos");
		}
	}
		
	public void guardarProducto(ProductoNuevoDto producto, String codigo, String descripcion, TipoDto tipo, MarcaDto marca, 
			String stock, String stockMinimo, String stockCompra, String precioVenta, String precioCompra, ProveedorDto proveedor, boolean confirmado, String talle) {
		
		if(Validator.isNull(producto))
			producto = new ProductoNuevoDto();
		
		producto.setCodigo(codigo);
		producto.setTalle(talle);
		producto.setDescripcion(descripcion);
		producto.setPrecioVenta(precioVenta);
		producto.setPrecioCompra(precioCompra);
		producto.setTipo(tipo);
		producto.setMarca(marca);
		producto.setStock(stock);
		producto.setStockMinimo(stockMinimo);
		producto.setStockCompra(stockCompra);
		producto.setProveedor(proveedor);
		producto.setConfirmado(confirmado);
		
		validateDto(producto);
		
		try {
			getProductoServicio().guardarProducto(producto);
		}
		catch(BusinessException e) {
			throw new PresentationException(e.getMessage(), "Hubo un error al intentar dar del alta el producto: " + producto);
		}
	}
	
	public void guardarProducto(ProductoNuevoDto producto, String stock) {
		if(Validator.isNull(producto))
			producto = new ProductoNuevoDto();
		
		if (Validator.isBlankOrNull(stock) || !Validator.isNumeric(stock))
			throw new PresentationException("error.actualizarStock.stock.invalido");
		producto.setPrecioVenta(producto.getPrecioVentaConIva().toString());
		producto.setStock(stock);
		
		validateDto(producto);
		
		try {
			getProductoServicio().guardarProducto(producto);
		}
		catch(BusinessException e) {
			throw new PresentationException(e.getMessage(), "Hubo un error al intentar dar del alta el producto: " + producto);
		}
	}
	
	@AuthorizationRequired(role = MitnickConstants.Role.ADMIN)
	public void eliminarProducto() {
		ProductoDto productoDto = null;
		try {
			int index = getProductoPanel().getTable().getSelectedRow();
			if (index>-1)
				index = getProductoPanel().getTable().convertRowIndexToModel(index);
			productoDto = getProductoPanel().getTableModel().getProducto(index);
		}
		catch (IndexOutOfBoundsException exception) {
			if(getProductoPanel().getTableModel().getRowCount() == 0) {
				throw new PresentationException("error.productoPanel.productos.vacio");
			}
			else {
				throw new PresentationException("error.productoPanel.producto.noSeleccionado");
			}
		}
		
		try {
			getProductoServicio().bajaProducto(productoDto);
		}
		catch(BusinessException e) {
			throw new PresentationException(e.getMessage(), "Hubo un error al intentar eliminar el producto");
		}
	}
	
	@AuthorizationRequired(role = MitnickConstants.Role.ADMIN)
	public void altaProducto() {
		productoNuevoPanel.setProducto(null);
		mostrarProductoNuevoPanel();
	}
	
	@AuthorizationRequired(role = MitnickConstants.Role.ADMIN)
	public void editarProducto() {
		ProductoDto productoDto = null;
		try {
			int index = getProductoPanel().getTable().getSelectedRow();
			if (index>-1)
				index = getProductoPanel().getTable().convertRowIndexToModel(index);
			productoDto = getProductoPanel().getTableModel().getProducto(index);
		}
		catch (IndexOutOfBoundsException exception) {
			if(getProductoPanel().getTableModel().getRowCount() == 0) {
				throw new PresentationException("error.productoPanel.productos.editar.vacio");
			}
			else {
				throw new PresentationException("error.productoPanel.producto.editar.noSeleccionado");
			}
		}
		
		try {
			productoNuevoPanel.setProducto(getProductoNuevo(productoDto));
			mostrarProductoNuevoPanel();
		}
		catch(BusinessException e) {
			throw new PresentationException(e.getMessage(), "Hubo un error al intentar editar el producto");
		}
	}
	
	protected ProductoNuevoDto getProductoNuevo(ProductoDto productoDto) {
		try {
			return productoServicio.getProductoNuevo(productoDto);
		}
		catch (BusinessException e) {
			throw new PresentationException(e.getMessage(), "Hubo un error al intentar obtener el producto");
		}
	}
	
	public ProductoNuevoDto getProductoByCode(String code){
		try {
			return productoServicio.getProductoByCode(code);
		}
		catch (BusinessException e) {
			throw new PresentationException(e.getMessage(), "Hubo un error al intentar obtener el producto");
		}
	}
	
	public void agregarNuevoTipo(String descripcion) {
		productoServicio.agregarNuevoTipo(descripcion);
	}
	
	public void agregarNuevaMarca(String descripcion) {
		productoServicio.agregarNuevaMarca(descripcion);
	}
	
	public ProductoView getProductoView() {
		return productoView;
	}

	public ProductoPanel getProductoPanel() {
		return productoPanel;
	}

	public ProductoNuevoPanel getProductoNuevoPanel() {
		return productoNuevoPanel;
	}

	protected IProductoServicio getProductoServicio() {
		if(productoServicio == null)
			throw new PresentationException("error.unknown", "El servicio: " + productoServicio.getClass().getSimpleName() + "");
		return productoServicio;
	}

	@Override
	public void mostrarUltimoPanelMostrado() {
		
	}

}