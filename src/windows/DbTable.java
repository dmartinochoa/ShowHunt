package windows;

import controlador.*;

import modelo.*;
import net.proteanit.sql.DbUtils;

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

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DbTable extends JFrame {
	private Controlador control;
	private Modelo model;

	private int xx, xy; // Position to move window
	private JLabel lblBackround;
	private JLabel lblExit;
	private JLabel lblMinimize;
	private JTable table;
	private JScrollPane scrollPane;
	private JButton btnUsers;
	private JButton btnConcerts;
	private JButton btnBand;
	private final JButton btnBack = new JButton("Back");

	public DbTable() {
		setTitle("ShowHunt");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/img/2smaller.jpg")));
		setResizable(false);
		setBounds(100, 100, 800, 500);
		setUndecorated(true); // Removes border
		setShape(new RoundRectangle2D.Double(0, 0, 800, 500, 40, 40)); // Border radius
		getContentPane().setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 55, 772, 414);
		getContentPane().add(scrollPane);

//DATA TABLE
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setFont(new Font("SansSerif", Font.PLAIN, 14));
		table.setModel(new DefaultTableModel(new Object[][] { {}, }, new String[] {}));

//BOTONES
		btnConcerts = new JButton("Concert List");
		btnConcerts.setFont(new Font("SansSerif", Font.PLAIN, 11));
		btnConcerts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				table.setModel(DbUtils.resultSetToTableModel(model.getShows()));
			}
		});

		btnConcerts.setBounds(140, 11, 119, 23);
		getContentPane().add(btnConcerts);

		btnUsers = new JButton("User List");
		btnUsers.setFont(new Font("SansSerif", Font.PLAIN, 11));
		btnUsers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				table.setModel(DbUtils.resultSetToTableModel(model.getUsers()));
			}
		});
		btnUsers.setBounds(10, 11, 120, 23);
		getContentPane().add(btnUsers);

		btnBand = new JButton("Band List");
		btnBand.setFont(new Font("SansSerif", Font.PLAIN, 11));
		btnBand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				table.setModel(DbUtils.resultSetToTableModel(model.getBands()));
			}
		});
		btnBand.setBounds(269, 11, 109, 23);
		getContentPane().add(btnBand);
		btnBack.setFont(new Font("SansSerif", Font.PLAIN, 11));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				control.goToLogin();
			}
		});

		btnBack.setBounds(592, 12, 69, 23);
		getContentPane().add(btnBack);

// EXIT
		lblExit = new JLabel("x");
		lblExit.setForeground(Color.WHITE);
		lblExit.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 40));
		lblExit.setBounds(752, 11, 38, 33);
		getContentPane().add(lblExit);
		lblExit.addMouseListener(new MouseAdapter() {

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

			public void mouseClicked(MouseEvent e) {
				setState(ICONIFIED);
			}

			public void mouseEntered(MouseEvent e) {
				lblMinimize.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
		});

// BACKROUND
		lblBackround = new JLabel("");
		lblBackround.setIcon(new ImageIcon(DbTable.class.getResource("/img/2smaller.jpg")));
		lblBackround.setBounds(0, 0, 800, 500);
		getContentPane().add(lblBackround);

// Listeners para mover la ventana
		getContentPane().addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent e) {
				xx = e.getX();
				xy = e.getY();
			}
		});

		getContentPane().addMouseMotionListener(new MouseMotionAdapter() {

			public void mouseDragged(MouseEvent e) {
				int x = e.getXOnScreen();
				int y = e.getYOnScreen();
				DbTable.this.setLocation(x - xx, y - xy);
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