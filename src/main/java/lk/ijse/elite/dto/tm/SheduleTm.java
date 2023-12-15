package lk.ijse.elite.dto.tm;

import javafx.scene.control.Button;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class SheduleTm {
    private String shedule_id;
    private String admin_id;
    private String date;
    private String time;
    private String status;
    private Button btnCompleted;
    private Button btnCansel;


    {
        btnCompleted = new Button("Complete");
        btnCansel = new Button("Cansel");

        btnCansel.setCursor(javafx.scene.Cursor.HAND);
        btnCansel.setStyle("-fx-background-color: #ff0000; -fx-text-fill: #ffffff");

        btnCompleted.setCursor(javafx.scene.Cursor.HAND);
        btnCompleted.setStyle("-fx-background-color: #00ff00; -fx-text-fill: #ffffff");

        btnCompleted.setPrefWidth(100);
        btnCansel.setPrefWidth(100);
        btnCansel.setPrefHeight(30);
        btnCompleted.setPrefHeight(30);
    }
}
