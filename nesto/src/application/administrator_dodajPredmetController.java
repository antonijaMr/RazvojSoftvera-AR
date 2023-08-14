package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;

import javafx.scene.layout.AnchorPane;

import javafx.scene.control.PasswordField;

import javafx.scene.layout.Pane;
import models.Nastavnik;
import models.Predmet;

public class administrator_dodajPredmetController implements Initializable {
	private MySQLConnection mysql = new MySQLConnection();
	private SceneLoader s = new SceneLoader();
	private String[] semestar = { "Ljetni", "Zimski" };
	private List<String> predmeti = new ArrayList<String>();
	private List<String> listaPreduslova = new ArrayList<String>();
	private List<Nastavnik> nastavnici = new ArrayList<Nastavnik>();
	
	private Nastavnik nosioc = new Nastavnik();
	private Nastavnik predavac = new Nastavnik();
	private String sifPred = new String();

	@FXML
	private HBox root;
	@FXML
	private AnchorPane side_anchorpane;
	@FXML
	private Pane inner_pane;
	@FXML
	private Button btn_logout;
	@FXML
	private TextField sifraPred_tf;
	@FXML
	private TextField kratica_tf;
	@FXML
	private TextField naziv_tf;
	@FXML
	private TextField naucnaOblast_tf;
	@FXML
	private TextField p_tf;
	@FXML
	private TextField av_tf;
	@FXML
	private TextField lv_tf;
	@FXML
	private TextField ects_tf;
	@FXML
	private Label preduslovi;
	@FXML
	private ChoiceBox<String> semestarChoice;
	@FXML
	private ChoiceBox<Nastavnik> nosiocChoice;
	@FXML
	private ChoiceBox<Nastavnik> predavacChoice;
	@FXML
	private ChoiceBox<String> preduslovChoice;

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
	public void pogledajPredmete(ActionEvent event) {
		s.loadAdminPredmeti(event);
	}

	@FXML
	public void dodajPredmet(ActionEvent event) {
		s.loadAdminPredmet(event);
	}

	@FXML
	public void dodaj(ActionEvent event) {
		nosioc = nosiocChoice.getValue();
		predavac = predavacChoice.getValue();
		sifPred = sifraPred_tf.getText();
		if (provjeri()) {
			dodajUBazu();
			dodajPredusloveUBazu();
			dodajNastavnike();
			refresh();
		} else {
			System.out.println("greska");
		}
	}

	@FXML
	public void obrisi(ActionEvent event) {
		refresh();
	}

	@FXML
	public void dodajPreduslov(ActionEvent event) {
		String selectedValue = preduslovChoice.getValue();
		listaPreduslova.add(selectedValue);
		predmeti.removeIf(s -> s.equals(selectedValue));
		preduslovChoice.getItems().clear();
		preduslovChoice.getItems().addAll(predmeti);

		preduslovChoice.getSelectionModel().clearSelection();
		preduslovi.setText("Preduslovi: " + listaPreduslova);
	}

	private boolean empty() {
		if (sifraPred_tf.getText().isEmpty() || kratica_tf.getText().isEmpty() || naziv_tf.getText().isEmpty()
				|| naucnaOblast_tf.getText().isEmpty() || p_tf.getText().isEmpty() || av_tf.getText().isEmpty()
				|| lv_tf.getText().isEmpty() || semestarChoice.getValue() == null || nosiocChoice.getValue() == null
				|| predavacChoice.getValue() == null) {
			return true;
		}
		return false;
	}

	private void dodajPredusloveUBazu() {
		try {
			for (String e : listaPreduslova) {
				mysql.pst = mysql.con.prepareStatement("insert into preduslov(sifPred,sifPreduslov) values (?,?);");
				mysql.pst.setString(1, sifPred);
				mysql.pst.setString(2, e);

				int rowsAffected = mysql.pst.executeUpdate();
				System.out.println("inserted:"+ e + "sifpred" + sifPred);
				if (rowsAffected != 1) {
					s.alertEror("Doslo je do greske");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		listaPreduslova.clear();
	}

	private void dodajNastavnike() {
		try {
			System.out.println("prije" + sifPred);
				mysql.pst = mysql.con.prepareStatement(
						"insert into predaje (sifPred,sifNastavnikNosioc,sifNastavnikPredaje)  values (?,?,?);");
				mysql.pst.setString(1, sifPred);
				mysql.pst.setString(2, nosioc.getSifNast());
				mysql.pst.setString(3, predavac.getSifNast());

				int rowsAffected = mysql.pst.executeUpdate();
				if (rowsAffected != 1) {
					s.alertEror("Doslo je do greske");
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private boolean provjeri() {
		if (!empty()) {
			try {
				mysql.pst = mysql.con.prepareStatement("select count(1) from predmet where sifPred= ?;");
				mysql.pst.setString(1, sifPred);
				ResultSet rs = mysql.pst.executeQuery();
				if (rs.next()) {
					if (rs.getInt(1) == 0)
						return true;
				}
				s.alert("Sifra vec postoji");
				return false;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			s.alertEror("Ispunite sva polja!");
		}
		return false;

	}

	private void dodajUBazu() {
		try {
			mysql.pst = mysql.con.prepareStatement(
					"insert into predmet (sifPred,kratPred,nazivPred,uzaNaucnaOblast,satiPredavanja,satiAV,satiLV,ECTS,semestar)"
							+ " values (?,?,?,?,?,?,?,?,?);");
			mysql.pst.setString(1, sifraPred_tf.getText());
			mysql.pst.setString(2, kratica_tf.getText());
			mysql.pst.setString(3, naziv_tf.getText());
			mysql.pst.setString(4, naucnaOblast_tf.getText());
			mysql.pst.setInt(5, !p_tf.getText().isEmpty() ? Integer.parseInt(p_tf.getText()) : 0);
			mysql.pst.setInt(6, !av_tf.getText().isEmpty() ? Integer.parseInt(av_tf.getText()) : 0);
			mysql.pst.setInt(7, !lv_tf.getText().isEmpty() ? Integer.parseInt(lv_tf.getText()) : 0);
			mysql.pst.setInt(8, !ects_tf.getText().isEmpty() ? Integer.parseInt(ects_tf.getText()) : 0);
			mysql.pst.setString(9, semestarChoice.getValue());

			int rowsAffected = mysql.pst.executeUpdate();
			if (rowsAffected > 0) {
				s.alert("Predmet je uspjesno dodan!");
			} else {
				s.alertEror("Doslo je do greske.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void setData() {
		dohvatiPredmete();
		dohvatiNastavnike();

		nosiocChoice.getItems().addAll(nastavnici);
		predavacChoice.getItems().addAll(nastavnici);
		semestarChoice.getItems().addAll(semestar);
		preduslovChoice.getItems().addAll(predmeti);
	}

	private void dohvatiNastavnike() {
		mysql.Connect();
		try {
			mysql.pst = mysql.con.prepareStatement("Select ime, prezime,nastavnik_id from nastavnik;");
			ResultSet rs = mysql.pst.executeQuery();

			while (rs.next()) {
				Nastavnik n = new Nastavnik();
				n.setIme(rs.getString("ime"));
				n.setPrezime(rs.getString("prezime"));
				n.setSifNast(rs.getString("nastavnik_id"));
				nastavnici.add(n);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void dohvatiPredmete() {
		mysql.Connect();
		try {
			mysql.pst = mysql.con.prepareStatement("Select sifPred from predmet;");
			ResultSet rs = mysql.pst.executeQuery();

			while (rs.next()) {
				predmeti.add(rs.getString("sifPred"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void refresh() {
		sifraPred_tf.setText("");
		kratica_tf.setText("");
		naziv_tf.setText("");
		naucnaOblast_tf.setText("");
		p_tf.setText("");
		av_tf.setText("");
		lv_tf.setText("");
		ects_tf.setText("");

		semestarChoice.getSelectionModel().clearSelection();
		preduslovChoice.getSelectionModel().clearSelection();
		nosiocChoice.getSelectionModel().clearSelection();
		predavacChoice.getSelectionModel().clearSelection();
		
		listaPreduslova.clear();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		mysql.Connect();
		setData();

	}
}
