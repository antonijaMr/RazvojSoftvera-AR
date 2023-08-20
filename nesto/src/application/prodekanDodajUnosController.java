package application;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;

public class prodekanDodajUnosController implements Initializable {
	private MySQLConnection mysql = new MySQLConnection();
	private SceneLoader s = new SceneLoader();

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
	private Button dodajUnosButton;
	@FXML
	private Button potvrdiUnosButton;
	@FXML
	private Button PregledPlanaButton;
	@FXML
	private Button btn_prijavljeniPredmeti;
	@FXML
	private Button btn_bitniDatumi;

	@FXML
	private ComboBox<String> nastavnik_choice;
	@FXML
	private ComboBox<String> predmet_choice;
	@FXML
	private ChoiceBox<String> nosioc_choice;

	String query = null;
	ResultSet res = null;

	private ObservableList<String> nastavnici = FXCollections.observableArrayList();
	private ObservableList<String> predmeti = FXCollections.observableArrayList();
	private String[] nosioc = { "Da", "Ne" };

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		mysql.Connect();
		ucitajNastavnike();
		ucitajPredmete();
		nosioc_choice.getItems().addAll(nosioc);

	}
	
	private boolean empty() {
		return nastavnik_choice.getValue() == null || predmet_choice.getValue() == null ||
				nosioc_choice.getValue() == null;
	}

	private void ucitajNastavnike() {
		try {
			String query = "SELECT ime,prezime,zvanje FROM nastavnik";
			PreparedStatement preparedStatement = mysql.con.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				String nastavnik = resultSet.getString("ime") + " " + resultSet.getString("prezime") + ", "
						+ resultSet.getString("zvanje");
				nastavnici.add(nastavnik);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		nastavnik_choice.getItems().addAll(nastavnici);

	}

	private void ucitajPredmete() {
		try {
			String query = "SELECT nazivPred FROM predmet";
			PreparedStatement preparedStatement = mysql.con.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				String predmet = resultSet.getString("nazivPred");
				predmeti.add(predmet);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		predmet_choice.getItems().addAll(predmeti);

	}

	@FXML
	private void potvrdiUnos(ActionEvent e) throws SQLException {

		if (!empty()) {
			String[] odabraniNastavnik = (String[]) nastavnik_choice.getSelectionModel().getSelectedItem().split(",");
			String odabraniPredmet = (String) predmet_choice.getSelectionModel().getSelectedItem();
			String odabraniNosioc = (String) nosioc_choice.getSelectionModel().getSelectedItem();
			String[] imePrezime = odabraniNastavnik[0].split(" ");
			query = "SELECT sifNast FROM nastavnik WHERE ime=? AND prezime=?";
			mysql.pst = mysql.con.prepareStatement(query);
			mysql.pst.setString(1, imePrezime[0]);
			mysql.pst.setString(2, imePrezime[1]);
			res = mysql.pst.executeQuery();
			int id = 0;

			if (res.next()) {
				id = res.getInt("sifNast");


			}
			query = "SELECT sifPred FROM predmet WHERE nazivPred=?";
			mysql.pst = mysql.con.prepareStatement(query);
			mysql.pst.setString(1, odabraniPredmet);
			res = mysql.pst.executeQuery();
			res.next();
			String sifraP = res.getString("sifPred");
			query = "SELECT COUNT(*) FROM predaje WHERE sifPred=?";
			mysql.pst = mysql.con.prepareStatement(query);
			mysql.pst.setString(1, sifraP);
			res = mysql.pst.executeQuery();
			res.next();
			if (res.getInt("COUNT(*)") == 1 && odabraniNosioc.equals("Da")) {
				s.alertEror("Predmet vec ima nosioca");
			} else {

				query = "INSERT INTO predaje (sifNastavnik, sifPred,godina,nosioc) VALUES ( ?,?,YEAR(NOW()),?)";
				mysql.pst = mysql.con.prepareStatement(query);
				mysql.pst.setInt(1, id);
				mysql.pst.setString(2, sifraP);

				// pst.setString(3, Year.now());
				boolean n = false;
				if (odabraniNosioc.equals("Da")) {
					n = true;
				}
				mysql.pst.setBoolean(3, n);
				int rowsAffected = mysql.pst.executeUpdate();

				if (rowsAffected > 0) {
					s.alert("Uspjesan update");
				} else {
					s.alertEror("Doslo je do greske");
				}
			}
		} else {
			s.alert("Ispunite sva polja!");
		}
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

	@FXML
	private void to_pregled_plana(ActionEvent e) {
		s.loadProdekan_to_pregled_plana(e);
	}

}
