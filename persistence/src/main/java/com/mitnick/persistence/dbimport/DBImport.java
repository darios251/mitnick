package com.mitnick.persistence.dbimport;

import java.io.File;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import nl.knaw.dans.common.dbflib.Field;
import nl.knaw.dans.common.dbflib.IfNonExistent;
import nl.knaw.dans.common.dbflib.Record;
import nl.knaw.dans.common.dbflib.Table;
import nl.knaw.dans.common.dbflib.ValueTooLargeException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mitnick.persistence.daos.ICiudadDao;
import com.mitnick.persistence.daos.IClienteDao;
import com.mitnick.persistence.daos.IMarcaDao;
import com.mitnick.persistence.daos.IProductoDAO;
import com.mitnick.persistence.daos.IProvinciaDao;
import com.mitnick.persistence.daos.ITipoDao;
import com.mitnick.persistence.entities.Ciudad;
import com.mitnick.persistence.entities.Cliente;
import com.mitnick.persistence.entities.Direccion;
import com.mitnick.persistence.entities.Marca;
import com.mitnick.persistence.entities.Producto;
import com.mitnick.persistence.entities.Provincia;

@Service("dbImport")
public class DBImport {

	private static Logger logger = Logger.getLogger(DBImport.class);

	@Autowired
	protected IClienteDao clienteDao;

	@Autowired
	protected IProvinciaDao provinciaDao;

	@Autowired
	protected ICiudadDao ciudadDao;

	@Autowired
	protected IProductoDAO productoDao;

	@Autowired
	protected IMarcaDao marcaDao;

	@Autowired
	protected ITipoDao tipoDao;

	private Ciudad santoTome;
	
	List<Marca> marcas = null;

	// migracion de cliente
	private static String RAZONSOC = "RAZONSOC";
	private static String DOMICILIO = "DOMICILIO";
	private static String POSTAL = "POSTAL";
	private static String LOCALIDAD = "LOCALIDAD";
	private static String PROVINCIA = "PROVINCIA";
	private static String CUIT = "CUIT";
	private static String TELEFONO = "TELEFONO";
	private static String ACTIVIDAD = "ACTIVIDAD";
	private static String nullCuit = "-00000000-";

	// migracion de producto
	private static String ARTICULO = "ARTICULO";
	// private static String MARCA ="MARCA";
	private static String DESCRIPCIO = "DESCRIPCIO";
	private static String STOCK = "STOCK";
	private static String STOCKMIN = "STOCKMIN";
	private static String STOCKMAX = "STOCKMAX";
	private static String PRECIOVTA = "PRECIOVTA";

	public void ejecutar(String path) {
		try {
			marcas = marcaDao.getAll();
			santoTome = ciudadDao.getById(new Long(1864));
			migrarProductos(path);
			migrarClientes(path);

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	private void migrarProductos(String path) {

		String file = path + "PRODUCTO.DBF";

		Producto producto = null;
		final Table table = new Table(new File(file));
		try {
			table.open(IfNonExistent.ERROR);

			final List<Field> fields = table.getFields();

			final Iterator<Record> recordIterator = table.recordIterator();

			while (recordIterator.hasNext()) {
				final Record record = recordIterator.next();

				producto = new Producto();

				// marca, tipo, precioCompra, proveedor

				String descripcion = "";
				String codigo = "";
				String stock = "";
				String stockMinimo = "";
				String stockCompra = "";
				String precioVenta = "";

				for (final Field field : fields) {
					try {
						byte[] rawValue = record.getRawValue(field);
						logger.info(field.getName()	+ " : "	+ (rawValue == null ? "<NULL>" : new String(rawValue)));
						if (DESCRIPCIO.equals(field.getName()))
							descripcion = (rawValue == null ? "<NULL>" : new String(rawValue));
						if (ARTICULO.equals(field.getName()))
							codigo = (rawValue == null ? "<NULL>" : new String(rawValue));
						if (STOCK.equals(field.getName()))
							stock = (rawValue == null ? "<NULL>" : new String(rawValue));
						if (STOCKMIN.equals(field.getName()))
							stockMinimo = (rawValue == null ? "<NULL>" : new String(rawValue));
						if (STOCKMAX.equals(field.getName()))
							stockCompra = (rawValue == null ? "<NULL>" : new String(rawValue));
						if (PRECIOVTA.equals(field.getName()))
							precioVenta = (rawValue == null ? "<NULL>" : new String(rawValue));

					} catch (ValueTooLargeException vtle) {
						// Cannot happen :)
					}
				}

				codigo = codigo.trim();
				if (!codigo.equals("") && codigo.length()>=2) {
					producto.setCodigo(codigo.trim());
					producto.setDescripcion(descripcion.trim());
					producto.setEliminado(false);

					BigDecimal precioSinIva = getBigDecimal(precioVenta).setScale(2, BigDecimal.ROUND_HALF_UP);
					BigDecimal iva = precioSinIva.multiply(new BigDecimal(0.21)).setScale(2, BigDecimal.ROUND_HALF_UP);
					BigDecimal precioFinal = precioSinIva.add(iva.setScale(2, BigDecimal.ROUND_HALF_UP));

					producto.setIva(iva);
					producto.setPrecioVenta(precioSinIva);

					producto.setStock(getInt(stock));
					producto.setStockCompra(getInt(stockCompra));
					producto.setStockMinimo(getInt(stockMinimo));
					producto.setPrecioCompra(new BigDecimal(0));

					actualizarProductos(producto);
					logger.info("IVA: ".concat(iva.toString()).concat(" PRECIO FINAL: ").concat(precioFinal.toString()));

					try {
						logger.info(producto.toString());
						productoDao.saveOrUpdate(producto);

					} catch (Exception e) {
						logger.error("PRODUCTO NO GUARDADO: ".concat(producto.toString()));
						logger.error(e);
					}

				} else
					logger.error("PRODUCTO NO GUARDADO POR CODIGO VACIO: ".concat(descripcion));

			}

		} catch (Exception e) {
			logger.error(e);
		}
		logger.info("listo Productos!");
	}

	private void actualizarProductos(Producto producto) {
		try {
			String prodCod = producto.getCodigo().substring(0, 2);
			try{
				Long prodTipo = Long.parseLong(prodCod);

				producto.setTipo(tipoDao.findById(prodTipo));
			} catch (Exception e){
				logger.error("No se encuentra tipo para el producto: " + e);
			}

			String description = producto.getDescripcion();

			description = description.toLowerCase();

			for (Marca marca : marcas) {
				String marcaDesc = marca.getDescripcion();
				marcaDesc = marcaDesc.toLowerCase();
				if (description.contains(marcaDesc)) {
					producto.setMarca(marca);
				} else {
					if (description.contains("wr"))
						producto.setMarca(marcaDao.findById(new Long(11)));
					else if (description.contains("dior"))
						producto.setMarca(marcaDao.findById(new Long(5)));
					else if (description.contains("cotton"))
						producto.setMarca(marcaDao.findById(new Long(8)));
					else if (description.contains(" rc"))
						producto.setMarca(marcaDao.findById(new Long(8)));
					else if (description.contains(" tnn"))
						producto.setMarca(marcaDao.findById(new Long(1)));
				}
			}
		} catch (Exception e) {
			logger.error("Error al actualizar tipo y marca del producto - ", e);
		}

	}

	private void migrarClientes(String path) {

		String file = path + "DEUDORES.DBF";

		Cliente cliente = null;
		final Table table = new Table(new File(file));
		try {
			table.open(IfNonExistent.ERROR);

			final List<Field> fields = table.getFields();

			final Iterator<Record> recordIterator = table.recordIterator();

			while (recordIterator.hasNext()) {
				final Record record = recordIterator.next();

				cliente = new Cliente();

				String nombre = "";
				String domicilio = "";
				String postal = "";
				String localidad = "";
				String provincia = "";
				String cuit = "";
				String telefono = "";
				String actividad = "";

				for (final Field field : fields) {
					try {
						byte[] rawValue = record.getRawValue(field);
						logger.info(field.getName()	+ " : "	+ (rawValue == null ? "<NULL>" : new String(rawValue)));
						if (RAZONSOC.equals(field.getName()))
							nombre = (rawValue == null ? "<NULL>" : new String(rawValue));
						if (DOMICILIO.equals(field.getName()))
							domicilio = (rawValue == null ? "<NULL>" : new String(rawValue));
						if (POSTAL.equals(field.getName()))
							postal = (rawValue == null ? "<NULL>" : new String(rawValue));
						if (LOCALIDAD.equals(field.getName()))
							localidad = (rawValue == null ? "<NULL>" : new String(rawValue));
						if (PROVINCIA.equals(field.getName()))
							provincia = (rawValue == null ? "<NULL>" : new String(rawValue));
						if (CUIT.equals(field.getName()))
							cuit = (rawValue == null ? "<NULL>" : new String(rawValue));
						if (TELEFONO.equals(field.getName()))
							telefono = (rawValue == null ? "<NULL>"	: new String(rawValue));
						if (ACTIVIDAD.equals(field.getName()))
							actividad = (rawValue == null ? "<NULL>" : new String(rawValue));

					} catch (ValueTooLargeException vtle) {
						//
					}
				}

				if (postal != null && !postal.trim().isEmpty())
					if (provincia != null) {
						Provincia prov = provinciaDao.get(new Long(provincia
								.trim()));
						Ciudad ciudad = null;
						if (postal.trim().equals("3016"))
							ciudad = santoTome;
						else {
							List<Ciudad> ciudades = ciudadDao.getByProvinciaPostalCode(prov, postal);
							
							if (ciudades == null || ciudades.isEmpty()) {
								if (localidad!=null)
									ciudades = ciudadDao.getByProvinciaDescription(prov, localidad.trim().toUpperCase());
							} 
							if (ciudades != null && !ciudades.isEmpty()){
								ciudad = ciudades.get(0);
								postal = ciudad.getCodigoPostal();
							}
						}
							
						
						Direccion direccion = new Direccion();
						direccion.setDomicilio(new String(domicilio.toString().getBytes("ISO-8859-1")));
						direccion.setCodigoPostal(postal);
						direccion.setCiudad(ciudad);
						cliente.setDireccion(direccion);
					}
				cliente.setActividad(new String(actividad.toString().getBytes("ISO-8859-1")));
				if (!nullCuit.equals(cuit.trim()))
					cliente.setCuit(cuit.trim());
				cliente.setNombre(new String(nombre.toString().getBytes("ISO-8859-1")));
				cliente.setTelefono(telefono);
				try {
					logger.info(cliente.toString());
					clienteDao.saveOrUpdate(cliente);
				} catch (Exception e) {
					logger.error("CLIENTE NO GUARDADO: ".concat(cliente.toString()));
					e.printStackTrace();
				}
			}

		} catch (Exception e) {
			logger.error(e);
		}
		logger.info("listo Cliente!");
	}

	private int getInt(String valor) {
		int retorno = -1;
		if (valor != null && !valor.isEmpty()) {
			Double d = new Double(valor.trim());
			retorno = d.intValue();
		}
		return retorno;
	}

	private BigDecimal getBigDecimal(String valor) {
		BigDecimal retorno = new BigDecimal(0);
		if (valor != null && !valor.isEmpty()) {
			retorno = new BigDecimal(valor.trim());
		}
		return retorno;
	}

}
