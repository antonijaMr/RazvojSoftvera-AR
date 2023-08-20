package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableCell;
import javafx.scene.layout.HBox;

import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.beans.property.ReadOnlyObjectWrapper;
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
	private TextField search_tf;
	@FXML
	private TableView<Nastavnik> nastavniciT;
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
	private TableColumn<Nastavnik, Button> actionC;

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
	public void pogledajNastavnike(ActionEvent event) {
		s.loadAdminNastavnici(event);
	}

	@FXML
	public void dodajNastavnika(ActionEvent event) {
		s.loadAdminNastavnik(event);
	}

	public void tableNast() {
		mysql.Connect();

		ObservableList<Nastavnik> nast = FXCollections.observableArrayList();
		try {
			mysql.pst = mysql.con.prepareStatement("Select * from nastavnik;");
			ResultSet rs = mysql.pst.executeQuery();
			{
				while (rs.next()) {
					Nastavnik n = new Nastavnik();
					n.setSifNast(rs.getString("sifNast"));
					n.setPrezime(rs.getString("prezime"));
					n.setIme(rs.getString("ime"));
					n.setEmail(rs.getString("email"));
					n.setZvanje(rs.getString("zvanje"));
					n.setOdsjek(rs.getString("odsjek"));

					nast.add(n);
				}

			}

			nastavniciT.setItems(nast);
			imeC.setCellValueFactory(f -> f.getValue().imeProperty());
			prezimeC.setCellValueFactory(f -> f.getValue().prezimeProperty());
			emailC.setCellValueFactory(f -> f.getValue().emailProperty());
			zvanjeC.setCellValueFactory(f -> f.getValue().zvanjeProperty());
			odsjekC.setCellValueFactory(f -> f.getValue().odsjekProperty());

			actionC.setCellValueFactory(
					cellData -> new ReadOnlyObjectWrapper<Button>(cellData.getValue().getActionButton()));
			actionC.setCellFactory(param -> new TableCell<Nastavnik, Button>() {
				@Override
				protected void updateItem(Button item, boolean empty) {
					super.updateItem(item, empty);
					if (empty || item == null) {
						setGraphic(null);
					} else {
						setGraphic(item);
						item.setOnAction(event -> {
							int rowIndex = getIndex();
							Nastavnik selected = getTableView().getItems().get(rowIndex);
							openDialog(selected, event);
						});
					}
				}
			});

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void openDialog(Nastavnik p, ActionEvent e) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("administrator_updateNastavnik.fxml"));
			DialogPane odgovoriDialog;
			odgovoriDialog = loader.load();
			System.out.println("eror");
			administrator_updateNastavnikController cont = loader.getController();
			System.out.println("eror");
			cont.setData(p);
			Dialog<ButtonType> dialog = new Dialog<>();
			dialog.setDialogPane(odgovoriDialog);
			dialog.setTitle("Promjena nastavnika");

			dialog.showAndWait();
			tableNast();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	private void setupSearch() {
		FilteredList<Nastavnik> filteredData = new FilteredList<>(nastavniciT.getItems(), p -> true);

		search_tf.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(nastavnik -> {
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
