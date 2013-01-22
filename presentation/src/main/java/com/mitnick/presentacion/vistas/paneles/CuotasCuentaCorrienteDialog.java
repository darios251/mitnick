package com.mitnick.presentacion.vistas.paneles;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.mitnick.presentacion.modelos.CuentaCorrienteTableModel;
import com.mitnick.utils.PropertiesManager;
import com.mitnick.utils.dtos.CuotaDto;

public class CuotasCuentaCorrienteDialog extends BaseDialog {

	private static final long serialVersionUID = 1L;
	private JTable table;
	private CuentaCorrienteTableModel model;
	private JScrollPane scrollPane;
	private JButton btnAceptar;
	private JButton btnCancelar;
	private String montoTotal;
	public boolean aceptar = true;
	
	public CuotasCuentaCorrienteDialog(JFrame frame, List<CuotaDto> cuotasDto, String montoTotal) {
		super(frame, true);
		getContentPane().setLayout(null);
		setSize(576, 271);
		this.montoTotal = montoTotal;
		setLocationRelativeTo(null);
		
		getContentPane().add(getScrollPane());
		getContentPane().add(getBtnAceptar());
		getContentPane().add(getBtnCancelar());
		getModel().setCuotas(cuotasDto);
		
		this.setTitle(PropertiesManager.getProperty("cuentaCorrienteDialog.title"));
		
		setVisible(true);
	}
	
	public JButton getBtnAceptar() {
		if(btnAceptar == null) {
			btnAceptar = new JButton(PropertiesManager.getProperty("cuentaCorrienteDialog.aceptar"));;
			btnAceptar.setIcon(new ImageIcon(this.getClass().getResource("/img/aceptar.png")));
			btnAceptar.setBounds(314, 174, 100, 52);
			btnAceptar.addActionListener(new ActionListener() {
				@Override public void actionPerformed(ActionEvent e) {
					int valid = validarCuotas();
					if (valid==10){
						aceptar= true;
						setVisible(false);
					}
				}
			});
		}
		
		return btnAceptar;
	}
	
	private int validarCuotas(){
		BigDecimal total = new BigDecimal(montoTotal);
		List<CuotaDto> cuotas = model.getCuotas();
		for (int i = 0; i < cuotas.size(); i++) {
			total = total.subtract(cuotas.get(i).getTotal());
		}
		int cubre = new BigDecimal("0").compareTo(total); 
		if (cubre < 0) {
			Object[] options = { PropertiesManager.getProperty( "dialog.error.okbutton" ) };
			return JOptionPane.showOptionDialog( this, "Las cuotas no cubren el monto total a pagar: " + montoTotal, PropertiesManager.getProperty( "dialog.error.titulo" ), JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[ 0 ] );
		}
		return 10;
	}
	
	public JButton getBtnCancelar() {
		if(btnCancelar == null) {
			btnCancelar = new JButton(PropertiesManager.getProperty("cuentaCorrienteDialog.cancelar"));;
			btnCancelar.setIcon(new ImageIcon(this.getClass().getResource("/img/cancelar.png")));
			btnCancelar.setBounds(436, 174, 100, 52);
			btnCancelar.addActionListener(new ActionListener() {
				@Override public void actionPerformed(ActionEvent e) {
					aceptar = false;
					setVisible(false);
				}
			});
		}
		
		return btnCancelar;
	}
	public JTable getTable() {
		if(table == null) {
			table = new JTable(getModel());
			table.setBounds(200, 400, 200, 28);
		}
		return table;
	}
	
	public CuentaCorrienteTableModel getModel() {
		if(model == null) {
			model = new CuentaCorrienteTableModel();
		}
		return model;
	}
	
	public JScrollPane getScrollPane() {
		if(scrollPane == null) {
			scrollPane = new JScrollPane(getTable());
			scrollPane.setBounds(20, 20, 516, 143);
		}
		return scrollPane;
	}
	
	@Override
	protected void keyF5() {
		getBtnAceptar().doClick();
	}
	
	@Override
	protected void keyEscape() {
		getBtnCancelar().doClick();
	}
}
