package hdn.dev.android_api.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketRequest {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotBlank(message = "Flight code is required")
    private String flightCode;

    @NotEmpty(message = "At least one seat is required")
    private List<Long> seatIds;
}