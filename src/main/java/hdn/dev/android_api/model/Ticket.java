package hdn.dev.android_api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ticket")
@NoArgsConstructor
@Data
@AllArgsConstructor
public class Ticket implements Serializable {
    private static final long serialVersionUID = -297553281792804396L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id", nullable = false)
    private Long ticketId;

    @Column(name = "total_amount", nullable = false)
    private double totalAmount;
    @Column(name = "time_booking", nullable = false)
    private LocalDateTime time_booking;
    @Column(name ="list_seat", nullable = false)
    private String list_seat;
    @JsonIgnore
    @Column(name = "momo", nullable = true)
    private String momo;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "flight_code")
    private String flightCode;



}
