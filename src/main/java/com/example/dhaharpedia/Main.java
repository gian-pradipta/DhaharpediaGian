package com.example.dhaharpedia;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.*;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;

public class Main extends Application {
    public static MainMenu landingPage;
    public static Stage stage;
    @Override
    public void start(Stage PrimaryStage) throws IOException {
        stage = new Stage();
        stage.setTitle("DhaharPedia!");

        //-----------------------------------------//
        landingPage = new MainMenu();
        Button best = landingPage.getButtonBest();
        Button kategori = landingPage.getButtonKategori();
        Scene scene = landingPage.getScene();
        stage.setScene(scene);
        stage.setOnCloseRequest(e -> {
            exitConfirm(e);
        });
        VBox top10 = top10();
        best.setOnMouseClicked(e -> {

            landingPage.setCenterOfScene(top10);
        });
        kategori.setOnMouseClicked(e -> {
            FileChooser fileChooser = new FileChooser();
            File selectedfile = fileChooser.showOpenDialog(stage);
            if (selectedfile != null){
                System.out.println(selectedfile.getAbsolutePath());
            }

        });
        stage.setMaximized(true);
        stage.show();
        if (!MainMenu.isLoginned){
            Login.display();
        }
    }

    public static void main(String[] args) {
        launch();
    }

    public static void exitConfirm(Event e){
        Stage stage = new Stage();
        Button yes, no;

        VBox containerExitConfirm = new VBox(20);
        containerExitConfirm.setAlignment(Pos.CENTER);
        Text confirmationText = new Text("Do you want to exit?");
        yes = new Button("yes");
        no = new Button("No");
        HBox containerButton = new HBox(30);
        containerButton.setAlignment(Pos.CENTER);
        containerButton.getChildren().addAll(yes, no);
        containerExitConfirm.getChildren().addAll(confirmationText, containerButton);
        Scene scene = new Scene(containerExitConfirm, 200, 200);
        stage.setScene(scene);
        yes.setOnMouseClicked(ev -> {
            stage.close();
            Main.stage.close();
        });
        no.setOnMouseClicked(ev -> {
            e.consume();
            stage.close();

        });
        stage.setTitle("Exit ?");
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    public static VBox top10() {
        VBox container = new VBox(20);
        container.setAlignment(Pos.BASELINE_CENTER);

        VBox root = new VBox(50);
//            root.setBackground(Background.fill(Color.RED));
        ScrollPane scroll = new ScrollPane();
        scroll.setPadding(new Insets(50, 0, 0, 0));

        try {
            Koneksi.start();
            String sql = "SELECT * FROM restoran";
            ResultSet resultSet = Koneksi.statement.executeQuery(sql);
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String nama = resultSet.getString("nama_restoran");
                String kategori = resultSet.getString("kategori_restoran");
                String alamat = resultSet.getString("alamat_restoran");
                String noTelp = resultSet.getString("no_telp");
                String peringkat = Float.toString(resultSet.getFloat("rating"));
                String path = resultSet.getString("path");
                root.getChildren().add((new RumahMakan(id, nama, kategori, alamat, noTelp, peringkat, path)).getDeskripsi());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        scroll.setContent(root);

        Text h2 = new Text("Top 10 Rumah Makan!");
        h2.getStyleClass().add("h2");
        container.getChildren().addAll(h2, scroll);
        return container;
    }
}