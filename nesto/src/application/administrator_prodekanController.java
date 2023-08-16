package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import javafx.scene.layout.HBox;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;

import javafx.scene.control.Label;

import javafx.scene.layout.AnchorPane;

import javafx.scene.control.ChoiceBox;

import javafx.scene.layout.Pane;
import models.Nastavnik;

public class administrator_prodekanController implements Initializable {
	MySQLConnection mysql = new MySQLConnection();
	private SceneLoader s = new SceneLoader();

	private int trenutniID;

	@FXML
	private HBox root;
	@FXML
	private AnchorPane side_anchorpane;
	@FXML
	private Pane inner_pane;
	@FXML
	private Button btn_logout;
	@FXML
	private Label trenutni;
	@FXML
	private ChoiceBox<Nastavnik> prodekanChoice;

	@FXML
	public void studenti(ActionEvent event) {
		s.loadAdminStudent(event);
	}

	@FXML
	public void nastavnici(ActionEvent event) {
		s.loadAdminNastavnik(event);
	}

	@FXML
	public void predmeti(ActionEvent event) {
		s.loadAdminPredmet(event);
	}

	@FXML
	public void prodekan(ActionEvent event) {
		s.loadAdminProdekan(event);
	}

	@FXML
	public void logout(ActionEvent event) {
		s.logout(event);
	}

	@FXML
	public void dodaj(ActionEvent event) {
		if (provjeri()) {
			updateOld();
			addNew();
			s.alert("Prodekan je promjenjen!");
			refresh();
		}else {
			s.alert("Ispunite sva polja.");
		}
	}
	
	private boolean provjeri() {
		return prodekanChoice.getValue() != null;
	}

	private void updateOld() {
		try {
			String query = "update nastavnik set prodekan = false where sifNast = ?;";

			mysql.pst = mysql.con.prepareStatement(query);
			mysql.pst.setInt(1, trenutniID);

			if (mysql.pst.executeUpdate() != 1) {
				System.out.println("vise of jednog");
				s.alertEror("Doslo je do greske");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void addNew() {
		try {
			String query = "update nastavnik set prodekan = true where sifNast = ?;";

			mysql.pst = mysql.con.prepareStatement(query);
			mysql.pst.setInt(1, Integer.parseInt(prodekanChoice.getValue().getSifNast()));

			if (mysql.pst.executeUpdate() != 1) {
				System.out.println("add new");
				s.alertEror("Doslo je do greske");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void refresh() {
		prodekanChoice.getSelectionModel().clearSelection();
		setData();
	}

	private void setData() {
		setTrenutni();
		mysql.Connect();

		ArrayList<Nastavnik> nlist = new ArrayList<>();
		try {
			mysql.pst = mysql.con.prepareStatement("Select * from nastavnik where prodekan = false;");
			ResultSet rs = mysql.pst.executeQuery();
			while (rs.next()) {
				Nastavnik n = new Nastavnik();
				n.setSifNast(rs.getString("sifNast"));
				n.setPrezime(rs.getString("prezime"));
				n.setIme(rs.getString("ime"));

				nlist.add(n);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		prodekanChoice.getItems().clear();
		prodekanChoice.getItems().addAll(nlist);
	}

	private void setTrenutni() {
		try {
			mysql.pst = mysql.con
					.prepareStatement("Select ime, prezime, sifNast from nastavnik where prodekan = true;");
			ResultSet rs = mysql.pst.executeQuery();
			if (rs.next()) {
				trenutni.setText(rs.getString("ime") + " " + rs.getString("prezime"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		mysql.Connect();
		setData();
	}
}
