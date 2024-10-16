package edu.icet.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import edu.icet.dto.Customer;
import edu.icet.dto.Employee;
import edu.icet.service.ServiceFactory;
import edu.icet.service.custom.CustomerService;
import edu.icet.service.custom.EmployeeService;
import edu.icet.util.ServiceType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomerFormController  implements Initializable {

    public TableView<Customer> tblCustomer;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colEmail;
    public TableColumn colAddress;
    @FXML
    private AnchorPane Anchor;

    @FXML
    private Pane body;

    @FXML
    private Pane box1;

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
    private Pane navbar;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtId;

    @FXML
    private JFXTextField txtName;


    @FXML
    private JFXTextField txtSearch;

    SceneSwitchController sceneSwitch = SceneSwitchController.getInstance();
    CustomerService customerService = ServiceFactory.getInstance().getServiceType(ServiceType.CUSTOMER);

    @FXML
    void btnAddOnAction(ActionEvent event) {
        Customer customer=new Customer(
                txtId.getText(),
                txtName.getText(),
                txtAddress.getText(),
                txtEmail.getText()
        );

        if (!txtName.getText().equals("") && !txtEmail.getText().equals("") && !txtAddress.getText().equals("")) {


            boolean b=customerService.addCustomer(customer);
            if (b) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Customer Added");
                alert.setContentText("Customer Added Successfully..!");
                alert.showAndWait();
                clear();
                txtId.setText(customerService.generateCustomerId());

            }
            loadTable();

        } else {
            new Alert(Alert.AlertType.ERROR, "Somthing Wrong..!!!").show();
        }



    }

    @FXML
    void btnDeleteOnActiion(ActionEvent event) {
        if (!txtId.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Deleting");
            alert.setContentText("Are you sure want to delete this Customer");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get()== ButtonType.OK){
                boolean isDeleted = customerService.deleteCustomer(txtId.getText());
                if (isDeleted){
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Customer Deleted");
                    alert2.setContentText("Customer deleted successfully");
                    alert2.showAndWait();
                    clear();
                    loadTable();
                    txtId.setText(customerService.generateCustomerId());
                }
            }
        }

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        if (!txtEmail.getText().equals("") && !txtAddress.getText().equals("") && !txtName.getText().equals("")){
            Customer customer = new Customer(
                    txtId.getText(),
                    txtName.getText(),
                    txtEmail.getText(),
                    txtAddress.getText()
            );

            boolean isUpdated = customerService.updateCustomer(customer);
            if (isUpdated){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Customer Update");
                alert.setContentText("Customer Updated Successfully");
                alert.showAndWait();
                clear();
                loadTable();
                txtId.setText(customerService.generateCustomerId());

            }
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Something Missing");
            alert.setContentText("Please Check your Form again..!!!");
            alert.showAndWait();
        }


    }

    @FXML
    void btnsearchOnAction(ActionEvent event) {
        Customer customer =customerService.searchByName(txtSearch.getText());
        setTextToValues(customer);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtId.setText(customerService.generateCustomerId());

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        tblCustomer.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            setTextToValues(newValue);
        });

        loadTable();

    }

    private void setTextToValues(Customer newValue){
        txtId.setText(newValue.getId());
        txtName.setText(newValue.getName());
        txtEmail.setText(newValue.getEmail());
        txtAddress.setText(newValue.getAddress());
    }

    private void loadTable(){
        tblCustomer.setItems(customerService.getAllCustomer());

    }
    public void clear(){
        txtId.setText("");
        txtName.setText("");
        txtEmail.setText("");
        txtAddress.setText("");
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
