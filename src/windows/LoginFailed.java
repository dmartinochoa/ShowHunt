package windows;

import controlador.*;

import modelo.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;

import javax.swing.ImageIcon;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.RoundRectangle2D;

public class LoginFailed extends JFrame {
	private Controlador control;
	private Modelo model;

	private int xx, xy; // Position to move window
	private JLabel lblShowhunt;
	private JLabel lblBackround;
	private JLabel lblExit;
	private JLabel lblMinimize;
	private JLabel lblNewLabel;

	public LoginFailed() {
		setTitle("ShowHunt");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/img/fail.jpg")));
		setResizable(false);
		setBounds(100, 100, 800, 500);
		setUndecorated(true); // Removes border
		setShape(new RoundRectangle2D.Double(0, 0, 800, 500, 40, 40)); // Border radius
		getContentPane().setLayout(null);

// LABELS
		lblShowhunt = new JLabel("I'll fucking do it again");
		lblShowhunt.setForeground(Color.WHITE);
		lblShowhunt.setBounds(299, 393, 579, 107);
		lblShowhunt.setFont(new Font("SansSerif", Font.BOLD, 40));
		getContentPane().add(lblShowhunt);

		lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Login.class.getResource("/img/logoWhiteMid.png")));
		lblNewLabel.setBounds(1, 11, 152, 140);
		getContentPane().add(lblNewLabel);

// EXIT
		lblExit = new JLabel("x");
		lblExit.setForeground(Color.WHITE);
		lblExit.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 40));
		lblExit.setBounds(752, 11, 38, 33);
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
		lblBackround.setIcon(new ImageIcon(LoginFailed.class.getResource("/img/fail.jpg")));
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
				LoginFailed.this.setLocation(x - xx, y - xy);
			}
		});
	}

// SETTERS
	public void setControl(Controlador control) {
		this.control = control;
	}

	public void setModelo(Modelo model) {
		this.model = model;
	}
}
