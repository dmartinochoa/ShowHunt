package windows;

import controlador.*;

import modelo.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.RoundRectangle2D;
import java.util.Arrays;
import java.awt.Cursor;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

import javax.swing.JPasswordField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class CreateAccount extends JFrame {
	private Controlador control;
	private Modelo model;

	private int xx, xy; // position to move window

	private JTextField txtUsername;
	private JLabel lblShowhunt;
	private JLabel lblUsername;
	private JButton btnCreateAccount;
	private JLabel lblPwd;
	private JPasswordField txtPwd;
	private JPasswordField txtPwdCheck;
	private JLabel lblPwdCheck;
	private JLabel lblCity;
	private JLabel lblEmail;
	private JTextField txtEmail;
	private JCheckBox checkTerms;
	private JComboBox comboBoxCity;
	private JLabel lblBackround;
	private JLabel lblExit;
	private JLabel lblMinimize;

	public CreateAccount() {
		setTitle("ShowHunt");

		setIconImage(Toolkit.getDefaultToolkit().getImage(CreateAccount.class.getResource("/img/logoWhiteMid.png")));
		setResizable(false);
		setBounds(100, 100, 800, 500);

		setUndecorated(true); // Removes border
		setShape(new RoundRectangle2D.Double(0, 0, 800, 500, 40, 40)); // Border radius
		getContentPane().setLayout(null);

// BOTENES
		// Create account button
		btnCreateAccount = new JButton("Create Account");
		btnCreateAccount.setBounds(330, 369, 155, 25);
		btnCreateAccount.setFont(new Font("SansSerif", Font.BOLD, 15));
		getContentPane().add(btnCreateAccount);
		btnCreateAccount.setEnabled(false);
		btnCreateAccount.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnCreateAccount.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			public void mouseClicked(MouseEvent e) {
				createAccChecker();
			}
		});

		// Back button
		JButton btnBack = new JButton("Back");
		btnBack.setBounds(353, 407, 97, 25);
		btnBack.setFont(new Font("SansSerif", Font.BOLD, 15));
		getContentPane().add(btnBack);
		btnBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnBack.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			public void mouseClicked(MouseEvent e) {
				control.goToLogin();
			}
		});

// LABELS
		lblShowhunt = new JLabel("Create Account");
		lblShowhunt.setForeground(Color.WHITE);
		lblShowhunt.setBounds(200, 42, 400, 107);
		lblShowhunt.setFont(new Font("SansSerif", Font.BOLD, 50));
		getContentPane().add(lblShowhunt);

		lblEmail = new JLabel("E:mail:");
		lblEmail.setForeground(Color.WHITE);
		lblEmail.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblEmail.setBounds(235, 180, 87, 20);
		getContentPane().add(lblEmail);

		lblUsername = new JLabel("UserName:");
		lblUsername.setForeground(Color.WHITE);
		lblUsername.setBounds(235, 210, 87, 20);
		lblUsername.setFont(new Font("SansSerif", Font.BOLD, 15));
		getContentPane().add(lblUsername);

		lblPwd = new JLabel("Password:");
		lblPwd.setForeground(Color.WHITE);
		lblPwd.setBounds(235, 240, 87, 20);
		lblPwd.setFont(new Font("SansSerif", Font.BOLD, 15));
		getContentPane().add(lblPwd);

		lblPwdCheck = new JLabel("Password:");
		lblPwdCheck.setForeground(Color.WHITE);
		lblPwdCheck.setBounds(235, 270, 87, 20);
		lblPwdCheck.setFont(new Font("SansSerif", Font.BOLD, 15));
		getContentPane().add(lblPwdCheck);

		lblCity = new JLabel("City:");
		lblCity.setForeground(Color.WHITE);
		lblCity.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblCity.setBounds(235, 300, 87, 20);
		getContentPane().add(lblCity);

// TEXTFIELDS
		txtEmail = new JTextField();
		txtEmail.setFont(new Font("SansSerif", Font.PLAIN, 13));
		txtEmail.setColumns(10);
		txtEmail.setBounds(330, 180, 155, 22);
		getContentPane().add(txtEmail);
		txtEmail.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					createAccChecker();
				}
			}
		});

		txtUsername = new JTextField();
		txtUsername.setBounds(330, 210, 155, 22);
		txtUsername.setFont(new Font("SansSerif", Font.PLAIN, 13));
		getContentPane().add(txtUsername);
		txtUsername.setColumns(10);
		txtUsername.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					createAccChecker();
				}
			}
		});

		txtPwd = new JPasswordField();
		txtPwd.setBounds(330, 240, 155, 22);
		getContentPane().add(txtPwd);
		txtPwd.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					createAccChecker();
				}
			}
		});

		txtPwdCheck = new JPasswordField();
		txtPwdCheck.setBounds(330, 270, 155, 22);
		getContentPane().add(txtPwdCheck);
		txtPwdCheck.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					createAccChecker();
				}
			}
		});

// DROPDOWN
		comboBoxCity = new JComboBox();
		comboBoxCity.setFont(new Font("SansSerif", Font.BOLD, 12));
		comboBoxCity.setModel(new DefaultComboBoxModel(
				new String[] { null, "Madrid", "Barcerlona", "Esto es trabajo de chino", "test" }));
		comboBoxCity.setBounds(330, 300, 155, 22);
		getContentPane().add(comboBoxCity);
		comboBoxCity.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					createAccChecker();
				}
			}
		});

// CHECKBOX
		checkTerms = new JCheckBox("Accept terms of use");
		checkTerms.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (checkTerms.isSelected()) {
					btnCreateAccount.setEnabled(true);
				} else {
					btnCreateAccount.setEnabled(false);
				}
			}
		});
		checkTerms.setFont(new Font("SansSerif", Font.BOLD, 12));
		checkTerms.setBounds(330, 329, 155, 23);
		getContentPane().add(checkTerms);
		checkTerms.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					createAccChecker();
				}
			}
		});

// EXIT
		lblExit = new JLabel("x");
		lblExit.setForeground(Color.WHITE);
		lblExit.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 40));
		lblExit.setBounds(752, 11, 30, 33);
		getContentPane().add(lblExit);
		lblExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				lblExit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
		});

// Minimize		
		lblMinimize = new JLabel("-");
		lblMinimize.setForeground(Color.WHITE);
		lblMinimize.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 40));
		lblMinimize.setBounds(715, 11, 30, 33);
		getContentPane().add(lblMinimize);
		lblMinimize.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setState(ICONIFIED);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				lblMinimize.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
		});

// BACKROUND
		lblBackround = new JLabel("");
		lblBackround.setIcon(new ImageIcon(Login.class.getResource("/img/2smaller.jpg")));
		lblBackround.setBounds(0, 0, 800, 500);
		getContentPane().add(lblBackround);

// Listeners para poder mover la vista
		getContentPane().addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				xx = e.getX();
				xy = e.getY();
			}
		});
		getContentPane().addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int x = e.getXOnScreen();
				int y = e.getYOnScreen();
				CreateAccount.this.setLocation(x - xx, y - xy);
			}
		});
	}

// Create Account info checker
	public void createAccChecker() {
		char[] pwd = txtPwd.getPassword();
		char[] pwdCheck = txtPwdCheck.getPassword();
		// Primero comprueba que todos los campos esten rellenos
		if (pwd.length > 0 && pwdCheck.length > 0 && txtEmail.getText().length() > 0
				&& txtUsername.getText().length() > 0 && comboBoxCity.getSelectedItem() != null) {
			// Despues comprueba que los campos sean validos
			if (checkTerms.isSelected() && Arrays.equals(pwd, pwdCheck) && pwd.length >= 8) {
				// Aqui deberia comprobar que el usuario/email no existe en la base de datos
				control.createAccountPress();
				// pdwHash sera la variable que guardaremos como contraseña
				String pwdHash = String.valueOf(txtPwd.getText().hashCode());
			} else if (checkTerms.isSelected() && Arrays.equals(pwd, pwdCheck) && pwd.length < 8) {
				JOptionPane.showMessageDialog(checkTerms, "Your password must be at least 8 characters long");
			} else if (checkTerms.isSelected() && !Arrays.equals(pwd, pwdCheck) && pwd.length >= 8) {
				JOptionPane.showMessageDialog(checkTerms, "Your passwords dont match");
			} else if (!checkTerms.isSelected()) {
				JOptionPane.showMessageDialog(checkTerms, "You must accept the terms of use");
			}
		} else {
			JOptionPane.showMessageDialog(checkTerms, "You must fill out all the fields");
		}
	}

// Setters
	public void setControl(Controlador control) {
		this.control = control;
	}

	public void setModelo(Modelo model) {
		this.model = model;
	}
}