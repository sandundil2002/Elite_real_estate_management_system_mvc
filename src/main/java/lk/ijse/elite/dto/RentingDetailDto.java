package lk.ijse.elite.dto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class RentingDetailDto {
    private String rentId;
    private String propertyId;
    private String description;
}
