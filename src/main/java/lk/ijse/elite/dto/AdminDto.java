package lk.ijse.elite.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class AdminDto {
    private String Admin_id;
    private String Name;
    private String Address;
    private int Mobile;
    private String Email;
    private String Password;
}
