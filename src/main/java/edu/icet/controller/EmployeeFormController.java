package edu.icet.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import edu.icet.dto.Employee;
import edu.icet.service.ServiceFactory;
import edu.icet.service.custom.EmployeeService;
import edu.icet.service.custom.impl.EmployeeServiceImpl;
import edu.icet.util.ServiceType;
import edu.icet.util.Validator;
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

public class EmployeeFormController  implements Initializable {

    public TableView <Employee> tblEmployee;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colEmail;
    public JFXTextField txtPassword;
    public JFXTextField txtSearch;
    public Button btnSearch;
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

    SceneSwitchController sceneSwitch = SceneSwitchController.getInstance();



    EmployeeService employeeService=ServiceFactory.getInstance().getServiceType(ServiceType.EMPLOYEE);
    Validator validator= new Validator();


    @FXML
    void AddbtnOnAction(ActionEvent event) {

        Employee employee=new Employee(
                txtId.getText(),
                txtName.getText(),
                txtEmail.getText(),
                txtAddress.getText(),
                validator.hashPassword(txtPassword.getText())
        );


        if (!txtName.getText().equals("") && validator.isValidEmail(txtEmail.getText()) &&!txtPassword.getText().equals("") && !txtAddress.getText().equals("")) {

            boolean b=employeeService.addEmployee(employee);
            if (b){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Employee Added");
                alert.setContentText("Employee Added Successfully..!");
                alert.showAndWait();
                clear();
                txtId.setText(employeeService.generateEmployeeId());
            }

            loadTable();

        } else {
            new Alert(Alert.AlertType.ERROR, "Somthing Wrong..!!!").show();
        }
    }

    @FXML
    void DeletebtnOnActiion(ActionEvent event) {
        if (!txtId.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Deleting");
            alert.setContentText("Are you sure want to delete this Employee");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get()== ButtonType.OK){
                boolean isDeleted = employeeService.deleteEmployee(txtId.getText());
                if (isDeleted){
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Employee Deleted");
                    alert2.setContentText("Employee deleted successfully");
                    alert2.showAndWait();
                    clear();
                    loadTable();

                    txtId.setText(employeeService.generateEmployeeId());
                }
            }
        }

    }

    @FXML
    void UpdatebtnOnAction(ActionEvent event){
        if (!txtEmail.getText().equals("") && !txtAddress.getText().equals("") && !txtName.getText().equals("")){
            Employee employee = new Employee(
                    txtId.getText(),
                    txtName.getText(),
                    txtEmail.getText(),
                    txtAddress.getText(),
                    validator.hashPassword(txtPassword.getText())
            );

            boolean isUpdated = employeeService.updateEmployee(employee);
            if (isUpdated){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Employee Update");
                alert.setContentText("Employee Updated Successfully");
                alert.showAndWait();
                clear();
                loadTable();
                txtId.setText(employeeService.generateEmployeeId());
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Something Missing");
            alert.setContentText("Please Check your Form again..!!!");
            alert.showAndWait();
        }

    }
    public void clear(){
        txtId.setText("");
        txtName.setText("");
        txtEmail.setText("");
        txtAddress.setText("");
        txtPassword.setText("");
    }



    public void btnsearchOnAction(ActionEvent actionEvent) {
        Employee employee =employeeService.searchByName(txtSearch.getText());
        setTextToValues(employee);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtId.setText(employeeService.generateEmployeeId());

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        tblEmployee.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            setTextToValues(newValue);
        });

        loadTable();


    }

    private void setTextToValues(Employee newValue){
        txtId.setText(newValue.getId());
        txtName.setText(newValue.getName());
        txtEmail.setText(newValue.getEmail());
        txtAddress.setText(newValue.getAddress());
        txtPassword.setText(newValue.getPassword());

    }

    private void loadTable(){
        tblEmployee.setItems(employeeService.getAllEmployee());

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


    public void viewOrderHistorybtnOnAction(ActionEvent event) throws IOException {
        sceneSwitch.switchScene(Anchor, "viewOrder_form.fxml");

    }


}
