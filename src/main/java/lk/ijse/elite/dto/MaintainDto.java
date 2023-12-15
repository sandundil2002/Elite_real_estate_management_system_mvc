package lk.ijse.elite.dto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class MaintainDto {
    private String maintain_id;
    private String rent_id;
    private String date;
    private String status;
}
