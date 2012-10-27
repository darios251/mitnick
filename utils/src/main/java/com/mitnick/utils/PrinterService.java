package com.mitnick.utils;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.mitnick.exceptions.PrinterException;
import com.mitnick.utils.dtos.ClienteDto;
import com.mitnick.utils.dtos.ConfiguracionImpresoraDto;
import com.mitnick.utils.dtos.PagoDto;
import com.mitnick.utils.dtos.ProductoVentaDto;
import com.mitnick.utils.dtos.VentaDto;

@Component(value="printerService")
public class PrinterService {
	
	protected Logger logger = Logger.getLogger(this.getClass());
	
	protected Socket currentConnection;
	protected PrintStream output = null;
	protected DataInputStream input = null;
	
	private static final String TICKET_TAG = "[TICKET]";
	private static final String FIN_TICKET_TAG = "[FIN-TICKET]";
	private static final String ITEM_TAG = "[ITEM]";
	private static final String FIN_ITEM_TAG = "[FIN-ITEM]";
	private static final String ITEM_CANTIDAD = "[ITEM-CANTIDAD]";
	private static final String ITEM_DESCRIPCION = "[ITEM-DESCRIPTION]";
	private static final String FIN_ITEM_DESCRIPCION = "[FIN-ITEM-DESCRIPTION]";
	private static final String ITEM_PRECIO = "[ITEM-PRECIO]";
	private static final String ITEM_IVA = "[ITEM-IVA]";
	private static final String SUBTOTAL = "[SUBTOTAL]";
	private static final String PAYMENT = "[PAYMENT]";
	private static final String PAGO_MONTO = "[PAGO-MONTO]";
	private static final String PAGO_DESCRIPCION = "[PAGO-DESCRIPTION]";
	private static final String FIN_PAGO_DESCRIPCION = "[FIN-PAGO-DESCRIPTION]";
	private static final String FIN_PAGO = "[FIN-PAGO]";
	private static final String DESCUENTO = "[DISCOUNT]";
	private static final String RECARGO = "[SURCHARGE]";
	private static final String CLOSE_COLA = "[CLOSE-COLA]";
	private static final String FIN_COLA = "[FIN-COLA]";
	private static final String BLANK_LINE = "[BLANK-LINE]";
	
	private static final String CIERRE_Z_TAG = "[CIERRE-Z]";
	private static final String CIERRE_X_TAG = "[CIERRE-X]";
	private static final String INFORME_JORNADA_TAG = "[INFORME-JORNADA]";
	
	private static final String TICKET_FACTURA_TAG = "[TICKET-FACTURA]";
	private static final String DATOS_COMPRADOR = "[DATOS-COMPRADOR]";
	private static final String NOMBRE_COMPRADOR = "[NOMBRE-COMPRADOR]";
	private static final String DIRECCION_COMPRADOR = "[DIRECCION-COMPRADOR]";
	private static final String TIPO_DOCUMENTO_COMPRADOR = "[TIPO-DOCUMENTO-COMPRADOR]";
	private static final String NUMERO_DOCUMENTO_COMPRADOR = "[NUMERO-DOCUMENTO-COMPRADOR]";
	private static final String TIPO_IVA_COMPRADOR = "[TIPO-IVA-COMPRADOR]";
	private static final String LINEA_REMITOS_ASOCIADOS = "[LINEA-REMITOS-ASOCIADOS]";
	private static final String FIN_DATOS_COMPRADOR = "[FIN-DATOS-COMPRADOR]";
	
	private static final String CONFIGURAR = "[CONFIGURACION]";
	private static final String DOMICILIO_COMERCIAL_1 = "[DOMICILIO-COMERCIAL-1]";
	private static final String DOMICILIO_COMERCIAL_2 = "[DOMICILIO-COMERCIAL-2]";
	private static final String DOMICILIO_COMERCIAL_3 = "[DOMICILIO-COMERCIAL-3]";
	private static final String DOMICILIO_FISCAL_1 = "[DOMICILIO-FISCAL-1]";
	private static final String DOMICILIO_FISCAL_2 = "[DOMICILIO-FISCAL-2]";
	private static final String DOMICILIO_FISCAL_3 = "[DOMICILIO-FISCAL-3]";
	private static final String INGRESOS_BRUTOS_1 = "[INGRESOS-BRUTOS-1]";
	private static final String INGRESOS_BRUTOS_2 = "[INGRESOS-BRUTOS-2]";
	private static final String INGRESOS_BRUTOS_3 = "[INGRESOS-BRUTOS-3]";
	private static final String INGRESO_ACTIVIDADES = "[INGRESO-ACTIVIDADES]";
	
	private static final String INFO_TICKET_FACTURA = "[INFO-TICKET-FACTURA]";
	private static final String CANCELAR_TICKET_FACTURA = "[CANCELAR-TICKET-FACTURA]";
	private static final String INFO_TICKET = "[INFO-TICKET]";
	private static final String CANCELAR_TICKET = "[CANCELAR-TICKET]";
	
	public boolean imprimirTicket(VentaDto venta) {
		try {
			connect();
			
			output.println(TICKET_TAG);
			checkErrors();
			output.println(BLANK_LINE);
			
			getInfoTicket(venta, true);
			
			for(ProductoVentaDto producto : venta.getProductos()) {
				output.println(ITEM_TAG);
				
				output.println(ITEM_DESCRIPCION); //extra 1
				output.println(producto.getProducto().getDescripcion());
				output.println(ITEM_DESCRIPCION); //extra 2
				output.println("");
				output.println(ITEM_DESCRIPCION); //extra 3
				output.println("");
				output.println(ITEM_DESCRIPCION); //extra 4
				output.println("");
				output.println(ITEM_DESCRIPCION);
				output.println("Código -- " + producto.getProducto().getCodigo());

				output.println(ITEM_CANTIDAD);
				output.println(producto.getCantidad());
				
				output.println(ITEM_PRECIO);
				output.println(producto.getProducto().getPrecioVenta().setScale (2, BigDecimal.ROUND_HALF_UP));
				
				output.println(ITEM_IVA);
				output.println("21");
				
				output.println(FIN_ITEM_TAG);
			}
			
			checkErrors();
			
			output.println(SUBTOTAL);
			
			checkErrors();
			
			if(venta.getAjusteRedondeo().compareTo(BigDecimal.ZERO) < 0) {
				output.println(DESCUENTO);
				output.println("Ajuste por redondeo");
				output.println(venta.getAjusteRedondeo().abs().setScale (2, BigDecimal.ROUND_HALF_UP));
			}
			else if(venta.getAjusteRedondeo().compareTo(BigDecimal.ZERO) > 0) {
				output.println(RECARGO);
				output.println("Ajuste por redondeo");
				output.println(venta.getAjusteRedondeo().abs().setScale (2, BigDecimal.ROUND_HALF_UP));
			}
			
			checkErrors();
			
			for(PagoDto pago : venta.getPagos()) {
				output.println(PAYMENT);
				output.println(PAGO_DESCRIPCION);
				output.println(""); // linea extra
				output.println(PAGO_DESCRIPCION);
				output.println(pago.getMedioPago().getDescripcion());
				output.println(PAGO_MONTO);
				output.println(pago.getMonto().setScale (2, BigDecimal.ROUND_HALF_UP));
				output.println(FIN_PAGO);
			}
			
			checkErrors();
			
			output.println(FIN_TICKET_TAG);
			
			output.println(CLOSE_COLA);
			output.println("Este comprobante es válido para");
			output.println(CLOSE_COLA);
			output.println("utilizar en devoluciones en el local");
			output.println(CLOSE_COLA);
			output.println("que se presente");
			output.println("[FIN-COLA-TICKET]");
			
			checkErrors();
			
			output.println(FIN_TICKET_TAG);
			
			checkErrors();
			
		    String line = "";
		    
		    while(!(line = input.readLine()).equals("<FIN DE IMPRESION>")) {
		    	if(line.startsWith("[ERROR]")) {
		    		line = input.readLine();
		    		throw new PrinterException(line);
		    	}
		    	else
		    		logger.info(line);
		    }
		    
		}
		catch (PrinterException ex) {
			throw ex;
		}
		catch (Exception e) {
			String errorLine = readErrorLine();
			cancelarTicket(venta, true);
			throw new PrinterException(errorLine);
		}
		finally {
			closeConnection();
		}
		
		return true;
	}
	
	public boolean imprimirTicketFactura(VentaDto venta) {
		try {
			connect();
			output.println(TICKET_FACTURA_TAG);
			checkErrors();
			
			output.println(DATOS_COMPRADOR);
			
			ClienteDto cliente = venta.getCliente();
			output.println(NOMBRE_COMPRADOR);
			output.println(cliente.getApellido() + " " + cliente.getNombre());
			output.println(NOMBRE_COMPRADOR);
			output.println("");
			output.println(DIRECCION_COMPRADOR);
			output.println(cliente.getDireccion().getDomicilio());
			output.println(DIRECCION_COMPRADOR);
			output.println(cliente.getDireccion().getCodigoPostal() + " - " + cliente.getDireccion().getCiudad().getDescripcion() + " - " + cliente.getDireccion().getCiudad().getPrinvincia().getDescripcion());
			output.println(DIRECCION_COMPRADOR);
			output.println("");
			output.println(TIPO_DOCUMENTO_COMPRADOR);
			output.println(Validator.isBlankOrNull(cliente.getCuit()) ? "D" : "T");
			output.println(NUMERO_DOCUMENTO_COMPRADOR);
			output.println(Validator.isBlankOrNull(cliente.getCuit()) ? cliente.getDocumento() : cliente.getCuit().replaceAll("-", ""));
			output.println(TIPO_IVA_COMPRADOR);
			output.println("I"); //TODO: ARREGLAR ESTO
			output.println(LINEA_REMITOS_ASOCIADOS);
			output.println("............");
			output.println(LINEA_REMITOS_ASOCIADOS);
			output.println("............");
			output.println(FIN_DATOS_COMPRADOR);
			
			checkErrors();
			
			getInfoTicketFactura(venta, true);
			
			checkErrors();
			
			for(ProductoVentaDto producto : venta.getProductos()) {
				output.println(ITEM_TAG);
				
				output.println(ITEM_DESCRIPCION); //extra 1
				output.println(producto.getProducto().getDescripcion());
				output.println(ITEM_DESCRIPCION); //extra 2
				output.println("");
				output.println(ITEM_DESCRIPCION); //extra 3
				output.println("");
				output.println(ITEM_DESCRIPCION); //extra 4
				output.println("");
				output.println(ITEM_DESCRIPCION);
				output.println("Código -- " + producto.getProducto().getCodigo());

				output.println(ITEM_CANTIDAD);
				output.println(producto.getCantidad());
				
				output.println(ITEM_PRECIO);
				output.println(VentaHelper.calcularPrecioSinIva(producto.getPrecioTotal()));
				
				output.println(ITEM_IVA);
				output.println("21");
				
				output.println(FIN_ITEM_TAG);
			}
			
			checkErrors();
			
			output.println(SUBTOTAL);
			
			checkErrors();
			
			if(venta.getAjusteRedondeo().compareTo(BigDecimal.ZERO) < 0) {
				output.println(DESCUENTO);
				output.println("Ajuste por redondeo");
				output.println(venta.getAjusteRedondeo().abs().setScale (2, BigDecimal.ROUND_HALF_UP));
			}
			else if(venta.getAjusteRedondeo().compareTo(BigDecimal.ZERO) > 0) {
				output.println(RECARGO);
				output.println("Ajuste por redondeo");
				output.println(venta.getAjusteRedondeo().abs().setScale (2, BigDecimal.ROUND_HALF_UP));
			}
			
			checkErrors();
			
			for(PagoDto pago : venta.getPagos()) {
				output.println(PAYMENT);
				output.println(PAGO_DESCRIPCION);
				output.println(""); // linea extra
				output.println(PAGO_DESCRIPCION);
				output.println(pago.getMedioPago().getDescripcion());
				output.println(PAGO_MONTO);
				output.println(pago.getMonto().setScale (2, BigDecimal.ROUND_HALF_UP));
				output.println(FIN_PAGO);
			}
			
			checkErrors();
			
			output.println(FIN_TICKET_TAG);
			
			output.println(CLOSE_COLA);
			output.println("Este comprobante es válido para");
			output.println(CLOSE_COLA);
			output.println("utilizar en devoluciones en el local");
			output.println(CLOSE_COLA);
			output.println("que se presente");
			output.println("[FIN-COLA-TICKET]");
			
			checkErrors();
			
			output.println(FIN_TICKET_TAG);
			
			checkErrors();
			
		    String line = "";
		    
		    while(!(line = input.readLine()).equals("<FIN DE IMPRESION>")) {
		    	if(line.startsWith("[ERROR]")) {
		    		line = input.readLine();
		    		throw new PrinterException(line);
		    	}
		    	else
		    		logger.info(line);
		    }
			
		}
		catch (PrinterException ex) {
			throw ex;
		}
		catch (Exception e) {
			String errorLine = readErrorLine();
			cancelarTicketFactura(venta, true);
			throw new PrinterException(errorLine);
		}
		finally {
			closeConnection();
		}
		
		return true;
	}
	
	public boolean imprimirCierreZ() {
		try {
			connect();
			
			output.println(CIERRE_Z_TAG);
			
			checkErrors();
			
			output.println(FIN_TICKET_TAG);
			
			checkErrors();
			
			String line = "";
			    
		    while(!(line = input.readLine()).equals("<FIN DE IMPRESION>")) {
		    	if(line.startsWith("[ERROR]")) {
		    		line = input.readLine();
		    		throw new PrinterException(line);
		    	}
		    	else
		    		logger.info(line);
		    }
		} 
		catch (PrinterException ex) {
			throw ex;
		}
		catch (Exception e) {
			throw new PrinterException(readErrorLine());
		}
		finally {
			closeConnection();
		}
		return true;
	}
	
	public boolean imprimirCierreX() {
		try {
			connect();
			output.println(CIERRE_X_TAG);
			checkErrors();
			
			output.println(FIN_TICKET_TAG);
			checkErrors();
			
			String line = "";
			    
		    while(!(line = input.readLine()).equals("<FIN DE IMPRESION>")) {
		    	if(line.startsWith("[ERROR]")) {
		    		line = input.readLine();
		    		throw new PrinterException(line);
		    	}
		    	else
		    		logger.info(line);
		    }
			
		} 
		catch (PrinterException ex) {
			throw ex;
		}
		catch (Exception e) {
			throw new PrinterException(readErrorLine());
		}
		finally {
			closeConnection();
		}
		return true;
	}
	
	public boolean imprimirInformeJornada() {
		try {
			connect();
			output.println(INFORME_JORNADA_TAG);
			checkErrors();
			
			output.println(FIN_TICKET_TAG);
			
			String line = "";
			    
		    while(!(line = input.readLine()).equals("<FIN DE IMPRESION>")) {
		    	if(line.startsWith("[ERROR]")) {
		    		line = input.readLine();
		    		throw new PrinterException(line);
		    	}
		    	else
		    		logger.info(line);
		    }
			
		} 
		catch (PrinterException ex) {
			throw ex;
		}
		catch (Exception e) {
			throw new PrinterException(readErrorLine());
		}
		
		finally {
			closeConnection();
		}
		
		return true;
	}
	
	private void checkErrors() throws Exception {
		if(input.available() > 0)
			throw new Exception();
	}

	private String readErrorLine() {
		try {
			String line = "";
			if(input.available() > 0)
				line = input.readLine();
			
			if(line.startsWith("[ERROR]"))
				return input.readLine();
			else if("".equals(line))
				return "Hubo un error con la impresora";
			else
				return line;
				
		}
		catch (PrinterException ex) {
			throw ex;
		}
		catch(Exception e) {
			return "Hubo un error con la impresora";
		}
	}

	public boolean configurarImpresora(ConfiguracionImpresoraDto configuracion) {
		try {
			connect();
			output.println(CONFIGURAR);
			output.println(DOMICILIO_COMERCIAL_1);
			output.println(configuracion.getDomicilioComercial1());
			checkErrors();
			
			output.println(CONFIGURAR);
			output.println(DOMICILIO_COMERCIAL_2);
			output.println(configuracion.getDomicilioComercial2());
			checkErrors();
			
			output.println(CONFIGURAR);
			output.println(DOMICILIO_COMERCIAL_3);
			output.println(configuracion.getDomicilioComercial3());
			checkErrors();
			
			output.println(CONFIGURAR);
			output.println(DOMICILIO_FISCAL_1);
			output.println(configuracion.getDomicilioFiscal1());
			checkErrors();
			
			output.println(CONFIGURAR);
			output.println(DOMICILIO_FISCAL_2);
			output.println(configuracion.getDomicilioFiscal2());
			checkErrors();
			
			output.println(CONFIGURAR);
			output.println(DOMICILIO_FISCAL_3);
			output.println(configuracion.getDomicilioFiscal3());
			checkErrors();
			
			output.println(CONFIGURAR);
			output.println(INGRESOS_BRUTOS_1);
			output.println(configuracion.getIngresosBrutos1());
			checkErrors();
			
			output.println(CONFIGURAR);
			output.println(INGRESOS_BRUTOS_2);
			output.println(configuracion.getIngresosBrutos2());
			checkErrors();
			
			output.println(CONFIGURAR);
			output.println(INGRESOS_BRUTOS_3);
			output.println(configuracion.getIngresosBrutos3());
			checkErrors();
			
			output.println(CONFIGURAR);
			output.println(INGRESO_ACTIVIDADES);
			output.println(configuracion.getFechaInicioActividades());
			checkErrors();
			
			output.println(FIN_TICKET_TAG);
			checkErrors();
			
			String line = "";
			    
		    while(!(line = input.readLine()).equals("<FIN DE IMPRESION>")) {
		    	if(line.startsWith("[ERROR]")) {
		    		line = input.readLine();
		    		throw new PrinterException(line);
		    	}
		    	else
		    		logger.info(line);
		    }
			
		}
		catch (PrinterException ex) {
			throw ex;
		}
		catch (Exception e) {
			throw new PrinterException(readErrorLine());
		}
		finally {
			closeConnection();
		}
		
		return true;
	}
	
	public boolean getInfoTicketFactura(VentaDto venta, boolean useCurrentConnection) {
		try {
			connect(useCurrentConnection);
			
			output.println(INFO_TICKET_FACTURA);
			checkErrors();
			
			String line = "";
			
			String nroTicket;
		    
		    line = input.readLine();
		    
	    	if(line.startsWith("[ERROR]")) {
	    		line = input.readLine();
	    		throw new PrinterException(line);
	    	}
	    	else {
	    		logger.info("info tique factura: ");
	    		logger.info(line);
	    		
	    		if(venta != null) {
		    		for(int i = 1; i <= 19; i++) {
		    			if(i == 1)
			    			venta.setNumeroTicket(line.split(":")[1]);
		    			else if(i == 2)
		    				venta.setTipoTicket(line.split(":")[1]);
		    			if(i < 19) {
		    				line = input.readLine();
		    				logger.info(line);
		    			}
		    		}
	    		}
	    	}
		}
		catch (PrinterException ex) {
			throw ex;
		}
		catch (Exception e) {
			throw new PrinterException(readErrorLine());
		}
		
		return true;
	}
	
	public boolean getInfoTicket(VentaDto venta, boolean useCurrentConnection) {
		try {
			connect(useCurrentConnection);
			
			output.println(INFO_TICKET);
			checkErrors();
			
			String line = "";
			
			String nroTicket;
		    
		   line = input.readLine();
		   
		   if(line.startsWith("[ERROR]")) {
	    		line = input.readLine();
	    		throw new PrinterException(line);
	    	}
	    	else {
	    		logger.info("info ticket:");
	    		logger.info(line);
	    		
	    		if(venta != null) {
		    		for(int i = 1; i <= 14; i++) {
		    			if(i == 1)
			    			venta.setNumeroTicket(line.split(":")[1]);
		    			if(i < 14) {
		    				line = input.readLine();
		    				logger.info(line);
		    			}
		    		}
	    		}
	    	}
		} 
		catch (PrinterException ex) {
			throw ex;
		}
		catch (Exception e) {
			throw new PrinterException(readErrorLine());
		}
		
		return true;
	}
	
	public boolean cancelarTicketFactura(VentaDto venta, boolean useCurrentConnection) {
		try {
			connect(useCurrentConnection);
			
			output.println(CANCELAR_TICKET_FACTURA);
			
			String line = input.readLine();
			
			if(line.startsWith("[ERROR]")) {
	    		line = input.readLine();
	    		throw new PrinterException(line);
	    	}
	    	else {
	    		logger.info("Se canceló el tique factura n°:" + line);
	    		line = input.readLine();
	    		logger.info("y el tipo de factura:" + line);
	    	}
		}
		catch (PrinterException ex) {
			throw ex;
		}
		catch (Exception e) {
			throw new PrinterException(readErrorLine());
		}
		
		return true;
	}
	
	public boolean cancelarTicket(VentaDto venta, boolean useCurrentConnection) {
		try {
			connect(useCurrentConnection);
			
			output.println(CANCELAR_TICKET);
			checkErrors();
			
			String line = input.readLine();
			
			if(line.startsWith("[ERROR]")) {
	    		line = input.readLine();
	    		throw new PrinterException(line);
	    	}
	    	else
	    		logger.info("Se canceló el tique n°:" + line);
		} 
		catch (PrinterException ex) {
			throw ex;
		}
		catch (Exception e) {
			throw new PrinterException(readErrorLine());
		}
		return true;
	}
	
	protected Socket connect(boolean getCurrentConnection) throws UnknownHostException, IOException {
		if(getCurrentConnection && currentConnection != null && currentConnection.isConnected())
			return currentConnection;
		else
			return connect();
	}
	
	protected Socket connect() throws UnknownHostException, IOException {
		currentConnection = new Socket("192.168.1.105", 9095);
		currentConnection.setSoTimeout(15000);
		output = new PrintStream(currentConnection.getOutputStream());
		input = new DataInputStream(currentConnection.getInputStream());
		return currentConnection;
	}
	
	protected boolean closeConnection() {
		try {
			input.close();
			output.close();
			currentConnection.close();
			currentConnection = null;
		}
		catch(Exception e) {
			try {
				logger.error(e);
				input.close();
				output.close();
				currentConnection.close();
				currentConnection = null;
				return false;
			}
			catch(Exception e1) {
				logger.error(e1);
			}
			
		}
		return true;
	}
	
}
