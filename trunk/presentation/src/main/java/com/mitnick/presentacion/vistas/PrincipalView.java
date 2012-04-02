package com.mitnick.presentacion.vistas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mitnick.presentacion.controladores.ClienteController;
import com.mitnick.presentacion.controladores.ProductoController;
import com.mitnick.presentacion.controladores.VentaController;
import com.mitnick.presentacion.vistas.controles.DetailPanel;
import com.mitnick.presentacion.vistas.controles.JTabbedPaneConBoton;
import com.mitnick.utils.PropertiesManager;
import com.mitnick.utils.anotaciones.View;

@View("principalView")
public class PrincipalView extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(PrincipalView.class);

	//	 declaraciones de inyecciones de spring
	@Autowired
	private VentaController ventaController;
	@Autowired
	private ProductoController productoController;
	@Autowired
	private ClienteController clienteController;
	
	private DetailPanel pnlPrincipal;
	private DetailPanel pnlToolBar;
	
	private JTabbedPaneConBoton jTabbedPaneConBoton;
	
	private JPanel jContentPane;
	private JPanel pnlToolBarArrow;
	private JToolBar tlbQuickAccess;
	private JPanel pnlArrow;
	
	private JButton btnVentas;
	private JButton btnArticulos;
	private JButton btnClientes;

	private JMenuBar menuBar;
	private JMenu menuArticulo;
	private JMenu menuArchivo;
	private JMenu menuAyuda;

	private JLabel lblArrow;
	public static JLabel lblLogo;
	
	public PrincipalView()
	{
		super();
//		addComponentListener(new ComponentAdapter() {
//			@Override
//			public void componentResized(ComponentEvent e) {
//				logger.info(getJTabbedPane().size());
//			}
//		});
		initialize();
	}

	private void initialize()
	{
		this.setTitle(PropertiesManager.getProperty("principalView.titulo"));
		this.setMinimumSize(new Dimension(990,600));
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//		this.setExtendedState(MAXIMIZED_BOTH);
		this.setLocationRelativeTo(null);
		this.setJMenuBar(getMenuBarra());
		this.setContentPane(getJContentPane());
	}

	private JMenuBar getMenuBarra() {
		if(menuBar == null)
		{
			menuBar = new JMenuBar();
			menuBar.add(getMenuArchivo());
			menuBar.add(getMenuArticulo());
			menuBar.add(getMenuAyuda());
		}
		return menuBar;
	}

	private JMenu getMenuArticulo() {
		if (menuArticulo == null)
		{
			menuArticulo = new JMenu();
			menuArticulo.setText("Art�culo");
			menuArticulo.add("Consultar");
			menuArticulo.add("Nuevo");
		}
		return menuArticulo;
	}

	private JMenu getMenuAyuda() {
		if (menuAyuda == null)
		{
			menuAyuda = new JMenu();
			menuAyuda.setText("Ayuda");
			menuAyuda.add("Manual de Usuario");
		}
		return menuAyuda;
	}

	private JMenu getMenuArchivo() {
		if (menuArchivo == null)
		{
			menuArchivo = new JMenu();
			menuArchivo.setText("Archivo");
			menuArchivo.add("Salir");
		}
		return menuArchivo;
	}

	private JButton getBtnVentas()
	{
		if(btnVentas == null)
		{
			int witdh = 100;
			int height = 100;
			btnVentas = new JButton();
			btnVentas.setSize(new Dimension(witdh, height));
			btnVentas.setMinimumSize(new Dimension(witdh, height));
			btnVentas.setText(PropertiesManager.getProperty("principalView.button.ventas"));

			ImageIcon iconoOriginal = new ImageIcon(this.getClass().getResource("/img/bolsa_blanca.png"));
//			Image imgagenOriginal = iconoOriginal.getImage();
//		    Image imagenEscalada = imgagenOriginal.getScaledInstance(witdh-50, height-50, Image.SCALE_DEFAULT);
//		    ImageIcon iconoEscalado = new ImageIcon(imagenEscalada);

			btnVentas.setIcon(iconoOriginal);
			btnVentas.setHorizontalTextPosition( SwingConstants.CENTER );
			btnVentas.setVerticalTextPosition( SwingConstants.BOTTOM );
			btnVentas.setMargin(new Insets(-1, -1, -1, -1));
			btnVentas.addMouseListener(new MouseAdapter()
			{
				public void mouseClicked(MouseEvent e)
				{
					if (getJTabbedPane().indexOfComponent(ventaController.getVentaView()) == -1) {
						logger.info("Agregando el panel de ventas al tabbedPane");
						getJTabbedPane().addTab(PropertiesManager.getProperty("venta.titulo"), ventaController.getVentaView());
					}
					logger.info("Mostrando el panel de ventas");
					getJTabbedPane().setVisible(true);
					ventaController.mostrarVentasPanel();
					getJTabbedPane().setSelectedComponent(ventaController.getVentaView());
				}
			});
		}

		return btnVentas;
	}
	
	private JButton getBtnArticulos()
	{
		if(btnArticulos == null)
		{
			btnArticulos = new JButton();
			btnArticulos.setSize(new Dimension(100, 100));
			btnArticulos.setMinimumSize(new Dimension(100, 100));
			btnArticulos.setText(PropertiesManager.getProperty("principalView.button.articulos"));
			btnArticulos.setIcon(new ImageIcon(this.getClass().getResource("/img/remera.png")));
			btnArticulos.setHorizontalTextPosition( SwingConstants.CENTER );
			btnArticulos.setVerticalTextPosition( SwingConstants.BOTTOM );
			btnArticulos.setMargin(new Insets(-1, -1, -1, -1));
			btnArticulos.addMouseListener(new MouseAdapter()
			{
				public void mouseClicked(MouseEvent e)
				{
					if (getJTabbedPane().indexOfComponent(productoController.getProductoView()) == -1) {
						logger.info("Agregando el panel de articulos al tabbedPane");
						getJTabbedPane().addTab(PropertiesManager.getProperty("producto.titulo"), productoController.getProductoView());
					}
					logger.info("Mostrando el panel de articulos");
					getJTabbedPane().setVisible(true);
					productoController.mostrarProductosPanel();
					getJTabbedPane().setSelectedComponent(productoController.getProductoView());
				}
			});
		}

		return btnArticulos;
	}
	
	private JButton getBtnClientes()
	{
		if(btnClientes == null)
		{
			btnClientes = new JButton();
			btnClientes.setText(PropertiesManager.getProperty("principalView.button.clientes"));
			btnClientes.setIcon(new ImageIcon(this.getClass().getResource("/img/clientes.png")));
			
			btnClientes.setHorizontalTextPosition( SwingConstants.CENTER );
			btnClientes.setVerticalTextPosition( SwingConstants.BOTTOM );
			btnClientes.setMargin(new Insets(-1, -1, -1, -1));
			
			btnClientes.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e)	{
					if (getJTabbedPane().indexOfComponent(clienteController.getClientePanel()) == -1) {
						logger.info("Agregando el panel de clientes al tabbedPane");
						jTabbedPaneConBoton.addTab(PropertiesManager.getProperty("cliente.titulo"), clienteController.getClientePanel());
					}
					logger.info("Mostrando el panel de clientes");
					getJTabbedPane().setVisible(true);
					clienteController.mostrarClientePanel();
					getJTabbedPane().setSelectedComponent(clienteController.getClientePanel());
				}
			});
		}

		return btnClientes;
	}
	
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane()
	{
		if(jContentPane == null)
		{
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.setBackground(Color.GRAY);
			jContentPane.add(getPnlToolBarArrow(), BorderLayout.WEST);	
			jContentPane.add(getJplPrincipalPanel(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	private DetailPanel getJplPrincipalPanel() {
		if(pnlPrincipal == null)
		{
			pnlPrincipal = new DetailPanel();
			pnlPrincipal.setLayout(new BorderLayout());
			pnlPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

			pnlPrincipal.add(getLblLogo(), BorderLayout.WEST);
			pnlPrincipal.add(getJTabbedPane(), BorderLayout.CENTER);
		}
		return pnlPrincipal;
	}

	private JLabel getLblLogo() {
		if(lblLogo == null)
		{
			lblLogo = new JLabel();
//			lblLogo.setIcon(new ImageIcon(this.getClass().getResource("/img/logo.png")));
		}	
		return lblLogo;
	}

	private JPanel getPnlToolBarArrow() {
		if(pnlToolBarArrow == null)
		{
			pnlToolBarArrow = new JPanel();
			pnlToolBarArrow.setLayout(new BoxLayout(pnlToolBarArrow, BoxLayout.X_AXIS));
			pnlToolBarArrow.setBackground(Color.GRAY);
			pnlToolBarArrow.add(getPnlToolBar());
			pnlToolBarArrow.add(getPanelFlecha());
		}
		return pnlToolBarArrow;
	}

	private DetailPanel getPnlToolBar() {
		if(pnlToolBar == null)
		{
			pnlToolBar = new DetailPanel();
			pnlToolBar.setLayout(new BorderLayout());
			pnlToolBar.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
			pnlToolBar.add(getQuickAccessBar());
		}
		return pnlToolBar;
	}

	/**
	 * This method initializes jLabel	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JLabel getLblArrow()
	{
		if(lblArrow == null)
		{
			lblArrow = new JLabel();
			lblArrow.setSize(new Dimension(20,20));
			lblArrow.setIcon(new ImageIcon(this.getClass().getResource("/img/flecha_izquierda.jpg")));

			lblArrow.addMouseListener(new MouseAdapter()
			{
				public void mouseClicked(MouseEvent e)
				{
					if(pnlToolBar.isVisible())
					{
						//jToolBar.setVisible(false);
						pnlToolBar.setVisible(false);
						//panelFondo2.setPreferredSize(new Dimension(jContentPane.getWidth() - 23, 0));
						//panelFondo2.setBorder(BorderFactory.createEmptyBorder(17, 14, 0, 14));
						//jTabbedPane.setPreferredSize(new Dimension(jContentPane.getWidth() - 68, jContentPane.getHeight() - 48));
						lblArrow.setIcon(new ImageIcon(this.getClass().getResource("/img/flecha_derecha.jpg")));
						pnlArrow.setBorder(BorderFactory.createEmptyBorder(23, 3, 0, 0));
					}
					else
					{
						//jToolBar.setVisible(true);
						pnlToolBar.setVisible(true);
						//panelFondo2.setPreferredSize(new Dimension(jContentPane.getWidth() - 163, 0));
						//panelFondo2.setBorder(BorderFactory.createEmptyBorder(17, 14, 0, 14));
						//jTabbedPane.setPreferredSize(new Dimension(jContentPane.getWidth() - 207, jContentPane.getHeight() - 48));
						lblArrow.setIcon(new ImageIcon(this.getClass().getResource("/img/flecha_izquierda.jpg")));
						pnlArrow.setBorder(BorderFactory.createEmptyBorder(23, 0, 0, 0));
					}
				}
			});	
		}

		return lblArrow;
	}

	/**
	 * This method initializes jTabbedPane	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */
	private JTabbedPaneConBoton getJTabbedPane()
	{
		if(jTabbedPaneConBoton == null)
		{	
			jTabbedPaneConBoton = new JTabbedPaneConBoton();
//			jtbPrincipalPanel.setTabLayoutPolicy(JTabbedPaneConBoton.SCROLL_TAB_LAYOUT);
			jTabbedPaneConBoton.setTabLayoutPolicy(JTabbedPaneConBoton.CENTER);	
			jTabbedPaneConBoton.setVisible(false);
		}

		return jTabbedPaneConBoton;
	}

	/**
	 * This method initializes jToolBar	
	 * 	
	 * @return javax.swing.JToolBar	
	 */
	private JToolBar getQuickAccessBar()
	{
		if(tlbQuickAccess == null)
		{
			tlbQuickAccess = new JToolBar(null, JToolBar.VERTICAL);
			tlbQuickAccess.setLayout(new BoxLayout(getQuickAccessBar(), BoxLayout.Y_AXIS));
			tlbQuickAccess.add(getBtnVentas());
			tlbQuickAccess.add(getBtnArticulos());
			tlbQuickAccess.add(getBtnClientes());
			tlbQuickAccess.setFloatable(false);
		}

		return tlbQuickAccess;
	}

	/**
	 * This method initializes panelFlecha	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanelFlecha()
	{
		if(pnlArrow == null)
		{
			pnlArrow = new JPanel();
			pnlArrow.setLayout(new BoxLayout(getPanelFlecha(), BoxLayout.Y_AXIS));
			pnlArrow.add(getLblArrow(), null);
			pnlArrow.setBackground(Color.GRAY);
		}

		return pnlArrow;
	}

	public void setVentaController(VentaController ventaController) {
		this.ventaController = ventaController;
	}

	public void setProductoController(ProductoController productoController) {
		this.productoController = productoController;
	}

	public void setClienteController(ClienteController clienteController) {
		this.clienteController = clienteController;
	}
}