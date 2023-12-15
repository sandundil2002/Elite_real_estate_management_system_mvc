package lk.ijse.elite.dto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class RentingDto {
    private String rent_id;
    private String property_id;
    private String agent_id;
    private String date;
    private String duration;
}
