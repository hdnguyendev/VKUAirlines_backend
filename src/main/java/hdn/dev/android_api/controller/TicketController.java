package hdn.dev.android_api.controller;

import hdn.dev.android_api.model.*;
import hdn.dev.android_api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/")
public class TicketController {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/tickets")
    public ResponseEntity<ResponseObject> getTicket() {
        return ResponseEntity.ok().body(new ResponseObject("success", "Success", null));
    }

    @PostMapping("/tickets")
    public ResponseEntity<ResponseObject> addTicket(@RequestBody TicketRequest ticketRequest) {

        return ResponseEntity.ok().body(new ResponseObject("success", "Successfully", null));

    }

    @GetMapping("tickets/{ticket_id}")
    public ResponseEntity<ResponseObject> getTicket(@PathVariable("ticket_id") Long ticket_id) {

        return ResponseEntity.ok().body(new ResponseObject("success", "Successfully", null));

    }

}