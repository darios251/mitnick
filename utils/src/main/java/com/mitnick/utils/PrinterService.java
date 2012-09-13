package com.mitnick.utils;

import java.io.DataInputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.net.Socket;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.mitnick.utils.dtos.PagoDto;
import com.mitnick.utils.dtos.ProductoVentaDto;
import com.mitnick.utils.dtos.VentaDto;

@Component(value="printerService")
public class PrinterService {
	
	protected Logger logger = Logger.getLogger(this.getClass());
	
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
	private static final String CLOSE_COLA = "[CLOSE-COLA]";
	private static final String FIN_COLA = "[FIN-COLA]";
	
	public boolean imprimirTicket(VentaDto venta) {
		PrintStream output = null;
		DataInputStream input = null;
		Socket socket = null;
		
		try {
			socket = new Socket("192.168.1.105", 9095);
			socket.setSoTimeout(15000);
			output = new PrintStream(socket.getOutputStream());
			input = new DataInputStream(socket.getInputStream());
			
			output.println(TICKET_TAG);
			
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
			
			output.println(SUBTOTAL);
			
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
			
			output.println(FIN_TICKET_TAG);
			
			output.println(PAGO_MONTO);
			output.println(CLOSE_COLA);
			output.println("Este comprobante es válido para");
			output.println(CLOSE_COLA);
			output.println("utilizar en devoluciones en el local");
			output.println(CLOSE_COLA);
			output.println("que se presente");
			output.println("[FIN-COLA-TICKET]");
			
			output.println(FIN_TICKET_TAG);
			
		    String line = "";
		    
		    while((line = input.readLine()).equals("<FIN DE IMPRESION>")) {
		    	logger.error(line);
		    	return false;
		    }
			
		    input.close();
			output.close();
			socket.close();
		} catch (Exception e) {
			logger.error(e);
			return false;
		}
		
		return true;
	}
	
}
