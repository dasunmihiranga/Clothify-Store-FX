package edu.icet.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import edu.icet.dto.Product;
import edu.icet.dto.Supplier;
import edu.icet.service.ServiceFactory;
import edu.icet.service.custom.ProductService;
import edu.icet.service.custom.SupplierService;
import edu.icet.util.ServiceType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ProductFormController implements Initializable {

    @FXML
    private AnchorPane Anchor;

    @FXML
    private Pane body;

    @FXML
    private Pane box1;

    @FXML
    private Pane box2;

    @FXML
    private JFXButton btn0;

    @FXML
    private JFXButton btn1;

    @FXML
    private JFXButton btn2;

    @FXML
    private JFXButton btn3;

    @FXML
    private JFXButton btn4;

    @FXML
    private JFXButton btn5;

    @FXML
    private Button btnSearch;

    @FXML
    private ComboBox<String> cmbCategory;

    @FXML
    private ComboBox<String> cmbSize;

    @FXML
    private ComboBox<String> cmbSup;

    @FXML
    private TableColumn<?, ?> colCategory;

    @FXML
    private TableColumn<?, ?> colProductId;

    @FXML
    private TableColumn<?, ?> colProductName;

    @FXML
    private TableColumn<?, ?> colProductSize;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colSupId;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private Pane navbar;

    @FXML
    private TableView<Product> tblProducts;

    @FXML
    private JFXTextField txtId;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtQty;

    @FXML
    private JFXTextField txtSearch;

    @FXML
    private JFXTextField txtUnitprice;


    SceneSwitchController sceneSwitch = SceneSwitchController.getInstance();

    SupplierService supplierService = ServiceFactory.getInstance().getServiceType(ServiceType.SUPPLIER);
    ProductService productService =ServiceFactory.getInstance().getServiceType(ServiceType.PRODUCT);


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadSupplierIds();
        loadCategory();
        loadSize();

        colProductId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colProductSize.setCellValueFactory(new PropertyValueFactory<>("size"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colSupId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("category"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        tblProducts.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            setTextToValues(newValue);
        });

        loadTable();
    }

    private void setTextToValues(Product newValue) {
        txtId.setText(newValue.getProductId());
        txtName.setText(newValue.getName());
        txtQty.setText(String.valueOf(newValue.getQty()));
        txtUnitprice.setText(String.valueOf(newValue.getUnitPrice()));
        cmbCategory.setValue(newValue.getCategory());
        cmbSize.setValue(newValue.getSize());
        cmbSup.setValue(newValue.getSupplierId());

    }
    private void clear(){

        txtId.setText("");
        txtName.setText("");
        txtQty.setText("");
        txtUnitprice.setText("");
        cmbCategory.setValue("");
        cmbSize.setValue("");
        cmbSup.setValue("");

    }

    private void loadTable(){
        tblProducts.setItems(productService.getAllProduct());
    }
    private void loadCategory() {
        ObservableList<String> category = FXCollections.observableArrayList();
        category.add("Ladies");
        category.add("Gents");
        category.add("Kids");
        cmbCategory.setItems(category);
    }

    private void loadSize() {
        ObservableList<String> sizes = FXCollections.observableArrayList();
        sizes.add("XS");
        sizes.add("S");
        sizes.add("M");
        sizes.add("L");
        sizes.add("XL");
        sizes.add("2XL");
        sizes.add("3XL");
        cmbSize.setItems(sizes);
    }

    public void loadSupplierIds(){
        ObservableList<Supplier> supplierList =supplierService.getAllSupplier();
        ObservableList<String>supplierIds = FXCollections.observableArrayList();
        supplierList.forEach(supplier -> {
            supplierIds.add(supplier.getId());
        });
        cmbSup.setItems(supplierIds);
    }

    @FXML
    void AddbtnOnAction(ActionEvent event) {
        Product product =new Product(
                txtId.getText(),
                txtName.getText(),
                cmbSize.getValue(),
                Integer.parseInt(txtQty.getText()),
                cmbSup.getValue(),
                Double.parseDouble(txtUnitprice.getText()),
                cmbCategory.getValue()

        );

        boolean b = productService.addProduct(product);
        if (b){
            new Alert(Alert.AlertType.INFORMATION,"Product Added Successfully").show();
        }
        loadTable();
        clear();


    }

    @FXML
    void DeletebtnOnActiion(ActionEvent event) {
        if (!txtId.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Deleting");
            alert.setContentText("Are you sure want to delete this Product");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get()== ButtonType.OK){
                boolean isDeleted = productService.deleteProduct(txtId.getText());
                if (isDeleted){
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Product Deleted");
                    alert2.setContentText("Product deleted successfully");
                    alert2.showAndWait();
                    clear();
                    loadTable();
                }
            }
        }

    }

    @FXML
    void UpdatebtnOnAction(ActionEvent event) {
        if (
                !txtQty.getText().equals("") &&
                !txtUnitprice.getText().equals("") &&
                !txtName.getText().equals("") &&
                !cmbSup.getValue().equals("") &&
                !cmbSize.getValue().equals("")&&
                !cmbCategory.getValue().equals("")
        ){
            Product product =new Product(
                    txtId.getText(),
                    txtName.getText(),
                    cmbSize.getValue(),
                    Integer.parseInt(txtQty.getText()),
                    cmbSup.getValue(),
                    Double.parseDouble(txtUnitprice.getText()),
                    cmbCategory.getValue()

            );

            boolean isUpdated = productService.updateProduct(product);
            if (isUpdated){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Product Update");
                alert.setContentText("Product Updated Successfully");
                alert.showAndWait();
                clear();
                loadTable();

            }
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Something Missing");
            alert.setContentText("Please Check your Form again..!!!");
            alert.showAndWait();
        }

    }

    public void btnsearchOnAction(ActionEvent actionEvent) {
        Product product =productService.searchByName(txtSearch.getText());
        setTextToValues(product);
    }


    public void customerManagementbtnOnAction(ActionEvent actionEvent) throws IOException {
        sceneSwitch.switchScene(Anchor, "customer_form.fxml");
    }

    public void placeOrderbtnOnAction(ActionEvent actionEvent) throws IOException {
        sceneSwitch.switchScene(Anchor, "placeOrder_form.fxml");
    }

    public void supplierDetailsbtnOnAction(ActionEvent actionEvent) throws IOException {
        sceneSwitch.switchScene(Anchor, "supplier_form.fxml");
    }

    public void productDetailsbtnOnAction(ActionEvent actionEvent) throws IOException {
        sceneSwitch.switchScene(Anchor, "product_form.fxml");
    }

    public void employeeManagementbtnOnAction(ActionEvent actionEvent) throws IOException {
        sceneSwitch.switchScene(Anchor, "employee_form.fxml");

    }

    public void homebtnOnAction(ActionEvent actionEvent) throws IOException {
        sceneSwitch.switchScene(Anchor, "dash.fxml");
    }




}
