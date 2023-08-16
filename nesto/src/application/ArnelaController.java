package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Nastavnik;

public class ArnelaController implements Initializable {

	DataSingleton ds;
	private Stage stage;
	private Scene scene;
	private Parent root;
//	private String username1;
	// private String password1;
	@FXML
	private TextField tf_username;
	@FXML
	private TextField tf_password;
	@FXML
	private Button b_login;
	@FXML
	private ChoiceBox<String> MyChoiceBox;


	private String selectedItem;

	@FXML
	private Label L1;
	@FXML
	private Label L2;
	@FXML
	private Label L3;
	@FXML
	private Label L4;

	Connection con;
	PreparedStatement pst;
	int myIndex;
	int id;
	ResultSet res = null;

	public void Connect() {

		try {
	
			con = DriverManager.getConnection("jdbc:mysql://localhost/projekat", "root", "2481632am*");
		}
		catch (SQLException exe) {
			exe.printStackTrace();
		}

	}

	private String[] uloge = { "nastavnik", "prodekan", "administrator", "student" };

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ds = DataSingleton.getInstance();
		MyChoiceBox.getItems().addAll(uloge);

		Connect();

		MyChoiceBox.setOnAction((event) -> {
			selectedItem = (String) MyChoiceBox.getSelectionModel().getSelectedItem();

			if (selectedItem.equals("student"))
				changeColor("#7CE48B");
			if (selectedItem.equals("administrator"))
				changeColor("red");
			if (selectedItem.equals("nastavnik"))
				changeColor("grey");
			if (selectedItem.equals("prodekan"))
				changeColor("#5b6ad0");

		});
	}

	private void changeColor(String string) {
		L1.setStyle("-fx-text-fill:" + string);
		L2.setStyle("-fx-text-fill:" + string);
		L3.setStyle("-fx-text-fill:" + string);
		L4.setStyle("-fx-text-fill:" + string);
		b_login.setStyle("-fx-text-fill:" + string);

	}

	@FXML
	private void login(ActionEvent e) throws IOException {
		String nextScene = selectedItem + ".fxml";
		String pass = tf_password.getText();
		String user = tf_username.getText();
		String query = null;
		try {

			if (selectedItem.equals("nastavnik"))
				query = "SELECT lozinka FROM nastavnik WHERE email=? ";
			else if (selectedItem.equals("student"))
				query = "SELECT lozinka FROM student WHERE email=? ";
			else if (selectedItem.equals("prodekan"))
				query = "SELECT lozinka FROM nastavnik WHERE email=? AND prodekan=1";
			else if (selectedItem.equals("administrator"))
				query = "SELECT lozinka FROM administrator WHERE email=?";

			pst = con.prepareStatement(query);
			pst.setString(1, user);
			res = pst.executeQuery();
			if (res.next()) {
				do {
					String retrievedPassword = res.getString("lozinka");

					if (retrievedPassword.equals(pass)) {
						setNastavnik();
						root = FXMLLoader.load(getClass().getResource(nextScene));
						stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
						scene = new Scene(root);
						stage.setScene(scene);

					} else {
						Alert a = new Alert(AlertType.ERROR);
						a.setContentText("Wrong password");
						a.show();
					}
				} while (res.next());
			} else {
				Alert a = new Alert(AlertType.ERROR);
				a.setContentText("User not found");
				a.show();
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}

	}

	private void setNastavnik() {
		if (selectedItem.equals("nastavnik")) {
			try {
				Nastavnik n = new Nastavnik();
				String query = "SELECT * from nastavnik where email=?";
				pst = con.prepareStatement(query);
				pst.setString(1, tf_username.getText());
				res = pst.executeQuery();
				if (res.next()) {
					n.setSifNast(res.getString("sifNast"));
					n.setIme(res.getString("ime"));
					n.setPrezime(res.getString("prezime"));
					n.setEmail(res.getString("email"));
					n.setZvanje(res.getString("zvanje"));
					n.setOdsjek(res.getString("odsjek"));
					n.setProdekan(res.getString("prodekan"));
					ds.setNastavnik(n);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
