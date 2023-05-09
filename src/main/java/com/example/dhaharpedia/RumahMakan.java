package com.example.dhaharpedia;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RumahMakan {
    int id;
    private static List<RumahMakan> allObjects = new ArrayList<>();
    Scene scene;
    GridPane container;
    VBox gambarContainer, deskripsi;
    int jumlahRating = 0;
    int sumRating;
    String nama;
    String kategori;
    String alamat;
    String noTelp;
    String rating;
    Button hapus ,update , rate;
    Image a;
    ImageView gambar;
    Text kategoriText = new Text(), namaText = new Text(), alamatText = new Text(),
    noTelpText = new Text(), ratingText;

    public RumahMakan(int id, String nama, String kategori, String alamat, String noTelp, String rating, String path) {
        this.a = new Image(path);
        this.id = id;
        this.kategori = kategori; kategoriText.setText(this.kategori);
        this.hapus = new Button("Hapus");
        this.update = new Button("Update");
        this.rate = new Button("Rate");
        this.nama = nama; namaText.setText(nama);
        this.alamat = alamat; alamatText.setText(alamat);
        this.noTelp = noTelp; noTelpText.setText(noTelp);
        this.rating = rating;
        ratingText = new Text(this.rating);
        allObjects.add(this);

    }

    public GridPane getDeskripsi() {
        gambar = new ImageView(a);
        gambar.setFitHeight(200);
        gambar.setFitWidth(200);

        container = new GridPane();
        container.setHgap(20);
        container.setPadding(new Insets(0, 0, 0, 250));

        gambarContainer = new VBox(20);
        gambarContainer.getChildren().add(gambar);
        hapus.setOnMouseClicked(e -> {
            Delete.deleteConfirm(this);
        });
        rate.setOnMouseClicked(e -> {
            this.rate();
        });
        update.setOnMouseClicked(e -> {
            Update.display(this);
        });
        HBox containerNama = new HBox(20); containerNama.getChildren().addAll(new Text("Nama\t: "), namaText);
        HBox containerKategori = new HBox(20); containerKategori.getChildren().addAll(new Text("Kategori\t: "), kategoriText);
        HBox containerAlamat = new HBox(20); containerAlamat.getChildren().addAll(new Text("Alamat\t: " ), alamatText);
        HBox containerNoTelp = new HBox(20); containerNoTelp.getChildren().addAll(new Text("No Telp.\t: "), noTelpText);
        deskripsi = new VBox(5,
                containerNama,
                containerKategori,
                containerAlamat,
                containerNoTelp,
                hapus,
                update,
                rate
        );



        HBox containerRating = new HBox(20);

        HBox.setMargin(containerRating, new Insets(20, 0, 0, 0));

        containerRating.getChildren().add(ratingText);

        container.add(gambarContainer, 0, 0);
        container.add(deskripsi, 1, 0);
        container.add(containerRating, 0, 1);
//        container.setGridLinesVisible(true);
        return container;
    }



    public static void disableRating(boolean t){
        for (RumahMakan i : allObjects){
            i.rate.setDisable(t);
        }

    }
    public static void disableUpdate(boolean t){
        for (RumahMakan i : allObjects){
            i.update.setDisable(t);
        }
    }
    public static void disableDelete(boolean t){
        for (RumahMakan i : allObjects){
            i.hapus.setDisable(t);
        }
    }
    public static void disableButton(boolean t) {
        disableDelete(t);
        disableRating(t);
        disableUpdate(t);
    }
    public static RumahMakan returnRM(RumahMakan rm) {
        RumahMakan a = rm;
        return rm;
    }

    public void rate() {

        Button satu = new Button("1");
        satu.setOnMouseClicked(e -> {
            ratingText.setText("1");
            updateRating();
        });
        Button dua = new Button("2");
        dua.setOnMouseClicked(e -> {
            ratingText.setText("2");
            updateRating();
        });
        Button tiga = new Button("3");
        tiga.setOnMouseClicked(e -> {
            ratingText.setText("3");
            updateRating();
        });
        Button empat = new Button("4");
        empat.setOnMouseClicked(e -> {
            ratingText.setText("4");
            updateRating();
        });
        Button lima = new Button("5");
        lima.setOnMouseClicked(e -> {
            ratingText.setText("5");
            updateRating();
        });

        Stage stage = new Stage();
        HBox containerRating = new HBox(satu, dua, tiga, empat, lima);
        Scene scene = new Scene(containerRating, 200, 200);

        containerRating.setSpacing(20);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    private void updateRating() {
            try {
                int ratingInt = Integer.parseInt(ratingText.getText());
                sumRating += ratingInt;
                jumlahRating++;
                float rataRata = (float) sumRating / (float )jumlahRating;
                String sql = "UPDATE restoran SET rating = " +
                        rataRata +
                        "WHERE id = " + this.id;
                Koneksi.statement.execute(sql);
                ratingText.setText(Float.toString(rataRata));
            } catch (Exception exc) {
                exc.printStackTrace();
            }

    }
}
