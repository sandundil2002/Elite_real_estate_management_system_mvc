package lk.ijse.elite.dto.tm;
import javafx.scene.control.Button;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class EmployeeTm {
    private String empid;
    private String adid;
    private String name;
    private String address;
    private String mobile;
    private String position;
    private String basicSalary;
    private Button Pay_Salary;

    {
        Pay_Salary = new Button("Payment");

        Pay_Salary.setCursor(javafx.scene.Cursor.HAND);
        Pay_Salary.setStyle("-fx-background-color: #00ff00; -fx-text-fill: #ffffff");

        Pay_Salary.setPrefWidth(100);
        Pay_Salary.setPrefHeight(30);
    }
}
