package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import models.Nastavnik;
import models.ZahtjevZaSlusanjePredmeta;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class nastavnik_odgovoriPredmetController implements Initializable {
	private MySQLConnection mysql = new MySQLConnection();
	private SceneLoader s = new SceneLoader();

	private ZahtjevZaSlusanjePredmeta zahtjev;
	private Nastavnik nastavnik;

	@FXML
	private TextField tf_odgovor;
	@FXML
	private Label ime;
	@FXML
	private Label prezime;
	@FXML
	private Label studID;
	@FXML
	private Label smjer;
	@FXML
	private Label predmet;
	@FXML
	private Label poruka;

	private Popup popup;

	private boolean btnPressed;

	@FXML
	public void ponisti(ActionEvent event) {
		update(false);
		s.alert("Zahtjev odbijen.");
		close(event);
	}

	@FXML
	public void odobri(ActionEvent event) {
		update(false);
		s.alert("Zahtjev odobren.");
//		try {
//			mysql.pst = mysql.con.prepareStatement("insert into slusaPred values(?,?,year(now()),false,");
//			mysql.pst.setString(1, nast.getSifNast());
//			int rowsAffected = mysql.pst.executeUpdate();
//			if (rowsAffected > 0) {
//				s.alert("Nastavnik je izbrisan!");
//			} else {
//				s.alertEror("Doslo je do greske.");
//			}
//		} catch (SQLException e) {
//			s.alertEror("Nastavnik se ne moze izbrisati");
//		}
		close(event);
	}

	private void close(ActionEvent event) {
		Stage stage = (Stage) smjer.getScene().getWindow();
		stage.close();
	}

	public void setData(ZahtjevZaSlusanjePredmeta z) {
		zahtjev = z;
		ime.setText(z.getStud().getIme() + " " + z.getStud().getPrezime());
		predmet.setText(z.getPred().getNazivPred());
		poruka.setText(z.getPoruka());
	}

	public void updateData() {
		if (btnPressed) {
			System.out.println("tuu sam");
			update(true);
		} else {
			update(false);
		}
	}

	public void update(boolean b) {
		try {
			String query = "update zahtjevZaSlusanje set odobreno = ?,odgovor=?"
					+ "where idStud = ? and sifNast = ? and sifPred = ?;";

			mysql.pst = mysql.con.prepareStatement(query);
			mysql.pst.setInt(1, b ? 1 : 0);
			mysql.pst.setString(2, tf_odgovor.getText());
			mysql.pst.setString(3, zahtjev.getStud().getId());
			mysql.pst.setString(4, nastavnik.getSifNast());
			mysql.pst.setString(5, zahtjev.getPred().getSifraPred());

			int rows = mysql.pst.executeUpdate();
			System.out.println(rows);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		mysql.Connect();
		nastavnik = DataSingleton.getInstance().getNastavnik();
	}
}
