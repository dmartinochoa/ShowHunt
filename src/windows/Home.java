package windows;

import javax.swing.ImageIcon;

import javax.swing.JFrame;

import controlador.Controlador;
import modelo.Modelo;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.RoundRectangle2D;
import java.awt.Cursor;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.UIManager;
import javax.swing.JTextField;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JScrollBar;
import java.awt.ScrollPane;
import javax.swing.ListSelectionModel;

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

// BOTONES 
		btnSearch = new JButton("Search");
		btnSearch.setFont(new Font("SansSerif", Font.BOLD, 12));
		btnSearch.setBounds(415, 85, 75, 23);
		getContentPane().add(btnSearch);
		btnSearch.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnSearch.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
		});

// CONCERT LIST
		JList listConcert = new JList();
		listConcert.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listConcert.setFont(new Font("SansSerif", Font.BOLD, 15));
		listConcert.setForeground(Color.WHITE);
		listConcert.setModel(new AbstractListModel() {
			String[] values = new String[] {"tes", "", "s", "sd", "fds", "dsf", "ds", "", "d", "df", "ds", "fds", "sd", "d", "ds", "ffd", "s", "df", "dfs", "dfs", "dfs", "dsf", "d", "fs", "dfs", "ds", "dsf", "dsf", "dfs", "ds", "f", "dfs", "dsf", "dfs", "dfs", "dfs", "sdf", "d", "sdf"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		listConcert.setOpaque(false);
		listConcert.setCellRenderer(new TransparentListCellRenderer());
		listConcert.setBounds(25, 158, 751, 304);
		//getContentPane().add(listConcert);

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
			@Override
			public void mouseEntered(MouseEvent e) {
				lblUser.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			@Override
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
			@Override
			public void mouseEntered(MouseEvent e) {
				lblRecIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
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
			@Override
			public void mouseEntered(MouseEvent e) {
				listUser.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			@Override
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

			@Override
			public void mouseExited(MouseEvent e) {
				listUser.setVisible(false);
			}
		});

// EXIT
		lblExit = new JLabel("x");
		lblExit.setBounds(752, 11, 24, 33);
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
		lblBackround.setForeground(Color.WHITE);
		lblBackround.setFont(new Font("SansSerif", Font.BOLD, 12));
		lblBackround.setBounds(0, -124, 1100, 750);
		lblBackround.setIcon(new ImageIcon(Login.class.getResource("/img/2smaller.jpg")));
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
		@Override
		public Component getListCellRendererComponent(JList<?> listConcert, Object value, int index, boolean isSelected,
				boolean cellHasFocus) {
			super.getListCellRendererComponent(listConcert, value, index, isSelected, cellHasFocus);
			setOpaque(isSelected);
			return this;
		}
	}
}