package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;

import javafx.scene.layout.AnchorPane;

import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
	private TableView<ZahtjevZaSlusanjePredmeta> zahtjeviTable;
	@FXML
	private TableColumn<ZahtjevZaSlusanjePredmeta, String> indexC;
	@FXML
	private TableColumn<ZahtjevZaSlusanjePredmeta, String> imeC;
	@FXML
	private TableColumn<ZahtjevZaSlusanjePredmeta, String> prezimeC;
	@FXML
	private TableColumn<ZahtjevZaSlusanjePredmeta, String> predmetC;
//	@FXML
//	private TableColumn<ZahtjevZaSlusanje, String> actionC;
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
	private TableColumn<Predmet,String> nazivPolozenogC;

	public void setCurrentNastavnik() {
		currentNastavnik = DataSingleton.getInstance().getNastavnik();
		nastIme.setText(currentNastavnik.getIme() + " " + currentNastavnik.getPrezime());
	}

	@FXML
	public void predmeti(ActionEvent e) {
		s.loadPredmeti(e);
	}

	@FXML
	public void to_zahtjevi(ActionEvent e) {
		s.loadZahtjevi(e);
	}

	@FXML
	public void to_predZahtjevi(ActionEvent e) {
		s.loadPredZahtjevi(e);
	}

	@FXML
	public void logout(ActionEvent e) {
		s.logout(e);
	}

	public void TableZahtjevi() {
		mysql.Connect();

		ObservableList<ZahtjevZaSlusanjePredmeta> zahtjevi = FXCollections.observableArrayList();
		try {
			mysql.pst = mysql.con.prepareStatement(
					"select student_id,ime,prezime,email,godStudija,statusStud,imeUsmjerenja,ostvareniECTS,nazivPred,predmet.sifPred from student \n"
							+ "inner join zahtjevZaSlusanje on zahtjevZaSlusanje.idStud = student.student_id\n"
							+ "inner join usmjerenje on usmjerenje.sifUsmjerenja = student.sifUsmjerenja\n"
							+ "inner join predmet on predmet.sifPred = zahtjevZaSlusanje.sifPred\n"
							+ "where sifNast = ?;");
			mysql.pst.setInt(1, currentNastavnik.getSifNast());
			ResultSet rs = mysql.pst.executeQuery();
			{
				while (rs.next()) {
					ZahtjevZaSlusanjePredmeta z = new ZahtjevZaSlusanjePredmeta();
					z.getStudent().setId(rs.getInt("student_id"));
					z.getStudent().setIme(rs.getString("ime"));
					z.getStudent().setPrezime(rs.getString("prezime"));
					z.getStudent().setEmail(rs.getString("email"));
					z.getStudent().setGodinaStudija(rs.getString("godStudija"));
					z.getStudent().setStatus(rs.getString("statusStud"));
					z.getStudent().setSifUsmjerena(rs.getString("imeUsmjerenja"));
					z.getStudent().setOstvareniECTS(rs.getString("ostvareniECTS"));
					z.getPredmet().setNazivPred(rs.getString("nazivPred"));
					z.getPredmet().setSifraPred(rs.getString("sifPred"));

					zahtjevi.add(z);
				}

			}

			zahtjeviTable.setItems(zahtjevi);

			indexC.setCellValueFactory(new PropertyValueFactory<>("student_id"));
			imeC.setCellValueFactory(new PropertyValueFactory<>("ime"));
			prezimeC.setCellValueFactory(new PropertyValueFactory<>("prezime"));
			predmetC.setCellValueFactory(new PropertyValueFactory<>("nazivPred"));

		} catch (SQLException e) {
			e.printStackTrace();
		}
//		 ON ClICKED
		zahtjeviTable.setRowFactory(tv -> {
			TableRow<ZahtjevZaSlusanjePredmeta> myRow = new TableRow<>();
			myRow.setOnMouseClicked(event -> {
				if (event.getClickCount() == 1 && (!myRow.isEmpty())) {
					int myIndex = zahtjeviTable.getSelectionModel().getSelectedIndex();
					studIndex = String.valueOf(zahtjeviTable.getItems().get(myIndex).getStudent().getId());
					predIndex = String.valueOf(zahtjeviTable.getItems().get(myIndex).getPredmet().getSifraPred());
					TablePreduslovi();
					TablePredmeti();
				}
			});
			return myRow;
		});
	}

	public void TablePreduslovi() {
		mysql.Connect();

		ObservableList<Preduslov> preduslovi = FXCollections.observableArrayList();
		try {
			mysql.pst = mysql.con.prepareStatement(
					"select nazivPred,ocjena,obnova from predmet inner join preduslov on preduslov.sifPreduslov = predmet.sifPred\n"
							+ "inner join slusaPred on slusaPred.sifPred = preduslov.sifPreduslov\n"
							+ "where preduslov.sifPred = ? and idStud = ?;");
			mysql.pst.setString(1, predIndex);
			mysql.pst.setString(2, studIndex);
			ResultSet rs = mysql.pst.executeQuery();
			{
				while (rs.next()) {
					Preduslov p = new Preduslov();
					p.getPredmet().setNazivPred((rs.getString("nazivPred")));
					p.setOcjena(rs.getInt("ocjena"));
					p.setObnova(rs.getBoolean("obnova"));

					preduslovi.add(p);
				}

			}

			predusloviTable.setItems(preduslovi);

			nazivPreduslovaC.setCellValueFactory(new PropertyValueFactory<>("nazivPred"));
			odslusanoC.setCellValueFactory(new PropertyValueFactory<>("ocjena"));
			ocjenaC.setCellValueFactory(new PropertyValueFactory<>("obnova"));

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void TablePredmeti() {
		mysql.Connect();

		ObservableList<Predmet> predmeti= FXCollections.observableArrayList();
		try {
			mysql.pst = mysql.con.prepareStatement(
					"select nazivPred from predmet \n"
					+ "inner join slusaPred on predmet.sifPred = slusaPred.sifPred\n"
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

			nazivPolozenogC.setCellValueFactory(new PropertyValueFactory<>("nazivPred"));

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setCurrentNastavnik();
		mysql.Connect();
		TableZahtjevi();
	}
}
