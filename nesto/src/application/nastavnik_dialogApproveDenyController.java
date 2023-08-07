package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import models.Nastavnik;
import models.ZahtjevZaPrenosBodova;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;

import javafx.scene.control.Label;

public class nastavnik_dialogApproveDenyController implements Initializable {
	MySQLConnection mysql = new MySQLConnection();

	ZahtjevZaPrenosBodova zahtjev;
	Nastavnik nastavnik;
	boolean btnPressed;

	@FXML
	private Label ime;
	@FXML
	private Label predmet;
	@FXML
	private Label bodovi;
	@FXML
	private TextField tf_poruka;

	// Event Listener on Button.onAction
	@FXML
	public void ponisti(ActionEvent event) {
		btnPressed = false;
	}

	// Event Listener on Button.onAction
	@FXML
	public void odobri(ActionEvent event) {
		System.out.println("odobri");
		btnPressed = true;
	}

	public void setData(ZahtjevZaPrenosBodova z) {
		zahtjev = z;
		ime.setText(zahtjev.stud.getIme() + " " + zahtjev.stud.getPrezime());
		predmet.setText(zahtjev.pred.getNazivPred());
		bodovi.setText(zahtjev.getBrojBodova());
	}

	public void updateData() {
		if (btnPressed) {
			update(true);
		} else {
			update(false);
		}
	}

	public void update(boolean b) {
	    try {
	        String query = "UPDATE zahtjevZaPrenos SET odobreno=?, poruka=? WHERE idStud=? AND sifNast=? AND sifPred=?";
	        mysql.pst = mysql.con.prepareStatement(query);

	        mysql.pst.setBoolean(1, b);
	        mysql.pst.setString(2, tf_poruka.getText());
	        mysql.pst.setString(3, zahtjev.stud.getId());
	        mysql.pst.setString(4, nastavnik.getSifNast());
	        mysql.pst.setString(5, zahtjev.pred.getSifraPred());

	        mysql.pst.executeUpdate();

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		mysql.Connect();
		nastavnik = DataSingleton.getInstance().getNastavnik();
		tf_poruka.setText("");

	}
}
