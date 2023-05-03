package hdn.dev.android_api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "flight")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Flight {
    @Id
    private String flightCode;
    private String flight_name;
    private String departure;
    private String destination;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;

}
