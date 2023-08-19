package application;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import models.Nastavnik;
import models.Predmet;

public class ProdekanBitniDatumiController implements Initializable {
	private MySQLConnection mysql = new MySQLConnection();
	private SceneLoader s = new SceneLoader();

	@FXML
	private Button logout;
	@FXML
	private Button btn_nastavnici;
	@FXML
	private Button btn_predmeti;
	@FXML
	private Button btn_zahtjevi;
	@FXML
	private Button btn_noviplan;
	@FXML
	private Button btn_bitniDatumi;
	@FXML
	private Button btn_prijavljeniPredmeti;

	@FXML
	private DatePicker PocetakRegistracije;
	@FXML
	private DatePicker KrajRegistracije;
	@FXML
	private Button btn_potvrdi;

	String query = null;
	ResultSet res = null;
	Predmet predmet = null;

	ObservableList<Nastavnik> List = FXCollections.observableArrayList();
	private ObservableList<Nastavnik> filteredList = FXCollections.observableArrayList();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		mysql.Connect();

	}

	@FXML
	private void potvrdi(ActionEvent e) {
		LocalDate pocetak = PocetakRegistracije.getValue();
		LocalDate kraj = KrajRegistracije.getValue();
		String query = "INSERT INTO periodregistracije (akademskaGodina,datumPocetka,datumZavrsetka) VALUES (YEAR(NOW()),?,?)";
		try (PreparedStatement preparedStatement = mysql.con.prepareStatement(query)) {

			preparedStatement.setDate(1, java.sql.Date.valueOf(pocetak));
			preparedStatement.setDate(2, java.sql.Date.valueOf(kraj));
			int rowsAffected = preparedStatement.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("Data inserted successfully.");
			} else {
				System.out.println("Insertion failed.");
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	@FXML
	private void logout(ActionEvent e) {
		s.logout(e);
	}

	@FXML
	private void nastavnici(ActionEvent e) {
		s.loadProdekan_nastavnici(e);
	}

	@FXML
	private void predmeti(ActionEvent e) {
		s.loadProdekan_to_predmeti(e);
	}

	@FXML
	private void to_zahtjevi(ActionEvent e) {
		s.loadProdekan_to_zahtjevi(e);
	}

	@FXML
	private void to_plan_realizacije(ActionEvent e) {
		s.loadProdekan_to_plan_realizacije(e);
	}

	@FXML
	private void to_prijavljeni_predmeti(ActionEvent e) {
		s.loadProdekan_to_prijavljeni_predmeti(e);
	}

	@FXML
	private void to_bitni_datumi(ActionEvent e) {
		s.loadProdekan_to_bitni_datumi(e);
	}
}
