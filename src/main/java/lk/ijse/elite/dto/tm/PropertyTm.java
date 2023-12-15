package lk.ijse.elite.dto.tm;

import javafx.scene.control.Button;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data

public class PropertyTm {
    private String propertyId;
    private String agentId;
    private String price;
    private String address;
    private String type;
    private String status;
    private String perches;
    private Button btnRemove;

    {
        btnRemove = new Button("Remove");
        btnRemove.setCursor(javafx.scene.Cursor.HAND);
        btnRemove.setStyle("-fx-background-color: #ff0000; -fx-text-fill: #ffffff");

        btnRemove.setPrefWidth(100);
        btnRemove.setPrefHeight(30);
    }
}
