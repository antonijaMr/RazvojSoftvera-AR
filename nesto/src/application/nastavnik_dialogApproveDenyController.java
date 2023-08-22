package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Nastavnik;
import models.ZahtjevZaPrenosBodova;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;

import javafx.scene.control.Label;

public class nastavnik_dialogApproveDenyController implements Initializable {
	private MySQLConnection mysql = new MySQLConnection();
	private SceneLoader s = new SceneLoader();

	private ZahtjevZaPrenosBodova zahtjev;
	private Nastavnik nastavnik;

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
	private Label bodovi;
	@FXML
	private TextField tf_poruka;


	@FXML
	public void ponisti(ActionEvent event) {
		update(false);
		s.alert("Zahtjev odbijen.");
		close(event);
	}

	@FXML
	public void odobri(ActionEvent event) {
		update(true);
		s.alert("Zahtjev odobren");
		close(event);
	}
	
	private void close(ActionEvent event) {
		Stage stage = (Stage) smjer.getScene().getWindow();
		stage.close();
	}

	public void setData(ZahtjevZaPrenosBodova z) {
		zahtjev = z;
		ime.setText(zahtjev.stud.getIme());
		prezime.setText(zahtjev.stud.getPrezime());
		studID.setText(zahtjev.stud.getId());
		smjer.setText(zahtjev.stud.getSifUsmjerenja());
		predmet.setText(zahtjev.pred.getNazivPred());
		bodovi.setText(zahtjev.getBrojBodova());
	}

	public void update(boolean b) {
		try {
			String query = "UPDATE zahtjevZaPrenos SET odobreno=? WHERE idStud=? AND sifNast=? AND sifPred=?";
			mysql.pst = mysql.con.prepareStatement(query);
			mysql.pst.setInt(1, b?1:0);
			mysql.pst.setString(2, zahtjev.stud.getId());
			mysql.pst.setString(3, nastavnik.getSifNast());
			mysql.pst.setString(4, zahtjev.pred.getSifraPred());

			int rows=mysql.pst.executeUpdate();
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
