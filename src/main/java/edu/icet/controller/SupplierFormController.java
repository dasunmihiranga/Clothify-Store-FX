package edu.icet.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import edu.icet.dto.Employee;
import edu.icet.dto.Supplier;
import edu.icet.repository.SuperDao;
import edu.icet.service.ServiceFactory;
import edu.icet.service.custom.SupplierService;
import edu.icet.util.ServiceType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class SupplierFormController implements Initializable {

    public TableView <Supplier>tblSupplier;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colCompany;
    public TableColumn colEmail;
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
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtId;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtcompany;

    SceneSwitchController sceneSwitch = SceneSwitchController.getInstance();

    SupplierService supplierService = ServiceFactory.getInstance().getServiceType(ServiceType.SUPPLIER);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtId.setText(supplierService.generateSupplierId());

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCompany.setCellValueFactory(new PropertyValueFactory<>("company"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tblSupplier.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            setTextToValues(newValue);
        });

        loadTable();

    }



    @FXML
    void AddbtnOnAction(ActionEvent event) {
        Supplier supplier =new Supplier(
                txtId.getText(),
                txtName.getText(),
                txtcompany.getText(),
                txtEmail.getText()
        );



        if (!txtName.getText().equals("") && !txtEmail.getText().equals("") &&!txtcompany.getText().equals("")) {


            boolean b = supplierService.addSupplier(supplier);
            if (b) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Supplier Added");
                alert.setContentText("Supplier Added Successfully..!");
                alert.showAndWait();
                clear();
                txtId.setText(supplierService.generateSupplierId());

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
            alert.setContentText("Are you sure want to delete this Supplier");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get()== ButtonType.OK){
                boolean isDeleted = supplierService.deleteSupplier(txtId.getText());
                if (isDeleted){
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Supplier Deleted");
                    alert2.setContentText("Supplier deleted successfully");
                    alert2.showAndWait();
                    clear();
                    loadTable();
                    txtId.setText(supplierService.generateSupplierId());
                }
            }
        }

    }

    @FXML
    void UpdatebtnOnAction(ActionEvent event) {
        if (!txtEmail.getText().equals("") && !txtcompany.getText().equals("") && !txtName.getText().equals("")){
            Supplier supplier = new Supplier(
                    txtId.getText(),
                    txtName.getText(),
                    txtcompany.getText(),
                    txtEmail.getText()

            );

            boolean isUpdated = supplierService.updateSupplier(supplier);
            if (isUpdated){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Supplier Update");
                alert.setContentText("Supplier Updated Successfully");
                alert.showAndWait();
                clear();
                loadTable();
                txtId.setText(supplierService.generateSupplierId());

            }
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Something Missing");
            alert.setContentText("Please Check your Form again..!!!");
            alert.showAndWait();
        }


    }


    @FXML
    void rowdata(MouseEvent event) {

    }

    public void btnsearchOnAction(ActionEvent actionEvent) {
        Supplier supplier =supplierService.searchByName(txtSearch.getText());
        setTextToValues(supplier);

    }

    private void setTextToValues(Supplier newValue) {
        txtId.setText(newValue.getId());
        txtName.setText(newValue.getName());
        txtcompany.setText(newValue.getCompany());
        txtEmail.setText(newValue.getEmail());
    }
    private  void loadTable(){
        tblSupplier.setItems(supplierService.getAllSupplier());

    }
    public void clear(){
        txtId.setText("");
        txtName.setText("");
        txtEmail.setText("");
        txtcompany.setText("");
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
