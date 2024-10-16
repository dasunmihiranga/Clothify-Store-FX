package edu.icet.controller;

import com.jfoenix.controls.JFXButton;
import edu.icet.dto.*;
import edu.icet.entity.OrderDetailEntity;
import edu.icet.service.ServiceFactory;
import edu.icet.service.custom.CustomerService;
import edu.icet.service.custom.OrderService;
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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class PlaceOrderFormController implements Initializable {

    public TextField txtQty;
    public Label lblStock;
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
    private TableView<CartTM> tblCart;

    @FXML
    private TextField txtQtyFroCustomer;

    CustomerService customerService = ServiceFactory.getInstance().getServiceType(ServiceType.CUSTOMER);
    ProductService productService =ServiceFactory.getInstance().getServiceType(ServiceType.PRODUCT);
    OrderService orderService =ServiceFactory.getInstance().getServiceType(ServiceType.ORDER);
    SceneSwitchController sceneSwitch = SceneSwitchController.getInstance();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblOrderId.setText(orderService.generateOrderId());

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

        colItemCode.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

    }

    private void searchItem(String id) {
        Product product = productService.searchById(id);
        lblDesc.setText(product.getName());
        lblUnitPrice.setText(String.valueOf(product.getUnitPrice()));
        lblSize.setText(product.getSize());
        lblStock.setText(String.valueOf(product.getQty()));

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

    ObservableList<CartTM> cartTMS =FXCollections.observableArrayList();

    @FXML
    void btnAddToCartOnAction(ActionEvent event) {


        String productId =cmbItemCode.getValue();
        String description =lblDesc.getText();
        Integer qty = Integer.valueOf(txtQty.getText());
        Double unitPrice = Double.parseDouble(lblUnitPrice.getText());
        Double total =unitPrice*qty;

        if(qty>Integer.parseInt(lblStock.getText())){
            new Alert(Alert.AlertType.WARNING,"Invalid QTY! ").show();
        }else {
            cartTMS.add(new CartTM(productId,description,qty,unitPrice,total));
            calcNetTotal();
        }

        tblCart.setItems(cartTMS);

    }
    public void calcNetTotal(){
        Double total=0.0;
        for (CartTM cartTM:cartTMS){
            total+=cartTM.getTotal();
        }
        lblNetTotal.setText(total.toString());

    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {
        String orderId =lblOrderId.getText();
        LocalDate orderDate =LocalDate.now();
        String customerId =cmbCustomerIDs.getValue();
        Customer customer=customerService.searchById(cmbCustomerIDs.getValue());


        String productId =cmbItemCode.getValue();

        Order order= new Order(orderId,customer,LocalDate.now(),Double.parseDouble(lblNetTotal.getText()));


        ObservableList<OrderDetailEntity> orderDetails=FXCollections.observableArrayList();
        cartTMS.forEach(obj->{
            orderDetails.add(new OrderDetailEntity( obj.getProductId(), obj.getQty(), obj.getTotal()));
        });

        System.out.println(orderDetails);

       

        System.out.println(orderDetails);

        boolean b = orderService.addOrder(order, orderDetails);


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
