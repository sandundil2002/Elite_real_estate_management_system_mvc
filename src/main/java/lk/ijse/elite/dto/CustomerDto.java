package lk.ijse.elite.dto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class CustomerDto {
    private String customer_id;
    private String shedule_id;
    private String name;
    private String address;
    private String mobile;
    private String email;
}
