package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import javafx.scene.layout.HBox;

import javafx.scene.control.TextField;

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

import javafx.scene.control.TableView;

import javafx.scene.layout.Pane;
import models.Nastavnik;
import models.Predmet;
import javafx.scene.control.TableColumn;

public class administrator_pogledajNastavnikeController implements Initializable {
	private MySQLConnection mysql = new MySQLConnection();
	private SceneLoader s = new SceneLoader();

	@FXML
	private HBox root;
	@FXML
	private AnchorPane side_anchorpane;
	@FXML
	private Pane inner_pane;
	@FXML
	private Button btn_logout;
	@FXML
	private TextField search_tf;
	@FXML
	private TableView<Nastavnik> nastavniciT;
	@FXML
	private TableColumn<Nastavnik, String> idC;
	@FXML
	private TableColumn<Nastavnik, String> imeC;
	@FXML
	private TableColumn<Nastavnik, String> prezimeC;
	@FXML
	private TableColumn<Nastavnik, String> emailC;
	@FXML
	private TableColumn<Nastavnik, String> zvanjeC;
	@FXML
	private TableColumn<Nastavnik, String> odsjekC;

	@FXML
	public void studenti(ActionEvent event) {
		s.loadAdminStudent(event);
	}

	@FXML
	public void nastavnici(ActionEvent event) {
		s.loadAdminNastavnik(event);
	}

	@FXML
	public void predmeti(ActionEvent event) {
		s.loadAdminPredmet(event);
	}

	@FXML
	public void prodekan(ActionEvent event) {
		s.loadAdminProdekan(event);
	}

	@FXML
	public void logout(ActionEvent event) {
		s.logout(event);
	}

	@FXML
	public void pogledajNastavnike(ActionEvent event) {
		s.loadAdminNastavnici(event);
	}

	@FXML
	public void dodajNastavnika(ActionEvent event) {
		s.loadAdminNastavnik(event);
	}

	public void tableNast() {
		mysql.Connect();

		ObservableList<Nastavnik> nast= FXCollections.observableArrayList();
		try {
			mysql.pst = mysql.con.prepareStatement("Select * from nastavnik;");
			ResultSet rs = mysql.pst.executeQuery();
			{
				while (rs.next()) {
					Nastavnik n = new Nastavnik();
					n.setSifNast(rs.getString("nastavnik_id"));
					n.setPrezime(rs.getString("prezime"));
					n.setIme(rs.getString("ime"));
					n.setEmail(rs.getString("email"));
					n.setZvanje(rs.getString("zvanje"));
					n.setOdsjek(rs.getString("odsjek"));

					nast.add(n);
				}

			}

			nastavniciT.setItems(nast);

			idC.setCellValueFactory(f -> f.getValue().sifNastProperty());
			imeC.setCellValueFactory(f -> f.getValue().imeProperty());
			prezimeC.setCellValueFactory(f -> f.getValue().prezimeProperty());
			emailC.setCellValueFactory(f -> f.getValue().emailProperty());
			zvanjeC.setCellValueFactory(f -> f.getValue().zvanjeProperty());
			odsjekC.setCellValueFactory(f -> f.getValue().odsjekProperty());

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	private void setupSearch() {
		FilteredList<Nastavnik> filteredData = new FilteredList<>(nastavniciT.getItems(), p -> true);

		search_tf.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(nastavnik-> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = search_tf.getText().toLowerCase();

				if (nastavnik.getIme().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if (nastavnik.getPrezime().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				}
				return false;
			});
		});

		SortedList<Nastavnik> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(nastavniciT.comparatorProperty());

		nastavniciT.setItems(sortedData);
	}
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		mysql.Connect();
		tableNast();
		setupSearch();
	}
}
