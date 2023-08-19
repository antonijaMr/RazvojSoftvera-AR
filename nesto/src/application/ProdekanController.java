package application;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import models.Predmet;
import javafx.scene.control.TextField;

public class ProdekanController implements Initializable {
	private MySQLConnection mysql = new MySQLConnection();
	private SceneLoader s = new SceneLoader();


	@FXML
	private TableView<Predmet> predmeti_table;

	@FXML
	private TableColumn<Predmet, String> sifraCol;
	@FXML
	private TableColumn<Predmet, String> nazCol;
	@FXML
	private TableColumn<Predmet, String> UsmjerenjeCol;
	@FXML
	private TableColumn<Predmet, String> PCol;
	@FXML
	private TableColumn<Predmet, String> ACol;
	@FXML

	private TableColumn<Predmet, String> LCol;
	@FXML
	private TableColumn<Predmet, String> ECTSCol;
	@FXML
	private TableColumn<Predmet, String> SemestarCol;
	@FXML
	private TableColumn<Predmet, String> PredusloviCol;
	@FXML
	private TableColumn<Predmet, String> OpcijeCol;

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
	private TextField searchTextField;
	@FXML
	private Button Search;
	@FXML
	private Button btn_prijavljeniPredmeti;
	@FXML
	private Button btn_bitniDatumi;
	String query = null;
	ResultSet res = null;
	Predmet predmet = null;

	ObservableList<Predmet> List = FXCollections.observableArrayList();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		mysql.Connect();
		load();
	}

	private void refreshTable() {

		try {
			List.clear();
			query = "SELECT * FROM predmet ";
			mysql.pst = mysql.con.prepareStatement(query);
			res = mysql.pst.executeQuery();
			ResultSet res2;
			while (res.next()) {
				query = "SELECT preduslov.sifPreduslov FROM preduslov  WHERE preduslov.sifPred=?";
				mysql.pst = mysql.con.prepareStatement(query);
				mysql.pst.setString(1, res.getString("sifPred"));
				res2 = mysql.pst.executeQuery();
				String Preduslov = "";

				while (res2.next()) {
					Preduslov += res2.getString("sifPreduslov") + ",";
				}
				Predmet p = new Predmet();
				p.setSifraPred(res.getString("sifPred"));
				p.setKratPred("");
				p.setNazivPred(res.getString("nazivPred"));
				p.setUzaNaucnaOblast(res.getString("uzaNaucnaOblast"));
				p.setPredavanja_sati(res.getString("satiPredavanja"));
				p.setAv_sati(res.getString("satiAV"));
				p.setLab_sati(res.getString("satiLV"));
				p.setECTS(res.getString("ECTS"));
				p.setSemestar(res.getString("semestar"));
				p.setPreduslovi(Preduslov);
				List.add(p);
//					List.add(new Predmet(
//							res.getString("sifraPred"),
//							res.getString("nazivPred"),
//							res.getString("usmjerenje"),
//							res.getInt("predavanja_sati"),
//							res.getInt("lab_sati"),
//							res.getInt("av_sati"),
//							res.getInt("ECTS"),
//							res.getString("semestar")
//							,Preduslov));
				predmeti_table.setItems(List);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private ObservableList<Predmet> filteredList = FXCollections.observableArrayList();

	@FXML
	private void handleSearch() {
		String searchText = searchTextField.getText().toLowerCase();

		filteredList.clear();

		for (Predmet predmet : List) {
			if (predmet.getSifraPred().toLowerCase().contains(searchText)
					|| predmet.getNazivPred().toLowerCase().contains(searchText)
					|| predmet.getSemestar().toLowerCase().contains(searchText)
					|| predmet.getUsmjerenje().toLowerCase().contains(searchText)) {
				filteredList.add(predmet);
			}
		}

		// Update your TableView with the filtered data
		predmeti_table.setItems(filteredList);
	}

	void load() {
		refreshTable();
		sifraCol.setCellValueFactory(new PropertyValueFactory<>("sifraPred"));
		nazCol.setCellValueFactory(new PropertyValueFactory<>("nazivPred"));
		UsmjerenjeCol.setCellValueFactory(new PropertyValueFactory<>("usmjerenje"));
		PCol.setCellValueFactory(new PropertyValueFactory<>("predavanja_sati"));
		ACol.setCellValueFactory(new PropertyValueFactory<>("lab_sati"));
		LCol.setCellValueFactory(new PropertyValueFactory<>("av_sati"));
		ECTSCol.setCellValueFactory(new PropertyValueFactory<>("ECTS"));
		SemestarCol.setCellValueFactory(new PropertyValueFactory<>("semestar"));
		// PredusloviCol.setCellValueFactory(new PropertyValueFactory<>("preduslovi"));

		PredusloviCol.setCellValueFactory(new PropertyValueFactory<>("preduslovi"));
		PredusloviCol.setCellFactory(param -> new TableCell<>() {
			@Override
			protected void updateItem(String item, boolean empty) {
				super.updateItem(item, empty);
				if (empty || item == null) {
					setText(null);
				} else {
					setText(item);
				}
			}
		});
		// Add the button to the "OpcijeCol" column
		OpcijeCol.setCellFactory(param -> new TableCell<>() {
			private final Button updateButton = new Button("Update Preduslovi");

			{
				updateButton.setOnAction(event -> {
					Predmet predmet = getTableView().getItems().get(getIndex());
					openCustomDialog(predmet.getSifraPred());

					System.out.println("Updating preduslovi for " + predmet.getSifraPred());
				});
			}

			@Override
			protected void updateItem(String item, boolean empty) {
				super.updateItem(item, empty);
				if (empty) {
					setGraphic(null);
				} else {
					setGraphic(updateButton);
				}
			}
		});

	}

	private void openCustomDialog(String sifraPredmet) {
		// Create the custom dialog
		Dialog<ButtonType> dialog = new Dialog<>();
		dialog.setTitle("Custom Dialog");
		dialog.setHeaderText("Select an option:");

		// Create buttons for each option
		ButtonType addPrerequisitesButton = new ButtonType("Add New Prerequisites");
		ButtonType deletePrerequisitesButton = new ButtonType("Delete Existing Prerequisites");

		// Add buttons to the dialog pane
		DialogPane dialogPane = dialog.getDialogPane();
		dialogPane.getButtonTypes().addAll(addPrerequisitesButton, deletePrerequisitesButton, ButtonType.CANCEL);

		// Show the dialog and wait for user input
		dialog.showAndWait().ifPresent(buttonType -> {
			if (buttonType == addPrerequisitesButton) {
				try {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("dodaj_preduslov.fxml"));
					Parent root = loader.load();
					preduslovController dodajPreduslovController = loader.getController();
					dodajPreduslovController.setSifraPredmet(sifraPredmet);

					Scene newScene = new Scene(root);
					Stage newStage = new Stage();
					newStage.setScene(newScene);
					dodajPreduslovController.setStage(newStage);
					// newStage.setTitle("");
					newStage.show();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if (buttonType == deletePrerequisitesButton) {
				try {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("obrisi_preduslov.fxml"));
					Parent root = loader.load();
					ObrisiPreduslovController ObrisiPreduslovController = loader.getController();
					ObrisiPreduslovController.setSifraPr(sifraPredmet);
					Scene newScene = new Scene(root);
					Stage newStage = new Stage();
					newStage.setScene(newScene);
					ObrisiPreduslovController.setStage(newStage);
					// newStage.setTitle("");
					newStage.show();

				} catch (IOException e) {
					e.printStackTrace();
				}

			} else {
				// User canceled the dialog
				// Add any necessary cleanup or handling here
			}
		});
	}

	@FXML
	private void logout(MouseEvent e) {
		s.logout(e);
	}

	@FXML
	private void nastavnici(MouseEvent e) {
		s.loadProdekan_nastavnici(e);
	}

	@FXML
	private void predmeti(MouseEvent e) {
		s.loadProdekan_to_predmeti(e);
	}

	@FXML
	private void to_zahtjevi(MouseEvent e) {
		s.loadProdekan_to_zahtjevi(e);
	}

	@FXML
	private void to_plan_realizacije(MouseEvent e) {
		s.loadProdekan_to_plan_realizacije(e);
	}

	@FXML
	private void refresh(ActionEvent e) {
		refreshTable();
	}

	@FXML
	private void to_prijavljeni_predmeti(MouseEvent e) {
		s.loadProdekan_to_prijavljeni_predmeti(e);
	}

	@FXML
	private void to_bitni_datumi(MouseEvent e) {
		s.loadProdekan_to_bitni_datumi(e);
	}
}
