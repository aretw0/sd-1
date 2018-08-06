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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import sd1.clients.StoreClient;
import sd1.commons.Product;
import sd1.commons.ProductChange;
import sd1.commons.ProductList;
import sd1.commons.ProductTemp;

public class ViewController implements Initializable{	
	
	// Type List
	private ObservableList<String> listaType;	

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
	@FXML private ComboBox<String> typeField;
	@FXML private TextField priceField;
	@FXML private TextField amountField;
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {	
		clmncod.setCellValueFactory(new PropertyValueFactory<Product,Number>("cod"));
		clmntype.setCellValueFactory(new PropertyValueFactory<Product,Number>("type"));
		clmnprice.setCellValueFactory(new PropertyValueFactory<Product,Double>("price"));
		clmnname.setCellValueFactory(new PropertyValueFactory<Product,String>("name"));
		clmnamount.setCellValueFactory( new PropertyValueFactory<Product,Number>("amount"));		
		
		// inicializa os botoes desativados até que um item da lista seja escolhido
		buyProduct.setDisable(true);
		addNewProduct.setDisable(true);
		editProduct.setDisable(true);
		removeProduct.setDisable(true);
		
		// ComboBox dos types
		listaType = FXCollections.observableArrayList();
		for (String s : Product.types) {
			listaType.add(s);
		}
		typeField.setItems(listaType);
		
		
		// table listener
		productTableCliente.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Product>(){			
			@Override
			public void changed(ObservableValue<? extends Product> observable, Product oldValue, Product newValue) {				
				if (newValue != null) {
					// reativa os botões
					if (newValue.getAmount() > 0)
						buyProduct.setDisable(false);
					else
						buyProduct.setDisable(true);
					editProduct.setDisable(false);
					removeProduct.setDisable(false);
					// seta os campos para edição/compra/edição
					nameField.setText(newValue.getName());
					typeField.getSelectionModel().select(newValue.getType());
					priceField.setText(String.valueOf(newValue.getPrice()));
					amountField.setText(String.valueOf(newValue.getAmount()));					
				}else {
					// limpa os campos
					nameField.clear();
					typeField.getSelectionModel().clearSelection();
					priceField.clear();
					amountField.clear();
				}
			}						
						
		});	  
		// Inicializa no modo cliente
		isFunc.setSelected(true);
		
		// altera os botões quando o modo cliente é selecionado
		isCliente.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
            if (isNowSelected) {
            	// Buttons
            	isFunc.setSelected(false);
            	buyProduct.setVisible(true);
            	addNewProduct.setVisible(false);
            	removeProduct.setVisible(false);
            	editProduct.setVisible(false);
            	
            	// Fields
            	nameField.setEditable(false);
            	typeField.setDisable(true);
            	priceField.setEditable(false);
            	amountField.setEditable(false);
            } 

        });
		// altera os botões quando o modo funcionário é selecionado
		isFunc.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
            if (isNowSelected) {
            	// Buttons
            	isCliente.setSelected(false);
            	buyProduct.setVisible(false);
            	addNewProduct.setVisible(true);
            	removeProduct.setVisible(true);
            	editProduct.setVisible(true);
            	
            	// Fields
            	nameField.setEditable(true);
            	typeField.setEditable(true);
            	priceField.setDisable(false);
            	amountField.setEditable(true);
            }

        });	
        
	}	
	
	@FXML
	public void searchProduct() {
		for (Product p: productTableCliente.getItems()) {
			if (p.getName().toLowerCase().equals(searchField.getText().toLowerCase())) {
				productTableCliente.getSelectionModel().select(p);
				return;
			}
			try {
				if (p.getCod() == Integer.parseInt(searchField.getText())) {
					productTableCliente.getSelectionModel().select(p);
				}
			}catch(NumberFormatException e) {
				
			}
		}
	}
	
	@FXML
	public void buyProduct() {
		Product seleted = productTableCliente.getSelectionModel().getSelectedItem();
		if (seleted != null) {
			// double check
			if (seleted.getAmount() > 0) {
				if (StoreClient.buyProduct(seleted.getCod())) {
					productTableCliente.getSelectionModel().clearSelection();
				}				
			}
		}
	}
	
	@FXML
	public void addNewProduct() {
		if (StoreClient.addProduct(new ProductTemp(typeField.getSelectionModel().getSelectedIndex(),
												Double.valueOf(priceField.getText()), 
												nameField.getText(), 
												Integer.parseInt(amountField.getText())))) {
			// TODO fazer com q a lista seja atualizada sem dar o get novamente			
		}
		productTableCliente.getSelectionModel().clearSelection();
		nameField.clear();
		typeField.getSelectionModel().clearSelection();
		priceField.clear();
		amountField.clear();
		
	}
	
	@FXML
	public void removeProduct() {
		Product seleted = productTableCliente.getSelectionModel().getSelectedItem();
		if (seleted != null) {
			if (StoreClient.delProduct(seleted.getName())) {
				productTableCliente.getSelectionModel().clearSelection();								
			}
		}
	}
	
	@FXML
	public void editProduct() {
		Product selected = productTableCliente.getSelectionModel().getSelectedItem();
		if (selected != null) {
			StoreClient.updProduct(new ProductChange(selected.getCod(), selected.getCod(),
																		typeField.getSelectionModel().getSelectedIndex(),
																		Double.valueOf(priceField.getText()), 
																		nameField.getText(), 
																		Integer.parseInt(amountField.getText())));
			productTableCliente.getSelectionModel().clearSelection();
		}
	}
	
	@FXML
	public void connectToServe() {
		StoreClient.setHostAdd(hostField.getText());
		StoreClient.setHostPort(Integer.parseInt(portField.getText()));
		
		if (StoreClient.startPoolList()) {
			// desativa os campos de conexão e ativa o botão de adicionar produto
			hostField.setDisable(true);
			portField.setDisable(true);
			connect.setDisable(true);	
			addNewProduct.setDisable(false);
			StoreClient.getList();
			productTableCliente.setItems(StoreClient.getPdl().getList());
			
		}else {
			
		}
		
		
	}
	
}