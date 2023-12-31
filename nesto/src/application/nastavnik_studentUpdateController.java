package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Predmet;
import models.Student;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;

import javafx.scene.control.Label;

public class nastavnik_studentUpdateController implements Initializable {
	private MySQLConnection mysql = new MySQLConnection();
	private SceneLoader s = new SceneLoader();
	int staraOcjena;

	private Predmet predmet;

	@FXML
	private Label studID;
	@FXML
	private Label ime;
	@FXML
	private Label prezime;
	@FXML
	private Label smjer;
	@FXML
	private Label ocjena;
	@FXML
	private Label nazivPred;
	@FXML
	private TextField tf_bodovi;

	public void setData(Student s) {
		studID.setText(s.getId());
		ime.setText(s.getIme());
		prezime.setText(s.getPrezime());
		smjer.setText(s.getSifUsmjerenja());
		tf_bodovi.setText(s.getSlusaPred().getBodovi());
		ocjena.setText(s.getSlusaPred().getOcjena().equals("NP") ? "NP" : s.getSlusaPred().getOcjena());
		System.out.println("sifra: " + predmet.getSifraPred());
		nazivPred.setText(predmet.getNazivPred());
	}

	public void update() {
		try {
			mysql.pst = mysql.con.prepareStatement("select ocjena from slusaPred where sifPred = ? and idStud = ?");
			mysql.pst.setString(1, predmet.getSifraPred());
			mysql.pst.setString(2, studID.getText());
			ResultSet rs = mysql.pst.executeQuery();
			rs.next();
			staraOcjena = rs.getInt("ocjena");

			System.out.println("sifra2: " + predmet.getSifraPred());
			String query = "update slusaPred set bodovi = ? where idStud = ? and sifPred =?;";
			mysql.pst = mysql.con.prepareStatement(query);
			mysql.pst.setDouble(1, tf_bodovi.getText().isEmpty() ? 0 : Double.parseDouble(tf_bodovi.getText()));
			mysql.pst.setString(2, studID.getText());
			mysql.pst.setString(3, predmet.getSifraPred());
			mysql.pst.executeUpdate();

			setECTS();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void setECTS() {
		if (staraOcjena <= 5) {

			System.out.println("ECTS: " + predmet.getECTS());
			String query = "update student set ostvareniECTS = ostvareniECTS + ? where brojIndeksa= ?;";
			try {
				mysql.pst = mysql.con.prepareStatement(query);
				mysql.pst.setInt(1, Integer.parseInt(predmet.getECTS()));
				mysql.pst.setString(2, studID.getText());
				mysql.pst.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

	}

	private void close(ActionEvent event) {
		Stage stage = (Stage) ocjena.getScene().getWindow();
		stage.close();
	}

	@FXML
	public void dodaj(ActionEvent e) {
		if (Double.parseDouble(tf_bodovi.getText()) < 1000) {
			update();
			s.alert("Bodovi promijenjeni");
			close(e);
		} else {
			s.alertEror("Pogresna vrijednost.");
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		mysql.Connect();
		predmet = DataSingleton.getInstance().getPredmet();

	}
}
