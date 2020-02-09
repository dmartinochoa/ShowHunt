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

public class ManageAccount extends JFrame {
	private Controlador control;
	private Modelo model;

	private int xx, xy; // position to move window

	private JTextField txtCurrentPwd;
	private JLabel lblManageAcc;
	private JLabel lblChangeCity;
	private JButton btnChangePwd;
	private JLabel lblNewPwd;
	private JPasswordField txtNewPwd;
	private JPasswordField txtPwdCheck;
	private JLabel lblPwdCheck;
	private JLabel lblcurrentCity;
	private JLabel lblChangePwd;
	private JComboBox comboBoxCity;
	private JLabel lblBackround;
	private JLabel lblExit;
	private JLabel lblMinimize;
	private JLabel lblCurrentPwd;
	private JLabel lblNewCity;
	private JLabel lblcurrentCityShow;
	private JButton btnChangeCity;
	private JButton btnBack;

	public ManageAccount() {
		setTitle("ShowHunt");
		setIconImage(Toolkit.getDefaultToolkit().getImage(ManageAccount.class.getResource("/img/logoWhiteMid.png")));
		setResizable(false);
		setBounds(100, 100, 800, 500);
		setUndecorated(true); // Removes border
		setShape(new RoundRectangle2D.Double(0, 0, 800, 500, 40, 40)); // Border radius
		getContentPane().setLayout(null);

// BOTENES
		// Change pwd button
		btnChangePwd = new JButton("Change Password");
		btnChangePwd.setBounds(159, 298, 168, 25);
		btnChangePwd.setFont(new Font("SansSerif", Font.BOLD, 15));
		getContentPane().add(btnChangePwd);
		btnChangePwd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnChangePwd.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			public void mouseClicked(MouseEvent e) {
				createAccChecker();
			}
		});

		// Change city button
		btnChangeCity = new JButton("Change City");
		btnChangeCity.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnChangeCity.setBounds(585, 262, 168, 25);
		getContentPane().add(btnChangeCity);

		// Back button
		btnBack = new JButton("Back");
		btnBack.setBounds(663, 451, 97, 25);
		btnBack.setFont(new Font("SansSerif", Font.BOLD, 15));
		getContentPane().add(btnBack);
		btnBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnBack.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			public void mouseClicked(MouseEvent e) {
				control.goToHomeFromManageAcc();
			}
		});

// LABELS
		lblManageAcc = new JLabel("Manage Account");
		lblManageAcc.setForeground(Color.WHITE);
		lblManageAcc.setBounds(257, 11, 290, 107);
		lblManageAcc.setFont(new Font("SansSerif", Font.BOLD, 35));
		getContentPane().add(lblManageAcc);

		lblChangePwd = new JLabel("Change Password");
		lblChangePwd.setForeground(Color.WHITE);
		lblChangePwd.setFont(new Font("SansSerif", Font.BOLD, 20));
		lblChangePwd.setBounds(79, 113, 187, 33);
		getContentPane().add(lblChangePwd);

		lblChangeCity = new JLabel("Change Default City");
		lblChangeCity.setForeground(Color.WHITE);
		lblChangeCity.setBounds(539, 119, 221, 25);
		lblChangeCity.setFont(new Font("SansSerif", Font.BOLD, 20));
		getContentPane().add(lblChangeCity);

		lblNewPwd = new JLabel("New Password:");
		lblNewPwd.setForeground(Color.WHITE);
		lblNewPwd.setBounds(38, 218, 128, 20);
		lblNewPwd.setFont(new Font("SansSerif", Font.BOLD, 15));
		getContentPane().add(lblNewPwd);

		lblPwdCheck = new JLabel("Retype Password:");
		lblPwdCheck.setForeground(Color.WHITE);
		lblPwdCheck.setBounds(38, 251, 140, 20);
		lblPwdCheck.setFont(new Font("SansSerif", Font.BOLD, 15));
		getContentPane().add(lblPwdCheck);

		lblcurrentCity = new JLabel("Current City:");
		lblcurrentCity.setForeground(Color.WHITE);
		lblcurrentCity.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblcurrentCity.setBounds(485, 187, 109, 20);
		getContentPane().add(lblcurrentCity);

		lblCurrentPwd = new JLabel("Current Password:");
		lblCurrentPwd.setForeground(Color.WHITE);
		lblCurrentPwd.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblCurrentPwd.setBounds(38, 187, 140, 20);
		getContentPane().add(lblCurrentPwd);

		lblcurrentCityShow = new JLabel("idfk");
		lblcurrentCityShow.setForeground(Color.WHITE);
		lblcurrentCityShow.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblcurrentCityShow.setBounds(616, 186, 109, 20);
		getContentPane().add(lblcurrentCityShow);

		lblNewCity = new JLabel("New City:");
		lblNewCity.setForeground(Color.WHITE);
		lblNewCity.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblNewCity.setBounds(485, 218, 109, 20);
		getContentPane().add(lblNewCity);

// TEXT FIELDS
		txtCurrentPwd = new JTextField();
		txtCurrentPwd.setBounds(182, 187, 155, 22);
		txtCurrentPwd.setFont(new Font("SansSerif", Font.PLAIN, 13));
		getContentPane().add(txtCurrentPwd);
		txtCurrentPwd.setColumns(10);
		txtCurrentPwd.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					createAccChecker();
				}
			}
		});

		txtNewPwd = new JPasswordField();
		txtNewPwd.setBounds(182, 219, 155, 22);
		getContentPane().add(txtNewPwd);
		txtNewPwd.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					createAccChecker();
				}
			}
		});

		txtPwdCheck = new JPasswordField();
		txtPwdCheck.setBounds(182, 252, 155, 22);
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
		comboBoxCity.setBounds(598, 218, 155, 22);
		getContentPane().add(comboBoxCity);
		comboBoxCity.addKeyListener(new KeyAdapter() {
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
				ManageAccount.this.setLocation(x - xx, y - xy);
			}
		});
	}

// Create Account info checker
	public void createAccChecker() {
		char[] pwd = txtNewPwd.getPassword();
		char[] pwdCheck = txtPwdCheck.getPassword();
		// Primero comprueba que todos los campos esten rellenos
		// faltaria comprobar con la contrase�a original
		if (pwd.length > 0 && pwdCheck.length > 0) {

			if (Arrays.equals(pwd, pwdCheck) && pwd.length >= 8) {
				JOptionPane.showMessageDialog(btnChangePwd, "Your password was changed");
				// pdwHash sera la variable que guardaremos como contrase�a
				String pwdHash = String.valueOf(txtNewPwd.getText().hashCode());

			} else if (Arrays.equals(pwd, pwdCheck) && pwd.length < 8) {
				JOptionPane.showMessageDialog(btnChangePwd, "Your password must be at least 8 characters long");

			} else if (!Arrays.equals(pwd, pwdCheck) && pwd.length >= 8) {
				JOptionPane.showMessageDialog(btnChangePwd, "Your passwords dont match");
			}
		} else {
			JOptionPane.showMessageDialog(btnChangePwd, "Fill out all the information");

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