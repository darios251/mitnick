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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mitnick.persistence.daos.ICiudadDao;
import com.mitnick.persistence.daos.IClienteDao;
import com.mitnick.persistence.daos.IProductoDAO;
import com.mitnick.persistence.daos.IProvinciaDao;
import com.mitnick.persistence.entities.Ciudad;
import com.mitnick.persistence.entities.Cliente;
import com.mitnick.persistence.entities.Direccion;
import com.mitnick.persistence.entities.Producto;
import com.mitnick.persistence.entities.Provincia;

@SuppressWarnings("rawtypes")
@Service("dbImport")
public class DBImport {

	@Autowired
	protected IClienteDao clienteDao;

	@Autowired
	protected IProvinciaDao provinciaDao;

	@Autowired
	protected ICiudadDao ciudadDao;
	
	@Autowired
	protected IProductoDAO productoDao;
	

	//migracion de cliente
	private static String RAZONSOC = "RAZONSOC";
	private static String DOMICILIO = "DOMICILIO";
	private static String POSTAL = "POSTAL";
	private static String LOCALIDAD = "LOCALIDAD";
	private static String PROVINCIA = "PROVINCIA";
	private static String CUIT = "CUIT";
	private static String TELEFONO = "TELEFONO";
	private static String ACTIVIDAD = "ACTIVIDAD";
	private static String nullCuit = "-00000000-";

	//migracion de producto
	private static String ARTICULO = "ARTICULO";
	private static String MARCA ="MARCA";    
	private static String DESCRIPCIO ="DESCRIPCIO";
	private static String STOCK ="STOCK";
	private static String STOCKMIN ="STOCKMIN";
	private static String STOCKMAX ="STOCKMAX";          
	private static String PRECIOVTA ="PRECIOVTA";
	private static String TASA_IVA ="TASA_IVA";
	
	//migracion de marca
	
	
	public void ejecutar() {
		try {
			String path = "C:/project/mitnick/archivosSusmann/Copia (2) de comersis/";
			migrarProductos(path);
			migrarClientes(path);
			// migrarClientes(args[0].toString());

		} catch (Exception e) {
			System.out.println(e.getMessage());
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

				//marca, tipo, precioCompra, proveedor
				
				String descripcion = "";
				String codigo = "";
				String stock = "";
				String stockMinimo = "";
				String stockCompra = "";
				String precioVenta = "";
				String iva = "";
				

				for (final Field field : fields) {
					try {
						byte[] rawValue = record.getRawValue(field);
						System.out.println(field.getName()
								+ " : "
								+ (rawValue == null ? "<NULL>" : new String(
										rawValue)));
						if (DESCRIPCIO.equals(field.getName()))
							descripcion = (rawValue == null ? "<NULL>" : new String(
									rawValue));
						if (ARTICULO.equals(field.getName()))
							codigo= (rawValue == null ? "<NULL>"
									: new String(rawValue));
						if (STOCK.equals(field.getName()))
							stock = (rawValue == null ? "<NULL>" : new String(
									rawValue));
						if (STOCKMIN.equals(field.getName()))
							stockMinimo = (rawValue == null ? "<NULL>"
									: new String(rawValue));
						if (STOCKMAX.equals(field.getName()))
							stockCompra = (rawValue == null ? "<NULL>"
									: new String(rawValue));
						if (PRECIOVTA.equals(field.getName()))
							precioVenta = (rawValue == null ? "<NULL>" : new String(
									rawValue));
						if (TASA_IVA.equals(field.getName()))
							iva = (rawValue == null ? "<NULL>"
									: new String(rawValue));

					} catch (ValueTooLargeException vtle) {
						// Cannot happen :)
					}
				}

				producto.setCodigo(codigo);
				producto.setDescripcion(descripcion);
				producto.setEliminado(false);
				producto.setIva(getBigDecimal(iva));
				producto.setPrecioVenta(getBigDecimal(precioVenta));
				producto.setStock(getInt(stock));
				producto.setStockCompra(getInt(stockCompra));
				producto.setStockMinimo(getInt(stockMinimo));
				
						
				try {
					System.out.println(producto.toString());
					productoDao.saveOrUpdate(producto);
				} catch (Exception e) {	
					
					e.printStackTrace();
				}

				System.out.println();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("listo Productos!");
	}
	
	private int getInt(String valor){
		int retorno = -1;
		if (valor!=null && !valor.isEmpty()) {
			Double d = new Double(valor.trim());
			retorno = d.intValue();
		}
			return retorno;
	}
		
	
	private BigDecimal getBigDecimal(String valor){
		BigDecimal retorno = new BigDecimal(0);
		if (valor!=null && !valor.isEmpty()) {
			retorno = new BigDecimal(valor.trim());
		}
			return retorno;
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
						System.out.println(field.getName()
								+ " : "
								+ (rawValue == null ? "<NULL>" : new String(
										rawValue)));
						if (RAZONSOC.equals(field.getName()))
							nombre = (rawValue == null ? "<NULL>" : new String(
									rawValue));
						if (DOMICILIO.equals(field.getName()))
							domicilio = (rawValue == null ? "<NULL>"
									: new String(rawValue));
						if (POSTAL.equals(field.getName()))
							postal = (rawValue == null ? "<NULL>" : new String(
									rawValue));
						if (LOCALIDAD.equals(field.getName()))
							localidad = (rawValue == null ? "<NULL>"
									: new String(rawValue));
						if (PROVINCIA.equals(field.getName()))
							provincia = (rawValue == null ? "<NULL>"
									: new String(rawValue));
						if (CUIT.equals(field.getName()))
							cuit = (rawValue == null ? "<NULL>" : new String(
									rawValue));
						if (TELEFONO.equals(field.getName()))
							telefono = (rawValue == null ? "<NULL>"
									: new String(rawValue));
						if (ACTIVIDAD.equals(field.getName()))
							actividad = (rawValue == null ? "<NULL>"
									: new String(rawValue));

					} catch (ValueTooLargeException vtle) {
						// Cannot happen :)
					}
				}

				if (postal!=null && !postal.trim().isEmpty())
					if (provincia != null) {
						Provincia prov = provinciaDao.get(new Long(provincia.trim()));
						List<Ciudad> ciudades = ciudadDao
								.getByPostal(postal);
						Ciudad ciudad = null;
						if (ciudades == null || ciudades.isEmpty()) {
							ciudad = new Ciudad();
							ciudad.setCodigoPostal(postal);
							ciudad.setDescripcion(new String(localidad.toString().getBytes("ISO-8859-1")));
							ciudad.setProvincia(prov);
							Direccion direccion = new Direccion();
							direccion.setDomicilio(new String(domicilio.toString().getBytes("ISO-8859-1")));
							direccion.setCodigoPostal(postal);
							direccion.setCiudad(ciudad);
							cliente.setDireccion(direccion);
						} else
							ciudad = ciudades.get(0);
					}
				cliente.setActividad(new String(actividad.toString().getBytes("ISO-8859-1")));				
				if (!nullCuit.equals(cuit.trim()))
					cliente.setCuit(cuit);
				cliente.setNombre(new String(nombre.toString().getBytes("ISO-8859-1")));
				cliente.setTelefono(telefono);				
				try {
					System.out.println(cliente.toString());
					clienteDao.saveOrUpdate(cliente);
				} catch (Exception e) {	
					
					e.printStackTrace();
				}

				System.out.println();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("listo Cliente!");
	}

}
