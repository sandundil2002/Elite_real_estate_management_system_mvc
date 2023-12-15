package lk.ijse.elite.dto;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class PaymentDto {
    private String payment_id;
    private String customer_id;
    private String property_id;
    private String date;
    private String price;
    private String method;
}
