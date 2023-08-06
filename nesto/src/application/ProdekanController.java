package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.security.auth.callback.Callback;

//import com.mysql.cj.x.protobuf.MysqlxExpect.Open;

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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import models.Nastavnik;
import models.Predmet;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;

public class ProdekanController implements Initializable{
	
	@FXML
	private TableView<Predmet> predmeti_table;
	
	@FXML
	private TableColumn<Predmet,String> sifraCol;
	@FXML
	private TableColumn<Predmet,String> nazCol;
	@FXML
	private TableColumn<Predmet,String> UsmjerenjeCol;
	@FXML
	private TableColumn<Predmet,String> PCol;
	@FXML
	private TableColumn<Predmet,String> ACol;
	@FXML
	
	
	private TableColumn<Predmet,String> LCol;
	@FXML
	private TableColumn<Predmet,String> ECTSCol;
	@FXML
	private TableColumn<Predmet,String> SemestarCol;
	@FXML
	private TableColumn<Predmet,String> PredusloviCol;
	@FXML
	private TableColumn<Predmet,String> OpcijeCol;
	
	
	
	
	
	
	
	
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
	private TextField searchTextField;
	@FXML
	private Button Search;
	
	String query=null;
	Connection con=null;
	PreparedStatement pst=null;
	ResultSet res=null;
	Predmet predmet=null;
	
	
	ObservableList<Predmet>List=FXCollections.observableArrayList();
	
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
				query="SELECT * FROM predmeti ";
				pst=con.prepareStatement(query);
				res=pst.executeQuery();
				ResultSet res2;
				while(res.next()) {
					query="SELECT preduslov.sifraPreduslov FROM preduslov  WHERE preduslov.sifraPred=?";
					pst=con.prepareStatement(query);
					pst.setString(1, res.getString("sifraPred"));
					res2=pst.executeQuery();
					String Preduslov="";
					
					while(res2.next()) {
						Preduslov+=res2.getString("sifraPreduslov")+",";
					}
					
					
					List.add(new Predmet(
							res.getString("sifraPred"),
							res.getString("nazivPred"),
							res.getString("usmjerenje"),
							res.getInt("predavanja_sati"),
							res.getInt("lab_sati"),
							res.getInt("av_sati"),
							res.getInt("ECTS"),
							res.getString("semestar")
							,Preduslov));
					predmeti_table.setItems(List);
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
		}
		private ObservableList<Predmet> filteredList = FXCollections.observableArrayList();
	      @FXML
			private void handleSearch() {
			    String searchText = searchTextField.getText().toLowerCase();

			    filteredList.clear();

			    for (Predmet predmet : List) {
			        if (predmet.getSifraPred().toLowerCase().contains(searchText)
			                || predmet.getNazivPred().toLowerCase().contains(searchText)
			                || predmet.getSemestar().toLowerCase().contains(searchText)
			                || predmet.getUsmjerenje().toLowerCase().contains(searchText)) {
			            filteredList.add(predmet);
			        }
			    }

			    // Update your TableView with the filtered data
			    predmeti_table.setItems(filteredList);
			}
		
		 void load() {
			refreshTable();
			sifraCol.setCellValueFactory(new PropertyValueFactory<>("sifraPred"));
			nazCol.setCellValueFactory(new PropertyValueFactory<>("nazivPred"));
			UsmjerenjeCol.setCellValueFactory(new PropertyValueFactory<>("usmjerenje"));
			PCol.setCellValueFactory(new PropertyValueFactory<>("predavanja_sati"));
			ACol.setCellValueFactory(new PropertyValueFactory<>("lab_sati"));
			LCol.setCellValueFactory(new PropertyValueFactory<>("av_sati"));
			ECTSCol.setCellValueFactory(new PropertyValueFactory<>("ECTS"));
			SemestarCol.setCellValueFactory(new PropertyValueFactory<>("semestar"));
			//PredusloviCol.setCellValueFactory(new PropertyValueFactory<>("preduslovi"));
			
			 PredusloviCol.setCellValueFactory(new PropertyValueFactory<>("preduslovi"));
			    PredusloviCol.setCellFactory(param -> new TableCell<>() {
			        @Override
			        protected void updateItem(String item, boolean empty) {
			            super.updateItem(item, empty);
			            if (empty || item == null) {
			                setText(null);
			            } else {
			                setText(item);
			            }
			        }
			    });
			    // Add the button to the "OpcijeCol" column
			    OpcijeCol.setCellFactory(param -> new TableCell<>() {
			        private final Button updateButton = new Button("Update Preduslovi");

			        {
			            updateButton.setOnAction(event -> {
			                Predmet predmet = getTableView().getItems().get(getIndex());
			                openCustomDialog(predmet.getSifraPred());
			     
			                System.out.println("Updating preduslovi for " + predmet.getSifraPred());
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
		private void openCustomDialog(String sifraPredmet) {
	        // Create the custom dialog
	        Dialog<ButtonType> dialog = new Dialog<>();
	        dialog.setTitle("Custom Dialog");
	        dialog.setHeaderText("Select an option:");

	        // Create buttons for each option
	        ButtonType addPrerequisitesButton = new ButtonType("Add New Prerequisites");
	        ButtonType deletePrerequisitesButton = new ButtonType("Delete Existing Prerequisites");

	        // Add buttons to the dialog pane
	        DialogPane dialogPane = dialog.getDialogPane();
	        dialogPane.getButtonTypes().addAll(addPrerequisitesButton, deletePrerequisitesButton, ButtonType.CANCEL);

	        // Show the dialog and wait for user input
	        dialog.showAndWait().ifPresent(buttonType -> {
	            if (buttonType == addPrerequisitesButton) {
	            	try {
	            	 FXMLLoader loader = new FXMLLoader(getClass().getResource("dodaj_preduslov.fxml"));
	                 Parent root = loader.load();
	                 preduslovController dodajPreduslovController = loader.getController();
	                 dodajPreduslovController.setSifraPredmet(sifraPredmet);
	             
	                 Scene newScene = new Scene(root);
	                 Stage newStage = new Stage();
	                 newStage.setScene(newScene);
	                 dodajPreduslovController.setStage(newStage);
	                // newStage.setTitle("");
	                 newStage.show();
	            	}catch(IOException e) {
	            		e.printStackTrace();
	            	}
	            } else if (buttonType == deletePrerequisitesButton) {
	            	   try {
	            		   FXMLLoader loader = new FXMLLoader(getClass().getResource("obrisi_preduslov.fxml"));
	            		   Parent root = loader.load();
	            		    ObrisiPreduslovController ObrisiPreduslovController = loader.getController();
		  	                ObrisiPreduslovController.setSifraPr(sifraPredmet);
		  	              Scene newScene = new Scene(root);
			                 Stage newStage = new Stage();
			                 newStage.setScene(newScene);
			                 ObrisiPreduslovController.setStage(newStage);
			                // newStage.setTitle("");
			                 newStage.show();
	                   

	                   } catch (IOException e) {
	                       e.printStackTrace();
	                   }
	               
	            } else {
	                // User canceled the dialog
	                // Add any necessary cleanup or handling here
	            }
	        });
	    }

	    private void showAlert(String message) {
	        Alert alert = new Alert(AlertType.INFORMATION);
	        alert.setTitle("Information");
	        alert.setHeaderText(null);
	        alert.setContentText(message);
	        alert.showAndWait();
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
	    	Parent root = FXMLLoader.load(getClass().getResource("prodekan_nastavnici.fxml"));
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
	 private void refresh(ActionEvent e) {
		 refreshTable();
	 }

}

