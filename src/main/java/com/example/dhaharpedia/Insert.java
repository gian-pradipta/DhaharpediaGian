package com.example.dhaharpedia;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.sql.ResultSet;

public class Insert {
    static Button insertGambar = new Button("Tambahkan Gambar"), submit = new Button("Submit");

    static GridPane containerInsert;


    public static void display() {
        TextField nama = new TextField(), alamat = new TextField(), noTelp = new TextField(), kategori = new TextField();
        Stage stage = new Stage();
        containerInsert = new GridPane();
        containerInsert.add(new Text("Nama\t: "), 0, 0);
        containerInsert.add(new Text("Kategori\t: "), 0, 1);
        containerInsert.add(new Text("Alamat\t: "), 0, 2);
        containerInsert.add(new Text("No Telp.\t: "), 0, 3);
        containerInsert.add(insertGambar, 0, 4);
        containerInsert.add(submit, 1, 4);
        Text jalan = new Text("a.png");
        submit.setOnMouseClicked(e -> {
            try
            {
                String sql2 = "INSERT INTO restoran (nama_restoran, kategori_restoran, alamat_restoran, no_telp, path)" +
                        "VALUES (" + "'"+nama.getText()+ "', "
                        + "'" + kategori.getText() + "', " +
                        "'" + alamat.getText() +"', " +
                        "'" + noTelp.getText() + "'," +
                        "'" + jalan.getText() + "'" + ")";
                Koneksi.statement.execute(sql2);


                stage.close();
            }
            catch (Exception exception) {
                exception.printStackTrace();

            }
            finally {
                Main.landingPage.setCenterOfScene(Main.top10());
            }


        });
        insertGambar.setOnMouseClicked(e -> {
            FileChooser fileChooser = new FileChooser();
            File selectedGambar = fileChooser.showOpenDialog(Main.stage);

            if (selectedGambar != null) {

                    jalan.setText(selectedGambar.getAbsolutePath().replace("\\", "/"));

            }
        });


        containerInsert.add(nama, 1, 0);
        containerInsert.add(kategori, 1, 1);
        containerInsert.add(alamat, 1, 2);
        containerInsert.add(noTelp, 1, 3);
        Scene scene = new Scene(containerInsert, 500, 500);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.showAndWait();

    }


}
