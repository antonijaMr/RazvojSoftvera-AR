package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.HBox;

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

import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.layout.AnchorPane;

import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import models.Nastavnik;
import models.Predmet;
import models.Preduslov;
import models.ZahtjevZaSlusanjePredmeta;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;

public class nastavnik_zahtjeviZaPredmetController implements Initializable {
	MySQLConnection mysql = new MySQLConnection();

	private Nastavnik currentNastavnik;
	private SceneLoader s = new SceneLoader();
	private String predIndex;
	private String studIndex;

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
	private TextField search_tf;
	@FXML
	private TableView<ZahtjevZaSlusanjePredmeta> zahtjeviTable;
	@FXML
	private TableColumn<ZahtjevZaSlusanjePredmeta, String> indexC;
	@FXML
	private TableColumn<ZahtjevZaSlusanjePredmeta, String> imeC;
	@FXML
	private TableColumn<ZahtjevZaSlusanjePredmeta, String> prezimeC;
	@FXML
	private TableColumn<ZahtjevZaSlusanjePredmeta, String> predmetC;
	@FXML
	private TableColumn<ZahtjevZaSlusanjePredmeta, Button> actionC;
	@FXML
	private TableView<Preduslov> predusloviTable;
	@FXML
	private TableColumn<Preduslov, String> nazivPreduslovaC;
	@FXML
	private TableColumn<Preduslov, String> odslusanoC;
	@FXML
	private TableColumn<Preduslov, String> ocjenaC;
	@FXML
	private TableView<Predmet> polozeniPT;
	@FXML
	private TableColumn<Predmet, String> nazivPolozenogC;

	public void setCurrentNastavnik() {
		currentNastavnik = DataSingleton.getInstance().getNastavnik();
		nastIme.setText(currentNastavnik.getIme() + " " + currentNastavnik.getPrezime());
	}

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

	public void TableZahtjevi() {
		mysql.Connect();

		ObservableList<ZahtjevZaSlusanjePredmeta> zahtjevi = FXCollections.observableArrayList();
		try {
			mysql.pst = mysql.con.prepareStatement(
					"select student_id,ime,prezime,email,godStudija,statusStud,sifUsmjerenja,ostvareniECTS,nazivPred,zahtjevZaSlusanje.poruka,predmet.sifPred from student \n"
							+ "inner join zahtjevZaSlusanje on zahtjevZaSlusanje.idStud = student.student_id\n"
							+ "inner join predmet on predmet.sifPred = zahtjevZaSlusanje.sifPred\n"
							+ "where sifNast = ? and odobreno is null;");
			mysql.pst.setString(1, currentNastavnik.getSifNast());
			ResultSet rs = mysql.pst.executeQuery();
			{
				while (rs.next()) {
					ZahtjevZaSlusanjePredmeta z = new ZahtjevZaSlusanjePredmeta();
					z.getStud().setId(rs.getString("student_id"));
					z.getStud().setIme(rs.getString("ime"));
					z.getStud().setPrezime(rs.getString("prezime"));
					z.getStud().setEmail(rs.getString("email"));
					z.getStud().setGodStudija(rs.getString("godStudija"));
					z.getStud().setStatusStud(rs.getString("statusStud"));
					z.getStud().setSifUsmjerenja(rs.getString("sifUsmjerenja"));
					z.getStud().setOstvareniECTS(rs.getString("ostvareniECTS"));
					z.getPred().setNazivPred(rs.getString("nazivPred"));
					z.getPred().setSifraPred(rs.getString("sifPred"));
					z.setPoruka(rs.getString("poruka"));

					zahtjevi.add(z);
				}

			}

			zahtjeviTable.setItems(zahtjevi);

			indexC.setCellValueFactory(f -> f.getValue().getStud().idProperty());
			imeC.setCellValueFactory(f -> f.getValue().getStud().imeProperty());
			prezimeC.setCellValueFactory(f -> f.getValue().getStud().prezimeProperty());
			predmetC.setCellValueFactory(f -> f.getValue().getPred().nazivPredProperty());

			actionC.setCellValueFactory(
					cellData -> new ReadOnlyObjectWrapper<Button>(cellData.getValue().getActionButton()));
			actionC.setCellFactory(param -> new TableCell<ZahtjevZaSlusanjePredmeta, Button>() {
				@Override
				protected void updateItem(Button item, boolean empty) {
					super.updateItem(item, empty);
					if (empty || item == null) {
						setGraphic(null);
					} else {
						setGraphic(item);
						item.setOnAction(event -> {
							int rowIndex = getIndex();
							ZahtjevZaSlusanjePredmeta selectedZahtjev = getTableView().getItems().get(rowIndex);
							try {
								openOdgovoriDialog(selectedZahtjev);
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
		zahtjeviTable.setRowFactory(tv -> {
			TableRow<ZahtjevZaSlusanjePredmeta> myRow = new TableRow<>();
			myRow.setOnMouseClicked(event -> {
				if (event.getClickCount() == 1 && (!myRow.isEmpty())) {
					int myIndex = zahtjeviTable.getSelectionModel().getSelectedIndex();
					studIndex = String.valueOf(zahtjeviTable.getItems().get(myIndex).getStud().getId());
					predIndex = String.valueOf(zahtjeviTable.getItems().get(myIndex).getPred().getSifraPred());
					TablePreduslovi();
					TablePredmeti();
				}
			});
			return myRow;
		});
	}
	public void openOdgovoriDialog(ZahtjevZaSlusanjePredmeta z) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("nastavnik_odgovoriPredmet.fxml"));
		DialogPane odgovoriDialog = loader.load();
		nastavnik_odgovoriPredmetController odgovoriC = loader.getController();
		odgovoriC.setData(z);
		Dialog<ButtonType> dialog = new Dialog<>();
		dialog.setDialogPane(odgovoriDialog);
		dialog.setTitle("Zahtjev za slusanje predmeta");

		Optional<ButtonType> clickedButton = dialog.showAndWait();

		if (clickedButton.get() == ButtonType.OK) {
			odgovoriC.updateData();
			System.out.println("Updatated");
			TableZahtjevi();
		}
	}

	public void TablePreduslovi() {
		mysql.Connect();

		ObservableList<Preduslov> preduslovi = FXCollections.observableArrayList();
		try {
			mysql.pst = mysql.con.prepareStatement(
					"SELECT DISTINCT p.nazivPred, s.ocjena, s.obnova\n"
					+ "FROM predmet p\n"
					+ "INNER JOIN preduslov pr ON pr.sifPreduslov = p.sifPred\n"
					+ "LEFT JOIN slusaPred s ON s.sifPred = pr.sifPreduslov AND s.idStud = ?"
					+ "WHERE pr.sifPred = ?;");
			mysql.pst.setString(1, studIndex);
			mysql.pst.setString(2, predIndex);
			ResultSet rs = mysql.pst.executeQuery();
			{
				while (rs.next()) {
					Preduslov p = new Preduslov();
					p.getPredmet().setNazivPred((rs.getString("nazivPred")));
					p.setOcjena(rs.getString("ocjena"));
					p.setObnova(rs.getString("obnova"));

					preduslovi.add(p);
				}

			}

			predusloviTable.setItems(preduslovi);

			nazivPreduslovaC.setCellValueFactory(f -> f.getValue().getPredmet().nazivPredProperty());
			odslusanoC.setCellValueFactory(f -> f.getValue().obnovaProperty());
			ocjenaC.setCellValueFactory(f -> f.getValue().ocjenaProperty());

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void TablePredmeti() {
		mysql.Connect();

		ObservableList<Predmet> predmeti = FXCollections.observableArrayList();
		try {
			mysql.pst = mysql.con.prepareStatement(
					"select nazivPred from predmet \n" + "inner join slusaPred on predmet.sifPred = slusaPred.sifPred\n"
							+ "where ocjena >= 6 and idStud = ?;");
			mysql.pst.setString(1, studIndex);
			ResultSet rs = mysql.pst.executeQuery();
			{
				while (rs.next()) {
					Predmet p = new Predmet();
					p.setNazivPred(rs.getString("nazivPred"));

					predmeti.add(p);
				}

			}

			polozeniPT.setItems(predmeti);

			nazivPolozenogC.setCellValueFactory(f -> f.getValue().nazivPredProperty());

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	private void setupSearch() {
		FilteredList<ZahtjevZaSlusanjePredmeta> filteredData = new FilteredList<>(zahtjeviTable.getItems(), p -> true);

		search_tf.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(zahtjev -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = search_tf.getText().toLowerCase();

				if (zahtjev.getPred().getNazivPred().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if (zahtjev.getStud().getIme().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if (zahtjev.getStud().getPrezime().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				}

				return false;
			});
		});

		SortedList<ZahtjevZaSlusanjePredmeta> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(zahtjeviTable.comparatorProperty());

		zahtjeviTable.setItems(sortedData);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setCurrentNastavnik();
		mysql.Connect();
		TableZahtjevi();
		setupSearch();
	}
}
