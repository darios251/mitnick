package com.mitnick.presentacion.controladores;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.mitnick.presentacion.vistas.ProductoView;
import com.mitnick.presentacion.vistas.paneles.ProductoNuevoPanel;
import com.mitnick.presentacion.vistas.paneles.ProductoPanel;
import com.mitnick.servicio.servicios.IProductoServicio;
import com.mitnick.servicio.servicios.dtos.ConsultaProductoDto;
import com.mitnick.utils.dtos.MarcaDto;
import com.mitnick.utils.dtos.ProductoDto;
import com.mitnick.utils.dtos.TipoDto;

@Controller("productoController")
public class ProductoController extends BaseController {
	
	private static Logger logger = Logger.getLogger(ProductoController.class);
	
	@Autowired
	private ProductoView productoView;
	@Autowired
	private ProductoPanel productoPanel;
	@Autowired
	private ProductoNuevoPanel productoNuevoPanel;
	@Autowired
	private IProductoServicio productoServicio;
	
	public ProductoController() {
		
	}
	
	public ProductoView getProductoView() {
		return productoView;
	}

	public void setProductoView(ProductoView productoView) {
		this.productoView = productoView;
	}

	public ProductoPanel getProductoPanel() {
		return productoPanel;
	}

	public void setProductoPanel(ProductoPanel productoPanel) {
		this.productoPanel = productoPanel;
	}

	public ProductoNuevoPanel getProductoNuevoPanel() {
		return productoNuevoPanel;
	}

	public void setProductoNuevoPanel(ProductoNuevoPanel productoNuevoPanel) {
		this.productoNuevoPanel = productoNuevoPanel;
	}

	public void mostrarProductoNuevoPanel() {
		logger.info("Mostrando el panel de producto nuevo");
		productoPanel.setVisible(false);
		productoNuevoPanel.setVisible(true);
	}

	public void mostrarProductosPanel() {
		logger.info("Mostrando el panel de productos");
		productoNuevoPanel.setVisible(false);
		productoPanel.setVisible(true);
		
	}
	
	public List<ProductoDto> getProductosByFilter(ConsultaProductoDto dto) {
		logger.info("Consultando productos por filtro");
		
		return productoServicio.consultaProducto(dto);
	}
	
	public List<ProductoDto> getAllProductos() {
		List<ProductoDto> productos = new ArrayList<ProductoDto>();

		ProductoDto p1 = new ProductoDto();
		TipoDto tipo1 = new TipoDto();
		MarcaDto marca1 = new MarcaDto();

		p1.setCodigo("1");
		p1.setDescripcion("Pantalon de vestir");
		tipo1.setDescripcion("Pantalon");
		p1.setTipo(tipo1);
		p1.setTalle("L");
		marca1.setDescripcion("Wrangler");
		p1.setMarca(marca1);
		p1.setPrecio(new BigDecimal(120.50));

		productos.add(p1);

		ProductoDto p2 = new ProductoDto();
		TipoDto tipo2 = new TipoDto();
		MarcaDto marca2 = new MarcaDto();

		p2.setCodigo("2");
		p2.setDescripcion("Camisa manga larga");
		tipo2.setDescripcion("Camisa");
		p2.setTipo(tipo2);
		p2.setTalle("XL");
		marca2.setDescripcion("Polo");
		p2.setMarca(marca2);
		p2.setPrecio(new BigDecimal(80.00));

		productos.add(p2);

		return productos;
	}

	public void setProductoServicio(IProductoServicio productoServicio) {
		this.productoServicio = productoServicio;
	}
	
	
}