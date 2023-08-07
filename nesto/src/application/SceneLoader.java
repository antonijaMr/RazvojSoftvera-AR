package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneLoader {
	private Stage stage;
	private Scene scene;
	private Parent root;

	public void loadPredmeti(ActionEvent e) {
		try {
			root = FXMLLoader.load(getClass().getResource("nastavnik.fxml"));
			stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	public void loadZahtjevi(ActionEvent e) {
		try {
			root = FXMLLoader.load(getClass().getResource("nastavnik_zahtjevi.fxml"));
			stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	public void loadPredZahtjevi(ActionEvent e) {
		try {
			root = FXMLLoader.load(getClass().getResource("nastavnik_zahtjeviZaPredmet.fxml"));
			stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	public void logout(ActionEvent e) {
		// myb are u sure u want to log out
		try {
			root = FXMLLoader.load(getClass().getResource("Arnela.fxml"));
			stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}
}
