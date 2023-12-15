package lk.ijse.elite.dto;
import lk.ijse.elite.dto.tm.PaymentTm;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class PlaceOrderDto {
    private String paymentId;
    private String customerId;
    private String propertyId;
    private String name;
    private String date;
    private String price;
    private String address;
    private String method;
    //private List<PaymentTm>paymentTmList = new ArrayList<>();
}
