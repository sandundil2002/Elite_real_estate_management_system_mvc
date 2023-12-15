package lk.ijse.elite.dto.tm;
import javafx.scene.control.Button;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class RentingTm {
    private String rent_id;
    private String property_id;
    private String agent_id;
    private String date;
    private String duration;
    private Button btnMaintain;
    private Button btnDelete;

    {
        btnMaintain = new Button("Maintain");
        btnDelete = new Button("Delete");

        btnDelete.setCursor(javafx.scene.Cursor.HAND);
        btnDelete.setStyle("-fx-background-color: #ff0000; -fx-text-fill: #ffffff");

        btnMaintain.setCursor(javafx.scene.Cursor.HAND);
        btnMaintain.setStyle("-fx-background-color: #00ff00; -fx-text-fill: #ffffff");

        btnMaintain.setPrefWidth(100);
        btnDelete.setPrefWidth(100);
        btnDelete.setPrefHeight(30);
        btnMaintain.setPrefHeight(30);
    }
}
