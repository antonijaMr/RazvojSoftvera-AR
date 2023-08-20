package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Nastavnik;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;

import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

public class administrator_updateNastavnikController implements Initializable {
	private MySQLConnection mysql = new MySQLConnection();
	private SceneLoader s = new SceneLoader();
	private Nastavnik nast;

	@FXML
	private Button cancelButton;
	@FXML
	private TextField ime_tf;
	@FXML
	private TextField prezime_tf;
	@FXML
	private TextField email;
	@FXML
	private PasswordField lozinka;
	@FXML
	private ChoiceBox<String> zvanjeChoice;
	@FXML
	private ChoiceBox<String> smjerChoice;
	private String[] zvanja = { "vanredni profesor", "redovni profesor", "docent" };
	private String[] smjer = { "AR", "RI", "EEMS", "ESKE", "TK", "Ostalo" };

	@FXML
	public void delete(ActionEvent event) {
		try {
			mysql.pst = mysql.con.prepareStatement("delete from nastavnik where sifNast = ?");
			mysql.pst.setString(1, nast.getSifNast());
			int rowsAffected = mysql.pst.executeUpdate();
			if (rowsAffected > 0) {
				s.alert("Nastavnik je izbrisan!");
			} else {
				s.alertEror("Doslo je do greske.");
			}
		} catch (SQLException e) {
			s.alertEror("Nastavnik se ne moze izbrisati");
			e.printStackTrace();
		}

	}

	@FXML
	public void update(ActionEvent event) {
		try {
			mysql.pst = mysql.con.prepareStatement("update nastavnik where sifNast = ?");
			mysql.pst.setString(1, nast.getSifNast());
			int rowsAffected = mysql.pst.executeUpdate();
			if (rowsAffected > 0) {
				s.alert("Nastavnik je promijenjen!");
			} else {
				s.alertEror("Doslo je do greske.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@FXML
	public void cancel(ActionEvent event) {
		Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
	}

	public void setData(Nastavnik n) {
		nast = n;
		ime_tf.setText(n.getIme());
		prezime_tf.setText(n.getPrezime());
		email.setText(n.getEmail());
		zvanjeChoice.getItems().addAll(zvanja);
		smjerChoice.getItems().addAll(smjer);
		zvanjeChoice.setValue(n.getZvanje());
		smjerChoice.setValue(n.getOdsjek());
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		System.out.println("hi");
		mysql.Connect();

	}
}
