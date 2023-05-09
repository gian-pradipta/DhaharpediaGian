package com.example.dhaharpedia;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Delete {
    public static void delete(RumahMakan rm) {
        try {
            String sql = "DELETE FROM restoran WHERE id = " + rm.id ;
            Koneksi.statement.execute(sql);
            Main.landingPage.setCenterOfScene(Main.top10());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteConfirm(RumahMakan rm) {
        Stage stage = new Stage();
        Button yes, no;

        VBox containerDeleteConfirm = new VBox(20);
        containerDeleteConfirm.setAlignment(Pos.CENTER);
        Text confirmationText = new Text("Do you want to delete this entry?");
        yes = new Button("yes");
        no = new Button("No");
        HBox containerButton = new HBox(30);
        containerButton.setAlignment(Pos.CENTER);
        containerButton.getChildren().addAll(yes, no);
        containerDeleteConfirm.getChildren().addAll(confirmationText, containerButton);
        Scene scene = new Scene(containerDeleteConfirm, 200, 200);
        stage.setScene(scene);
        yes.setOnMouseClicked(ev -> {
            stage.close();
            Delete.delete(rm);
        });
        no.setOnMouseClicked(ev -> {

            stage.close();

        });
        stage.setTitle("Delete ?");
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }
}
