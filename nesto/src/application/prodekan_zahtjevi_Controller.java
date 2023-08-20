package application;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import javafx.scene.layout.HBox;
import javafx.scene.control.TextInputDialog;
import models.Predmet;
import models.Student;
import models.Zahtjevi;

public class prodekan_zahtjevi_Controller implements Initializable {
	private MySQLConnection mysql = new MySQLConnection();
	private SceneLoader s = new SceneLoader();
	
	@FXML
	private TextField search_tf;

	@FXML
	private TableView<Zahtjevi> zahtjeviTable;

	@FXML
	private TableColumn<Zahtjevi, String> ImeCol;
	@FXML
	private TableColumn<Zahtjevi, String> PrezimeCol;
	@FXML
	private TableColumn<Zahtjevi, String> BrIndeksaCol;
	@FXML
	private TableColumn<Zahtjevi, String> Predmet1Col;
	@FXML
	private TableColumn<Zahtjevi, String> Predmet2Col;
	@FXML
	private TableColumn<Zahtjevi, String> ObrazlozenjeCol;
	@FXML
	private TableColumn<Zahtjevi, String> OpcijeCol;

	@FXML
	private Button logout;
	@FXML
	private Button btn_nastavnici;
	@FXML
	private Button btn_predmeti;
	@FXML
	private Button btn_zahtjevi;
	@FXML
	private Button btn_noviplan;
	@FXML
	private Button btn_prijavljeniPredmeti;
	@FXML
	private Button refreshButton;
	@FXML
	private Button btn_bitniDatumi;

	String query = null;
	ResultSet res = null;
	Predmet predmet = null;

	ObservableList<Zahtjevi> List = FXCollections.observableArrayList();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		mysql.Connect();
		load();
		setupSearch();
	}

	private void refreshTable() {

		try {
			List.clear();
			query = "SELECT ime,prezime,student.brojIndeksa,predmeti1.nazivPred as prvipredmet,predmeti2.nazivPred as drugipredmet,poruka FROM zahtjevZaPromjenu INNER JOIN predmet predmeti1"
					+ " ON sifPred1=predmeti1.sifPred INNER JOIN predmet predmeti2 ON sifPred2=predmeti2.sifPred INNER JOIN student ON zahtjevZaPromjenu.idStud=student.brojIndeksa"
					+ " WHERE odobreno IS NULL";

			mysql.pst = mysql.con.prepareStatement(query);
			res = mysql.pst.executeQuery();

			while (res.next()) {
				List.add(new Zahtjevi(res.getString("ime"), res.getString("prezime"), res.getInt("student.brojIndeksa"),
						res.getString("prvipredmet"), res.getString("drugipredmet"), res.getString("poruka")));
			}
			zahtjeviTable.setItems(List);
//					//handleSearch();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	void load() {
		refreshTable();

		ImeCol.setCellValueFactory(new PropertyValueFactory<>("ime"));
		PrezimeCol.setCellValueFactory(new PropertyValueFactory<>("prezime"));
		BrIndeksaCol.setCellValueFactory(new PropertyValueFactory<>("brIndeks"));
		Predmet1Col.setCellValueFactory(new PropertyValueFactory<>("predmet1"));
		Predmet2Col.setCellValueFactory(new PropertyValueFactory<>("predmet2"));
		ObrazlozenjeCol.setCellValueFactory(new PropertyValueFactory<>("obrazlozenje"));

		OpcijeCol.setCellFactory(new Callback<TableColumn<Zahtjevi, String>, TableCell<Zahtjevi, String>>() {
			@Override
			public TableCell<Zahtjevi, String> call(TableColumn<Zahtjevi, String> param) {
				return new TableCell<Zahtjevi, String>() {
					private final Button prihvatiButton = new Button("Prihvati");
					private final Button odbijButton = new Button("Odbij");

					{
						prihvatiButton.setOnAction(event -> {
							try {
								Zahtjevi zahtjev = getTableView().getItems().get(getIndex());
								query = "SELECT sifPred as sifra1 FROM predmet WHERE nazivPred=?";
								mysql.pst = mysql.con.prepareStatement(query);
								mysql.pst.setString(1, zahtjev.getPredmet1());
								res = mysql.pst.executeQuery();
								res.next();
								String sifra1 = res.getString("sifra1");
								query = "SELECT sifPred as sifra2 FROM predmet WHERE nazivPred=?";
								mysql.pst = mysql.con.prepareStatement(query);
								mysql.pst.setString(1, zahtjev.getPredmet2());
								res = mysql.pst.executeQuery();
								res.next();
								String sifra2 = res.getString("sifra2");
								String updateQuery = "UPDATE slusaPred SET sifPred = ? WHERE sifPred = ? AND idStud=?";
								PreparedStatement updateStatement = mysql.con.prepareStatement(updateQuery);
								updateStatement.setString(1, sifra2);
								updateStatement.setString(2, sifra1);
								updateStatement.setInt(3, zahtjev.getBrIndeks());
								int rowsAffected1 = updateStatement.executeUpdate();
								String updateQuery2 = "UPDATE zahtjevZaPromjenu SET odobreno=true WHERE idStud= ? AND sifPred1=? AND "
										+ "sifPred2=?";
								PreparedStatement updateStatement2 = mysql.con.prepareStatement(updateQuery2);
								updateStatement2.setInt(1, zahtjev.getBrIndeks());
								updateStatement2.setString(2, sifra1);
								updateStatement2.setString(3, sifra2);

								int rowsAffected2 = updateStatement2.executeUpdate();

								if (rowsAffected1 > 0 && rowsAffected2 > 0) {

									List.remove(zahtjev);

								} else {
									System.out.println("Failed");
								}

							} catch (SQLException e) {
								e.printStackTrace();
							}

						});

						odbijButton.setOnAction(event -> {

							Zahtjevi zahtjev = getTableView().getItems().get(getIndex());
							TextInputDialog dialog = new TextInputDialog();

							dialog.setHeaderText("Unesite poruku obrazlozenja");
							dialog.setContentText("Obrazlozenje:");

							Optional<String> result = dialog.showAndWait();
							if (result.isPresent()) {
								String explanation = result.get();
								try {

									query = "SELECT sifPred as sifra1 FROM predmet WHERE nazivPred=?";
									mysql.pst = mysql.con.prepareStatement(query);
									mysql.pst.setString(1, zahtjev.getPredmet1());
									res = mysql.pst.executeQuery();
									res.next();
									String sifra1 = res.getString("sifra1");
									query = "SELECT sifPred as sifra2 FROM predmet WHERE nazivPred=?";
									mysql.pst = mysql.con.prepareStatement(query);
									mysql.pst.setString(1, zahtjev.getPredmet2());
									res = mysql.pst.executeQuery();
									res.next();
									String sifra2 = res.getString("sifra2");

									String updateQuery2 = "UPDATE zahtjevZaPromjenu SET odgovor=? WHERE idStud= ? AND sifPred1=? AND "
											+ "sifPred2=?";
									PreparedStatement updateStatement2 = mysql.con.prepareStatement(updateQuery2);
									updateStatement2.setString(1, explanation);
									updateStatement2.setInt(2, zahtjev.getBrIndeks());
									updateStatement2.setString(3, sifra1);
									updateStatement2.setString(4, sifra2);

									int rowsAffected2 = updateStatement2.executeUpdate();

									if (rowsAffected2 > 0) {

										List.remove(zahtjev);

									} else {
										System.out.println("Failed");
									}

								} catch (SQLException e) {
									e.printStackTrace();
								}

							}

						});
					}

					@Override
					protected void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);

						if (empty) {
							setGraphic(null);
						} else {
							HBox buttonBox = new HBox(10);
							buttonBox.getChildren().addAll(prihvatiButton, odbijButton);
							setGraphic(buttonBox);
						}
					}
				};
			}
		});

	}
	
	private void setupSearch() {
		FilteredList<Zahtjevi> filteredData = new FilteredList<>(zahtjeviTable.getItems(), p -> true);

		search_tf.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(zahtjev-> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = search_tf.getText().toLowerCase();

				if (zahtjev.getIme().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if (zahtjev.getPrezime().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				}else if( zahtjev.getPredmet1().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				}else if( zahtjev.getPredmet2().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				}
				return false;
			});
		});

		SortedList<Zahtjevi> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(zahtjeviTable.comparatorProperty());

		zahtjeviTable.setItems(sortedData);
	}


	@FXML
	private void logout(MouseEvent e) {
		s.logout(e);
	}

	@FXML
	private void nastavnici(MouseEvent e) {
		s.loadProdekan_nastavnici(e);
	}

	@FXML
	private void predmeti(MouseEvent e) {
		s.loadProdekan_to_predmeti(e);
	}

	@FXML
	private void to_zahtjevi(MouseEvent e) {
		s.loadProdekan_to_zahtjevi(e);
	}

	@FXML
	private void to_plan_realizacije(MouseEvent e) {
		s.loadProdekan_to_plan_realizacije(e);
	}

	@FXML
	private void refresh(ActionEvent e) {
		refreshTable();
	}

	@FXML
	private void to_prijavljeni_predmeti(MouseEvent e) {
		s.loadProdekan_to_prijavljeni_predmeti(e);
	}

	@FXML
	private void to_bitni_datumi(MouseEvent e) {
		s.loadProdekan_to_bitni_datumi(e);
	}

}
