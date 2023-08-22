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


import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

public class administrator_updatePredmetController implements Initializable {
	private MySQLConnection mysql = new MySQLConnection();
	private SceneLoader s = new SceneLoader();

	private Predmet pred;
	private String[] semestar = { "Ljetni", "Zimski" };

	@FXML
	private Label sifra;
	@FXML
	private TextField kratica;
	@FXML
	private TextField naziv;
	@FXML
	private TextField naucnaOblast;
	@FXML
	private TextField p;
	@FXML
	private TextField a;
	@FXML
	private TextField l;
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
				cancel(event);
			} else {
				s.alertEror("Doslo je do greske.");
			}
		} catch (SQLException e) {
			s.alertEror("Predmet se ne moze izbrisati");
		}
	}

	@FXML
	public void update(ActionEvent event) {
		if (!empty()) {
			try {
				mysql.pst = mysql.con.prepareStatement("update predmet set kratPred = ?, "
						+ "nazivPred =?, uzaNaucnaOblast = ?, satiPredavanja = ?,satiAV = ?, satiLV = ?, "
						+ "ECTS = ?,semestar = ? where sifPred = ?;");
				mysql.pst.setString(1, kratica.getText());
				mysql.pst.setString(2, naziv.getText());
				mysql.pst.setString(3, naucnaOblast.getText());
				mysql.pst.setString(4, p.getText());
				mysql.pst.setString(5, a.getText());
				mysql.pst.setString(6, l.getText());
				mysql.pst.setString(7, ects.getText());
				mysql.pst.setString(8, semestarChoice.getValue());
				mysql.pst.setString(9, pred.getSifraPred());
				int rowsAffected = mysql.pst.executeUpdate();
				if (rowsAffected > 0) {
					s.alert("Predmet je promijenjen!");
					cancel(event);
				} else {
					s.alertEror("Doslo je do greske.");
				}
			} catch (SQLException e) {
				s.alertEror("Doslo je do greske.");
				e.printStackTrace();
				cancel(event);
			}
		} else {
			s.alert("Popunite sva polja");
		}
	}

	private boolean empty() {
		return kratica.getText().isEmpty() || naziv.getText().isEmpty() || naucnaOblast.getText().isEmpty()
				|| p.getText().isEmpty() || a.getText().isEmpty() || l.getText().isEmpty() || ects.getText().isEmpty()
				|| semestarChoice.getValue() == null;
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
