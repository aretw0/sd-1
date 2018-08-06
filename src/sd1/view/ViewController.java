package sd1.view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import sd1.commons.Product;

public class ViewController implements Initializable{	
	
	// Product List
	private ObservableList<Product> productList;

	@FXML private TableView<Product> productTableCliente;
	@FXML private TableColumn<Product,Number> clmncod;
	@FXML private TableColumn<Product,Number> clmntype;
	@FXML private TableColumn<Product,Double> clmnprice;
	@FXML private TableColumn<Product,String> clmnname;
	@FXML private TableColumn<Product,Number> clmnamount;
	
	// FXML etc
	@FXML private Button searchProduct;
	@FXML private Button connect;
	@FXML private Button buyProduct;
	@FXML private Button addNewProduct;
	@FXML private Button removeProduct;
	@FXML private Button editProduct;
	@FXML private CheckBox isCliente;
	@FXML private CheckBox isFunc;
	@FXML private TextField searchField;
	@FXML private TextField hostField;
	@FXML private TextField portField;
	@FXML private TextField nameField;
	@FXML private TextField typeField;
	@FXML private TextField priceField;
	@FXML private TextField amountField;
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {	
		clmncod.setCellValueFactory(new PropertyValueFactory<Product,Number>("cod"));
		clmntype.setCellValueFactory(new PropertyValueFactory<Product,Number>("type"));
		clmnprice.setCellValueFactory(new PropertyValueFactory<Product,Double>("price"));
		clmnname.setCellValueFactory(new PropertyValueFactory<Product,String>("name"));
		clmnamount.setCellValueFactory( new PropertyValueFactory<Product,Number>("amount"));
		
		productList = FXCollections.observableArrayList();
		productTableCliente.setItems(productList);
		
		// table listener
		productTableCliente.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Product>(){			
			@Override
			public void changed(ObservableValue<? extends Product> observable, Product oldValue, Product newValue) {				
				if (newValue != null) {
					nameField.setText(newValue.getName());
					typeField.setText(Product.types[newValue.getType()]);
					priceField.setText(String.valueOf(newValue.getPrice()));
					amountField.setText(String.valueOf(newValue.getAmount()));					
				}
			}						
						
		});	  
		
		isCliente.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
            if (isNowSelected) {
            	isFunc.setSelected(false);
            	buyProduct.setVisible(true);
            	addNewProduct.setVisible(false);
            	removeProduct.setVisible(false);
            	editProduct.setVisible(false);
            	nameField.setEditable(false);
            	typeField.setEditable(false);
            	priceField.setEditable(false);
            	amountField.setEditable(false);
            } else {
//                isCliente.setSelected(true);
            }

        });
		isFunc.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
            if (isNowSelected) {
            	isCliente.setSelected(false);
            	buyProduct.setVisible(false);
            	addNewProduct.setVisible(true);
            	removeProduct.setVisible(true);
            	editProduct.setVisible(true);
            	nameField.setEditable(true);
            	typeField.setEditable(true);
            	priceField.setEditable(true);
            	amountField.setEditable(true);
            } else {
//                isFunc.setSelected(true);
            }

        });	
        
	}	
	
	@FXML
	public void searchProduct() {
		
	}
	
	@FXML
	public void buyProduct() {
		
	}
	
	@FXML
	public void addNewProduct() {
		
	}
	
	@FXML
	public void removeProduct() {
		
	}
	
	@FXML
	public void editProduct() {
		
	}
	
	@FXML
	public void connectToServe() {
		hostField.setDisable(true);
		portField.setDisable(true);
		connect.setDisable(true);
		
	}
	
}