package com.example.dhaharpedia;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.sql.ResultSet;

public class MainMenu extends User{
    public static boolean isLoginned = true;
    public static Text loginAs = new Text("Loginned as a guest");
    public static TextField searchBar;
    public static Button login, go;
    public Button best, kategori, tambahMakanan, tambahKategori;
    private BorderPane bp = new BorderPane();
    public MainMenu(){

        //========================= TOP ===============================
        GridPane containerTop = new GridPane();
        HBox hb = new HBox(20); hb.setAlignment(Pos.CENTER); hb.getStyleClass().add("hb");
        StackPane logo = new StackPane();
        Text text = new Text("Logo"); text.setFill(Color.WHITE);
        logo.getChildren().addAll(new Circle(20), text);
        Text judul = new Text("DhaharPedia!"); judul.getStyleClass().add("judul");

        HBox searchContainer = new HBox(5); searchContainer.setAlignment(Pos.CENTER_RIGHT);
        searchContainer.setPadding(new Insets(0, 10, 0, 0));
        go = new Button("Search!");
        go.setOnMouseClicked(e -> {
            search();
        });
        login = new Button("Login");
        login.setOnMouseClicked((e) -> {
            Login.display();
        });
        searchBar = new TextField();
        searchBar.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                    search();
                }
            }
        });

        searchContainer.getChildren().addAll(searchBar, go, login);
        hb.getChildren().addAll(logo, judul);

        ColumnConstraints c1 = new ColumnConstraints();
        ColumnConstraints c2 = new ColumnConstraints();
        ColumnConstraints c3 = new ColumnConstraints();
        containerTop.getColumnConstraints().addAll(c1, c2, c3);
        c1.setPercentWidth(40); c2.setPercentWidth(20); c3.setPercentWidth(40);
        HBox nameLogin = new HBox();
        nameLogin.setPadding(new Insets(0, 0, 0, 10));
        nameLogin.getChildren().add(loginAs);
        nameLogin.setAlignment(Pos.CENTER_LEFT);
        containerTop.add(nameLogin, 0, 0);
        containerTop.add(hb, 1, 0);
        containerTop.add(searchContainer, 2, 0);

        bp.setTop(containerTop);
        bp.getTop().getStyleClass().add("top");

        //====================== LEFT =============================
        VBox leftContainer = new VBox(75); leftContainer.setAlignment(Pos.CENTER);
        best = new Button("Peringkat Teratas");
        kategori = new Button("Kategori");
        tambahMakanan = new Button("Tambah Makanan");
        tambahKategori = new Button("Tambah Kategori");
        VBox containerMakanan = new VBox(best, tambahMakanan);
        VBox containerKategori = new VBox(kategori, tambahKategori);
        leftContainer.getChildren().addAll(containerMakanan, containerKategori);
        bp.setLeft(leftContainer);
        bp.getLeft().getStyleClass().add("left");
        //====================== CENTER ==============================

        //====================== FOOTER ==============================

        HBox footerContainer = new HBox(20); footerContainer.setAlignment(Pos.CENTER);
        Text footer = new Text("Footer wkwkwk!"); footer.getStyleClass().add("footer");
        footerContainer.getChildren().addAll(footer);
        bp.setBottom(footerContainer);
        bp.getBottom().getStyleClass().add("footer");


    }

    public Scene getScene() {
        Scene scene = new Scene(bp, 320, 240);

        scene.getStylesheets().add("style.css");
        return scene;
    }

    public Button getButtonBest(){
        return this.best;
    }
    public Button getButtonKategori() {
        return this.kategori;
    }
    public void setCenterOfScene(Region root){
        this.bp.setCenter(root);
    }

    public static void setIsLogin(boolean t){
        isLoginned = t;
        if (t){
            MainMenu.login.setText("Logout");

        }
        else {
            MainMenu.login.setText("Login");
            loginAs.setText("");
        }
    }

    public void search() {
        int row = 0;
        String searchItem = searchBar.getText();
        VBox container = new VBox(20);
        container.setAlignment(Pos.BASELINE_CENTER);

        VBox root = new VBox(50);
//            root.setBackground(Background.fill(Color.RED));
        ScrollPane scroll = new ScrollPane();
        scroll.setPadding(new Insets(50, 0, 0, 0));

        try {
            Koneksi.start();
            String sql = "SELECT * FROM restoran " +
                    "WHERE nama_restoran like " + "'%" + searchItem + "%'";

            ResultSet resultSet = Koneksi.statement.executeQuery(sql);

            while (resultSet.next()){
                row++;
                int id = resultSet.getInt("id");
                String nama = resultSet.getString("nama_restoran");
                String kategori = resultSet.getString("kategori_restoran");
                String alamat = resultSet.getString("alamat_restoran");
                String noTelp = resultSet.getString("no_telp");
                String peringkat = Float.toString(resultSet.getFloat("rating"));
                String path = resultSet.getString("path");
                RumahMakan rm = new RumahMakan(id, nama, kategori, alamat, noTelp, peringkat, path);
                if (MainMenu.loginAs.getText().equals("Loginned as Admin")){
                    RumahMakan.disableButton(false);
                }
                else if (MainMenu.loginAs.getText().equals("Loginned as a guest")){
                    RumahMakan.disableButton(true);
                }
                else {
                    RumahMakan.disableButton(true);
                    RumahMakan.disableRating(false);
                }
                root.getChildren().add((RumahMakan.returnRM(rm)).getDeskripsi());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        scroll.setContent(root);

        Text h2 = new Text(row +" Hasil Pencarian: ");
        h2.getStyleClass().add("h2");
        container.getChildren().addAll(h2, scroll);

        this.setCenterOfScene(container);
    }

    public void disableButton(boolean t){
        tambahKategori.setDisable(t);
        tambahMakanan.setDisable(t);
    }
}
