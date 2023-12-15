package lk.ijse.elite.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class RentDto {
    private String rentId;
    private String propertyId;
    private String customerId;
    private String date;
    private String duration;
}
