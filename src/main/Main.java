package main;
import windows.Login;
import modelo.Modelo;
import controlador.Controlador;

public class Main {
	private void run() {
		Login login = new Login();
		Modelo model = new Modelo();
		Controlador control = new Controlador();
		
		control.setModelo(model);
		control.setLogin(login);

		login.setControl(control);
		login.setModelo(model);

		model.setLogin(login);

		login.setVisible(true);
		model.conectar("usuario");
		
	}

	public static void main(String[] args) {
		new Main().run();
	}
}
