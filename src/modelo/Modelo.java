package modelo;

import windows.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Modelo {
	private Login login;
	private CreateAccount createAcc;
	private Home home;
	private ManageAccount manageAcc;

	private String USUARIO = "usuario";
	private static String PASS = "1234";
	private static final String BBDD = "showhuntdb";
	private static final String URL = "jdbc:mysql://localhost:3306/" + BBDD;
	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	private static String user;
	private Connection miConexion;

	public Modelo() {
		try {
			Class.forName(DRIVER);
		} catch (Exception e) {
			System.out.println("Error al cargar el driver");
			e.printStackTrace();
		}
	}

	/**
	 * metodo que establece la conexion con la base de datos se pasa como parametro
	 * el usuario de la base de datos, en el caso de que el usuario sea
	 * administrador se conecta como root
	 *
	 * @return
	 */
	public void conectar(String conexionUser) {

		if (this.USUARIO.equals("root")) {
			this.PASS = "";
		}
		try {
			miConexion = DriverManager.getConnection(URL, conexionUser, PASS);
			System.out.println("Conexión OK");

		} catch (SQLException e) {
			System.out.println("Error en la conexión");
			e.printStackTrace();
		}

	}

	/**
	 * metodo que utiliza el valor del atributo user de conexion para sacar el ID de
	 * ese usuario
	 */
	public int getUserID() {

		ResultSet rs = null;
		int userID = 0;
		try {
			String selectQuery = "select id_usuario from usuarios where nombreUsuario = '" + this.user + "';";
			PreparedStatement pstms = miConexion.prepareStatement(selectQuery);
			rs = pstms.executeQuery();

			if (rs.next()) {
				userID = rs.getInt("id_usuario");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}

			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return userID;
	}

	public int getBandID(String bandName) {
		ResultSet rs = null;
		int bandID = 0;
		try {
			String selectQuery = "select id_grupo from grupos where nombreGrupo = ?;";
			PreparedStatement pstms = miConexion.prepareStatement(selectQuery);
			pstms.setString(1, bandName);
			rs = pstms.executeQuery();

			while(rs.next()) {
				bandID = rs.getInt("id_grupo");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}

			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return bandID;
	}

	public boolean loginUser(String userName, String userPass) {
		ResultSet rs = null;// las querys

		try {
			this.conectar(this.USUARIO);// llama al metodo conectar de la clase conexion
			String query = "select nombreUsuario, passwordUsuario, administrador from usuarios where nombreUsuario = ? and passwordUsuario =?;";
			PreparedStatement pstms = miConexion.prepareStatement(query);
			pstms.setString(1, userName);
			pstms.setString(2, userPass);

			rs = pstms.executeQuery();

			if (rs.next()) {
				System.out.println("Login correcto");
				int administrador = rs.getInt("administrador");
				miConexion.close();
				if (administrador == 1) {
					this.USUARIO = "root";
					this.conectar(this.USUARIO);
					System.out.println("conectado como root");
				} else {
					this.conectar(this.USUARIO);
					System.out.println("conectado como usuario");
				}
				this.user = userName;
				return true;
			} else {
				System.out.println("Login incorrecto");
				miConexion.close();
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}

			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	/**
	 * Metodo para registrar usuarios, el Id de usuario se autoincrementa, por
	 * defecto el nuevo usuario tendra el rol de user
	 *
	 * @param userName
	 * @param userPass
	 * @param userMail
	 * @param userCity
	 */
	public boolean registerUser(String userName, String userPass, String userMail, String userCity) {
		
		ResultSet rs = null;
		try {
			this.conectar(this.USUARIO);
			
			String selectQuery = "select nombreUsuario , correoUsuario from usuarios where nombreUsuario = ? or correoUsuario = ?;";
			PreparedStatement selectPstms = miConexion.prepareStatement(selectQuery);
			selectPstms.setString(1, userName);
			selectPstms.setString(2, userMail);
			System.out.println(selectQuery);
			rs = selectPstms.executeQuery();

			if (!rs.next()) {
				String insertQuery = "insert into usuarios(nombreUsuario, passwordUsuario, correoUsuario, ciudadUsuario) values(?,?,?,?);";
				PreparedStatement insertPstms = miConexion.prepareStatement(insertQuery);
				insertPstms.setString(1, userName);
				insertPstms.setString(2, userPass);
				insertPstms.setString(3, userMail);
				insertPstms.setString(4, userCity);
				insertPstms.executeUpdate();
				closeSession();
				return true;
			} else {
				closeSession();
				return false;
			}
			
		} catch (SQLException e) {
			closeSession();
			e.printStackTrace();
			return false;
		}
	}

	public void closeSession() {
		if (miConexion != null) {
			try {
				miConexion.close();
				System.out.println("session cerrada");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

//selects
	/**
	 * Muestra todas las columnas de la tabla usuarios, solo los administradores
	 * pueden usarlo
	 * 
	 * @return
	 * @return
	 * @throws SQLException
	 */
	public ResultSet getUsers() {
		ResultSet rs = null;// las querys
		try {
			if (this.USUARIO.equals("root")) {
				String query = "select * from usuarios";
				PreparedStatement pst = miConexion.prepareStatement(query);
				rs = pst.executeQuery();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	/**
	 * Muestra todos los conciertos de la base de datos solo los administradores
	 * pueden usarlo
	 * 
	 * @return
	 * @throws SQLException
	 */
	public ResultSet getShows() {
		ResultSet rs = null;// las querys
		try {
			if (this.USUARIO.equals("root")) {
				String query = "select * from conciertos";
				PreparedStatement pst = miConexion.prepareStatement(query);
				rs = pst.executeQuery();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	/**
	 * Muestra todos los grupos de la base de datos, solo los administradores pueden
	 * usarlo
	 * 
	 * @return
	 * @throws SQLException
	 */
	public ResultSet getBands() {
		ResultSet rs = null;// las querys
		try {
			if (this.USUARIO.equals("root")) {
				String query = "select * from grupos";
				PreparedStatement pst = miConexion.prepareStatement(query);
				rs = pst.executeQuery();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

//Search methods

	public ResultSet searchByBand(String searchedBandName) {
		ResultSet rs = null;// las querys
		try {
			String selectQuery = "select g.id_grupo, nombreGrupo, ciudad, lugar,fecha,linkEntradas from conciertos\r\n"
					+ "    inner join grupos g on conciertos.id_grupo = g.id_grupo\r\n" + "    where nombreGrupo = ?;";
			PreparedStatement selectPstms = miConexion.prepareStatement(selectQuery);
			selectPstms.setString(1, searchedBandName);
			rs = selectPstms.executeQuery();

			if (rs.next()) {
				int userID = this.getUserID();
				int bandID = this.getBandID(searchedBandName);
				String insertQuery = "insert into historial(id_usuario, id_grupo) values(?,?);";
				System.out.println(insertQuery);
				System.out.println(userID);
				System.out.println(bandID);
				PreparedStatement insertPstms = miConexion.prepareStatement(insertQuery);
				insertPstms.setInt(1,userID);
				insertPstms.setInt(2, bandID);
				insertPstms.executeUpdate();
			} else {
				System.out.println("No hay registros relacionados con el criterio de busqueda");
			}
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
			return rs;
		} finally {
			try {
				if (rs != null) {
					//rs.close();
					//el rs.close daba un error
				}

			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	public ResultSet searchByCity(String city) {

		ResultSet rs = null;// las querys
		try {
			String query = "select nombreGrupo, ciudad, lugar,fecha,linkEntradas from conciertos\r\n"
					+ "    inner join grupos g on conciertos.id_grupo = g.id_grupo\r\n" + "    where ciudad  = ? ;";
			PreparedStatement pstms = miConexion.prepareStatement(query);
			pstms.setString(1, city);
			rs = pstms.executeQuery();

			if (!rs.next()) {
				System.out.println("No hay registros relacionados con el criterio de busqueda");
			}
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
			return rs;
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}

			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	public ResultSet searchByDate(int time) {
		Statement stm = null;// cosa que hace query
		ResultSet rs = null;// las querys

		try {
			stm = miConexion.createStatement();

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar date = Calendar.getInstance();
			String stringDate = sdf.format(date.getTime());

			date.add(Calendar.DAY_OF_MONTH, time);
			String newStringDate = sdf.format(date.getTime());

			String query = "select nombreGrupo,ciudad,lugar,fecha,linkEntradas from conciertos\r\n"
					+ "inner join grupos g on conciertos.id_grupo = g.id_grupo\r\n" + "where fecha > '" + stringDate
					+ "' and fecha < '" + newStringDate + "';";
			System.out.println(query);
			rs = stm.executeQuery(query);

			if (!rs.next()) {
				System.out.println("No hay registros relacionados con el criterio de busqueda");
			}
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
			return rs;
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	public ResultSet searchByGenre(String genre) {
		ResultSet rs = null;

		try {
			String query = "select nombreGrupo, ciudad, lugar,fecha,linkEntradas from conciertos\r\n"
					+ "    inner join grupos g on conciertos.id_grupo = g.id_grupo\r\n" + "    where genero  = ? ;";
			PreparedStatement pstms = miConexion.prepareStatement(query);
			pstms.setString(1, genre);
			rs = pstms.executeQuery();

			if (!rs.next()) {
				System.out.println("No hay registros relacionados con el criterio de busqueda");
			}
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
			return rs;
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}

			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

//Setters
	public void setLogin(Login login) {
		this.login = login;
	}

}
