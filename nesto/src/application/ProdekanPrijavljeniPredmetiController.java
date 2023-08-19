package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.Nastavnik;
import models.Predmet;
import models.Student_predmet;

public class ProdekanPrijavljeniPredmetiController implements Initializable {
	private MySQLConnection mysql = new MySQLConnection();
	private SceneLoader s = new SceneLoader();
	@FXML
	private TableView<Student_predmet> PrijavljeniStudentiTable;

	@FXML
	private TableColumn<Student_predmet, String> StudentCol;
	@FXML
	private TableColumn<Student_predmet, String> PredmetCol;

	private Stage stage;
	private Scene scene;
	private Parent root;
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
	private Button btn_prijavljeniPredmeti;
	@FXML
	private Button refreshButton;
	@FXML
	private Button btn_bitniDatumi;
	@FXML
	private TextField search_tf;
	@FXML
	private Button search_btn;

	String query = null;
	ResultSet res = null;
	Predmet predmet = null;

	ObservableList<Student_predmet> List = FXCollections.observableArrayList();
	private ObservableList<Student_predmet> filteredList = FXCollections.observableArrayList();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		mysql.Connect();
		load();
	}

	private void refreshTable() {

		try {
			List.clear();
			query = "SELECT student.brojIndeksa,predmeti.sifPred,nazivPred,CONCAT(ime,' ',prezime) AS StudIme FROM student INNER JOIN slusaPred ON student.brojIndeksa=slusaPred.brojIndeksa"
					+ " INNER JOIN predmeti ON predmeti.sifPred=slusaPred.sifPred ORDER BY prezime DESC";

			mysql.pst = mysql.con.prepareStatement(query);
			res = mysql.pst.executeQuery();

			while (res.next()) {
				List.add(new Student_predmet(res.getInt("student.brojIndeksa"), res.getString("StudIme"),
						res.getString("sifPred"), res.getString("nazivPred")));
			}
			PrijavljeniStudentiTable.setItems(List);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@FXML
	private void handleSearch() {
		String searchText = search_tf.getText().toLowerCase();

		filteredList.clear();

		for (Student_predmet nastavnik : List) {
			if (nastavnik.getImePrezimeStud().toLowerCase().contains(searchText)
					|| Integer.toString(nastavnik.getStudent_id()).toLowerCase().contains(searchText)
					|| nastavnik.getSifraPred().toLowerCase().contains(searchText)
					|| nastavnik.getNazivPred().toLowerCase().contains(searchText)) {
				filteredList.add(nastavnik);
			}
		}

		// Update your TableView with the filtered data
		PrijavljeniStudentiTable.setItems(filteredList);
	}

	void load() {
		refreshTable();

		StudentCol.setCellValueFactory(new PropertyValueFactory<>("ImePrezimeStud"));
		PredmetCol.setCellValueFactory(new PropertyValueFactory<>("NazivPred"));

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
