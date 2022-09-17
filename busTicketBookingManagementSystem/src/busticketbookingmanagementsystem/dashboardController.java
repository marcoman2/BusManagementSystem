/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package busticketbookingmanagementsystem;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author MarcoMan 
 * PLEASE SUBSCRIBE OUR CHANNEL FOR SUPPORT AND HIT THE LIKE BUTTON THANKS : )
 */
public class dashboardController implements Initializable {

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button close;

    @FXML
    private Button minimize;

    @FXML
    private Label username;

    @FXML
    private Button dashboard_Btn;

    @FXML
    private Button availableB_Btn;

    @FXML
    private Button bookingTicket_Btn;

    @FXML
    private Button customers_btn;

    @FXML
    private Button logout;

    @FXML
    private AnchorPane dashboard_form;

    @FXML
    private Label dashboard_availableB;

    @FXML
    private Label dashboard_incomeToday;

    @FXML
    private Label dashboard_totalIncome;

    @FXML
    private AreaChart<?, ?> dashboard_chart;

    @FXML
    private AnchorPane availableB_form;

    @FXML
    private TextField availableB_busID;

    @FXML
    private TextField availableB_location;

    @FXML
    private ComboBox<?> availableB_status;

    @FXML
    private TextField availableB_price;

    @FXML
    private DatePicker availableB_date;

    @FXML
    private Button availableB_addBtn;

    @FXML
    private Button availableB_updateBtn;

    @FXML
    private Button availableB_resetBtn;

    @FXML
    private Button availableB_deleteBtn;

    @FXML
    private TableView<busData> availableB_tableView;

    @FXML
    private TableColumn<busData, String> availableB_col_busID;

    @FXML
    private TableColumn<busData, String> availableB_col_location;

    @FXML
    private TableColumn<busData, String> availableB_col_status;

    @FXML
    private TableColumn<busData, String> availableB_col_price;

    @FXML
    private TableColumn<busData, String> availableB_col_date;

    @FXML
    private TextField availableB_search;

    @FXML
    private AnchorPane bookingTicket_form;

    @FXML
    private ComboBox<?> bookingTicket_busId;

    @FXML
    private ComboBox<?> bookingTicket_location;

    @FXML
    private ComboBox<?> bookingTicket_type;

    @FXML
    private ComboBox<?> bookingTicket_ticketNum;

    @FXML
    private TextField bookingTicket_firstName;

    @FXML
    private TextField bookingTicket_lastName;

    @FXML
    private ComboBox<?> bookingTicket_gender;

    @FXML
    private TextField bookingTicket_phoneNum;

    @FXML
    private DatePicker bookingTicket_date;

    @FXML
    private Button bookingTicket_selectBtn;

    @FXML
    private Button bookingTicket_resetBtn;

    @FXML
    private Label bookingTicket_sci_firstName;

    @FXML
    private Label bookingTicket_sci_lastNmae;

    @FXML
    private Label bookingTicket_sci_gender;

    @FXML
    private Label bookingTicket_sci_phoneNum;

    @FXML
    private Label bookingTicket_sci_date;

    @FXML
    private Label bookingTicket_sci_busID;

    @FXML
    private Label bookingTicket_sci_location;

    @FXML
    private Label bookingTicket_sci_type;

    @FXML
    private Label bookingTicket_sci_ticketNum;

    @FXML
    private Label bookingTicket_sci_total;

    @FXML
    private Button bookingTicket_sci_pay;

    @FXML
    private Button bookingTicket_sci_receipt;

    @FXML
    private AnchorPane customer_Form;

    @FXML
    private TableView<customerData> customers_tableView;

    @FXML
    private TableColumn<customerData, String> customers_customerNum;

    @FXML
    private TableColumn<customerData, String> customers_ticketNum;

    @FXML
    private TableColumn<customerData, String> customers_firstName;

    @FXML
    private TableColumn<customerData, String> customers_lastName;

    @FXML
    private TableColumn<customerData, String> customers_gender;

    @FXML
    private TableColumn<customerData, String> customers_phoneNum;

    @FXML
    private TableColumn<customerData, String> customers_busID;

    @FXML
    private TableColumn<customerData, String> customers_location;

    @FXML
    private TableColumn<customerData, String> customers_type;

    @FXML
    private TableColumn<customerData, String> customers_date;

    @FXML
    private TextField customers_search;

//    DATABASE TOOLS
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Statement statement;

//    LETS WORK FOR AVAILABLE BUSES FORM FIRST : ) 
    public void availableBusAdd() {

        String addData = "INSERT INTO bus (bus_id,location,status,price,date) VALUES(?,?,?,?,?)";

        connect = database.connectDb();

        try {

            Alert alert;

//             CHECK IF THE FIELDS ARE EMPTY
            if (availableB_busID.getText().isEmpty()
                    || availableB_location.getText().isEmpty()
                    || availableB_status.getSelectionModel().getSelectedItem() == null
                    || availableB_price.getText().isEmpty()
                    || availableB_date.getValue() == null) {

                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();

            } else {

//                CHECK IF THE BUS ID IS ALREADY EXIST
                String check = "SELECT bus_id FROM bus WHERE bus_id = '"
                        + availableB_busID.getText() + "'";

                statement = connect.createStatement();
                result = statement.executeQuery(check);

                if (result.next()) {

                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Bus ID: " + availableB_busID.getText() + " was already exist!");
                    alert.showAndWait();

                } else {

                    prepare = connect.prepareStatement(addData);
                    prepare.setString(1, availableB_busID.getText());
                    prepare.setString(2, availableB_location.getText());
                    prepare.setString(3, (String) availableB_status.getSelectionModel().getSelectedItem());
                    prepare.setString(4, availableB_price.getText());
                    prepare.setString(5, String.valueOf(availableB_date.getValue()));

                    prepare.executeUpdate();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!");
                    alert.showAndWait();

//                    TO UPDATE YOUR TABLE VIEW ONCE THE DATA IS SUCCESSFUL
                    availableBShowBusData();
                    availableBusReset();

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void availableBusUpdate() {

        String updateData = "UPDATE bus SET location = '"
                + availableB_location.getText() + "', status = '"
                + availableB_status.getSelectionModel().getSelectedItem()
                + "', price = '" + availableB_price.getText()
                + "', date = '" + availableB_date.getValue()
                + "' WHERE bus_id = '" + availableB_busID.getText() + "'";

        connect = database.connectDb();

        Alert alert;

        try {

            if (availableB_busID.getText().isEmpty()
                    || availableB_location.getText().isEmpty()
                    || availableB_status.getSelectionModel().getSelectedItem() == null
                    || availableB_price.getText().isEmpty()
                    || availableB_date.getValue() == null) {

                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please select the item first");
                alert.showAndWait();

            } else {

                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to UPDATE Bus ID: " + availableB_busID.getText() + "?");

                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {

                    prepare = connect.prepareStatement(updateData);
                    prepare.executeUpdate();
                    
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Updated!");
                    alert.showAndWait();

                    availableBShowBusData();
                    availableBusReset();
                    
                } else {
                    return;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void availableBusDelete(){
        
        String deleteData = "DELETE FROM bus WHERE bus_id = '"
                +availableB_busID.getText()+"'";
        
        connect = database.connectDb();
        
        try{
            
            Alert alert;
            
            if (availableB_busID.getText().isEmpty()
                    || availableB_location.getText().isEmpty()
                    || availableB_status.getSelectionModel().getSelectedItem() == null
                    || availableB_price.getText().isEmpty()
                    || availableB_date.getValue() == null) {

                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please select the item first");
                alert.showAndWait();

            } else {
                
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to delete Bus ID: " + availableB_busID.getText() + "?");
                
                Optional<ButtonType> option = alert.showAndWait();
                if(option.get().equals(ButtonType.OK)){
                    
                    statement = connect.createStatement();
                    statement.executeUpdate(deleteData);
                    
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Deleted!");
                    alert.showAndWait();
                    
                    availableBShowBusData();
                    availableBusReset();
                    
                }else{
                    return;
                }
            }
//            NOW LETS PROCEED TO BOOKING TICKET : ) 
        }catch(Exception e){e.printStackTrace();}
        
    }

    public void availableBusReset() {

        availableB_busID.setText("");
        availableB_location.setText("");
        availableB_status.getSelectionModel().clearSelection();
        availableB_price.setText("");
        availableB_date.setValue(null);

    }

    private String[] statusList = {"Available", "Not Available"};

    public void comboBoxStatus() {

        List<String> listS = new ArrayList<>();

        for (String data : statusList) {
            listS.add(data);
        }

        ObservableList listStatus = FXCollections.observableArrayList(listS);
        availableB_status.setItems(listStatus);

    }

    public ObservableList<busData> availableBusBusData() {

        ObservableList<busData> busListData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM bus";

        connect = database.connectDb();

        try {

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            busData busD;

            while (result.next()) {
                busD = new busData(result.getInt("bus_id"),
                        result.getString("location"),
                        result.getString("status"),
                        result.getDouble("price"),
                        result.getDate("date"));

                busListData.add(busD);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return busListData;

    }

    private ObservableList<busData> availableBBusListData;

    public void availableBShowBusData() {

        availableBBusListData = availableBusBusData();

        availableB_col_busID.setCellValueFactory(new PropertyValueFactory<>("busId"));
        availableB_col_location.setCellValueFactory(new PropertyValueFactory<>("location"));
        availableB_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        availableB_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        availableB_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));

        availableB_tableView.setItems(availableBBusListData);

    }

    public void avaialbleBSelectBusData() {

        busData busD = availableB_tableView.getSelectionModel().getSelectedItem();
        int num = availableB_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        availableB_busID.setText(String.valueOf(busD.getBusId()));
        availableB_location.setText(busD.getLocation());
        availableB_price.setText(String.valueOf(busD.getPrice()));
        availableB_date.setValue(LocalDate.parse(String.valueOf(busD.getDate())));

    }
    
    public void availableSearch(){
        
        FilteredList<busData> filter = new FilteredList<>(availableBBusListData, e-> true);
        
        availableB_search.textProperty().addListener((Observable, oldValue, newValue) ->{
            
            filter.setPredicate(predicateBusData ->{
                
                if(newValue.isEmpty() || newValue == null){
                    return true;
                }
                
                String searchKey = newValue.toLowerCase();
//                NOTHING? THEN WE NEED TO DO THIS FIRST
                if(predicateBusData.getBusId().toString().contains(searchKey)){
//                    NOTE, IF INTEGER OR IF THE DATA TYPE IS NOT STRING, YOU MUST BE DO toString()
                    return true;
                }else if(predicateBusData.getLocation().toLowerCase().contains(searchKey)){
                    return true;
                }else if(predicateBusData.getStatus().toLowerCase().contains(searchKey)){
                    return true;
                }else if(predicateBusData.getDate().toString().contains(searchKey)){
                    return true;
                }else if(predicateBusData.getPrice().toString().contains(searchKey)){
                    return true;
                }else return false;
                
            });
        });
        
        SortedList<busData> sortList = new SortedList<>(filter);
        
        sortList.comparatorProperty().bind(availableB_tableView.comparatorProperty());
        availableB_tableView.setItems(sortList);
    }
    
    public void busIdList(){
        
        String busD = "SELECT * FROM bus WHERE status = 'Available'";
        
        connect = database.connectDb();
        
        try{
            prepare = connect.prepareStatement(busD);
            result = prepare.executeQuery();
            
            ObservableList listB = FXCollections.observableArrayList();
            
            while(result.next()){
                
                listB.add(result.getString("bus_id"));
            }
            bookingTicket_busId.setItems(listB);
            
            ticketNumList();
            
        }catch(Exception e){e.printStackTrace();}
        
    }
    
    public void LocationList(){
        
        String locationL = "SELECT * FROM bus WHERE status = 'Available'";
        
        connect = database.connectDb();
        
        try{
            
            prepare = connect.prepareStatement(locationL);
            result = prepare.executeQuery();
            
            ObservableList listL = FXCollections.observableArrayList();
            
            while(result.next()){
                
                listL.add(result.getString("location"));
            }
            
            bookingTicket_location.setItems(listL);
            
        }catch(Exception e){e.printStackTrace();}
        
    }
    
    private String[] listT = {"First Class", "Economy Class"};
    
    public void typeList(){
        
        List<String> tList = new ArrayList<>();
        
        for(String data : listT){
            tList.add(data);
        }
        
        ObservableList listType = FXCollections.observableArrayList(tList);
        bookingTicket_type.setItems(listType);
        
    }
    
    public void ticketNumList(){
        List<String> listTicket = new ArrayList<>();
        for(int q = 1; q <= 40; q++){
            listTicket.add(String.valueOf(q));
        }
//        40 ARE OUR CAPACITY SEATS

        String removeSeat = "SELECT seatNum FROM customer WHERE bus_id='"
                +bookingTicket_busId.getSelectionModel().getSelectedItem()+"'";

        connect = database.connectDb();
        
        try{
            prepare = connect.prepareStatement(removeSeat);
            result = prepare.executeQuery();
            
            while(result.next()){
                listTicket.remove(result.getString("seatNum"));
            }
            
            ObservableList listTi = FXCollections.observableArrayList(listTicket);
            
            bookingTicket_ticketNum.setItems(listTi);
//            IT WORKS GOOD!
        }catch(Exception e){e.printStackTrace();}
    }
    
//    NOW LETS CREATE TABLE FOR customer

    private double priceData = 0;
    private double totalP = 0;
    public void bookingTicketSelect(){
        
        String firstName = bookingTicket_firstName.getText();
        String lastName = bookingTicket_lastName.getText();
        String gender = (String)bookingTicket_gender.getSelectionModel().getSelectedItem();
        String phoneNumber = bookingTicket_phoneNum.getText();
        String date = String.valueOf(bookingTicket_date.getValue());
        
        String busId = (String)bookingTicket_busId.getSelectionModel().getSelectedItem();
        String location = (String)bookingTicket_location.getSelectionModel().getSelectedItem();
        String type = (String)bookingTicket_type.getSelectionModel().getSelectedItem();
        String ticketNum = (String)bookingTicket_ticketNum.getSelectionModel().getSelectedItem();
        
        Alert alert;
        
        if(firstName == null || lastName == null 
                || gender == null || phoneNumber == null || date == null
                || busId == null || location == null
                || type == null || ticketNum == null){
            
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
            
        }else{
            
            String totalPrice = "SELECT price FROM bus WHERE location = '"
                    +location+"'";
            
            try{
                
                connect = database.connectDb();
                
                prepare = connect.prepareStatement(totalPrice);
                result = prepare.executeQuery();
                
                if(result.next()){
                    priceData = result.getDouble("price");
                }
                
                if(type == "First Class"){
                    totalP = (priceData + 100);
                }else if(type == "Economy Class"){
                    totalP = priceData; 
                }
            }catch(Exception e){e.printStackTrace();}
            
            bookingTicket_sci_total.setText("$"+String.valueOf(totalP));
            bookingTicket_sci_firstName.setText(firstName);
            bookingTicket_sci_lastNmae.setText(lastName);
            bookingTicket_sci_gender.setText(gender);
            bookingTicket_sci_phoneNum.setText(phoneNumber);
            bookingTicket_sci_date.setText(date);
            
            bookingTicket_sci_busID.setText(busId);
            bookingTicket_sci_location.setText(location);
            bookingTicket_sci_type.setText(type);
            bookingTicket_sci_ticketNum.setText(ticketNum);
            
            alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Message");
            alert.setHeaderText(null);
            alert.setContentText("Successfully Selected!");
            alert.showAndWait();
            
            bookingTicketReset();
            
        }
    }
    
    public void bookingTicketReset(){
        
        bookingTicket_firstName.setText("");
        bookingTicket_lastName.setText("");
        bookingTicket_gender.getSelectionModel().clearSelection();
        bookingTicket_phoneNum.setText("");
        bookingTicket_date.setValue(null);
        
    }
    
    private String[] genderL = {"Male","Female","Others"};
    
    public void genderList(){
        
        List<String> listG = new ArrayList<>();
        
        for(String data : genderL){
            listG.add(data);
        }
        
        ObservableList gList = FXCollections.observableArrayList(listG);
        bookingTicket_gender.setItems(gList);
        
    }
    
    private int countRow;
    public void bookingTicketPay(){
        
        String firstName = bookingTicket_sci_firstName.getText();
        String lastName = bookingTicket_sci_lastNmae.getText();
        String gender = bookingTicket_sci_gender.getText();
        String phoneNumber = bookingTicket_sci_phoneNum.getText();
        String date = bookingTicket_sci_date.getText();
        
        String busId = bookingTicket_sci_busID.getText();
        String location = bookingTicket_sci_location.getText();
        String type = bookingTicket_sci_type.getText();
        String seatNum = bookingTicket_sci_ticketNum.getText();
        
        String payData = "INSERT INTO customer (customer_id,firstName,lastName,gender,phoneNumber,bus_id,location,type,seatNum,total,date)"
                + " VALUES(?,?,?,?,?,?,?,?,?,?,?)";
        
        connect = database.connectDb();
        
        try{
            
            Alert alert;
            
            String countNum = "SELECT COUNT(id) FROM customer";
            statement = connect.createStatement();
            result = statement.executeQuery(countNum);
            
            while(result.next()){
                countRow = result.getInt("COUNT(id)");
            }
            
//            CHECK IF EMPTY
            if(bookingTicket_sci_firstName.getText().isEmpty()
                    || bookingTicket_sci_lastNmae.getText().isEmpty()
                    || bookingTicket_sci_gender.getText().isEmpty()
                    || bookingTicket_sci_phoneNum.getText().isEmpty()
                    || bookingTicket_sci_date.getText().isEmpty()
                    || bookingTicket_sci_busID.getText().isEmpty()
                    || bookingTicket_sci_location.getText().isEmpty()
                    || bookingTicket_sci_type.getText().isEmpty()
                    || bookingTicket_sci_ticketNum.getText().isEmpty()
                    || totalP == 0){
                
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please select the information first");
                alert.showAndWait();
                
            }else{
            
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure?");
                alert.showAndWait();
//                WE NEED TO REMOVE THE SEAT# IF THE CUSTOMER IS ALREADY CHOSE THAT 
                prepare = connect.prepareStatement(payData);
                prepare.setString(1, String.valueOf(countRow+1));
                prepare.setString(2, firstName);
                prepare.setString(3, lastName);
                prepare.setString(4, gender);
                prepare.setString(5, phoneNumber);
                prepare.setString(6, busId);
                prepare.setString(7, location);
                prepare.setString(8, type);
                prepare.setString(9, seatNum);
                prepare.setString(10, String.valueOf(totalP));
                prepare.setString(11, date);
                
                prepare.executeUpdate();
                
                String receiptData = "INSERT INTO customer_receipt (customer_id,total,date) VALUES(?,?,?)";
                
                getData.number = (countRow + 1);
                
                prepare = connect.prepareStatement(receiptData);
                prepare.setString(1, String.valueOf(countRow+1));
                prepare.setString(2, String.valueOf(totalP));
                prepare.setString(3, date);
                
                prepare.executeUpdate();
                
                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Successful!");
                alert.showAndWait();
//                NOW LETS PROCEED TO RECEIPT
                bookingTicket_sci_firstName.setText("");
                bookingTicket_sci_lastNmae.setText("");
                bookingTicket_sci_gender.setText("");
                bookingTicket_sci_phoneNum.setText("");
                bookingTicket_sci_date.setText("");
                bookingTicket_sci_busID.setText("");
                bookingTicket_sci_location.setText("");
                bookingTicket_sci_type.setText("");
                bookingTicket_sci_ticketNum.setText("");
                bookingTicket_sci_total.setText("$0.0");
//                WE NEED TO INSERT THE DATA ON CUSTOMER_RECEIPT FOR OUR RECEIPT : ) 
            }
        }catch(Exception e){e.printStackTrace();}
    }
    
    public void bookingTicketReceipt(){
        
        HashMap hash = new HashMap();
        hash.put("busD", getData.number);
        
        try{
            
            if(totalP > 0){
            
                JasperDesign jDesign = JRXmlLoader.load("C:\\Users\\WINDOWS 10\\Documents\\NetBeansProjects\\busTicketBookingManagementSystem\\src\\busticketbookingmanagementsystem\\report.jrxml");
                JasperReport jReport = JasperCompileManager.compileReport(jDesign);
                JasperPrint jPrint = JasperFillManager.fillReport(jReport, hash, connect);

                JasperViewer.viewReport(jPrint, false);
            }else{
                
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Invalid :3");
                alert.showAndWait();
                
            }
        }catch(Exception e){e.printStackTrace();}
    }
//    NOW LETS PROCEED TO CUSTOMER FORM : ) 
    
    public ObservableList<customerData> customersDataList(){
        
        ObservableList<customerData> customerList = FXCollections.observableArrayList();
        
        String sql = "SELECT * FROM customer";
        
        connect = database.connectDb();
        
        try{
            
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            customerData custD;
            
            while(result.next()){
                
                custD = new customerData(result.getInt("customer_id")
                        , result.getString("firstName")
                        , result.getString("lastName")
                        , result.getString("gender")
                        , result.getString("phoneNumber")
                        , result.getInt("bus_id")
                        , result.getString("location")
                        , result.getString("type")
                        , result.getInt("seatNum")
                        , result.getDouble("total")
                        , result.getDate("date"));
                
                customerList.add(custD);
                
            }
            
        }catch(Exception e){e.printStackTrace();}
        return customerList;
    }
    
    private ObservableList<customerData> customersDataL;
    public void customersShowDataList(){
        
        customersDataL = customersDataList();
        
        customers_customerNum.setCellValueFactory(new PropertyValueFactory<>("customerNum"));
        customers_ticketNum.setCellValueFactory(new PropertyValueFactory<>("seatNum"));
        customers_firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        customers_lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        customers_phoneNum.setCellValueFactory(new PropertyValueFactory<>("phoneNum"));
        customers_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        customers_busID.setCellValueFactory(new PropertyValueFactory<>("busId"));
        customers_location.setCellValueFactory(new PropertyValueFactory<>("location"));
        customers_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        customers_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        
        customers_tableView.setItems(customersDataL);
        
    }
    
    public void customersSearch(){
        
        FilteredList<customerData> filter = new FilteredList<>(customersDataL, e-> true);
        
        customers_search.textProperty().addListener((Observable, oldValue, newValue) ->{
            
            filter.setPredicate(predicateCustomerData ->{
                
                if(newValue == null || newValue.isEmpty()){
                    return true;
                }
                
                String searchKey = newValue.toLowerCase();
                
                if(predicateCustomerData.getCustomerNum().toString().contains(searchKey)){
                    return true;
                }else if(predicateCustomerData.getSeatNum().toString().contains(searchKey)){
                    return true;
                }else if(predicateCustomerData.getFirstName().toLowerCase().contains(searchKey)){
                    return true;
                }else if(predicateCustomerData.getLastName().toLowerCase().contains(searchKey)){
                    return true;
                }else if(predicateCustomerData.getGender().toLowerCase().contains(searchKey)){
                    return true;
                }else if(predicateCustomerData.getPhoneNum().toLowerCase().contains(searchKey)){
                    return true;
                }else if(predicateCustomerData.getBusId().toString().contains(searchKey)){
                    return true;
                }else if(predicateCustomerData.getLocation().toLowerCase().contains(searchKey)){
                    return true;
                }else if(predicateCustomerData.getTotal().toString().contains(searchKey)){
                    return true;
                }else if(predicateCustomerData.getType().toLowerCase().contains(searchKey)){
                    return true;
                }else if(predicateCustomerData.getDate().toString().contains(searchKey)){
                    return true;
                }else return false;
                
            });
        });
        
        SortedList<customerData> sortList = new SortedList<>(filter);
        
        sortList.comparatorProperty().bind(customers_tableView.comparatorProperty());
        customers_tableView.setItems(sortList);
    }
    
    private double x = 0;
    private double y = 0;
    public void logout() {

        try {

            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you wsnt to logout?");

            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {

                logout.getScene().getWindow().hide();
//                LOGIN FORM 
                Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);

                root.setOnMousePressed((MouseEvent event) -> {
                    x = event.getSceneX();
                    y = event.getSceneY();
                });

                root.setOnMouseDragged((MouseEvent event) -> {

                    stage.setX(event.getScreenX() - x);
                    stage.setY(event.getScreenY() - y);

                    stage.setOpacity(.8);
                });
                root.setOnMouseReleased((MouseEvent event) -> {
                    stage.setOpacity(1);
                });

                stage.initStyle(StageStyle.TRANSPARENT);

                stage.setScene(scene);
                stage.show();

            } else {
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    

    public void defaultBtn() {
        dashboard_Btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #a73f4a, #3ea763)");
        availableB_Btn.setStyle("-fx-background-color:transparent");
        bookingTicket_Btn.setStyle("-fx-background-color:transparent");
        customers_btn.setStyle("-fx-background-color:transparent");
    }

    public void switchForm(ActionEvent event) {

        if (event.getSource() == dashboard_Btn) {
            dashboard_form.setVisible(true);
            availableB_form.setVisible(false);
            bookingTicket_form.setVisible(false);
            customer_Form.setVisible(false);

            dashboard_Btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #a73f4a, #3ea763)");
            availableB_Btn.setStyle("-fx-background-color:transparent");
            bookingTicket_Btn.setStyle("-fx-background-color:transparent");
            customers_btn.setStyle("-fx-background-color:transparent");

            dashboardDisplayAB();
            dashboardDisplayIT();
            dashboardDisplayTI();
            dashboardChart();
            
        } else if (event.getSource() == availableB_Btn) {
            dashboard_form.setVisible(false);
            availableB_form.setVisible(true);
            bookingTicket_form.setVisible(false);
            customer_Form.setVisible(false);

            availableB_Btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #a73f4a, #3ea763)");
            dashboard_Btn.setStyle("-fx-background-color:transparent");
            bookingTicket_Btn.setStyle("-fx-background-color:transparent");
            customers_btn.setStyle("-fx-background-color:transparent");

//            TO UPDATE THE FORM ONCE YOU CLICK THE AVAIALABLE BUSES BUTTON 
            availableBShowBusData();
            availableSearch();

        } else if (event.getSource() == bookingTicket_Btn) {
            dashboard_form.setVisible(false);
            availableB_form.setVisible(false);
            bookingTicket_form.setVisible(true);
            customer_Form.setVisible(false);

            bookingTicket_Btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #a73f4a, #3ea763)");
            availableB_Btn.setStyle("-fx-background-color:transparent");
            dashboard_Btn.setStyle("-fx-background-color:transparent");
            customers_btn.setStyle("-fx-background-color:transparent");

            busIdList();
            LocationList();
            typeList();
            ticketNumList();
            genderList();
            
        } else if (event.getSource() == customers_btn) {
            dashboard_form.setVisible(false);
            availableB_form.setVisible(false);
            bookingTicket_form.setVisible(false);
            customer_Form.setVisible(true);

            customers_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #a73f4a, #3ea763)");
            availableB_Btn.setStyle("-fx-background-color:transparent");
            bookingTicket_Btn.setStyle("-fx-background-color:transparent");
            dashboard_Btn.setStyle("-fx-background-color:transparent");

            customersShowDataList();
            customersSearch();
//            LETS PROCEED TO OUR DASHBOARD FORM : ) 
        }
    }
    
    private int countAB = 0;
    public void dashboardDisplayAB(){
        
        String sql = "SELECT COUNT(id) FROM bus WHERE status = 'Available'";
        
        connect = database.connectDb();
        
        try{
            
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            while(result.next()){
                countAB = result.getInt("COUNT(id)");
            }
            
            dashboard_availableB.setText(String.valueOf(countAB));
            
        }catch(Exception e){e.printStackTrace();}
        
    }
    
    private double incomeToday = 0;
    public void dashboardDisplayIT(){
        
        Date date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        
        String sql = "SELECT SUM(total) FROM customer WHERE date ='"+sqlDate+"'";
        
        connect = database.connectDb();
        
        try{
            
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            while(result.next()){
                incomeToday = result.getDouble("SUM(total)");
            }
            
            dashboard_incomeToday.setText("$"+String.valueOf(incomeToday));
            
        }catch(Exception e){e.printStackTrace();}
        
    }
    
    private double totalIncome;
    public void dashboardDisplayTI(){
        
        String sql = "SELECT SUM(total) FROM customer";
        
        connect = database.connectDb();
        
        try{
            
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            while(result.next()){
                totalIncome = result.getDouble("SUM(total)");
            }
            
            dashboard_totalIncome.setText("$"+String.valueOf(totalIncome));
            
        }catch(Exception e){e.printStackTrace();}
        
    }
    
    public void dashboardChart(){
        
        dashboard_chart.getData().clear();
        
        String sql = "SELECT date,SUM(total) FROM customer WHERE date != '' GROUP BY date ORDER BY TIMESTAMP(date) ASC LIMIT 9";
        
        connect = database.connectDb();
        
        XYChart.Series chart = new XYChart.Series();
        
        try{
//            THATS IT FOR THIS VIDEO, THANKS FOR WATCHING!!
//            
//            DONT FORGET TO SUBSCRIBE AND HIT THE NOTIF BELL TO BECOME UPDATED!
//            DONT FORGET ALSO TO HIT THE LIKE BUTTON FOR SUPPORT : ) 
//            
//            THANKS GUYS!
//            
//            HAPPY LEARNINGS!
            
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            while(result.next()){
                chart.getData().add(new XYChart.Data(result.getString(1), result.getInt(2)));
            }
            
            dashboard_chart.getData().add(chart);
            
        }catch(Exception e){e.printStackTrace();}
        
    }
    
    public void displayUsername(){
        username.setText(getData.username);
    }

    public void close() {
        System.exit(0);
    }
//NOW LETS PROCEED TO OUR CHART
    public void minimize() {
        Stage stage = (Stage) main_form.getScene().getWindow();
        stage.setIconified(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        defaultBtn();
        displayUsername();
        
        dashboardDisplayAB();
        dashboardDisplayIT();
        dashboardDisplayTI();
        dashboardChart();

        comboBoxStatus();
        availableBShowBusData();

        busIdList();
        LocationList();
        typeList();
        ticketNumList();
        genderList();
        
        customersShowDataList();
        
    }

}
