package com.mitnick.presentacion.vistas.paneles;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.springframework.beans.factory.annotation.Autowired;

import com.mitnick.exceptions.PrinterException;
import com.mitnick.presentacion.modelos.CuentaCorrienteTableModel;
import com.mitnick.utils.PrinterService;
import com.mitnick.utils.PropertiesManager;
import com.mitnick.utils.anotaciones.View;
import com.mitnick.utils.dtos.ConfiguracionImpresoraDto;
import com.mitnick.utils.locator.BeanLocator;

@View("configuracionImpresoraDialog")
public class ConfiguracionImpresoraDialog extends BaseDialog {

	private static final long serialVersionUID = 1L;
	private CuentaCorrienteTableModel model;
	
	@Autowired
	private PrinterService printerService = (PrinterService) BeanLocator.getBean("printerService");
	
	private JTextField txtDomicilioFiscal1;
	private JTextField txtDomicilioFiscal2;
	private JTextField txtDomicilioFiscal3;
	private JTextField txtDomicilioComercial1;
	private JTextField txtDomicilioComercial2;
	private JTextField txtDomicilioComercial3;
	private JLabel lblFechaInicioActividades;
	private JTextField txtFechaInicioActividades;
	private JButton btnGuardar;
	private JDialog thisDialog;
	
	public ConfiguracionImpresoraDialog(JFrame frame) {
		super(frame, true);
		thisDialog = this;
		getContentPane().setLayout(null);
		
		txtDomicilioFiscal1 = new JTextField();
		txtDomicilioFiscal1.setBounds(164, 31, 86, 20);
		getContentPane().add(txtDomicilioFiscal1);
		txtDomicilioFiscal1.setColumns(10);
		
		JLabel lblDomicilioFiscal1 = new JLabel("Domicilio Fiscal 1");
		lblDomicilioFiscal1.setBounds(20, 31, 134, 14);
		getContentPane().add(lblDomicilioFiscal1);
		
		JLabel lblDomicilioFiscal2 = new JLabel("Domicilio Fiscal 2");
		lblDomicilioFiscal2.setBounds(20, 62, 134, 14);
		getContentPane().add(lblDomicilioFiscal2);
		
		txtDomicilioFiscal2 = new JTextField();
		txtDomicilioFiscal2.setColumns(10);
		txtDomicilioFiscal2.setBounds(164, 62, 86, 20);
		getContentPane().add(txtDomicilioFiscal2);
		
		JLabel lblDomicilioFiscal3 = new JLabel("Domicilio Fiscal 3");
		lblDomicilioFiscal3.setBounds(20, 93, 134, 14);
		getContentPane().add(lblDomicilioFiscal3);
		
		txtDomicilioFiscal3 = new JTextField();
		txtDomicilioFiscal3.setColumns(10);
		txtDomicilioFiscal3.setBounds(164, 93, 86, 20);
		getContentPane().add(txtDomicilioFiscal3);
		
		JLabel lblDomicilioComercial1 = new JLabel("Domicilio Comercial 1");
		lblDomicilioComercial1.setBounds(20, 124, 134, 14);
		getContentPane().add(lblDomicilioComercial1);
		
		txtDomicilioComercial1 = new JTextField();
		txtDomicilioComercial1.setColumns(10);
		txtDomicilioComercial1.setBounds(164, 124, 86, 20);
		getContentPane().add(txtDomicilioComercial1);
		
		JLabel lblDomicilioComercial2 = new JLabel("Domicilio Comercial 2");
		lblDomicilioComercial2.setBounds(20, 155, 134, 14);
		getContentPane().add(lblDomicilioComercial2);
		
		txtDomicilioComercial2 = new JTextField();
		txtDomicilioComercial2.setColumns(10);
		txtDomicilioComercial2.setBounds(164, 155, 86, 20);
		getContentPane().add(txtDomicilioComercial2);
		
		JLabel lblDomicilioComercial3 = new JLabel("Domicilio Comercial 3");
		lblDomicilioComercial3.setBounds(20, 186, 134, 14);
		getContentPane().add(lblDomicilioComercial3);
		
		txtDomicilioComercial3 = new JTextField();
		txtDomicilioComercial3.setColumns(10);
		txtDomicilioComercial3.setBounds(164, 186, 86, 20);
		getContentPane().add(txtDomicilioComercial3);
		
		lblFechaInicioActividades = new JLabel("Fecha Inicio Actividades");
		lblFechaInicioActividades.setBounds(20, 217, 134, 14);
		getContentPane().add(lblFechaInicioActividades);
		
		txtFechaInicioActividades = new JTextField();
		txtFechaInicioActividades.setColumns(10);
		txtFechaInicioActividades.setBounds(164, 217, 86, 20);
		getContentPane().add(txtFechaInicioActividades);
		
		btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ConfiguracionImpresoraDto configuracion = new ConfiguracionImpresoraDto();
				configuracion.setDomicilioComercial1(txtDomicilioComercial1.getText());
				configuracion.setDomicilioComercial2(txtDomicilioComercial2.getText());
				configuracion.setDomicilioComercial3(txtDomicilioComercial3.getText());
				configuracion.setDomicilioFiscal1(txtDomicilioFiscal1.getText());
				configuracion.setDomicilioFiscal2(txtDomicilioFiscal2.getText());
				configuracion.setDomicilioFiscal3(txtDomicilioFiscal3.getText());
				configuracion.setFechaInicioActividades(txtFechaInicioActividades.getText());
				
				try {
					printerService.configurarImpresora(configuracion);
				}
				catch (PrinterException ex) {
					mostrarMensajeError(ex.getMessage());
				}
			}
		});
		
		btnGuardar.setBounds(164, 256, 89, 23);
		getContentPane().add(btnGuardar);
		setSize(299, 328);
		
		setLocationRelativeTo(null);
		
		setVisible(true);
	}
	
	protected int mostrarMensajeError ( String message ) {
		Object[] options = { PropertiesManager.getProperty( "dialog.error.okbutton" ) };
		
		return JOptionPane.showOptionDialog( thisDialog, message, PropertiesManager.getProperty( "dialog.error.titulo" ), JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[ 0 ] );
	}
	
	public CuentaCorrienteTableModel getModel() {
		if(model == null) {
			model = new CuentaCorrienteTableModel();
		}
		return model;
	}
	
}
