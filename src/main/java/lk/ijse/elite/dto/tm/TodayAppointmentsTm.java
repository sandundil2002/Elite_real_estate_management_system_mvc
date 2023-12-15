package lk.ijse.elite.dto.tm;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class TodayAppointmentsTm {
    private String shedule_id;
    private String name;
    private String time;
    private String mobile;
}
