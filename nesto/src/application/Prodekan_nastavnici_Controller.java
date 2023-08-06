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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.Nastavnik;
import models.Predmet;
public class Prodekan_nastavnici_Controller implements Initializable{
	
	@FXML
	private TableView<Nastavnik> nastavnik_table;
	
	@FXML
	private TableColumn<Nastavnik,String> ImeCol;
	@FXML
	private TableColumn<Nastavnik,String> PrezimeCol;
	@FXML
	private TableColumn<Nastavnik,String> EmailCol;
	@FXML
	private TableColumn<Nastavnik,String> ZvanjeCol;
	@FXML
	private TableColumn<Nastavnik,String> OdsjekCol;
	@FXML
	private TableColumn<Nastavnik,String> OpcijeCol;
	
	
	
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
	private Button refreshButton;
	
	@FXML 
	private TextField searchTextField;
	@FXML 
	private Button Search;
	
	String query=null;
	Connection con=null;
	PreparedStatement pst=null;
	ResultSet res=null;
	Predmet predmet=null;
	
	
	ObservableList<Nastavnik>List=FXCollections.observableArrayList();
	private ObservableList<Nastavnik> filteredList = FXCollections.observableArrayList();
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
				query="SELECT ime,prezime,email,zvanje,odsjek FROM nastavnik ";
				pst=con.prepareStatement(query);
				res=pst.executeQuery();
			
				 while (res.next()) {
			            List.add(new Nastavnik(
			                    res.getString("ime"),
			                    res.getString("prezime"),
			                    res.getString("email"),
			                    res.getString("zvanje"),
			                    res.getString("odsjek")));
			        }
					nastavnik_table.setItems(List);
					//handleSearch();
					
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
        @FXML
		private void handleSearch() {
		    String searchText = searchTextField.getText().toLowerCase();

		    filteredList.clear();

		    for (Nastavnik nastavnik : List) {
		        if (nastavnik.getIme().toLowerCase().contains(searchText)
		                || nastavnik.getPrezime().toLowerCase().contains(searchText)
		                || nastavnik.getEmail().toLowerCase().contains(searchText)
		                || nastavnik.getZvanje().toLowerCase().contains(searchText)
		                || nastavnik.getOdsjek().toLowerCase().contains(searchText)) {
		            filteredList.add(nastavnik);
		        }
		    }

		    // Update your TableView with the filtered data
		    nastavnik_table.setItems(filteredList);
		}
		
		 void load() {
			refreshTable();
			
			ImeCol.setCellValueFactory(new PropertyValueFactory<>("ime"));
			PrezimeCol.setCellValueFactory(new PropertyValueFactory<>("prezime"));
			EmailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
			ZvanjeCol.setCellValueFactory(new PropertyValueFactory<>("zvanje"));
			OdsjekCol.setCellValueFactory(new PropertyValueFactory<>("odsjek"));
			
			
			
			    // Add the button to the "OpcijeCol" column
			    OpcijeCol.setCellFactory(param -> new TableCell<>() {
			        private final Button updateButton = new Button(" Azuriraj Zvanje");

			        {
			            updateButton.setOnAction(event -> {
			                Nastavnik nastavnik = getTableView().getItems().get(getIndex());
			                try {
								openUpdateZvanjeScene(nastavnik.getEmail());
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}			     
			                System.out.println("Updating zvanje for " + nastavnik.getEmail());
			            });
			        }

			        @Override
			        protected void updateItem(String item, boolean empty) {
			            super.updateItem(item, empty);
			            if (empty) {
			                setGraphic(null);
			            } else {
			                setGraphic(updateButton);
			            }
			        }
			    });
			
			
		
		}
		 private void openUpdateZvanjeScene(String email) throws SQLException {
			    try {
			        FXMLLoader loader = new FXMLLoader(getClass().getResource("update_zvanje.fxml"));
			        Parent root = loader.load();

			        UpdateZvanjeController updateZvanjeController = loader.getController();
			        updateZvanjeController.setNastavnikIdentifier(email); // Pass the email or any unique identifier

			        Scene newScene = new Scene(root);
			        Stage newStage = new Stage();
			        newStage.setScene(newScene);
			        newStage.setTitle("Update Zvanje");
			        newStage.show();
			    } catch (IOException e) {
			        e.printStackTrace();
			    }
			}
		

	    private void showAlert(String message) {
	        Alert alert = new Alert(AlertType.INFORMATION);
	        alert.setTitle("Information");
	        alert.setHeaderText(null);
	        alert.setContentText(message);
	        alert.showAndWait();
	    }

	  
	
	    @FXML
	    private void refreshButtonClicked(ActionEvent e) {
	    	refreshTable();
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

}
