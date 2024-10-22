package edu.icet.controller;

import edu.icet.dto.Employee;
import edu.icet.service.ServiceFactory;
import edu.icet.service.custom.EmployeeService;
import edu.icet.util.ServiceType;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class LoginFormController implements Initializable {

    @FXML
    private AnchorPane Anchor;

    @FXML
    private Pane body;

    @FXML
    private Button forgetPassword;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUserName;

    SceneSwitchController sceneSwitch = SceneSwitchController.getInstance();
    EmployeeService employeeService = ServiceFactory.getInstance().getServiceType(ServiceType.EMPLOYEE);

    ObservableList<Employee> allEmployee;

    @FXML
    void loginbtnOnAction(ActionEvent event) throws IOException {
        if (txtUserName.getText().equals("Admin") && txtPassword.getText().equals("1234")){
            sceneSwitch.switchScene(Anchor, "dash.fxml");
        }else {
            for (Employee employee : allEmployee){
                if (employee.getEmail().equals(txtUserName.getText())){
                    if (employee.getPassword().equals(txtPassword.getText())){
                        sceneSwitch.switchScene(Anchor,"dash_E_form.fxml");
                    } else {
                        new Alert(Alert.AlertType.ERROR,"UserName Wrong !").show();
                    }
                } else if (employee.getPassword().equals(txtPassword.getText())) {
                    if (employee.getEmail().equals(txtUserName.getText())){
                        sceneSwitch.switchScene(Anchor,"dash_E_form.fxml");
                    }else {
                        new Alert(Alert.AlertType.ERROR,"Password Wrong !").show();
                    }

                }else {
                    new Alert(Alert.AlertType.ERROR,"UserName & Password  Wrong !").show();
                }

            }

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        allEmployee= employeeService.getAllEmployee();
    }
}
