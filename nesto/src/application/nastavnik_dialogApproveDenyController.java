package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Popup;
import models.Nastavnik;
import models.ZahtjevZaPrenosBodova;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;

public class nastavnik_dialogApproveDenyController implements Initializable {
	private MySQLConnection mysql = new MySQLConnection();

	private ZahtjevZaPrenosBodova zahtjev;
	private Nastavnik nastavnik;
	private boolean btnPressed;

	@FXML
	private Label ime;
	@FXML
	private Label predmet;
	@FXML
	private Label bodovi;
	@FXML
	private TextField tf_poruka;

	private Popup popup;

	@FXML
	public void ponisti(ActionEvent event) {
		btnPressed = false;
		showPopUP(false);
	}

	@FXML
	public void odobri(ActionEvent event) {
		btnPressed = true;
		showPopUP(true);
	}

	public void setData(ZahtjevZaPrenosBodova z) {
		zahtjev = z;
		ime.setText(zahtjev.stud.getIme() + " " + zahtjev.stud.getPrezime());
		predmet.setText(zahtjev.pred.getNazivPred());
		bodovi.setText(zahtjev.getBrojBodova());
	}

	public void updateData() {
		if (btnPressed) {
			update(true);
		} else {
			update(false);
		}
	}

	public void update(boolean b) {
		try {
			String query = "UPDATE zahtjevZaPrenos SET odobreno=?, poruka=? WHERE idStud=? AND sifNast=? AND sifPred=?";
			mysql.pst = mysql.con.prepareStatement(query);

			mysql.pst.setBoolean(1, b);
			mysql.pst.setString(2, tf_poruka.getText());
			mysql.pst.setString(3, zahtjev.stud.getId());
			mysql.pst.setString(4, nastavnik.getSifNast());
			mysql.pst.setString(5, zahtjev.pred.getSifraPred());

			mysql.pst.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void showPopUP(boolean approved) {
		Platform.runLater(() -> {
			popup = new Popup();
			Label popupLabel = new Label();
			if (approved) {
				popupLabel.setText("Odobreno");
			} else {
				popupLabel.setText("Odbijeno");
			}
			popupLabel.setStyle("-fx-background-color: white; -fx-padding: 10px;");
			popup.getContent().add(popupLabel);
			popup.setAutoHide(true);
			popup.show(ime.getScene().getWindow(), ime.getScene().getWindow().getX() + ime.getLayoutX() + 50,
					ime.getScene().getWindow().getY() + ime.getLayoutY() + 220);

			// Hide the popup after a delay
			new Thread(() -> {
				try {
					Thread.sleep(2000); // Adjust the delay as needed
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Platform.runLater(() -> {
					if (popup.isShowing()) {
						popup.hide();
					}
				});
			}).start();
		});
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		mysql.Connect();
		nastavnik = DataSingleton.getInstance().getNastavnik();

	}
}
