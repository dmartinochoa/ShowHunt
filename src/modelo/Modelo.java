package modelo;

import windows.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

public class Modelo {
	private Login login;
	private CreateAccount createAcc;
	private Home home;
	private ManageAccount manageAcc;
	private String USUARIO;
	private String PASS;
	private String URL;
	private String DRIVER;
	private static String user;
	private Connection miConexion;
	private FileOutputStream fos;

	/**
	 * Constructor, los atributos del modelo se cogen de un fichero de configuracion
	 */
	public Modelo() {
		Properties propiedades = new Properties();
		InputStream entrada = null;
		try {
			File miFichero = new File("config/Prop.ini");
			if (miFichero.exists()) {
				entrada = new FileInputStream(miFichero);
				propiedades.load(entrada);

				this.USUARIO = propiedades.getProperty("USUARIO");
				this.PASS = propiedades.getProperty("PASS");
				this.URL = propiedades.getProperty("URL");
				this.DRIVER = propiedades.getProperty("DRIVER");

			} else
				System.err.println("Fichero no encontrado");
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (entrada != null) {
				try {
					entrada.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
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
			Class.forName(DRIVER);
			miConexion = DriverManager.getConnection(URL, conexionUser, PASS);
			System.out.println("Conexión OK");
		} catch (Exception e) {
			System.err.println("Error en la conexión");
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

	/**
	 * Utiliza el nombre del grupo para devolver el id de ese grupo
	 * 
	 * @param bandName
	 * @return
	 */
	public int getBandID(String bandName) {
		ResultSet rs = null;
		int bandID = 0;
		try {
			String selectQuery = "select id_grupo from grupos where nombreGrupo = ?;";
			PreparedStatement pstms = miConexion.prepareStatement(selectQuery);
			pstms.setString(1, bandName);
			rs = pstms.executeQuery();

			while (rs.next()) {
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

//account management

	public boolean loginUser(String userName, String userPass) {
		ResultSet rs = null;// las querys

		try {
			this.conectar(this.USUARIO);// llama al metodo conectar de la clase conexion
			String query = "select nombreUsuario, passwordUsuario, administrador from usuarios where nombreUsuario = ? and passwordUsuario = md5(?);";
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
				System.err.println("Login incorrecto");
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
			rs = selectPstms.executeQuery();

			if (!rs.next()) {
				String insertQuery = "insert into usuarios(nombreUsuario, passwordUsuario, correoUsuario, ciudadUsuario) values(?,md5(?),?,?);";
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

	/**
	 * Cierra la sesion
	 */
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

	/**
	 * Borra de la base de datos la cuenta asociada al id del usuario que esta
	 * conectado
	 */
	public void removeUser() {

		try {
			String query = "delete from usuarios where id_usuario = ?;";
			PreparedStatement pstms = miConexion.prepareStatement(query);
			pstms.setInt(1, this.getUserID());
			pstms.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Cambia la ciudad del usuario
	 * 
	 * @param newCity
	 */
	public void changeCity(String newCity) {
		try {
			String updateQuery = "update usuarios set ciudadUsuario = ? where id_usuario = ?;";
			PreparedStatement pstms = miConexion.prepareStatement(updateQuery);
			pstms.setString(1, newCity);
			pstms.setInt(2, this.getUserID());
			pstms.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Devuelve un String con la ciudad del usuario
	 * 
	 * @return currentCity
	 */
	public String getCurrentCity() {
		ResultSet rs = null;
		String currentCity = "";
		try {
			String query = "select ciudadUsuario from usuarios where id_usuario = ?";
			PreparedStatement pstms = miConexion.prepareStatement(query);
			pstms.setInt(1, this.getUserID());
			rs = pstms.executeQuery();
			while (rs.next()) {
				currentCity = rs.getString("ciudadUsuario");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return currentCity;
	}

	/**
	 * primero pide al usuario la antigua y la nueva password, si la antigua
	 * coincide con la que hay en la base de datos la sustituye por la nueva
	 * 
	 * @param oldPassword
	 * @param newPassword
	 */
	public boolean updatePassword(String oldPassword, String newPassword) {

		ResultSet rs = null;// las querys
		try {
			String selectQuery = "select passwordUsuario from usuarios where id_usuario = ? and passwordUsuario = md5(?);";
			PreparedStatement stms = miConexion.prepareStatement(selectQuery);

			stms.setInt(1, this.getUserID());
			stms.setString(2, oldPassword);
			rs = stms.executeQuery();

			if (rs.next()) {
				String updateQuery = "update usuarios set passwordUsuario = md5(?) where id_usuario = ?;";
				PreparedStatement updateStatement = miConexion.prepareStatement(updateQuery);

				updateStatement.setString(1, newPassword);
				updateStatement.setInt(2, this.getUserID());
				updateStatement.executeUpdate();
				return true;
			} else {
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void getUserData(int user_id) {
		ResultSet rs = null;
		
		try {
			String query = "select count(nombreGrupo), nombreGrupo ,genero  from showhuntdb.historial\r\n" + 
					"    inner join grupos g on historial.id_grupo = g.id_grupo\r\n" + 
					"    where id_usuario = ? group by historial.id_grupo;";
			PreparedStatement pstms = miConexion.prepareStatement(query);
			pstms.setInt(1, user_id);
			rs = pstms.executeQuery();
			
			String fileName = "userDataID:"+user_id;
			File userDataFile =  new File ("UserData/"+fileName+".txt");
			if(userDataFile.exists()) {
				try {
					OutputStream dataFile = new FileOutputStream(userDataFile);
					while(rs.next()) {
						String timesSearched = rs.getInt("count(nombreGrupo)")+"";
						String bandName = rs.getString("nombregrupo");
						String genre = rs.getString("genero");
						
						byte timesSearchedToArray [] = timesSearched.getBytes();
						byte bandNameToArray [] = bandName.getBytes();
						byte genreToArray [] = genre.getBytes();
						
						dataFile.write(timesSearchedToArray);
						dataFile.write(bandNameToArray);
						dataFile.write(genreToArray);
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

//selects
	/**
	 * Muestra todas las columnas de la tabla usuarios, solo los administradores
	 * pueden usarlo
	 * 
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

	/**
	 * Muestra los conciertos cuyo id de grupo aparezca en el historial de busqueda
	 * del usuario o cuya ciudad coincida con la ciudad del usuario.
	 * 
	 * @return
	 */
	public ResultSet getRecomended() {
		ResultSet rs = null;
		try {
			String query = "select distinct nombreGrupo, ciudad, lugar, fecha, linkEntradas\r\n" + "from conciertos\r\n"
					+ "inner join grupos g on conciertos.id_grupo = g.id_grupo\r\n"
					+ "where conciertos.id_grupo in (select id_grupo from showhuntdb.historial where id_usuario = ?)\r\n"
					+ "or conciertos.ciudad in (select ciudadUsuario from showhuntdb.usuarios where id_usuario = ?);";
			PreparedStatement pstms = miConexion.prepareStatement(query);
			pstms.setInt(1, this.getUserID());
			pstms.setInt(2, this.getUserID());
			rs = pstms.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	/**
	 * Utiliza el metodo getBandId para sacar el ID asociado al nombre del grupo y
	 * muestra los conciertos en los que aparezca ese grupo, ademas inserta en la
	 * tabla historial el id del usuario que ha realizado la busqueda y el id del
	 * grupo que ha buscado
	 * 
	 * @param searchedBandName
	 * @return
	 */
	public ResultSet searchByBand(String bandName) {
		ResultSet rs = null;// las querys
		try {
			String selectQuery = "select nombreGrupo, ciudad, lugar,fecha,linkEntradas from conciertos\r\n"
					+ "    inner join grupos g on conciertos.id_grupo = g.id_grupo where nombreGrupo = ?;";
			PreparedStatement selectPstms = miConexion.prepareStatement(selectQuery);

			selectPstms.setString(1, bandName);
			rs = selectPstms.executeQuery();
			if (rs.next()) {
				int userID = this.getUserID();
				int bandID = this.getBandID(bandName);
				String insertQuery = "insert into historial(id_usuario, id_grupo) values(?,?);";
				PreparedStatement insertPstms = miConexion.prepareStatement(insertQuery);
				insertPstms.setInt(1, userID);
				insertPstms.setInt(2, bandID);
				insertPstms.executeUpdate();
			} else {
				System.out.println("No hay registros relacionados con el criterio de busqueda");
			}
			rs.beforeFirst();
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
			return rs;
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
			rs.beforeFirst();
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
			return rs;
		}
	}

	public ResultSet cityAndBandSearch(String city, String bandName) {
		ResultSet rs = null;// las querys
		try {
			String query = "select nombreGrupo, ciudad, lugar,fecha,linkEntradas from conciertos\r\n"
					+ " inner join grupos g on conciertos.id_grupo = g.id_grupo\r\n where ciudad  = ? and nombreGrupo = ? ;";
			PreparedStatement pstms = miConexion.prepareStatement(query);
			pstms.setString(1, city);
			pstms.setString(2, bandName);
			rs = pstms.executeQuery();

			if (rs.next()) {
				int userID = this.getUserID();
				int bandID = this.getBandID(bandName);
				String insertQuery = "insert into historial(id_usuario, id_grupo) values(?,?);";
				PreparedStatement insertPstms = miConexion.prepareStatement(insertQuery);
				insertPstms.setInt(1, userID);
				insertPstms.setInt(2, bandID);
				insertPstms.executeUpdate();
			} else {
				System.out.println("No hay registros relacionados con el criterio de busqueda");
			}
			rs.beforeFirst();
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
			return rs;
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
			rs.beforeFirst();
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
			return rs;
		}
	}

//Setters
	public void setLogin(Login login) {
		this.login = login;
	}

}
