package com.mitnick.presentacion.vistas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;

import com.mitnick.utils.LoginUtils;
import com.mitnick.utils.PropertiesManager;
import com.mitnick.utils.anotaciones.View;
import com.mitnick.utils.locator.BeanLocator;

@View("loginView")
public class LoginView extends JDialog {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private LoginUtils loginUtils;
	
	protected Logger logger = Logger.getLogger(this.getClass());
	
	private JTextField txtUser;
	private JPasswordField txtPassword;
	private JButton btnNewButton;
	
	private LoginView thisView;
	
	public LoginView() {
		thisView = this;
		
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle(PropertiesManager.getProperty("loginView.dialog.title"));
		setSize(264, 175);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel(PropertiesManager.getProperty("loginView.label.username"));
		lblNewLabel.setBounds(36, 28, 85, 14);
		getContentPane().add(lblNewLabel);
		
		txtUser = new JTextField();
		txtUser.setBounds(131, 25, 86, 20);
		getContentPane().add(txtUser);
		txtUser.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel(PropertiesManager.getProperty("loginView.label.password"));
		lblNewLabel_1.setBounds(36, 63, 85, 14);
		getContentPane().add(lblNewLabel_1);
		
		txtPassword = new JPasswordField();
		txtPassword.setBounds(131, 60, 86, 20);
		getContentPane().add(txtPassword);
		txtPassword.setColumns(10);
		
		btnNewButton = new JButton(PropertiesManager.getProperty("loginView.button.login"));
		btnNewButton.setBounds(131, 91, 85, 23);
		btnNewButton.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			@Override public void actionPerformed(ActionEvent e) {
				try {
					if(loginUtils.authenticate(txtUser.getText(), txtPassword.getText()) != null) {
						logger.info("Iniciando la pantalla principal...");
						PrincipalView principalView = (PrincipalView) BeanLocator.getBean("principalView");
						thisView.setVisible(false);
						principalView.setVisible(true);
					}
					else {
						JOptionPane.showConfirmDialog((java.awt.Component) null, PropertiesManager.getProperty("error.loginView.auth"), "Error", JOptionPane.DEFAULT_OPTION);
					}
				}
				catch(BadCredentialsException e1) {
					JOptionPane.showConfirmDialog((java.awt.Component) null, PropertiesManager.getProperty("error.loginView.auth"), "Error", JOptionPane.DEFAULT_OPTION);
				}
				txtUser.setText("");
				txtPassword.setText("");
				txtUser.requestFocus();
			}
		});
		getContentPane().add(btnNewButton);
		
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
