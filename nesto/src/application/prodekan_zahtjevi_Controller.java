package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.layout.HBox;
import models.Nastavnik;
import models.Predmet;
import models.Student_predmet;
import models.Zahtjevi;
public class prodekan_zahtjevi_Controller implements Initializable {
	
	
	@FXML
	private TableView<Zahtjevi> zahtjeviTable;
	
	@FXML
	private TableColumn<Zahtjevi,String> ImeCol;
	@FXML
	private TableColumn<Zahtjevi,String> PrezimeCol;
	@FXML
	private TableColumn<Zahtjevi,String> BrIndeksaCol;
	@FXML
	private TableColumn<Zahtjevi,String> Predmet1Col;
	@FXML
	private TableColumn<Zahtjevi,String> Predmet2Col;
	@FXML
	private TableColumn<Zahtjevi,String> ObrazlozenjeCol;
	@FXML
	private TableColumn<Zahtjevi,String> OpcijeCol;
	
	
	private Stage stage;
	private Scene scene;
	private Parent root;
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
	
	
	
	String query=null;
	Connection con=null;
	PreparedStatement pst=null;
	ResultSet res=null;
	Predmet predmet=null;
	
	
	ObservableList<Zahtjevi>List=FXCollections.observableArrayList();
	private ObservableList<Zahtjevi> filteredList = FXCollections.observableArrayList();
	public  void Connect() {
		
		try {
		
			con=DriverManager.getConnection("jdbc:mysql://localhost/projekat","root","");
		}

		catch(SQLException exe) {
			exe.printStackTrace();
		}
		
	}

		
		
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {	
			Connect();	  
			load();
		}
	
		private void refreshTable() {
			
			try {
				List.clear();
			query="SELECT ime,prezime,brIndeks,predmeti1.nazivPred as prvipredmet,predmeti2.nazivPred as drugipredmet,obrazlozenje FROM zahtjevi_promjena INNER JOIN predmeti predmeti1"
					+ " ON sifraPrijavljeniPredmet=predmeti1.sifraPred INNER JOIN predmeti predmeti2 ON sifraZamjenskiPredmet=predmeti2.sifraPred INNER JOIN student ON id=brIndeks";
						
			
				pst=con.prepareStatement(query);
				res=pst.executeQuery();
			
				 while (res.next()) {
			            List.add(new Zahtjevi(
			                    res.getString("ime"),
			                    res.getString("prezime"),
			                    res.getInt("brIndeks"),
			                    res.getString("prvipredmet"),
			                    res.getString("drugipredmet"),
			                    res.getString("obrazlozenje")
			                    ));
			        }
				zahtjeviTable.setItems(List);
//					//handleSearch();
					
			} catch (SQLException e) {
				// TODO Auto-generated catch block
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
			                    query="SELECT sifraPred as sifra1 FROM predmeti WHERE nazivPred=?";
			                    pst=con.prepareStatement(query);
			                    pst.setString(1, zahtjev.getPredmet1());
			                    res=pst.executeQuery();
			                    res.next();
			                    String sifra1=res.getString("sifra1");
			                    query="SELECT sifraPred as sifra2 FROM predmeti WHERE nazivPred=?";
			                    pst=con.prepareStatement(query);
			                    pst.setString(1, zahtjev.getPredmet2());
			                    res=pst.executeQuery();
			                    res.next();
			                    String sifra2=res.getString("sifra2");
			                    String deleteQuery1 = "DELETE FROM jeprijavio WHERE id = ? AND sifraP=?";
			                    PreparedStatement deleteStatement = con.prepareStatement(deleteQuery1);
			                    deleteStatement.setInt(1, zahtjev.getBrIndeks());
			                    deleteStatement.setString(2, sifra1);
			                    int rowsAffected1 = deleteStatement.executeUpdate();
			                    String deleteQuery2 = "DELETE FROM zahtjevi_promjena WHERE brIndeks = ? AND sifraPrijavljeniPredmet=? AND "
			                    		+ "sifraZamjenskiPredmet=?";
			                    PreparedStatement deleteStatement2 = con.prepareStatement(deleteQuery2);
			                    deleteStatement2.setInt(1, zahtjev.getBrIndeks());
			                    deleteStatement2.setString(2, sifra1);
			                    deleteStatement2.setString(3, sifra2);
			                    
			                    int rowsAffected2 = deleteStatement2.executeUpdate();

			                    if (rowsAffected1 > 0 && rowsAffected2>0) {
	
			                        refreshTable();
			                   
			                    } else {
			                        // Handle deletion failure
			                    }
			               
			                    			                    
			                }catch(SQLException e) {
			                	e.printStackTrace();
			                }
			                   
			                });

			                odbijButton.setOnAction(event -> {
			                    // Handle delete action here
			                    Zahtjevi selectedItem = getTableView().getItems().get(getIndex());
			                    // Implement the delete action based on the selectedItem
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
	
	
	
	
	
	
	 @FXML
	    private void logout(ActionEvent e) throws IOException {
	    	root = FXMLLoader.load(getClass().getResource("Arnela.fxml"));
			stage=(Stage)((Node)e.getSource()).getScene().getWindow();
			scene=new Scene(root);
			stage.setScene(scene);
			stage.show();
	    }
	 @FXML
	    private void nastavnici(ActionEvent e) throws IOException {
	    	root = FXMLLoader.load(getClass().getResource("prodekan_nastavnici.fxml"));
			stage=(Stage)((Node)e.getSource()).getScene().getWindow();
			scene=new Scene(root);
			stage.setScene(scene);
			stage.show();
	    }

	 
	 @FXML
	    private void predmeti(ActionEvent e) throws IOException {
	    	root = FXMLLoader.load(getClass().getResource("prodekan.fxml"));
			stage=(Stage)((Node)e.getSource()).getScene().getWindow();
			scene=new Scene(root);
			stage.setScene(scene);
			stage.show();
	    }
	 @FXML
	 private void to_zahtjevi(ActionEvent e) throws IOException {
	    	root = FXMLLoader.load(getClass().getResource("prodekan_zahtjevi.fxml"));
			stage=(Stage)((Node)e.getSource()).getScene().getWindow();
			scene=new Scene(root);
			stage.setScene(scene);
			stage.show();
	    }
	 @FXML
	 private void to_plan_realizacije(ActionEvent e) throws IOException {
	    	root = FXMLLoader.load(getClass().getResource("prodekan_DodajUnos.fxml"));
			stage=(Stage)((Node)e.getSource()).getScene().getWindow();
			scene=new Scene(root);
			stage.setScene(scene);
			stage.show();
	    }
	 @FXML
	 private void to_prijavljeni_predmeti(ActionEvent e) throws IOException {
	    	root = FXMLLoader.load(getClass().getResource("ProdekanPrijavljeniPredmeti.fxml"));
			stage=(Stage)((Node)e.getSource()).getScene().getWindow();
			scene=new Scene(root);
			stage.setScene(scene);
			stage.show();
	    }
}
