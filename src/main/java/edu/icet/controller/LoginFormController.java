package edu.icet.controller;

import com.jfoenix.controls.JFXButton;
import edu.icet.dto.Employee;
import edu.icet.service.ServiceFactory;
import edu.icet.service.custom.EmployeeService;
import edu.icet.util.EmailUtil;
import edu.icet.util.OTPUtil;
import edu.icet.util.ServiceType;
import edu.icet.util.Validator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
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
    private TextField txtPassword1;

    @FXML
    private TextField txtUserName;


    SceneSwitchController sceneSwitch = SceneSwitchController.getInstance();
    EmployeeService employeeService = ServiceFactory.getInstance().getServiceType(ServiceType.EMPLOYEE);

    EmailUtil emailUtil =new EmailUtil();





    Validator validator =new Validator();

    @FXML
    void loginbtnOnAction(ActionEvent event) throws IOException {
        if (txtUserName.getText().equals("Admin") && txtPassword1.getText().equals("1234")){
            new Alert(Alert.AlertType.INFORMATION,"Admin Login Successfully !").show();
            sceneSwitch.switchScene(Anchor, "dash.fxml");
        }else{
            if (!validator.isValidEmail(txtUserName.getText())){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setContentText("Enter valid username!!");
                alert.showAndWait();

            }else {
                Employee employee =employeeService.searchByEmail(txtUserName.getText());
                if (employee==null){
                    new Alert(Alert.AlertType.ERROR,"Loging Error !").show();
                }else{
                    if (employee.getEmail().equals(txtUserName.getText())){
                        if (validator.checkPassword(txtPassword1.getText(),employee.getPassword())){
                            new Alert(Alert.AlertType.INFORMATION,"Login Successfully !").show();
                            sceneSwitch.switchScene(Anchor,"dash_E_form.fxml");
                        } else {
                            new Alert(Alert.AlertType.ERROR,"UserName Wrong !").show();
                        }
                    } else if (validator.checkPassword(txtPassword1.getText(),employee.getPassword())) {
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


    @FXML
    void btnForgetPasswordbtnOnAction(ActionEvent event) {

        TextInputDialog emailDialog = new TextInputDialog();
        emailDialog.setTitle("Forgot Password");
        emailDialog.setHeaderText("Enter your email address");
        emailDialog.setContentText("Email:");

        // Apply CSS to email dialog
        emailDialog.getDialogPane().getStylesheets().add(getClass().getResource("/css/alertStyle.css").toExternalForm());

        Optional<String> emailInput = emailDialog.showAndWait();
        if (emailInput.isPresent()) {
            String email = emailInput.get();

            // Validate the email address format
            if (!validator.isValidEmail(email)) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Invalid email address! Please enter a valid email.");
                errorAlert.getDialogPane().getStylesheets().add(getClass().getResource("/css/alertStyle.css").toExternalForm());
                errorAlert.show();
                return;
            }

            // Step 2: Send OTP (Implement sendOTP(email) method to send an email with OTP)
            String otp = OTPUtil.generateOTP(); // Method to generate OTP
            boolean otpSent = emailUtil.sendOTPEmail(email,otp);//sendOTP(email, otp); // Implement this method to send OTP via email

            if (!otpSent) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Failed to send OTP! Please try again.");
                errorAlert.getDialogPane().getStylesheets().add(getClass().getResource("/css/alertStyle.css").toExternalForm());
                errorAlert.show();
                return;
            }

            // Step 3: Show another dialog to input OTP and new password
            Dialog<ButtonType> otpDialog = new Dialog<>();
            otpDialog.setTitle("Enter OTP");
            otpDialog.setHeaderText("An OTP has been sent to your email.");

            // Apply CSS to OTP dialog
            otpDialog.getDialogPane().getStylesheets().add(getClass().getResource("/css/alertStyle.css").toExternalForm());

            // Custom layout for OTP and new password fields
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));

            TextField otpField = new TextField();
            otpField.setPromptText("Enter OTP");

            TextField newPasswordField = new TextField();
            newPasswordField.setPromptText("Enter new password");

            grid.add(new Label("OTP:"), 0, 0);
            grid.add(otpField, 1, 0);
            grid.add(new Label("New Password:"), 0, 1);
            grid.add(newPasswordField, 1, 1);

            otpDialog.getDialogPane().setContent(grid);
            otpDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            Optional<ButtonType> otpResult = otpDialog.showAndWait();
            if (otpResult.isPresent() && otpResult.get() == ButtonType.OK) {
                String enteredOtp = otpField.getText();
                String newPassword = newPasswordField.getText();
                String encriptedPassword =OTPUtil.hashPassword(newPassword);
                Employee employee = employeeService.searchByEmail(email);
                employee.setPassword(encriptedPassword);
                employeeService.updateEmployee(employee);

                // Verification and reset logic
                if (enteredOtp.equals(otp)) {
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION, "Password reset successfully!");
                    successAlert.getDialogPane().getStylesheets().add(getClass().getResource("/css/alertStyle.css").toExternalForm());
                    successAlert.show();
                } else {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Invalid OTP! Please try again.");
                    errorAlert.getDialogPane().getStylesheets().add(getClass().getResource("/css/alertStyle.css").toExternalForm());
                    errorAlert.show();
                }
            }
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
