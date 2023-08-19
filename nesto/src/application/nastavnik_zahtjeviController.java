package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
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
import models.Student;
import models.ZahtjevZaPrenosBodova;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class nastavnik_zahtjeviController implements Initializable {
	MySQLConnection mysql = new MySQLConnection();

	private Nastavnik currentNastavnik;
	private SceneLoader s = new SceneLoader();

	@FXML
	private Label nastIme;
	@FXML
	private TextField search_tf;

	@FXML
	private TableView<ZahtjevZaPrenosBodova> zahtjeviTable;
	@FXML
	private TableColumn<ZahtjevZaPrenosBodova, String> indexC;
	@FXML
	private TableColumn<ZahtjevZaPrenosBodova, String> imeC;
	@FXML
	private TableColumn<ZahtjevZaPrenosBodova, String> prezimeC;
	@FXML
	private TableColumn<ZahtjevZaPrenosBodova, String> predmetC;
	@FXML
	private TableColumn<ZahtjevZaPrenosBodova, String> bodoviC;
	@FXML
	private TableColumn<ZahtjevZaPrenosBodova, Button> actionC;

	@FXML
	public void predmeti(MouseEvent e) {
		s.loadPredmeti(e);
	}

	@FXML
	public void to_zahtjevi(MouseEvent e) {
		s.loadZahtjevi(e);
	}

	@FXML
	public void to_predZahtjevi(MouseEvent e) {
		s.loadPredZahtjevi(e);
	}

	@FXML
	public void logout(MouseEvent e) {
		s.logout(e);
	}
	
	@FXML
	public void tekucaGodina(ActionEvent e) {
		s.loadPredmeti(e);
	}
	
	@FXML
	public void novaGodina(ActionEvent e) {
		s.loadNastavnikIducaGodina(e);
	}



	private void TableZahtjevi() {
		mysql.Connect();

		ObservableList<ZahtjevZaPrenosBodova> zahtjevi = FXCollections.observableArrayList();
		try {

			mysql.pst = mysql.con.prepareStatement(
					"select brojIndeksa,student.ime,student.prezime,predmet.nazivPred,brojBodova,predmet.sifPred from zahtjevZaPrenos "
							+ "inner join student on student.brojIndeksa= idStud "
							+ "inner join predmet on predmet.sifPred = zahtjevZaPrenos.sifPred "
							+ "where odobreno is null and sifNast =? ;");
			mysql.pst.setString(1, currentNastavnik.getSifNast());
			ResultSet rs = mysql.pst.executeQuery();
			{
				while (rs.next()) {
					ZahtjevZaPrenosBodova z = new ZahtjevZaPrenosBodova();
					z.stud.setId(rs.getString("brojIndeksa"));
					z.stud.setIme(rs.getString("ime"));
					z.stud.setPrezime(rs.getString("prezime"));
					z.pred.setNazivPred(rs.getString("nazivPred"));
					z.pred.setSifraPred(rs.getString("sifPred"));
					z.setBrojBodova(rs.getString("brojBodova"));
					zahtjevi.add(z);
				}
			}

			zahtjeviTable.setItems(zahtjevi);
			indexC.setCellValueFactory(f -> f.getValue().stud.idProperty());
			imeC.setCellValueFactory(f -> f.getValue().stud.imeProperty());
			prezimeC.setCellValueFactory(f -> f.getValue().stud.prezimeProperty());
			predmetC.setCellValueFactory(f -> f.getValue().pred.nazivPredProperty());
			bodoviC.setCellValueFactory(f -> f.getValue().brojBodovaProperty());

			actionC.setCellValueFactory(
					cellData -> new ReadOnlyObjectWrapper<Button>(cellData.getValue().getActionButton()));
			actionC.setCellFactory(param -> new TableCell<ZahtjevZaPrenosBodova, Button>() {
				@Override
				protected void updateItem(Button item, boolean empty) {
					super.updateItem(item, empty);
					if (empty || item == null) {
						setGraphic(null);
					} else {
						setGraphic(item);
						item.setOnAction(event -> {
							int rowIndex = getIndex();
							ZahtjevZaPrenosBodova selectedZahtjev = getTableView().getItems().get(rowIndex);
							try {
								openDialog(selectedZahtjev);
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

	private void openDialog(ZahtjevZaPrenosBodova z) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("nastavnik_dialogApproveDeny.fxml"));
		DialogPane aproveDenyDialog = loader.load();
		nastavnik_dialogApproveDenyController adC = loader.getController();
		adC.setData(z);
		Dialog<ButtonType> dialog = new Dialog<>();
		dialog.setDialogPane(aproveDenyDialog);
		dialog.setTitle("Zahtjev za prenos bodova");

		Optional<ButtonType> clickedButton = dialog.showAndWait();

		if (clickedButton.get() == ButtonType.OK) {
			adC.updateData();
			TableZahtjevi();
		}
	}

	private void setCurrentNastavnik() {
		currentNastavnik = DataSingleton.getInstance().getNastavnik();
		nastIme.setText(currentNastavnik.getIme() + " " + currentNastavnik.getPrezime());
	}

	private void setupSearch() {
		FilteredList<ZahtjevZaPrenosBodova> filteredData = new FilteredList<>(zahtjeviTable.getItems(), p -> true);

		search_tf.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(zahtjev -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = search_tf.getText().toLowerCase();

				if (zahtjev.pred.getNazivPred().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if (zahtjev.stud.getIme().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if (zahtjev.stud.getPrezime().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				}

				return false;
			});
		});

		SortedList<ZahtjevZaPrenosBodova> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(zahtjeviTable.comparatorProperty());

		zahtjeviTable.setItems(sortedData);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setCurrentNastavnik();
		mysql.Connect();
		actionC.setCellValueFactory(new PropertyValueFactory<>("action"));
		actionC.setCellFactory(param -> new TableCell<>() {
			private final Button button = new Button("Your Button Text");

			@Override
			protected void updateItem(Button item, boolean empty) {
				super.updateItem(item, empty);

				if (empty) {
					setGraphic(null);
				} else {
					setGraphic(button);
				}
			}
		});
		TableZahtjevi();
		setupSearch();
	}

}