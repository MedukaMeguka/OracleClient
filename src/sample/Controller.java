package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;


public class Controller implements Initializable {
    @FXML private Button entry;
    @FXML private Button swap;
    @FXML private TextField login;
    @FXML private PasswordField password;
    @FXML private TextField empId;
    @FXML private TextField empZk;
    @FXML private TextField empFam;
    @FXML private TextField empIm;
    @FXML private TextField empOt;
    @FXML private TextField empSpec;
    @FXML private TextField empKurs;
    @FXML private TextField empGr;
    @FXML private TableView<OracleCl> tableStudents;
    @FXML private TableColumn<OracleCl, String> FAM;
    @FXML private TableColumn<OracleCl, String> IM;
    @FXML private TableColumn<OracleCl, String> OT;
    @FXML private TableColumn<OracleCl, String> SPEC;
    @FXML private TableColumn<OracleCl, String> GR;
    @FXML private TableColumn<OracleCl, Integer> NO_ZK;
    @FXML private TableColumn<OracleCl, Integer> STUD_ID;
    @FXML private TableColumn<OracleCl, Integer> KURS;

    private DBConnection con;
    private Connection connection;
    private ResultSet rs = null;
    private ObservableList<OracleCl> usersData = FXCollections.observableArrayList();
    private Alert alert = new Alert(Alert.AlertType.INFORMATION);
    private static String loginBD, passwordBD;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        con = new DBConnection();
    }


    @FXML
    private void entryInBD(ActionEvent event) {

            try {
                loginBD = login.getText();
                passwordBD = password.getText();
                connection = con.Connect(loginBD, passwordBD);
                if (!connection.isClosed()) {
                    Stage stage = (Stage) entry.getScene().getWindow();
                    stage.close();
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DB.fxml"));
                    Parent root1 = fxmlLoader.load();
                    stage = new Stage();
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.setTitle("База данных");
                    stage.setScene(new Scene(root1));
                    stage.show();
                }
            }
            catch (Exception ex) {err(ex); System.out.println(ex);}
    }

    @FXML
    private void entryAuth(ActionEvent event) throws IOException {
        Stage stage = (Stage) swap.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Авторизация");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void loadDataFromDatabase(ActionEvent event) {
        refreshtable();
        FAM.setCellValueFactory(new PropertyValueFactory<OracleCl, String>("FAM"));
        IM.setCellValueFactory(new PropertyValueFactory<OracleCl, String>("IM"));
        OT.setCellValueFactory(new PropertyValueFactory<OracleCl, String>("OT"));
        SPEC.setCellValueFactory(new PropertyValueFactory<OracleCl, String>("SPEC"));
        STUD_ID.setCellValueFactory(new PropertyValueFactory<OracleCl, Integer>("STUD_ID"));
        GR.setCellValueFactory(new PropertyValueFactory<OracleCl, String>("GR"));
        KURS.setCellValueFactory(new PropertyValueFactory<OracleCl, Integer>("KURS"));
        NO_ZK.setCellValueFactory(new PropertyValueFactory<OracleCl, Integer>("NO_ZK"));
        tableStudents.setItems(usersData);
        refreshtable();
    }

    private void err(Exception ex) {
        alert.setTitle("Ошибка");
        alert.setHeaderText(null);
        alert.setContentText("" + ex);
        alert.showAndWait();
    }

    private void refreshtable() {
        usersData.clear();
        try {
            connection = con.Connect(loginBD, passwordBD);
            rs = connection.createStatement().executeQuery("SELECT * FROM LISTOFSTUDENTS.STUDENTS");
            while (rs.next()) {
                usersData.add(new OracleCl(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getString(6), rs.getInt(7), rs.getString(8)));

            }
            tableStudents.setItems(usersData);
        }
        catch (Exception ex) {err(ex); System.out.println(ex);}
    }

    @FXML
    private void delStud(ActionEvent event) {
        Integer delID = Integer.parseInt(empId.getText());

        try {
            connection = con.Connect(loginBD, passwordBD);
            rs = connection.createStatement().executeQuery("DELETE FROM LISTOFSTUDENTS.STUDENTS\n" +
                    "WHERE STUD_ID = "+ delID +" ");
        }
        catch (Exception ex) {err(ex); System.out.println(ex);}

        refreshtable();
    }

    @FXML
    private void changeelem(ActionEvent event) {

       /* try {
            connection = con.Connect(loginBD, passwordBD);
            Statement stmt = connection.createStatement();
            rs = stmt.executeQuery(
                    "BEGIN\n"+
                     "UPDATE LISTOFSTUDENTS.STUDENTS\n" +
                          "SET NO_ZK = "+ Integer.parseInt(empZk.getText()) +",\n" +
                              "FAM = '"+ empFam.getText() +"',\n" +
                              "IM = '"+ empIm.getText() +"',\n" +
                              "OT = '"+ empOt.getText() +"',\n" +
                              "SPEC = '" + empSpec.getText() + "',\n" +
                              "KURS = "+ Integer.parseInt(empKurs.getText()) +",\n" +
                              "GR = '" + empGr.getText() + "',\n" +
                             "WHERE STUD_ID = "+ Integer.parseInt(empId.getText()) +";\n" +
                            "COMMIT; \n" +
                            "END;"
            );
        }
        catch (Exception ex) {err(ex); System.out.println(ex);} */
       try {
           delStud(event);
           addelem(event);
           refreshtable();
       }
       catch (Exception ex) {err(ex); System.out.println(ex);}
    }

    @FXML
    private void addelem(ActionEvent event) {
        Integer id = Integer.parseInt(empId.getText());
        Integer zk = Integer.parseInt(empZk.getText());
        Integer kr = Integer.parseInt(empKurs.getText());
        String fam = empFam.getText();
        String im = empIm.getText();
        String ot = empOt.getText();
        String sc = empSpec.getText();
        String gr = empGr.getText();
        try {
            connection = con.Connect(loginBD, passwordBD);
            rs = connection.createStatement().executeQuery("INSERT INTO  LISTOFSTUDENTS.STUDENTS\n" +
                    "(STUD_ID, NO_ZK, FAM, IM, OT, SPEC, KURS, GR, DATA_R)\n" +
                    "VALUES\n" +
                    "("+ id +", "+ zk +", '"+ fam +"', '"+ im +"', '"+ ot +"', '"+ sc +"', "+ kr +", '"+ gr +"', NULL)");
        }
        catch (Exception ex) {err(ex); System.out.println(ex);}
        refreshtable();
    }
}
