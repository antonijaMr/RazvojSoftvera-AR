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

import javafx.scene.layout.Pane;
import models.Nastavnik;
import models.Predmet;
import models.Student;
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
	private TableColumn<Predmet, Button> actionC;

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
			actionC.setCellValueFactory(
					cellData -> new ReadOnlyObjectWrapper<Button>(cellData.getValue().getActionButton()));
			actionC.setCellFactory(param -> new TableCell<Predmet, Button>() {
				@Override
				protected void updateItem(Button item, boolean empty) {
					super.updateItem(item, empty);
					if (empty || item == null) {
						setGraphic(null);
					} else {
						setGraphic(item);
						item.setOnAction(event -> {
							int rowIndex = getIndex();
							Predmet selected = getTableView().getItems().get(rowIndex);
							openDialog(selected, event);
						});
					}
				}
			});

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void openDialog(Predmet p, ActionEvent e) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("administrator_updatePredmet.fxml"));
			DialogPane odgovoriDialog;
			odgovoriDialog = loader.load();
			System.out.println("eror");
			administrator_updatePredmetController  cont = loader.getController();
			System.out.println("eror");
			cont.setData(p);
			Dialog<ButtonType> dialog = new Dialog<>();
			dialog.setDialogPane(odgovoriDialog);
			dialog.setTitle("Promjena studenta");

			dialog.showAndWait();
			tablePred();
		} catch (IOException e1) {
			e1.printStackTrace();
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
