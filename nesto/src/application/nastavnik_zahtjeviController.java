package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.layout.AnchorPane;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import models.Nastavnik;
import models.ZahtjevZaPrenosBodova;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class nastavnik_zahtjeviController implements Initializable {
	MySQLConnection mysql = new MySQLConnection();

	private Nastavnik currentNastavnik;
	private SceneLoader s= new SceneLoader();

	@FXML
	private Label nastIme;

	@FXML
	private AnchorPane side_anchorpane;
	@FXML
	private Pane inner_pane;
	@FXML
	private Button btn_predmeti;
	@FXML
	private Button btn_zahtjevi;
	@FXML
	private Button btn_logout;

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

	// Event Listener on Button[#btn_predmeti].onAction
	@FXML
	public void predmeti(ActionEvent e) throws IOException {
		s.loadPredmeti(e);
	}

	@FXML
	public void to_zahtjevi(ActionEvent e) throws IOException {
		s.loadZahtjevi(e);
	}

	@FXML
	public void to_predZahtjevi(ActionEvent e) throws IOException {
		s.loadPredZahtjevi(e);
	}

	@FXML
	public void logout(ActionEvent e) throws IOException {
		s.logout(e);
	}
	public void TableZahtjevi() {
		mysql.Connect();

		ObservableList<ZahtjevZaPrenosBodova> zahtjevi = FXCollections.observableArrayList();
		try {

			mysql.pst = mysql.con.prepareStatement(
					"select student_id,student.ime,student.prezime,predmet.nazivPred,brojBodova,predmet.sifPred from zahtjevZaPrenos "
							+ "inner join student on student.student_id = idStud "
							+ "inner join predmet on predmet.sifPred = zahtjevZaPrenos.sifPred "
							+ "where odobreno is null and sifNast =? ;");
			mysql.pst.setInt(1, currentNastavnik.getSifNast());
			ResultSet rs = mysql.pst.executeQuery();
			{
				while (rs.next()) {
					ZahtjevZaPrenosBodova z = new ZahtjevZaPrenosBodova();
					z.getStudent().setId(rs.getInt("student_id"));
					z.getStudent().setIme(rs.getString("ime"));
					z.getStudent().setPrezime(rs.getString("prezime"));
					z.getPredmet().setNazivPred(rs.getString("nazivPred"));
					z.getPredmet().setSifraPred(rs.getString("sifPred"));
					z.setBodovi(rs.getDouble("brojBodova"));
					zahtjevi.add(z);
				}
			}

			zahtjeviTable.setItems(zahtjevi);
			indexC.setCellValueFactory(new PropertyValueFactory<>("student_id"));
			imeC.setCellValueFactory(new PropertyValueFactory<>("ime"));
			prezimeC.setCellValueFactory(new PropertyValueFactory<>("prezime"));
			predmetC.setCellValueFactory(new PropertyValueFactory<>("nazivPred"));
			bodoviC.setCellValueFactory(new PropertyValueFactory<>("brojBodova"));

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
		loader.setLocation(getClass().getResource("dialogAproveDeny.fxml"));
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

	public void setCurrentNastavnik() {
		currentNastavnik = DataSingleton.getInstance().getNastavnik();
		nastIme.setText(currentNastavnik.getIme() + " " + currentNastavnik.getPrezime());
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

	}

}
