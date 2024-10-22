package edu.icet.controller;

import edu.icet.dto.Customer;
import edu.icet.dto.Employee;
import edu.icet.service.ServiceFactory;
import edu.icet.service.custom.CustomerService;
import edu.icet.service.custom.EmployeeService;
import edu.icet.util.ServiceType;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public class AdminDashboardFormController implements Initializable {
    public Label txtTime;
    public Text txtSales;
    public Label lblCustomer;
    public Label lblEmployee;
    @FXML
    private AnchorPane Anchor;
    public Label lblTitle;

    CustomerService customerService = ServiceFactory.getInstance().getServiceType(ServiceType.CUSTOMER);
    EmployeeService employeeService = ServiceFactory.getInstance().getServiceType(ServiceType.EMPLOYEE);

    SceneSwitchController sceneSwitch = SceneSwitchController.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDateAndTime();
        loadComponenets();

    }

    private void loadComponenets() {
        customerCount();
        employeeCount();

    }


    private void customerCount(){
        ObservableList<Customer> allCustomer = customerService.getAllCustomer();
        lblCustomer.setText("0");

        int count=0;
        for (Customer customer :allCustomer){
            count++;
        }

        if (count > 0){
            lblCustomer.setText(String.valueOf(count));
        }
    }

    private void employeeCount(){
        ObservableList<Employee> allEmployee = employeeService.getAllEmployee();
        lblEmployee.setText("0");

        int count=0;
        for (Employee employee :allEmployee){
            count++;
        }

        if (count > 0){
            lblEmployee.setText(String.valueOf(count));
        }


    }

    private void loadDateAndTime(){
        Date date =new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String dateNow=f.format(date);


        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime now = LocalTime.now();
            txtTime.setText(now.getHour() + " : " + now.getMinute() + " : " + now.getSecond());
        }),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();


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
