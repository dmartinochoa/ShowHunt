package modelo;

import windows.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import controlador.*;

public class Modelo {
    private Login login;
    private CreateAccount createAcc;
    private Home home;
    private ManageAccount manageAcc;
    
    private String USUARIO = "usuario";
    private static String PASS = "1234";
    private static final String BBDD = "showHuntDB";
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
     * el usuario de la base de datos, en el caso de que el usuario sea root se
     * quita la password
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
				if (administrador == 1) {
					this.USUARIO = "root";
					this.conectar(this.USUARIO);
					System.out.println("conectado como root");
				} else {
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
    
    
//Setters
    public void setLogin(Login login) {
        this.login = login;
    }

    public void setCreateAccount(CreateAccount createAccount) {
        this.createAcc = createAccount;
    }

	public void setHome(Home home) {
		this.home = home;
	}

	public void setManageAcc(ManageAccount manageAcc) {
		this.manageAcc = manageAcc;
	}

}
