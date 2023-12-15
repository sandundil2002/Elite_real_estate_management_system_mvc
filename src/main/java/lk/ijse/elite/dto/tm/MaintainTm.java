package lk.ijse.elite.dto.tm;
import javafx.scene.control.Button;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class MaintainTm {
    private String maintain_id;
    private String rent_id;
    private String date;
    private String status;
    private Button btnFinished;
    private Button btnCansel;

    {
        btnFinished = new Button("Finished");
        btnCansel = new Button("Cansel");

        btnCansel.setCursor(javafx.scene.Cursor.HAND);
        btnCansel.setStyle("-fx-background-color: #ff0000; -fx-text-fill: #ffffff");

        btnFinished.setCursor(javafx.scene.Cursor.HAND);
        btnFinished.setStyle("-fx-background-color: #00ff00; -fx-text-fill: #ffffff");

        btnFinished.setPrefWidth(100);
        btnCansel.setPrefWidth(100);
        btnCansel.setPrefHeight(30);
        btnFinished.setPrefHeight(30);
    }
}
