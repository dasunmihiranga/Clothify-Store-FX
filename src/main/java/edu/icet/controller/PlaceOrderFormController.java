package edu.icet.controller;

import com.jfoenix.controls.JFXButton;
import edu.icet.dto.Customer;
import edu.icet.dto.Product;
import edu.icet.service.ServiceFactory;
import edu.icet.service.custom.CustomerService;
import edu.icet.service.custom.ProductService;
import edu.icet.util.ServiceType;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.ResourceBundle;

public class PlaceOrderFormController implements Initializable {

    @FXML
    private AnchorPane Anchor;

    @FXML
    private Pane body;

    @FXML
    private Pane box1;

    @FXML
    private Pane box2;

    @FXML
    private Pane box3;

    @FXML
    private Pane box4;

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
    private ComboBox<String> cmbCustomerIDs;

    @FXML
    private ComboBox<String> cmbItemCode;

    @FXML
    private TableColumn<?, ?> colDesc;

    @FXML
    private TableColumn<?, ?> colItemCode;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private Label lblAddress;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblDesc;

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblName;

    @FXML
    private Label lblNetTotal;

    @FXML
    private Label lblOrderId;

    @FXML
    private Label lblSize;

    @FXML
    private Label lblTime;

    @FXML
    private Label lblUnitPrice;

    @FXML
    private Pane navbar;

    @FXML
    private TableView<?> tblCart;

    @FXML
    private TextField txtQtyFroCustomer;

    CustomerService customerService = ServiceFactory.getInstance().getServiceType(ServiceType.CUSTOMER);
    ProductService productService =ServiceFactory.getInstance().getServiceType(ServiceType.PRODUCT);
    SceneSwitchController sceneSwitch = SceneSwitchController.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        loadDateAndTime();
        loadCustomerIds();
        loadProductIds();

        cmbCustomerIDs.getSelectionModel().selectedItemProperty().addListener((observableValue, s, newValue) -> {
            if(newValue!=null){
                searchCustomer(newValue);
            }
        });

        cmbItemCode.getSelectionModel().selectedItemProperty().addListener((observableValue, s, newValue) ->{
            if (newValue!=null){
                searchItem(newValue);
            }
        });

    }

    private void searchItem(String id) {
        Product product = productService.searchById(id);
        lblDesc.setText(product.getName());
        lblUnitPrice.setText(String.valueOf(product.getUnitPrice()));
        lblSize.setText(product.getSize());

    }

    private void searchCustomer(String id) {
        Customer customer = customerService.searchById(id);
        lblName.setText(customer.getName());
        lblAddress.setText(customer.getAddress());
        lblEmail.setText(customer.getEmail());

    }


    private void loadDateAndTime(){
        Date date =new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String dateNow=f.format(date);

        lblDate.setText(dateNow);

        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime now = LocalTime.now();
            lblTime.setText(now.getHour() + " : " + now.getMinute() + " : " + now.getSecond());
        }),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();


    }
    private void loadCustomerIds(){
       ObservableList<Customer> allCustomers =customerService.getAllCustomer();
       ObservableList<String>customerIds = FXCollections.observableArrayList();

       allCustomers.forEach(customer -> {
           customerIds.add(customer.getId());
       });

        cmbCustomerIDs.setItems(customerIds);
    }
    private void loadProductIds() {
        ObservableList<Product> allProducts =productService.getAllProduct();
        ObservableList<String>productIds = FXCollections.observableArrayList();

        allProducts.forEach(product -> {
            productIds.add(product.getProductId());
        });

        cmbItemCode.setItems(productIds);
    }


    @FXML
    void btnAddToCartOnAction(ActionEvent event) {

    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {

    }
    @FXML
    void txtAddtoCartOnAction(ActionEvent event) {

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
