package edu.icet.controller;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
            sceneSwitch.switchScene(Anchor, "customer_E_form.fxml");

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


    public void btnGenerateReportOnAction(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save PDF Report");

        fileChooser.setInitialFileName("OrderReport.pdf");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));


        File file = fileChooser.showSaveDialog(new Stage());

        if (file != null) {
            Document document = new Document();

            try {
                PdfWriter.getInstance(document, new FileOutputStream(file));
                document.open();

                Font titleFont = new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD, BaseColor.GREEN);
                Paragraph title = new Paragraph("Order Report", titleFont);
                title.setAlignment(Element.ALIGN_CENTER);
                title.setSpacingAfter(20f);
                document.add(title);

                // Create a table with 4 columns
                PdfPTable table = new PdfPTable(4);
                table.setWidthPercentage(100);
                table.setSpacingBefore(10f);
                table.setSpacingAfter(10f);


                Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.WHITE);
                PdfPCell headerCell;

                String[] headers = {"Order ID", "Date", "Net Total", "Customer ID"};
                for (String header : headers) {
                    headerCell = new PdfPCell(new Phrase(header, headerFont));
                    headerCell.setBackgroundColor(BaseColor.DARK_GRAY);
                    headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    headerCell.setPadding(10f);
                    table.addCell(headerCell);
                }


                Font dataFont = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK);
                PdfPCell dataCell;

                ObservableList<ViewOrderTblObj> orders = tblViewOrders.getItems();
                for (ViewOrderTblObj order : orders) {
                    // Order ID
                    dataCell = new PdfPCell(new Phrase(order.getId(), dataFont));
                    dataCell.setPadding(8f);
                    dataCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(dataCell);

                    // Date
                    dataCell = new PdfPCell(new Phrase(order.getDate().toString(), dataFont));
                    dataCell.setPadding(8f);
                    dataCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(dataCell);

                    // Net Total
                    dataCell = new PdfPCell(new Phrase(String.valueOf(order.getNetTotal()), dataFont));
                    dataCell.setPadding(8f);
                    dataCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(dataCell);

                    // Customer ID
                    dataCell = new PdfPCell(new Phrase(order.getCustomerId(), dataFont));
                    dataCell.setPadding(8f);
                    dataCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(dataCell);
                }


                document.add(table);

                document.close();

            } catch (DocumentException | FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }
}
