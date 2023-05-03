package hdn.dev.android_api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketRequest {
    private Long userId;
    private String flightCode;
    private List<Integer> seatIds;
}
