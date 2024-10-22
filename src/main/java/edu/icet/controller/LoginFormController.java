package edu.icet.controller;

import edu.icet.dto.Employee;
import edu.icet.service.ServiceFactory;
import edu.icet.service.custom.EmployeeService;
import edu.icet.util.ServiceType;
import edu.icet.util.Validator;
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


    Validator validator =new Validator();

    @FXML
    void loginbtnOnAction(ActionEvent event) throws IOException {
        if (txtUserName.getText().equals("Admin") && txtPassword.getText().equals("1234")){
            sceneSwitch.switchScene(Anchor, "dash.fxml");
        }else{
            if (!validator.isValidEmail(txtUserName.getText())){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setContentText("Enter valid username!!");
                alert.showAndWait();
                return;
            }else {
                Employee employee =employeeService.searchByEmail(txtUserName.getText());
                if (employee==null){
                    new Alert(Alert.AlertType.ERROR,"Loging Error !").show();
                }else{
                    if (employee.getEmail().equals(txtUserName.getText())){
                        if (validator.checkPassword(txtPassword.getText(),employee.getPassword())){
                            new Alert(Alert.AlertType.INFORMATION,"Login Successfully !").show();
                            sceneSwitch.switchScene(Anchor,"dash_E_form.fxml");
                        } else {
                            new Alert(Alert.AlertType.ERROR,"UserName Wrong !").show();
                        }
                    } else if (validator.checkPassword(txtPassword.getText(),employee.getPassword())) {
                        if (employee.getEmail().equals(txtUserName.getText())){
                            new Alert(Alert.AlertType.INFORMATION,"Login Successfully !").show();
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
