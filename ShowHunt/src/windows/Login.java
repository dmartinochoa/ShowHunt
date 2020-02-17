package windows;

import controlador.*;

import modelo.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Point;

import javax.swing.JPasswordField;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.RoundRectangle2D;

public class Login extends JFrame {
	private Controlador control;
	private Modelo model;

	private int xx, xy; // Position to move window

	private JTextField txtUsername;
	private JLabel lblShowhunt;
	private JLabel lblUsername;
	private JButton btnLogin;
	private JButton btnNewUser;
	private JPasswordField passwordField;
	private JLabel lblBackround;
	private JLabel lblExit;

	private JLabel lblPassword;
	private JLabel lblMinimize;
	private JLabel lblNewLabel;

	public Login() {
		setTitle("ShowHunt");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/img/logoWhiteMid.png")));
		setResizable(false);
		setBounds(100, 100, 800, 500);
		setUndecorated(true); // Removes border
		setShape(new RoundRectangle2D.Double(0, 0, 800, 500, 40, 40)); // Border radius
		getContentPane().setLayout(null);

// BOTENES
		// Login button
		btnLogin = new JButton("Log in");
		btnLogin.setBounds(355, 333, 87, 25);
		btnLogin.setFont(new Font("SansSerif", Font.BOLD, 15));
		getContentPane().add(btnLogin);
		btnLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			public void mouseClicked(MouseEvent e) {
				
				String userName = txtUsername.getText();
				char [] userPassArray = passwordField.getPassword();
				String userPass = String.valueOf(userPassArray);
				System.out.println(userPass);
				
				control.loginPress(userName,userPass);
			}
		});

		// Create account button
		btnNewUser = new JButton("New User");
		btnNewUser.setBounds(342, 369, 115, 25);
		btnNewUser.setFont(new Font("SansSerif", Font.BOLD, 15));
		getContentPane().add(btnNewUser);
		btnNewUser.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNewUser.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			public void mouseClicked(MouseEvent e) {
				control.goToCreateAcc();
			}
		});

// LABELS
		lblShowhunt = new JLabel("ShowHunt");
		lblShowhunt.setForeground(Color.WHITE);
		lblShowhunt.setBounds(273, 26, 274, 107);
		lblShowhunt.setFont(new Font("SansSerif", Font.BOLD, 50));
		getContentPane().add(lblShowhunt);

		lblUsername = new JLabel("UserName:");
		lblUsername.setForeground(Color.WHITE);
		lblUsername.setBounds(235, 268, 87, 20);
		lblUsername.setFont(new Font("SansSerif", Font.BOLD, 15));
		getContentPane().add(lblUsername);

		lblPassword = new JLabel("Password:");
		lblPassword.setBounds(235, 295, 87, 20);
		lblPassword.setFont(new Font("SansSerif", Font.BOLD, 15));
		getContentPane().add(lblPassword);
		
		lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Login.class.getResource("/img/logoWhiteMid.png")));
		lblNewLabel.setBounds(330, 113, 152, 140);
		getContentPane().add(lblNewLabel);

// TEXT FIELDS
		txtUsername = new JTextField();
		txtUsername.setBounds(330, 268, 152, 22);
		txtUsername.setFont(new Font("SansSerif", Font.PLAIN, 13));
		getContentPane().add(txtUsername);
		txtUsername.setColumns(10);
		txtUsername.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				}
			}
		});

		passwordField = new JPasswordField();
		passwordField.setBounds(330, 295, 152, 22);
		getContentPane().add(passwordField);
		passwordField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				}
			}
		});

// EXIT
		lblExit = new JLabel("x");
		lblExit.setForeground(Color.WHITE);
		lblExit.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 40));
		lblPassword.setForeground(Color.WHITE);
		lblExit.setBounds(752, 11, 30, 33);
		getContentPane().add(lblExit);
		lblExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}

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

			public void mouseEntered(MouseEvent e) {
				lblMinimize.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
		});

// BACKROUND
		lblBackround = new JLabel("");
		lblBackround.setIcon(new ImageIcon(Login.class.getResource("/img/2smaller.jpg")));
		lblBackround.setBounds(0, 0, 800, 500);
		getContentPane().add(lblBackround);

// Listeners para mover la ventana
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
				Login.this.setLocation(x - xx, y - xy);
			}
		});
	}
	
	public void loginMessage() {
		JOptionPane.showMessageDialog(btnLogin, "Incorrect username or password");
	}

// SETTERS
	public void setControl(Controlador control) {
		this.control = control;
	}

	public void setModelo(Modelo model) {
		this.model = model;
	}
}
