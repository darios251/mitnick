package com.mitnick.presentacion.utils;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mitnick.persistence.daos.IProductoDAO;
import com.mitnick.persistence.entities.Producto;

@Component("dbInitialization")
public class DBInitialization {
	Logger logger = Logger.getLogger(DBInitialization.class);

	@Autowired
	private IProductoDAO productoDao;
	
	public void initializeDB() {
		for(Producto producto : productoDao.getAll()) {
			producto.setEliminado(false);
			productoDao.saveOrUpdate(producto);
		}
		
		logger.info("La base de datos se ha inicializado con éxito.");
	}

	public void setProductoDao(IProductoDAO productoDao) {
		this.productoDao = productoDao;
	}
}
