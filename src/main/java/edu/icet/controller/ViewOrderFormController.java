package edu.icet.controller;

import com.jfoenix.controls.JFXButton;
import edu.icet.dto.ViewOrderTblObj;
import edu.icet.service.ServiceFactory;
import edu.icet.service.custom.OrderService;
import edu.icet.util.ServiceType;
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

public class ViewOrderFormController implements Initializable {

    @FXML
    private AnchorPane Anchor;


    @FXML
    private Pane body;

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
    private TableColumn<?, ?> colCustomerId;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colNetTotal;

    @FXML
    private TableColumn<?, ?> colOrderId;

    @FXML
    private Pane navbar;

    public Label lblTitle;

    @FXML
    private TableView<ViewOrderTblObj> tblViewOrders;

    SceneSwitchController sceneSwitch = SceneSwitchController.getInstance();

    OrderService orderService= ServiceFactory.getInstance().getServiceType(ServiceType.ORDER);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colNetTotal.setCellValueFactory(new PropertyValueFactory<>("netTotal"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));

        loadTable();


    }
    public void loadTable(){
        ObservableList<ViewOrderTblObj> allOrders = orderService.getAllOrders();
        tblViewOrders.setItems(allOrders);

    }





    public void customerManagementbtnOnAction(ActionEvent actionEvent) throws IOException {
        if (lblTitle.getText().equals("Admin Page")) {
            sceneSwitch.switchScene(Anchor, "customer_form.fxml");
        } else if (lblTitle.getText().equals("Employee Page")) {
            System.out.println("Hello");

        }

    }

    public void placeOrderbtnOnAction(ActionEvent actionEvent) throws IOException {
        if (lblTitle.getText().equals("Admin Page")) {
            sceneSwitch.switchScene(Anchor, "placeOrder_form.fxml");
        } else if (lblTitle.getText().equals("Employee Page")) {
            sceneSwitch.switchScene(Anchor, "placeOrder_E_form.fxml");

        }
    }

    public void supplierDetailsbtnOnAction(ActionEvent actionEvent) throws IOException {
        sceneSwitch.switchScene(Anchor, "supplier_form.fxml");
    }

    public void productDetailsbtnOnAction(ActionEvent actionEvent) throws IOException {
        if (lblTitle.getText().equals("Admin Page")) {
            sceneSwitch.switchScene(Anchor, "product_form.fxml");
        } else if (lblTitle.getText().equals("Employee Page")) {
            sceneSwitch.switchScene(Anchor, "product_E_form.fxml");

        }

    }

    public void employeeManagementbtnOnAction(ActionEvent actionEvent) throws IOException {
        sceneSwitch.switchScene(Anchor, "employee_form.fxml");

    }

    public void homebtnOnAction(ActionEvent actionEvent) throws IOException {
        if (lblTitle.getText().equals("Admin Page")) {
            sceneSwitch.switchScene(Anchor, "dash.fxml");
        } else if (lblTitle.getText().equals("Employee Page")) {
            sceneSwitch.switchScene(Anchor, "dash_E_form.fxml");

        }

    }


    public void viewOrderHistorybtnOnAction(ActionEvent event) throws IOException {
        if (lblTitle.getText().equals("Admin Page")) {
            sceneSwitch.switchScene(Anchor, "viewOrder_form.fxml");
        } else if (lblTitle.getText().equals("Employee Page")) {
            sceneSwitch.switchScene(Anchor, "viewOrder_E_form.fxml");

        }

    }
    public void btnLoginOnAction(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Deleting");
        alert.setContentText("Are you sure want Log out !");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get()== ButtonType.OK){
            sceneSwitch.switchScene(Anchor,"login_form.fxml");
        }
    }


}
