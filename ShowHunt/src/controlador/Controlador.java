package controlador;

import modelo.Modelo;
import windows.*;

public class Controlador {
	private Modelo model;
	private Login login;
	private CreateAccount createAcc;
	private Home home;
	private ManageAccount manageAcc;

// VISTA LOGIN
	// Boton de iniciar session
	public void loginPress() {
		// comprobacion de usuario/contraseña para acceder
		this.login.dispose();
		if (home != null) {
			this.home.setVisible(true);
		} else {
			this.home = new Home();
			this.home.setControl(this);
			this.home.setModelo(model);
			this.home.setVisible(true);
		}
	}

	// Boton para cambiar a la vista de crear usuario
	public void goToCreateAcc() {
		this.login.setVisible(false);
		if (createAcc != null) {
			this.createAcc.setVisible(true);
		} else {
			this.createAcc = new CreateAccount();
			this.createAcc.setControl(this);
			this.createAcc.setModelo(model);
			this.createAcc.setVisible(true);
		}
	}

// VISTA CREATE ACCOUNT
	// Boton para crear usuario y volver a la vista de login
	public void createAccountPress() {
		this.createAcc.dispose();
		this.login.setVisible(true);
	}

// VISTA PRINCIPAL
	public void goToManageAcc() {
		this.home.setVisible(false);
		if (manageAcc != null) {
			this.manageAcc.setVisible(true);
		} else {
			this.manageAcc = new ManageAccount();
			this.manageAcc.setControl(this);
			this.manageAcc.setModelo(model);
			this.manageAcc.setVisible(true);
		}
	}

// MANAGE ACC VIEW
	public void goToHomeFromManageAcc() {
		this.manageAcc.dispose();
		this.home.setVisible(true);

	}

// temporal para volver a la vista de login 
	public void goToLogin() {
		if (createAcc != null) {
			this.createAcc.dispose();
		}
		if (home != null) {
			this.home.dispose();
		}
		this.login.setVisible(true);

	}

// Setters
	public void setModelo(Modelo model) {
		this.model = model;
	}

	public void setLogin(Login login) {
		this.login = login;
	}

}
