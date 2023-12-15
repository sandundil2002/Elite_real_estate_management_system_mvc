package lk.ijse.elite.dto.tm;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class CustomerTm {
    private String customer_id;
    private String shedule_id;
    private String name;
    private String address;
    private String mobile;
    private String email;
}
