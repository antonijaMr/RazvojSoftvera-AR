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
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;

public class SceneLoader {
	private Stage stage;
	private Scene scene;
	private Parent root;

	private void loadScene(String fxmlPath, String cssPath, MouseEvent eventSource) {
		try {
			root = FXMLLoader.load(getClass().getResource(fxmlPath));
			stage = (Stage) ((Node) eventSource.getSource()).getScene().getWindow();
			scene = new Scene(root);

			String css = this.getClass().getResource(cssPath).toExternalForm();
			scene.getStylesheets().add(css);

			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void loadScene(String fxmlPath, String cssPath, ActionEvent eventSource) {
		try {
			root = FXMLLoader.load(getClass().getResource(fxmlPath));
			stage = (Stage) ((Node) eventSource.getSource()).getScene().getWindow();
			scene = new Scene(root);

			String css = this.getClass().getResource(cssPath).toExternalForm();
			scene.getStylesheets().add(css);

			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadPredmeti(MouseEvent e) {
		loadScene("nastavnik.fxml", "applicationNastavnik.css", e);
	}

	public void loadPredmeti(ActionEvent e) {
		loadScene("nastavnik.fxml", "applicationNastavnik.css", e);
	}

	public void loadZahtjevi(MouseEvent e) {
		loadScene("nastavnik_zahtjevi.fxml", "applicationNastavnik.css", e);
	}

	public void loadPredZahtjevi(MouseEvent e) {
		loadScene("nastavnik_zahtjeviZaPredmet.fxml", "applicationNastavnik.css", e);
	}

	public void loadNastDetalji(ActionEvent e) {
		loadScene("nastavnik_detalji.fxml", "applicationNastavnik.css", e);
	}

	public void loadAdminStudent(MouseEvent e) {
		System.out.println("mouse");
		loadScene("administrator.fxml", "applicationAdmin.css", e);
	}

	public void loadAdminStudent(ActionEvent e) {
		System.out.println("action");
		loadScene("administrator.fxml", "applicationAdmin.css", e);
	}

	public void loadAdminStudenti(ActionEvent e) {
		loadScene("administrator_pogledajStudente.fxml", "applicationAdmin.css", e);
	}

	public void loadAdminPredmet(MouseEvent e) {
		loadScene("administrator_dodajPredmet.fxml", "applicationAdmin.css", e);
	}

	public void loadAdminPredmet(ActionEvent e) {
		loadScene("administrator_dodajPredmet.fxml", "applicationAdmin.css", e);
	}

	public void loadAdminPredmeti(ActionEvent e) {
		loadScene("administrator_pogledajPredmete.fxml", "applicationAdmin.css", e);
	}

	public void loadAdminNastavnik(MouseEvent e) {
		loadScene("administrator_dodajNastavnika.fxml", "applicationAdmin.css", e);
	}

	public void loadAdminNastavnik(ActionEvent e) {
		loadScene("administrator_dodajNastavnika.fxml", "applicationAdmin.css", e);
	}

	public void loadAdminNastavnici(ActionEvent e) {
		loadScene("administrator_pogledajNastavnike.fxml", "applicationAdmin.css", e);
	}

	public void loadAdminProdekan(MouseEvent e) {
		loadScene("administrator_prodekan.fxml", "applicationAdmin.css", e);
	}

	public void logout(MouseEvent e) {
		loadScene("Arnela.fxml", "application.css", e);
	}
	
	public void logout(ActionEvent e) {
		loadScene("Arnela.fxml", "application.css", e);
	}

	public void loadNastavnikDetaljiIducaGodina(ActionEvent e) {
		loadScene("nastavnik_detalji_iducaGodina.fxml", "applicationNastavnik.css", e);
	}

	public void loadNastavnikIducaGodina(ActionEvent e) {
		loadScene("nastavnik_iducaGodina.fxml", "applicationNastavnik.css", e);
	}
	
	public void loadNastavnikStudentUpdate(ActionEvent e) {
		loadScene("nastavnik_studentUpdate.fxml", "applicationNastavnik.css", e);
	}
	
	public void loadProdekan_to_bitni_datumi(ActionEvent e) {
		loadScene("ProdekanBitniDatumi.fxml","applicationP.css",e);
	}
	public void loadProdekan_to_prijavljeni_predmeti(ActionEvent e) {
		loadScene("ProdekanPrijavljeniPredmeti.fxml","applicationP.css",e);
	}
	
	public void loadProdekan_to_plan_realizacije(ActionEvent e) {
		loadScene("prodekan_DodajUnos.fxml","applicationP.css",e);
	}
	public void loadProdekan_to_zahtjevi(ActionEvent e) {
		loadScene("prodekan_zahtjevi.fxml","applicationP.css",e);
	}
	public void loadProdekan_to_predmeti(ActionEvent e) {
		loadScene("prodekan.fxml","applicationP.css",e);
	}
	public void loadProdekan_nastavnici(ActionEvent e) {
		loadScene("prodekan_nastavnici.fxml","applicationP.css",e);
	}
				
	public void loadProdekan_to_pregled_plana(ActionEvent e) {
		loadScene("prodekan_PregledPlana.fxml","applicationP.css",e);
	}	
	
	public void loadProdekan_to_bitni_datumi(MouseEvent e) {
		loadScene("ProdekanBitniDatumi.fxml","applicationP.css",e);
	}
	public void loadProdekan_to_prijavljeni_predmeti(MouseEvent e) {
		loadScene("ProdekanPrijavljeniPredmeti.fxml","applicationP.css",e);
	}
	
	public void loadProdekan_to_plan_realizacije(MouseEvent e) {
		loadScene("prodekan_DodajUnos.fxml","applicationP.css",e);
	}
	public void loadProdekan_to_zahtjevi(MouseEvent e) {
		loadScene("prodekan_zahtjevi.fxml","applicationP.css",e);
	}
	public void loadProdekan_to_predmeti(MouseEvent e) {
		loadScene("prodekan.fxml","applicationP.css",e);
	}
	public void loadProdekan_nastavnici(MouseEvent e) {
		loadScene("prodekan_nastavnici.fxml","applicationP.css",e);
	}
				
	public void loadProdekan_to_pregled_plana(MouseEvent e) {
		loadScene("prodekan_PregledPlana.fxml","applicationP.css",e);
	}	
	
	public void loadNastavnkUpdate(ActionEvent e) {
		loadScene("administrator_updateNastavnik.fxml", "applicationAdmin.css", e);
	}

	public void loadStudentUpdate(ActionEvent e) {
		loadScene("administrator_updateStudent.fxml", "applicationAdmin.css", e);
	}	

	public void loadPredmetUpdate(ActionEvent e) {
		loadScene("administrator_updatePredmet.fxml", "applicationAdmin.css", e);
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
