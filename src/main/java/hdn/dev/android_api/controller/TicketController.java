package hdn.dev.android_api.controller;

import hdn.dev.android_api.model.*;
import hdn.dev.android_api.repository.*;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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


    @PostMapping("/tickets")
    public ResponseEntity<ResponseObject> addTicket(@RequestBody TicketRequest ticketRequest) {
        Ticket ticket = new Ticket();
        ticket.setUserId(ticketRequest.getUserId());
        ticket.setFlightCode(ticketRequest.getFlightCode());
        Flight flight = flightRepository.findByFlightCode(ticket.getFlightCode());
        double totalAmount = 0;
        String list_seat = "";
        List<Seat> seatList = new ArrayList<>();
        for (Integer seat_id : ticketRequest.getSeatIds()) {
            Optional<Seat> seat = seatRepository.findById(Long.valueOf(seat_id));
            if (seat.isPresent()) {
                if (!seat.get().getFlightCode().trim().equals(ticketRequest.getFlightCode().trim())) {
                    return ResponseEntity.ok().body(new ResponseObject("error", "Ghế này thuộc chuyến bay khác (" + seat.get().getFlightCode() + ")", null));
                }
                seat.get().setAvailable(false);
                totalAmount += seat.get().getPrice();
                list_seat = list_seat.concat(seat.get().getSeat_name()+ ";");
            } else {
                return ResponseEntity.ok().body(new ResponseObject("error", "Ghế không tồn tại!", null));
            }
            seatList.add(seat.get());
        }
        LocalDateTime date = LocalDateTime.now();
        ticket.setTotalAmount(totalAmount);
        ticket.setTime_booking(date);
        ticket.setMomo("MOMO");
        ticket.setList_seat(list_seat);
        ticketRepository.save(ticket);
        for (Seat seat: seatList) {
            seat.setTicketId(ticket.getTicketId());
        }
        seatRepository.saveAll(seatList);
        flight.setRemaining_seat(flight.getRemaining_seat() - seatList.size());
        flightRepository.save(flight);
        return ResponseEntity.ok().body(new ResponseObject("success", "Đặt vé thành công!", ticket));

    }

    @GetMapping("tickets/{ticket_id}")
    public ResponseEntity getTicket(@PathVariable("ticket_id") Long ticket_id) {
        Ticket ticket = ticketRepository.findByTicketId(ticket_id);
        if (ticket == null) {
            return ResponseEntity.ok().body(new ResponseObject("error", "Ticket don't exist!", null));
        }
        User user = userRepository.findByUserId(ticket.getUserId());
        Flight flight = flightRepository.findByFlightCode(ticket.getFlightCode());
        return  ResponseEntity.ok().body(new ResponseObject("success", "Successfully!", new TicketResponse(ticket,user,flight)));

    }

    @GetMapping("tickets")
    public ResponseEntity getTicketFilter(@RequestParam Map<String, String> params) {
        if (params.isEmpty()) {
                List<Ticket> tickets = ticketRepository.findAll();
                return ResponseEntity.ok().body( tickets);
            }
        if (params.containsKey("user_id")) {
            List<Ticket> tickets = ticketRepository.findByUserId(Long.valueOf(params.get("user_id")));
            return ResponseEntity.ok().body(tickets);
        }
        return ResponseEntity.ok().body("No exist");


    }

}
