package lk.ijse.elite.dto;
import javafx.scene.control.Button;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class EmployeeDto {
    private String empid;
    private String adid;
    private String name;
    private String address;
    private String mobile;
    private String position;
    private String basicSalary;
}
