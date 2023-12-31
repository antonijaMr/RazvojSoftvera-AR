package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

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

import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.layout.AnchorPane;

import javafx.scene.control.TableView;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import models.Nastavnik;
import models.Predmet;
import javafx.scene.control.TableColumn;

public class nastavnik_iducaGodinaController implements Initializable {
	MySQLConnection mysql = new MySQLConnection();
	private Nastavnik currentNastavnik = new Nastavnik();
	private SceneLoader s = new SceneLoader();

	@FXML
	private HBox root;
	@FXML
	private AnchorPane side_anchorpane;
	@FXML
	private Pane inner_pane;
	@FXML
	private Label nastIme;
	@FXML
	private Button btn_predmeti;
	@FXML
	private Button btn_zahtjevi;
	@FXML
	private Button btn_logout;
	@FXML
	private TableView<Predmet> predTable;
	@FXML
	private TableColumn<Predmet, String> sifPredC;
	@FXML
	private TableColumn<Predmet, String> nazPredC;
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
	private TableColumn<Predmet, Button> detaljiC;
	@FXML
	private TextField search_tf;

	@FXML
	public void predmeti(MouseEvent e) throws IOException {
		s.loadPredmeti(e);
	}

	@FXML
	public void to_zahtjevi(MouseEvent e) throws IOException {
		s.loadZahtjevi(e);
	}

	@FXML
	public void to_predZahtjevi(MouseEvent e) throws IOException {
		s.loadPredZahtjevi(e);
	}

	@FXML
	public void logout(MouseEvent e) throws IOException {
		s.logout(e);
	}

	@FXML
	public void tekucaGodina(ActionEvent e) throws IOException {
		System.out.println("2023");
		s.loadPredmeti(e);
	}

	@FXML
	public void novaGodina(ActionEvent e) throws IOException {
		System.out.println("2024");
		s.loadNastavnikIducaGodina(e);
	}

	public void setCurrentNastavnik() {
		currentNastavnik = DataSingleton.getInstance().getNastavnik();
		nastIme.setText(currentNastavnik.getIme() + " " + currentNastavnik.getPrezime());
	}

	public void TablePred() {
		mysql.Connect();

		ObservableList<Predmet> predmeti = FXCollections.observableArrayList();
		try {
			mysql.pst = mysql.con.prepareStatement(
					"select predmet.sifPred,kratPred,nazivPred,uzaNaucnaOblast,satiPredavanja,satiAV,satiLV,ECTS,semestar\n"
							+ "from predmet inner join predaje on predaje.sifPred = predmet.sifPred where sifNastavnik =? and predaje.godina = 2024;");
			mysql.pst.setString(1, currentNastavnik.getSifNast());
			ResultSet rs = mysql.pst.executeQuery();
			{
				while (rs.next()) {
					Predmet pr = new Predmet();
					pr.setSifraPred(rs.getString("sifPred"));
					pr.setKratPred(rs.getString("kratPred"));
					pr.setNazivPred(rs.getString("nazivPred"));
					pr.setUzaNaucnaOblast(rs.getString("uzaNaucnaOblast"));
					pr.setPredavanja_sati(rs.getString("satiPredavanja"));
					pr.setAv_sati(rs.getString("satiAV"));
					pr.setLab_sati(rs.getString("satiLV"));
					pr.setECTS(rs.getString("ECTS"));
					pr.setSemestar(rs.getString("semestar"));

					predmeti.add(pr);
				}

			}

			predTable.setItems(predmeti);

			sifPredC.setCellValueFactory(f -> f.getValue().sifraPredProperty());
			nazPredC.setCellValueFactory(f -> f.getValue().nazivPredProperty());
			pC.setCellValueFactory(f -> f.getValue().predavanja_satiProperty());
			avC.setCellValueFactory(f -> f.getValue().av_satiProperty());
			lvC.setCellValueFactory(f -> f.getValue().lab_satiProperty());
			ectsC.setCellValueFactory(f -> f.getValue().ECTSProperty());
			semestarC.setCellValueFactory(f -> f.getValue().semestarProperty());

			detaljiC.setCellValueFactory(
					cellData -> new ReadOnlyObjectWrapper<Button>(cellData.getValue().getActionButton()));
			detaljiC.setCellFactory(param -> new TableCell<Predmet, Button>() {
				@Override
				protected void updateItem(Button item, boolean empty) {
					super.updateItem(item, empty);
					if (empty || item == null) {
						setGraphic(null);
					} else {
						setGraphic(item);
						item.setOnAction(event -> {
							int rowIndex = getIndex();
							Predmet selectedPredmet = getTableView().getItems().get(rowIndex);
							try {
								openDialog(selectedPredmet,event);
							} catch (IOException e) {
								e.printStackTrace();
							}
						});
					}
				}
			});

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void openDialog(Predmet p,ActionEvent e) throws IOException {
		DataSingleton.getInstance().setPredmet(p);
		s.loadNastavnikDetaljiIducaGodina(e);
	}

	private void setupSearch() {
		FilteredList<Predmet> filteredData = new FilteredList<>(predTable.getItems(), p -> true);

		search_tf.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(predmet -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = search_tf.getText().toLowerCase();

				if (predmet.getNazivPred().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if (predmet.getSifraPred().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				}
				return false;
			});
		});

		SortedList<Predmet> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(predTable.comparatorProperty());

		predTable.setItems(sortedData);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setCurrentNastavnik();
		mysql.Connect();
		TablePred();
		setupSearch();
	}
}
