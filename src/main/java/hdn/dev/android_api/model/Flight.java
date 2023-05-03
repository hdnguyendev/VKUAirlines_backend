package hdn.dev.android_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    private String flightName;
    private String departure;
    private String departureSort;

    private String destination;
    private String destinationSort;

    private LocalDateTime departureTime;

    private LocalDateTime arrivalTime;

    private int remaining_seat = 40;
}
