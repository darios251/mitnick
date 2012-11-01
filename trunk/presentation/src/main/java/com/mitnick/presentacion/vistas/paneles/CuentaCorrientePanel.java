package com.mitnick.presentacion.vistas.paneles;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.TableRowSorter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.mitnick.exceptions.PresentationException;
import com.mitnick.presentacion.controladores.ClienteController;
import com.mitnick.presentacion.modelos.CuotaTableModel;
import com.mitnick.utils.FocusTraversalOnArray;
import com.mitnick.utils.PropertiesManager;
import com.mitnick.utils.Validator;
import com.mitnick.utils.anotaciones.Panel;
import com.mitnick.utils.dtos.ClienteDto;
import com.mitnick.utils.dtos.CuotaDto;

@Panel("cuentaCorrientePanel")
public class CuentaCorrientePanel extends BasePanel<ClienteController> {
	
	private JPanel pnlCliente;
	private JLabel lblApellidoNombre;
	private JLabel lblDomicilio;
	private JLabel lblTelefono;
	
	private JLabel lblCliente;
	
	private static final long serialVersionUID = 1L;
	
	private List<CuotaDto> cuotas;
	private ClienteDto cliente;

	private JScrollPane scrollPane;
	private JTable table;
	private JButton btnNuevo;
	private JButton btnEditar;
	private JButton btnEliminar;
	private JButton btnPagar;
	private JButton btnVolver;
	private CuotaTableModel model;
	private TableRowSorter<CuotaTableModel> sorter;
	
	@Autowired(required = true)
	public CuentaCorrientePanel(@Qualifier("clienteController") ClienteController clienteController) {
		controller = clienteController;
	}

	/**
	 * @wbp.parser.constructor
	 */
	public CuentaCorrientePanel(boolean modoDisenio) {
		initializeComponents();

		throw new PresentationException("error.unknow",	"este constructor es solo parar el plugin de diseño");
	}

	@Override
	protected void limpiarCamposPantalla() {
		for (Component component : getComponents()) {
			if (component instanceof JTextField)
				((JTextField) component).setText("");
		}
		model.setCuotas(new ArrayList<CuotaDto>());
	}

	@Override
	protected void initializeComponents() {
		setLayout(null);
		setSize(new Dimension(815, 470));

		add(getScrollPane());
		
		add(getBtnNuevo());
		add(getBtnModificar());
		add(getBtnEliminar());
		add(getBtnPagar());
		add(getBtnVolver());
		
		add(getPnlCliente());
		add(getLblCliente());
		
		setFocusTraversalPolicy();
	}

	private JLabel getLblCliente() {
		if(lblCliente == null) {
			lblCliente = new JLabel("Cliente:");
			lblCliente.setBounds(113, 36, 46, 14);
		}
		return lblCliente;
	}
	
	protected void setFocusTraversalPolicy() {
		super.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[] { getBtnNuevo() }));
	}


	public JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane(getTable());
			scrollPane.setBounds(25, 115, 700, 315);
		}
		return scrollPane;
	}

	public JButton getBtnNuevo() {
		if (btnNuevo == null) {
			btnNuevo = new JButton(PropertiesManager.getProperty("cuentaPanel.button.nuevo.texto"));
			btnNuevo.setToolTipText(PropertiesManager.getProperty("cuentaPanel.button.nuevo.tooltip"));
			btnNuevo.setIcon(new ImageIcon(this.getClass().getResource("/img/agregar.png")));

			btnNuevo.setHorizontalTextPosition(SwingConstants.CENTER);
			btnNuevo.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnNuevo.setMargin(new Insets(-1, -1, -1, -1));
			btnNuevo.setBounds(735, 115, 60, 60);

			btnNuevo.addActionListener(new ActionListener() {
				@Override public void actionPerformed(ActionEvent e) {		
					nuevaCuota();					
				}
			});
		}
		return btnNuevo;
	}
	
	public JButton getBtnVolver() {
		if(btnVolver == null) {
			btnVolver = new JButton("Volver");
			btnVolver.setToolTipText(PropertiesManager.getProperty("pagoPanel.tooltip.volver"));
			btnVolver.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnVolver.setHorizontalTextPosition(SwingConstants.CENTER);
			btnVolver.setMargin(new Insets(-1, -1, -1, -1));
			btnVolver.setBounds(735, 394, 60, 60);
			btnVolver.setIcon(new ImageIcon(this.getClass().getResource("/img/volver.png")));
			btnVolver.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						controller.mostrarClientePanel();
					}
					catch(PresentationException ex) {
						mostrarMensaje(ex);
					}
				}
			});
		}
		return btnVolver;
	}
	
	private void nuevaCuota(){
		CuotaDto cuotaDto = new CuotaDto();
		cuotaDto.setClienteDto(getCliente());
		actualizarCuotas(cuotaDto);
	}

	private void actualizarCuotas(CuotaDto cuotaDto){
		new NuevaCuotaDialog((JFrame) this.getParent().getParent().getParent().getParent().getParent().getParent().getParent(), getCliente(), cuotaDto, this.controller);
		actualizarPantalla();
	}
	
	private void editarCuota(){
		try {
			int index = getTable().getSelectedRow();
			CuotaDto cuotaDto = getModel().getCuota(index);
			actualizarCuotas(cuotaDto);
		} catch (IndexOutOfBoundsException exception) {
				throw new PresentationException("error.cuentaPanel.cuota.noSeleccionado");	
		}
			
	}
	
	public JButton getBtnModificar() {
		if (btnEditar == null) {
			btnEditar = new JButton("Modificar");
			btnEditar.setToolTipText("Modificar");
			btnEditar.setIcon(new ImageIcon(this.getClass().getResource("/img/editar.png")));

			btnEditar.setHorizontalTextPosition(SwingConstants.CENTER);
			btnEditar.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnEditar.setMargin(new Insets(-1, -1, -1, -1));
			btnEditar.setBounds(735, 252, 60, 60);

			btnEditar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						editarCuota();
					} catch (PresentationException ex) {
						mostrarMensaje(ex);
					}
				}
			});
		}
		return btnEditar;
	}

	public JButton getBtnEliminar() {
		if (btnEliminar == null) {
			btnEliminar = new JButton(PropertiesManager.getProperty("cuentaPanel.button.eliminar.texto"));
			btnEliminar.setToolTipText(PropertiesManager.getProperty("cuentaPanel.button.eliminar.tooltip"));
			btnEliminar.setIcon(new ImageIcon(this.getClass().getResource("/img/cancelar.png")));

			btnEliminar.setHorizontalTextPosition(SwingConstants.CENTER);
			btnEliminar.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnEliminar.setMargin(new Insets(-1, -1, -1, -1));
			btnEliminar.setBounds(735, 181, 60, 60);

			btnEliminar.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int opcion = mostrarMensajeConsulta(PropertiesManager.getProperty("cuentaPanel.dialog.confirm.eliminar"));

					if (opcion == JOptionPane.YES_OPTION) {
						try {
							controller.eliminarCuota();
							actualizarPantalla();
						} catch (PresentationException ex) {
							mostrarMensaje(ex);
						}
					}
				}
			});
		}
		return btnEliminar;
	}
	
	public JButton getBtnPagar() {
		if (btnPagar == null) {
			btnPagar = new JButton();
			btnPagar.setToolTipText(PropertiesManager.getProperty("cuentaPanel.button.pagar.tooltip"));
			btnPagar.setIcon(new ImageIcon(this.getClass().getResource("/img/icono-pagos.jpg")));

			btnPagar.setHorizontalTextPosition(SwingConstants.CENTER);
			btnPagar.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnPagar.setMargin(new Insets(-1, -1, -1, -1));
			btnPagar.setBounds(735, 323, 60, 60);

			btnPagar.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						controller.mostrarCuentaCorrientePagoPanel();
					} catch (PresentationException ex) {
						mostrarMensaje(ex);
					}
					
				}
			});
		}
		return btnPagar;
	}
	
	public JPanel getPnlCliente() {
		if(pnlCliente == null) {
			pnlCliente = new JPanel();
			pnlCliente.setLayout(null);
			pnlCliente.setBounds(188, 23, 536, 39);
			pnlCliente.add(getLblApellidoNombre());
			pnlCliente.add(getLblDomicilio());
			pnlCliente.add(getLblTelefono());
		}
		return pnlCliente;
	}
	
	public JLabel getLblApellidoNombre() {
		if(lblApellidoNombre == null) {
			lblApellidoNombre = new JLabel(cliente.getNombre() + ", " +  cliente.getApellido());
			lblApellidoNombre.setBounds(10, 11, 251, 14);
			getPnlCliente().add(lblApellidoNombre);
		}
		return lblApellidoNombre;
	}
	
	public JLabel getLblDomicilio() {
		if(lblDomicilio == null) {
			lblDomicilio = new JLabel(cliente.getDireccion().getDomicilio());
			lblDomicilio.setBounds(312, 11, 182, 14);
			getPnlCliente().add(lblDomicilio);
		}
		return lblDomicilio;
	}

	public JLabel getLblTelefono() {
		if(lblTelefono == null) {
			lblTelefono = new JLabel(cliente.getTelefono());
			lblTelefono.setBounds(143, 11, 182, 14);
			getPnlCliente().add(lblTelefono);
		}
		return lblTelefono;
	}
	

	public TableRowSorter<CuotaTableModel> getSorter() {
		if (sorter == null) {
			sorter = new TableRowSorter<CuotaTableModel>(getModel());
		}
		return sorter;
	}

	public JTable getTable() {
		if (table == null) {
			table = new JTable(getModel());
			table.setRowSorter(getSorter());
			table.setBounds(0, 0, 1, 1);
		}
		return table;
	}

	public CuotaTableModel getModel() {
		if (model == null) {
			model = new CuotaTableModel();
		}
		return model;
	}

	@Override
	public void actualizarPantalla() {	
		controller.actualizarCuotas(getCliente());
		model.setCuotas(getCuotas());
		
	}

	@Override
	public void setDefaultFocusField() {
		
	}

	protected void setDefaultButton() {
		if(Validator.isNotNull(this.getRootPane()))
			this.getRootPane().setDefaultButton(this.btnEditar);
	}

	public List<CuotaDto> getCuotas() {
		return cuotas;
	}

	public void setCuotas(List<CuotaDto> cuotas) {
		this.cuotas = cuotas;
	}

	public ClienteDto getCliente() {
		return cliente;
	}

	public void setCliente(ClienteDto cliente) {
		this.cliente = cliente;
	}
	
	

}
