package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;

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

	public void loadAdminStudent(ActionEvent e) {
		try {
			root = FXMLLoader.load(getClass().getResource("administrator.fxml"));
			stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	public void loadAdminStudenti(ActionEvent e) {
		try {
			root = FXMLLoader.load(getClass().getResource("administrator_pogledajStudente.fxml"));
			stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	public void loadAdminPredmet(ActionEvent e) {
		try {
			root = FXMLLoader.load(getClass().getResource("administrator_dodajPredmet.fxml"));
			stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	public void loadAdminPredmeti(ActionEvent e) {
		try {
			root = FXMLLoader.load(getClass().getResource("administrator_pogledajPredmete.fxml"));
			stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	public void loadAdminNastavnik(ActionEvent e) {
		try {
			root = FXMLLoader.load(getClass().getResource("administrator_dodajNastavnika.fxml"));
			stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	public void loadAdminNastavnici(ActionEvent e) {
		try {
			root = FXMLLoader.load(getClass().getResource("administrator_pogledajNastavnike.fxml"));
			stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	public void loadAdminProdekan(ActionEvent e) {
		try {
			root = FXMLLoader.load(getClass().getResource("administrator_prodekan.fxml"));
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
	
	public void loadNastavnikDetaljiIducaGodina(ActionEvent e) {
		try {
			root = FXMLLoader.load(getClass().getResource("nastavnik_detalji_iducaGodina.fxml"));
			stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}	
	
	public void loadNastavnikIducaGodina(ActionEvent e) {
		try {
			root = FXMLLoader.load(getClass().getResource("nastavnik_iducaGodina.fxml"));
			stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}	
	
		

	public void alert(String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Obavijest");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
		// mozda centrirati
	}
	
	public void alertEror(String message) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Obavijest");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
		// mozda centrirati
	}
}
