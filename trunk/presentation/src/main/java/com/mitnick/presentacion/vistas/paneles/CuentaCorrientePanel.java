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
import javax.swing.JOptionPane;
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
public class CuentaCorrientePanel extends BasePanel {
	
	private static final long serialVersionUID = 1L;
	
	private List<CuotaDto> cuotas;
	private ClienteDto cliente;

	private JScrollPane scrollPane;
	private JTable table;
	private JButton btnNuevo;
	private JButton btnEditar;
	private JButton btnEliminar;
	private JButton btnPagar;
	private CuotaTableModel model;
	private TableRowSorter<CuotaTableModel> sorter;
	
	private ClienteController clienteController;

	@Autowired(required = true)
	public CuentaCorrientePanel(@Qualifier("clienteController") ClienteController clienteController) {
		this.clienteController = clienteController;
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
		
		setFocusTraversalPolicy();
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
			btnNuevo.setIcon(new ImageIcon(this.getClass().getResource("/img/nuevo_cliente.png")));

			btnNuevo.setHorizontalTextPosition(SwingConstants.CENTER);
			btnNuevo.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnNuevo.setMargin(new Insets(-1, -1, -1, -1));
			btnNuevo.setBounds(735, 139, 60, 60);

			btnNuevo.addActionListener(new ActionListener() {
				@Override public void actionPerformed(ActionEvent e) {		
					nuevaCuota();					
				}
			});
		}
		return btnNuevo;
	}
	
	private void nuevaCuota(){
		CuotaDto cuotaDto = new CuotaDto();
		cuotaDto.setClienteDto(getCliente());
		actualizarCuotas(cuotaDto);
	}

	private void actualizarCuotas(CuotaDto cuotaDto){
		new NuevaCuotaDialog((JFrame) this.getParent().getParent().getParent().getParent().getParent().getParent().getParent(), getCliente(), cuotaDto, this.clienteController);
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
			btnEditar.setIcon(new ImageIcon(this.getClass().getResource("/img/aceptar.png")));

			btnEditar.setHorizontalTextPosition(SwingConstants.CENTER);
			btnEditar.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnEditar.setMargin(new Insets(-1, -1, -1, -1));
			btnEditar.setBounds(735, 281, 60, 60);

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
			btnEliminar.setIcon(new ImageIcon(this.getClass().getResource("/img/eliminar_cliente.png")));

			btnEliminar.setHorizontalTextPosition(SwingConstants.CENTER);
			btnEliminar.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnEliminar.setMargin(new Insets(-1, -1, -1, -1));
			btnEliminar.setBounds(735, 210, 60, 60);

			btnEliminar.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int opcion = mostrarMensajeConsulta(PropertiesManager.getProperty("cuentaPanel.dialog.confirm.eliminar"));

					if (opcion == JOptionPane.YES_OPTION) {
						try {
							clienteController.eliminarCuota();
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
			btnPagar = new JButton(PropertiesManager.getProperty("cuentaPanel.button.pagar.texto"));
			btnPagar.setToolTipText(PropertiesManager.getProperty("cuentaPanel.button.pagar.tooltip"));
			btnPagar.setIcon(new ImageIcon(this.getClass().getResource("/img/eliminar_cliente.png")));

			btnPagar.setHorizontalTextPosition(SwingConstants.CENTER);
			btnPagar.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnPagar.setMargin(new Insets(-1, -1, -1, -1));
			btnPagar.setBounds(735, 352, 60, 60);

			btnPagar.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						clienteController.mostrarCuentaCorrientePagoPanel();
					} catch (PresentationException ex) {
						mostrarMensaje(ex);
					}
					
				}
			});
		}
		return btnPagar;
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
		clienteController.actualizarCuotas(getCliente());
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
