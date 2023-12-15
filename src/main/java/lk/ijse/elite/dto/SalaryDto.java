package lk.ijse.elite.dto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class SalaryDto {
    private String salary_id;
    private String employee_id;
    private String date;
    private String amount;
}
