package com.example.dhaharpedia;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.ResultSet;

public class Login extends User{
    public static void display(){
        if (MainMenu.isLoginned) {
            MainMenu.setIsLogin(false);
            Login.display();
        }
        else {
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setTitle("Login");
            stage.initModality(Modality.APPLICATION_MODAL);

            GridPane gp = new GridPane();
            gp.setHgap(35);
            gp.add(new Text("Name: "), 0, 0);
            TextField name = new TextField();
            gp.add(name, 1, 0);
            PasswordField pass = new PasswordField();
            gp.add(new Text("Password"), 0, 1);
            gp.add(pass, 1, 1);

            Button submit = new Button("Submit");
            Button loginAsGuest = new Button("Login As a Guest");
            submit.setOnMouseClicked(e -> {
                ResultSet result;
                String sql = "SELECT * FROM nama_pass " +
                        "WHERE username =" + "'" + name.getText() + "'" + "AND password = "
                        + "'" + pass.getText() + "'";
                ;
                try {
                    Koneksi.start();
                    result = Koneksi.statement.executeQuery(sql);
                    if (result.next()) {

                        String username = result.getString("username");

                        MainMenu.setIsLogin(true);
                        MainMenu.loginAs.setText("Loginned as " + username);
                        RumahMakan.disableButton(true);
                        Main.landingPage.disableButton(true);
                        RumahMakan.disableRating(false);
                        stage.close();
                    }
                    else if (name.getText().equals("admin") && pass.getText().equals("admin")) {
                        MainMenu.setIsLogin(true);
                        MainMenu.loginAs.setText("Loginned as Admin");
                        RumahMakan.disableButton(false);
                        Main.landingPage.disableButton(false);
                        stage.close();
                    }

                    else {
                        Alert salah = new Alert(Alert.AlertType.INFORMATION);
                        salah.setContentText("Username or Password salah");
                        salah.show();
                    }
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }

            });
            loginAsGuest.setOnMouseClicked(e -> {
                MainMenu.setIsLogin(true);
                MainMenu.loginAs.setText("Loginned as a guest");
                RumahMakan.disableButton(true);
                Main.landingPage.disableButton(true);
                stage.close();

            });
            gp.add(submit, 0, 2);
            gp.add(loginAsGuest, 1, 2);

            Scene scene = new Scene(gp, 500, 500);
            gp.setPadding(new Insets(20, 20, 20, 20));
            stage.setOnCloseRequest(e -> {
                Main.exitConfirm(e);
            });
            stage.setScene(scene);
            stage.showAndWait();
        }
    }

}
