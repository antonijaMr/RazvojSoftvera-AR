package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import javafx.scene.layout.HBox;

import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;

import javafx.scene.layout.AnchorPane;

import javafx.scene.control.PasswordField;

import javafx.scene.control.ChoiceBox;

import javafx.scene.layout.Pane;

public class administrator_dodajNastavnikaController implements Initializable {
	private MySQLConnection mysql = new MySQLConnection();
	private SceneLoader s = new SceneLoader();

	private String[] zvanja = { "vanredni profesor", "redovni profesor", "docent" };
	private String[] smjer = { "AR", "RI", "EEMS", "ESKE", "TK", "Ostalo" };

	
	@FXML
	private TextField ime_tf;
	@FXML
	private TextField prezime_tf;
	@FXML
	private Label email;
	@FXML
	private PasswordField lozinka;
	@FXML
	private ChoiceBox<String> zvanje;
	@FXML
	private ChoiceBox<String> odsjek;

	@FXML
	public void studenti(MouseEvent event) {
		s.loadAdminStudent(event);
	}

	@FXML
	public void nastavnici(MouseEvent event) {
		s.loadAdminNastavnik(event);
	}

	@FXML
	public void predmeti(MouseEvent event) {
		s.loadAdminPredmet(event);
	}

	@FXML
	public void prodekan(MouseEvent event) {
		s.loadAdminProdekan(event);
	}

	@FXML
	public void logout(MouseEvent event) {
		s.logout(event);
	}

	@FXML
	public void pogledajNastavnike(ActionEvent event) {
		s.loadAdminNastavnici(event);
	}

	@FXML
	public void dodajNastavnika(ActionEvent event) {
		s.loadAdminNastavnik(event);
	}

	@FXML
	public void dodaj(ActionEvent event) {
		dodajUBazu();
	}

	public int provjeri() {
		if (!empty()) {
			try {
				mysql.pst = mysql.con.prepareStatement("select count(1) from nastavnik where ime = ? and prezime=?;");
				mysql.pst.setString(1, ime_tf.getText());
				mysql.pst.setString(2, prezime_tf.getText());
				ResultSet rs = mysql.pst.executeQuery();
				if (rs.next()) {
					return rs.getInt(1);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 999;
	}

	private void dodajUBazu() {
		try {
			mysql.pst = mysql.con
					.prepareStatement("insert into nastavnik(ime,prezime,lozinka,email,zvanje,odsjek,prodekan)"
							+ " values (?,?,?,?,?,?,false);");
			mysql.pst.setString(1, ime_tf.getText());
			mysql.pst.setString(2, prezime_tf.getText());
			mysql.pst.setString(3, lozinka.getText());
			mysql.pst.setString(4, email.getText());
			mysql.pst.setString(5, zvanje.getValue());
			mysql.pst.setString(6, odsjek.getValue());

			int rowsAffected = mysql.pst.executeUpdate();
			if (rowsAffected > 0) {
				s.alert("Nastavnik uspjesno dodan!");
				refresh();
			} else {
				s.alertEror("Doslo je do greske.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private boolean empty() {
		return ime_tf.getText().isEmpty() || prezime_tf.getText().isEmpty() || lozinka.getText().isEmpty()
				|| zvanje.getValue() == null || odsjek.getValue() == null;
	}

	private void setData() {
		zvanje.getItems().addAll(zvanja);
		odsjek.getItems().addAll(smjer);
	}

	private void refresh() {
		ime_tf.setText("");
		prezime_tf.setText("");
		lozinka.setText("");
		email.setText("ime.prezime@fet.ba");
		zvanje.getSelectionModel().clearSelection();
		odsjek.getSelectionModel().clearSelection();
	}

	private class TextChangeListener implements ChangeListener<String> {
		@Override
		public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
			changeEmail();
		}
	}

	private void changeEmail() {
		int b = provjeri();
		if (b != 999) {
			if (b == 0)
				email.setText(ime_tf.getText().toLowerCase() + "." + prezime_tf.getText().toLowerCase() + "@fet.ba");
			else
				email.setText(
						ime_tf.getText().toLowerCase() + "." + prezime_tf.getText().toLowerCase() + b + "@fet.ba");
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		mysql.Connect();
		setData();

		ime_tf.textProperty().addListener(new TextChangeListener());
		prezime_tf.textProperty().addListener(new TextChangeListener());

	}
}
