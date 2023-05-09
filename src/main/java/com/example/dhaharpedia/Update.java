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

public class Update {
    static Button gantiGambar = new Button("Ganti Gambar"), submit = new Button("Submit");

    static GridPane containerUpdate;
    static String oldNama, oldAlamat, oldNoTelp, oldKategori, oldPath;
    static TextField nama = new TextField(), alamat = new TextField(), noTelp = new TextField(), kategori = new TextField();
    public static void display(RumahMakan rumahMakan) {
        Stage stage = new Stage();
        containerUpdate = new GridPane();
        containerUpdate.add(new Text("Nama\t: "), 0, 0);
        containerUpdate.add(new Text("Kategori\t: "), 0, 1);
        containerUpdate.add(new Text("Alamat\t: "), 0, 2);
        containerUpdate.add(new Text("No Telp.\t: "), 0, 3);
        containerUpdate.add(gantiGambar, 0, 4);
        containerUpdate.add(submit, 1, 4);
        submit.setOnMouseClicked(e -> {
            try
            {
                String sql2 = "UPDATE restoran SET " +
                        "nama_restoran = " +
                        "'" + nama.getText() + "'" + " " +
                        ", kategori_restoran = " +
                        "'" + kategori.getText() + "'" + " " +
                        ", alamat_restoran = " +
                        "'" + alamat.getText() + "'" + " " +
                        ", no_telp = " +
                        "'" + noTelp.getText() + "'" + " " +
                        "WHERE id = " + rumahMakan.id;
                Koneksi.statement.execute(sql2);
                rumahMakan.namaText.setText(nama.getText());
                rumahMakan.noTelpText.setText(noTelp.getText());
                rumahMakan.alamatText.setText(alamat.getText());
                rumahMakan.kategoriText.setText(kategori.getText());

                stage.close();
            }
            catch (Exception exception) {
                exception.printStackTrace();

            }

        });
        gantiGambar.setOnMouseClicked(e -> {
            FileChooser fileChooser = new FileChooser();
            File selectedGambar = fileChooser.showOpenDialog(Main.stage);
            if (selectedGambar != null) {
                rumahMakan.gambar.setImage(new Image(selectedGambar.getAbsolutePath()));
            }
            if (selectedGambar != null) {
                try {
                    String jalan = selectedGambar.getAbsolutePath().replace("\\", "/");
                    String sql = "UPDATE restoran SET " +
                            "path = " +
                            "'" + jalan + "'" + " " +
                            "WHERE id = " + rumahMakan.id;
                    Koneksi.statement.execute(sql);
                } catch (Exception exc) {
                    exc.printStackTrace();
                }
            }
        });

        try {
            String sql = "SELECT * FROM restoran WHERE id = " + rumahMakan.id;
            ResultSet resultSet = Koneksi.statement.executeQuery(sql);
            while (resultSet.next()) {
                oldNama = resultSet.getString("nama_restoran");
                oldKategori = resultSet.getString("kategori_restoran");
                oldAlamat = resultSet.getString("alamat_restoran");
                oldNoTelp = resultSet.getString("no_telp");
                oldPath = resultSet.getString("path");
                nama.setText(oldNama);
                kategori.setText(oldKategori);
                alamat.setText(oldAlamat);
                noTelp.setText(oldNoTelp);
                containerUpdate.add(nama, 1, 0);
                containerUpdate.add(kategori, 1, 1);
                containerUpdate.add(alamat, 1, 2);
                containerUpdate.add(noTelp, 1, 3);


            }
        }
        catch (Exception exc) {
            exc.printStackTrace();
        }
        Scene scene = new Scene(containerUpdate, 500, 500);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.showAndWait();
    }


}
