package application;

import javafx.scene.control.TextField;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import models.Predmet;

public class UpdateZvanjeController implements Initializable {
	private MySQLConnection mysql = new MySQLConnection();

	@FXML
	private Label sadasnje_zvanje;
	@FXML
	ChoiceBox<String> zvanja;
	@FXML
	private Button updateButton;

	private String nastavnik_email;
	String query = null;
	ResultSet res = null;
	Predmet predmet = null;

	public void setNastavnikIdentifier(String identifier) throws SQLException {
		nastavnik_email = identifier;
		query = "SELECT zvanje FROM nastavnik WHERE email=?";
		mysql.pst = mysql.con.prepareStatement(query);
		mysql.pst.setString(1, nastavnik_email);
		res = mysql.pst.executeQuery();
		if (res.next())
			sadasnje_zvanje.setText(res.getString("zvanje"));
	}

	private String[] zv = { "vanredni profesor", "redovni profesor", "docent" };

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		zvanja.getItems().addAll(zv);
		mysql.Connect();

	}

	@FXML
	private void updateZvanjeButtonClicked() {
		String newZvanje = zvanja.getValue();
		// Perform the update operation here using the nastavnikIdentifier and newZvanje
		// values
		System.out.println("Nastavnik Identifier: " + nastavnik_email);

		// Call a method to update the zvanje for the specific nastavnik in the database
		boolean success = updateZvanjeInDatabase(nastavnik_email, newZvanje);

		if (success) {
			showAlert("Zvanje updated successfully!");
			closeScene();
		} else {
			showAlert("Failed to update zvanje.");
		}
	}

	private void showAlert(String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	private boolean updateZvanjeInDatabase(String identifier, String newZvanje) {
		try {

			query = "UPDATE nastavnik SET zvanje = ? WHERE email = ?"; // Assuming email is the identifier
			PreparedStatement preparedStatement = mysql.con.prepareStatement(query);
			preparedStatement.setString(1, newZvanje);
			preparedStatement.setString(2, identifier);

			int rowsAffected = preparedStatement.executeUpdate();

			mysql.con.close();

			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	private void closeScene() {
		// Close the current scene (dialog)
		Stage stage = (Stage) updateButton.getScene().getWindow();
		stage.close();
	}

}
