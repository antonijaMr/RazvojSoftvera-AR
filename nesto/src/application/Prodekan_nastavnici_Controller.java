package application;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.Nastavnik;
import models.Predmet;
import models.Student;

public class Prodekan_nastavnici_Controller implements Initializable {
	private MySQLConnection mysql = new MySQLConnection();
	private SceneLoader s = new SceneLoader();

	@FXML
	private TableView<Nastavnik> nastavnik_table;

	@FXML
	private TableColumn<Nastavnik, String> ImeCol;
	@FXML
	private TableColumn<Nastavnik, String> PrezimeCol;
	@FXML
	private TableColumn<Nastavnik, String> EmailCol;
	@FXML
	private TableColumn<Nastavnik, String> ZvanjeCol;
	@FXML
	private TableColumn<Nastavnik, String> OdsjekCol;
	@FXML
	private TableColumn<Nastavnik, String> OpcijeCol;

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
	private Button refreshButton;
	@FXML
	private Button btn_prijavljeniPredmeti;
	@FXML
	private TextField searchTextField;
	@FXML
	private Button Search;

	String query = null;
	ResultSet res = null;
	Predmet predmet = null;

	ObservableList<Nastavnik> List = FXCollections.observableArrayList();
	private ObservableList<Nastavnik> filteredList = FXCollections.observableArrayList();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		mysql.Connect();
		load();
		setupSearch();
	}

	private void refreshTable() {

		try {
			List.clear();
			query = "SELECT sifNast,ime,prezime,email,zvanje,odsjek,prodekan FROM nastavnik ";
			mysql.pst = mysql.con.prepareStatement(query);
			res = mysql.pst.executeQuery();

			while (res.next()) {
				Nastavnik n = new Nastavnik();
				n.setSifNast(res.getString("sifNast"));
				n.setPrezime(res.getString("prezime"));
				n.setIme(res.getString("ime"));
				n.setEmail(res.getString("email"));
				n.setZvanje(res.getString("zvanje"));
				n.setOdsjek(res.getString("odsjek"));
				List.add(n);
			}
			nastavnik_table.setItems(List);
			// handleSearch();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@FXML
	private void handleSearch() {
		String searchText = searchTextField.getText().toLowerCase();

		filteredList.clear();

		for (Nastavnik nastavnik : List) {
			if (nastavnik.getIme().toLowerCase().contains(searchText)
					|| nastavnik.getPrezime().toLowerCase().contains(searchText)
					|| nastavnik.getEmail().toLowerCase().contains(searchText)
					|| nastavnik.getZvanje().toLowerCase().contains(searchText)
					|| nastavnik.getOdsjek().toLowerCase().contains(searchText)) {
				filteredList.add(nastavnik);
			}
		}

		// Update your TableView with the filtered data
		nastavnik_table.setItems(filteredList);
	}

	void load() {
		refreshTable();

		ImeCol.setCellValueFactory(new PropertyValueFactory<>("ime"));
		PrezimeCol.setCellValueFactory(new PropertyValueFactory<>("prezime"));
		EmailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
		ZvanjeCol.setCellValueFactory(new PropertyValueFactory<>("zvanje"));
		OdsjekCol.setCellValueFactory(new PropertyValueFactory<>("odsjek"));

		// Add the button to the "OpcijeCol" column
		OpcijeCol.setCellFactory(param -> new TableCell<>() {
			private final Button updateButton = new Button(" Azuriraj Zvanje");

			{
				updateButton.setOnAction(event -> {
					Nastavnik nastavnik = getTableView().getItems().get(getIndex());
					try {
						openUpdateZvanjeScene(nastavnik.getEmail());
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("Updating zvanje for " + nastavnik.getEmail());
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

	private void openUpdateZvanjeScene(String email) throws SQLException {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("update_zvanje.fxml"));
			Parent root = loader.load();

			UpdateZvanjeController updateZvanjeController = loader.getController();
			updateZvanjeController.setNastavnikIdentifier(email); // Pass the email or any unique identifier

			Scene newScene = new Scene(root);
			Stage newStage = new Stage();
			newStage.setScene(newScene);
			newStage.setTitle("Update Zvanje");
			newStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void showAlert(String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}
	
	private void setupSearch() {
		FilteredList<Nastavnik> filteredData = new FilteredList<>(nastavnik_table.getItems(), p -> true);

		searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(item-> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = searchTextField.getText().toLowerCase();

				if (item.getIme().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if (item.getPrezime().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				}
				return false;
			});
		});

		SortedList<Nastavnik> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(nastavnik_table.comparatorProperty());

		nastavnik_table.setItems(sortedData);
	}

	@FXML
	private void refreshButtonClicked(ActionEvent e) {
		refreshTable();
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
	private void to_prijavljeni_predmeti(MouseEvent e) {
		s.loadProdekan_to_prijavljeni_predmeti(e);
	}

	@FXML
	private void to_bitni_datumi(MouseEvent e) {
		s.loadProdekan_to_bitni_datumi(e);
	}

}
