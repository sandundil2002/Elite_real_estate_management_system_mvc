package lk.ijse.elite.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class AdminDto {
    private String Admin_id;
    private String Name;
    private String Otp;
    private String Mobile;
    private String Email;
    private String Password;
}
