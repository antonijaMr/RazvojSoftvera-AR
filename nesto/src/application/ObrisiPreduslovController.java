package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

public class ObrisiPreduslovController implements Initializable {
	private MySQLConnection mysql = new MySQLConnection();

	@FXML
	ChoiceBox<String> ChoiceBox;
	private ObservableList<String> prerequisitesList = FXCollections.observableArrayList();

	String sifraPr;
	Stage stage;

	public void setSifraPr(String p) {
		sifraPr = p;
		loadPreduslovi();
	}

	public void setStage(Stage s) {
		stage = s;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		mysql.Connect();

	}

	private void loadPreduslovi() {

		System.out.println(sifraPr);
		try {
			String query = "SELECT sifPreduslov FROM preduslov WHERE sifPred = ?";
			PreparedStatement preparedStatement = mysql.con.prepareStatement(query);
			preparedStatement.setString(1, sifraPr);

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				String sifraPreduslov = resultSet.getString("sifPreduslov");
				prerequisitesList.add(sifraPreduslov);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		ChoiceBox.getItems().addAll(prerequisitesList);

	}

	@FXML
	private void deletePrerequisite() {
		String selectedPrerequisite = ChoiceBox.getValue();

		try {

			String query = "DELETE FROM preduslov WHERE sifPred = ? AND sifPreduslov = ?";
			PreparedStatement preparedStatement = mysql.con.prepareStatement(query);
			preparedStatement.setString(1, sifraPr);
			preparedStatement.setString(2, selectedPrerequisite);

			int rowsAffected = preparedStatement.executeUpdate();

			if (rowsAffected > 0) {
				showAlert("Preduslov obrisan uspjesno");
			} else {
				showAlert("Brisanje preduslova nije uspjesno");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		stage.close();

	}

	private void showAlert(String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

}
