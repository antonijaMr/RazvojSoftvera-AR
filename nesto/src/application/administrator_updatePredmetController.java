package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Predmet;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;

import javafx.scene.control.PasswordField;

import javafx.scene.control.ChoiceBox;

public class administrator_updatePredmetController implements Initializable {
	private MySQLConnection mysql = new MySQLConnection();
	private SceneLoader s = new SceneLoader();

	private Predmet pred;
	private String[] semestar = { "Ljetni", "Zimski" };

	@FXML
	private TextField sifra;
	@FXML
	private TextField kratica;
	@FXML
	private TextField naziv;
	@FXML
	private TextField naucnaOblast;
	@FXML
	private TextField p;
	@FXML
	private PasswordField a;
	@FXML
	private PasswordField l;
	@FXML
	private TextField ects;
	@FXML
	private ChoiceBox<String> semestarChoice;
	@FXML
	private Button cancelButton;

	@FXML
	public void delete(ActionEvent event) {
		try {
			mysql.pst = mysql.con.prepareStatement("delete from predmet where sifPred= ?");
			mysql.pst.setString(1, pred.getSifraPred());
			int rowsAffected = mysql.pst.executeUpdate();
			if (rowsAffected > 0) {
				s.alert("Predmet je izbrisan!");
			} else {
				s.alertEror("Doslo je do greske.");
			}
		} catch (SQLException e) {
			s.alertEror("Predmet se ne moze izbrisati");
			e.printStackTrace();
		}
	}

	@FXML
	public void update(ActionEvent event) {
		if (provjeri()) {
			try {
				mysql.pst = mysql.con.prepareStatement("update predmet set sifPred = ?, kratPred = ?, "
						+ "nazivPred =?, uzaNaucnaOblast = ?, satiPredavanja = ?,satiAV = ?, satiLV = ?, "
						+ "ECTS = ?,semestar = ? where sifPred = ?;");
				mysql.pst.setString(1, sifra.getText());
				mysql.pst.setString(2, kratica.getText());
				mysql.pst.setString(3, naziv.getText());
				mysql.pst.setString(4, naucnaOblast.getText());
				mysql.pst.setString(5, p.getText());
				mysql.pst.setString(6, a.getText());
				mysql.pst.setString(7, l.getText());
				mysql.pst.setString(8, ects.getText());
				mysql.pst.setString(9, pred.getSifraPred());
				mysql.pst.setString(10, pred.getSifraPred());
				int rowsAffected = mysql.pst.executeUpdate();
				if (rowsAffected > 0) {
					s.alert("Predmet je izbrisan!");
				} else {
					s.alertEror("Doslo je do greske.");
				}
			} catch (SQLException e) {
				s.alertEror("Predmet se ne moze izbrisati");
				e.printStackTrace();
			}
		}
	}

	private boolean provjeri() {
		return true;
	}

	@FXML
	public void cancel(ActionEvent event) {
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}

	public void setData(Predmet P) {
		pred = P;
		sifra.setText(P.getSifraPred());
		kratica.setText(P.getKratPred());
		naziv.setText(P.getNazivPred());
		naucnaOblast.setText(P.getUzaNaucnaOblast());
		p.setText(P.getPredavanja_sati());
		a.setText(P.getAv_sati());
		l.setText(P.getLab_sati());
		ects.setText(P.getECTS());
		semestarChoice.getItems().addAll(semestar);
		semestarChoice.setValue(P.getSemestar());
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		mysql.Connect();
	}
}
