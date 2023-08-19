package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import javafx.scene.layout.HBox;

import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;

import javafx.scene.layout.AnchorPane;

import javafx.scene.layout.Pane;
import models.Nastavnik;
import models.Predmet;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class administrator_pogledajPredmeteController implements Initializable {
	private MySQLConnection mysql = new MySQLConnection();
	private SceneLoader s = new SceneLoader();

	@FXML
	private TextField search_tf;
	@FXML
	private TableView<Predmet> predmetiT;
	@FXML
	private TableColumn<Predmet, String> sifraC;
	@FXML
	private TableColumn<Predmet, String> kraticaC;
	@FXML
	private TableColumn<Predmet, String> nazivC;
	@FXML
	private TableColumn<Predmet, String> uzaNC;
	@FXML
	private TableColumn<Predmet, String> pC;
	@FXML
	private TableColumn<Predmet, String> avC;
	@FXML
	private TableColumn<Predmet, String> lvC;
	@FXML
	private TableColumn<Predmet, String> ectsC;
	@FXML
	private TableColumn<Predmet, String> semestarC;

	@FXML
	public void studenti(MouseEvent event) {
		s.loadAdminStudent(event);
	}

	@FXML
	public void nastavnici(MouseEvent event) {
		s.loadAdminNastavnik(event);
	}

	@FXML
	public void predmeti(MouseEvent event) {
		s.loadAdminPredmet(event);
	}

	@FXML
	public void prodekan(MouseEvent event) {
		s.loadAdminProdekan(event);
	}

	@FXML
	public void logout(MouseEvent event) {
		s.logout(event);
	}

	@FXML
	public void pogledajPredmete(ActionEvent event) {
		s.loadAdminPredmeti(event);
	}

	@FXML
	public void dodajPredmet(ActionEvent event) {
		s.loadAdminPredmet(event);
	}

	public void tablePred() {
		mysql.Connect();

		ObservableList<Predmet> predmeti= FXCollections.observableArrayList();
		try {
			mysql.pst = mysql.con.prepareStatement("Select * from predmet;");
			ResultSet rs = mysql.pst.executeQuery();
			{
				while (rs.next()) {
					Predmet p = new Predmet();
					p.setSifraPred(rs.getString("sifPred"));
					p.setKratPred(rs.getString("kratPred"));
					p.setNazivPred(rs.getString("nazivPred"));
					p.setUzaNaucnaOblast(rs.getString("uzaNaucnaOblast"));
					p.setPredavanja_sati(rs.getString("satiPredavanja"));
					p.setAv_sati(rs.getString("satiAV"));
					p.setLab_sati(rs.getString("satiLV"));
					p.setECTS(rs.getString("ECTS"));
					p.setSemestar(rs.getString("semestar"));

					predmeti.add(p);
				}

			}

			predmetiT.setItems(predmeti);
			sifraC.setCellValueFactory(f -> f.getValue().sifraPredProperty());
			kraticaC.setCellValueFactory(f -> f.getValue().kratPredProperty());
			nazivC.setCellValueFactory(f -> f.getValue().nazivPredProperty());
			uzaNC.setCellValueFactory(f -> f.getValue().uzaNaucnaOblastProperty());
			pC.setCellValueFactory(f -> f.getValue().predavanja_satiProperty());
			avC.setCellValueFactory(f -> f.getValue().av_satiProperty());
			lvC.setCellValueFactory(f -> f.getValue().lab_satiProperty());
			ectsC.setCellValueFactory(f -> f.getValue().ECTSProperty());
			semestarC.setCellValueFactory(f -> f.getValue().semestarProperty());

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void setupSearch() {
		FilteredList<Predmet> filteredData = new FilteredList<>(predmetiT.getItems(), p -> true);

		search_tf.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(predmet-> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = search_tf.getText().toLowerCase();
				if (predmet.getSifraPred().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if (predmet.getNazivPred().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				}
				return false;
			});
		});

		SortedList<Predmet> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(predmetiT.comparatorProperty());
		predmetiT.setItems(sortedData);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		mysql.Connect();
		tablePred();
		setupSearch();
	}
}
