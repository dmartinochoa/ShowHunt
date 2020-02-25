package windows;

import javax.swing.ImageIcon;

import javax.swing.JFrame;

import controlador.Controlador;
import modelo.Modelo;
import net.proteanit.sql.DbUtils;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.RoundRectangle2D;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.awt.Cursor;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.UIManager;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ListSelectionModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;

public class Home extends JFrame {
	private Controlador control;
	private Modelo model;

	private int xx, xy; // Position to move window

	private JLabel lblExit;
	private JLabel lblMinimize;
	private JLabel lblBackround;
	private JLabel lblShowHunt;
	private JLabel lblUser;
	private JList listUser;
	private JLabel lblBandName;
	private JTextField txtBandName;
	private JTextField txtCity;
	private JLabel lblRecIcon;
	private JLabel lblFavIcon;
	private JComboBox comboBoxGenre;
	private JButton btnSearch;
	private JLabel lblCity;
	private JTable tableConcert;

	public Home() {
		getContentPane().setBackground(Color.DARK_GRAY);
		getContentPane().setFont(new Font("SansSerif", Font.BOLD, 15));
		this.setBackground(Color.DARK_GRAY);
		setTitle("ShowHunt");
		setIconImage(Toolkit.getDefaultToolkit().getImage(CreateAccount.class.getResource("/img/logoWhiteMid.png")));
		setResizable(false);
		setBounds(100, 100, 800, 500);
		setUndecorated(true); // Removes border
		setShape(new RoundRectangle2D.Double(0, 0, 800, 500, 40, 40));
		getContentPane().setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(25, 139, 751, 257);
		getContentPane().add(scrollPane);
		
// CONCERT LIST
		tableConcert = new JTable();
		tableConcert.setFont(new Font("SansSerif", Font.BOLD, 14));
		scrollPane.setViewportView(tableConcert);

// LABELS
		// Logo
		lblShowHunt = new JLabel("ShowHunt");
		lblShowHunt.setBounds(15, 5, 183, 63);
		lblShowHunt.setBackground(Color.WHITE);
		lblShowHunt.setForeground(Color.WHITE);
		lblShowHunt.setFont(new Font("SansSerif", Font.BOLD, 35));
		getContentPane().add(lblShowHunt);

		// Search lbls
		lblBandName = new JLabel("Band Name:");
		lblBandName.setForeground(Color.WHITE);
		lblBandName.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblBandName.setBackground(Color.WHITE);
		lblBandName.setBounds(15, 65, 99, 63);
		getContentPane().add(lblBandName);

		lblCity = new JLabel("City:");
		lblCity.setForeground(Color.WHITE);
		lblCity.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblCity.setBackground(Color.WHITE);
		lblCity.setBounds(247, 81, 38, 31);
		getContentPane().add(lblCity);

		// User Icon
		lblUser = new JLabel("");
		lblUser.setIcon(new ImageIcon(Home.class.getResource("/img/user.png")));
		lblUser.setBounds(652, 12, 32, 41);
		getContentPane().add(lblUser);
		lblUser.addMouseListener(new MouseAdapter() {

			public void mouseEntered(MouseEvent e) {
				lblUser.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			public void mouseClicked(MouseEvent e) {
				listUser.setVisible(true);
			}
		});

		// Icon lbls
		lblRecIcon = new JLabel("");
		lblRecIcon.setIcon(new ImageIcon(Home.class.getResource("/img/heart.png")));
		lblRecIcon.setBounds(565, 22, 24, 31);
		getContentPane().add(lblRecIcon);
		lblRecIcon.addMouseListener(new MouseAdapter() {

			public void mouseEntered(MouseEvent e) {
				lblRecIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			public void mouseClicked(MouseEvent e) {
			}
		});

		lblFavIcon = new JLabel("");
		lblFavIcon.setIcon(new ImageIcon(Home.class.getResource("/img/star.png")));
		lblFavIcon.setBounds(607, 20, 24, 33);
		getContentPane().add(lblFavIcon);
		lblFavIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblFavIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			public void mouseClicked(MouseEvent e) {
			}
		});

//TEXT FIELDS
		txtBandName = new JTextField();
		txtBandName.setText("\r\n");
		txtBandName.setFont(new Font("SansSerif", Font.BOLD, 15));
		txtBandName.setBounds(114, 86, 120, 20);
		getContentPane().add(txtBandName);
		txtBandName.setColumns(10);

		txtCity = new JTextField();
		txtCity.setText("\r\n");
		txtCity.setFont(new Font("SansSerif", Font.BOLD, 15));
		txtCity.setColumns(10);
		txtCity.setBounds(285, 86, 120, 20);
		getContentPane().add(txtCity);

// BOTONES

		btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (!(txtBandName.getText().trim().equals("")) && txtCity.getText().trim().equals("")) {
					tableConcert
							.setModel(DbUtils.resultSetToTableModel(model.searchByBand(txtBandName.getText().trim())));

				} else if (!(txtCity.getText().trim().equals("")) && txtBandName.getText().trim().equals("")) {
					tableConcert.setModel(DbUtils.resultSetToTableModel(model.searchByCity(txtCity.getText().trim())));

				} else if (!(txtCity.getText().trim().equals("")) && !(txtBandName.getText().trim().equals(""))) {
					tableConcert.setModel(DbUtils.resultSetToTableModel(
							model.cityAndBandSearch(txtCity.getText().trim(), txtBandName.getText().trim())));

				}
			}
		});
		btnSearch.setFont(new Font("SansSerif", Font.BOLD, 12));
		btnSearch.setBounds(415, 85, 75, 23);
		getContentPane().add(btnSearch);
		btnSearch.addMouseListener(new MouseAdapter() {

			public void mouseEntered(MouseEvent e) {
				btnSearch.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
		});

//COMBO BOX
		comboBoxGenre = new JComboBox();
		comboBoxGenre.setModel(new DefaultComboBoxModel(
				new String[] { "Search Genre", "Rock", "Metal", "Pop", "Rap", "Indie", "Funk" }));
		comboBoxGenre.setFont(new Font("SansSerif", Font.BOLD, 12));
		comboBoxGenre.setBounds(500, 85, 105, 23);
		getContentPane().add(comboBoxGenre);

// USER ACTION LIST
		listUser = new JList();
		listUser.setBorder(UIManager.getBorder("Menu.border"));
		listUser.setForeground(Color.WHITE);
		listUser.setFont(new Font("SansSerif", Font.BOLD, 15));
		listUser.setModel(new AbstractListModel() {
			String[] values = new String[] { "- Manage Account", "- Log Out" };

			public int getSize() {
				return values.length;
			}

			public Object getElementAt(int index) {
				return values[index];
			}
		});
		listUser.setToolTipText("");
		listUser.setBackground(Color.DARK_GRAY);
		listUser.setBounds(644, 65, 146, 51);
		getContentPane().add(listUser);
		listUser.setVisible(false);
		listUser.addMouseListener(new MouseAdapter() {

			public void mouseEntered(MouseEvent e) {
				listUser.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			public void mouseClicked(MouseEvent e) {
				int choice = listUser.getSelectedIndex();
				switch (choice) {
				case 0:
					control.goToManageAcc();
					break;
				case 1:
					control.goToLogin();
					break;
				}
				listUser.setVisible(false);
			}

			public void mouseExited(MouseEvent e) {
				listUser.setVisible(false);
			}
		});

// EXIT
		lblExit = new JLabel("x");
		lblExit.setBounds(752, 11, 38, 33);
		lblExit.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}

			public void mouseEntered(MouseEvent e) {
				lblExit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
		});

		lblExit.setForeground(Color.WHITE);
		lblExit.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 40));
		getContentPane().add(lblExit);

// Minimize		
		lblMinimize = new JLabel("-");
		lblMinimize.setBounds(715, 11, 24, 33);
		lblMinimize.setForeground(Color.WHITE);
		lblMinimize.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 40));
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
		lblBackround.setForeground(Color.WHITE);
		lblBackround.setFont(new Font("SansSerif", Font.BOLD, 12));
		lblBackround.setBounds(0, -124, 1100, 750);
		lblBackround.setIcon(new ImageIcon(Login.class.getResource("/img/2smaller.jpg")));
		getContentPane().add(lblBackround);

// Listeners para poder mover la vista
		getContentPane().addMouseListener(new MouseAdapter() {

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
				Home.this.setLocation(x - xx, y - xy);
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

// Transparency class
	public class TransparentListCellRenderer extends DefaultListCellRenderer {

		public Component getListCellRendererComponent(JList<?> listConcert, Object value, int index, boolean isSelected,
				boolean cellHasFocus) {
			super.getListCellRendererComponent(listConcert, value, index, isSelected, cellHasFocus);
			setOpaque(isSelected);
			return this;
		}
	}
}
