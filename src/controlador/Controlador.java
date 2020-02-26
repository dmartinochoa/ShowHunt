package controlador;

import modelo.Modelo;

import windows.*;

public class Controlador {
	private Modelo model;
	private Login login;
	private CreateAccount createAcc;
	private Home home;
	private ManageAccount manageAcc;
	private LoginFailed loginFailed;
	private DbTable dbTable;

	private int loginAttempts = 0;

// VISTA LOGIN

	// Boton de iniciar session
	public void loginPress(String userName, String userPass) {
		// comprobacion de usuario/contraseña para acceder
		if (model.loginUser(userName, userPass)) {
			this.login.dispose();
			if (userName.equals("Admin")) {
				this.dbTable = new DbTable();
				this.dbTable.setControl(this);
				this.dbTable.setModelo(model);
				this.dbTable.setVisible(true);
			} else {
				if (home != null) {
					this.home.setVisible(true);
					this.home.clearTable();
					this.home.clearFields();
				} else {
					this.home = new Home();
					this.home.setControl(this);
					this.home.setModelo(model);
					this.home.setVisible(true);
					this.home.clearTable();
				}
			}
		} else {
			login.loginMessage();
			loginAttempts++;
		}
		if (loginAttempts >= 3) {
			login.dispose();
			this.loginFailed = new LoginFailed();
			this.loginFailed.setVisible(true);
			new java.util.Timer().schedule(new java.util.TimerTask() {

				public void run() {
					loginFailed.dispose();
				}
			}, 1500);
		}

	}

	// Boton para cambiar a la vista de crear usuario
	public void goToCreateAcc() {
		this.login.setVisible(false);
		if (createAcc != null) {
			this.createAcc.setVisible(true);
			this.createAcc.cleanFields();
		} else {
			this.createAcc = new CreateAccount();
			this.createAcc.setControl(this);
			this.createAcc.setModelo(this.model);
			this.createAcc.setVisible(true);
		}
	}

// VISTA CREATE ACCOUNT
	// Boton para crear usuario y volver a la vista de login
	public void createAccountPress(String userName, String userPass, String userMail, String userCity) {
		if (model.registerUser(userName, userPass, userMail, userCity)) {
			this.createAcc.dispose();
			this.login.setVisible(true);
		} else {
			createAcc.userExistMessage();
		}
	}

// VISTA PRINCIPAL
	public void goToManageAcc() {
		this.home.setVisible(false);
		if (manageAcc != null) {
			this.manageAcc.setVisible(true);
			this.manageAcc.tumadre();
			this.manageAcc.cityChangeBtnChecker();
			this.manageAcc.clearFields();
		} else {
			this.manageAcc = new ManageAccount();
			this.manageAcc.setControl(this);
			this.manageAcc.setModelo(model);
			this.manageAcc.setVisible(true);
			this.manageAcc.tumadre();
			this.manageAcc.cityChangeBtnChecker();
			this.manageAcc.clearFields();
		}
	}

// MANAGE ACC VIEW
	public void goToHomeFromManageAcc() {
		this.manageAcc.dispose();
		this.home.setVisible(true);
		this.home.clearFields();
		this.home.clearTable();
	}

// temporal para volver a la vista de login 
	public void goToLogin() {
		model.closeSession();
		if (createAcc != null) {
			this.createAcc.dispose();
		}
		if (home != null) {
			this.home.dispose();
		}
		if (dbTable != null) {
			this.dbTable.dispose();
		}
		if (login != null) {
			this.login.setVisible(true);
		} else {
			this.login = new Login();
			this.login.setControl(this);
			this.login.setModelo(model);
			this.login.setVisible(true);
		}
	}

// Setters
	public void setModelo(Modelo model) {
		this.model = model;
	}

	public void setLogin(Login login) {
		this.login = login;
	}

}
