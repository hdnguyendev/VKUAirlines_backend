package hdn.dev.android_api.controller;

import hdn.dev.android_api.model.Flight;
import hdn.dev.android_api.model.ResponseObject;
import hdn.dev.android_api.repository.FlightRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/")
public class FlightController {
    @Autowired
    private FlightRepository flightRepository;

    @GetMapping("/flights")
    public ResponseEntity<ResponseObject> getFlightByDate(@RequestParam Map<String, String> params) {
        System.out.println(params);
        List<Flight> flights = flightRepository.findAll();
        if (params.isEmpty()) return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success", "Successfully", flights));
        if (flights == null) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("error", "Don't exists flights", null));
        }
        if (params.containsKey("date")) {
            String date = params.get("date");
            System.out.println(date);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
            flights = flights.stream()
                    .filter(flight -> flight.getDepartureTime().isAfter(dateTime)).collect(Collectors.toList());

        }
        if (flights.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("error", "Don't exists flights", null));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success", "Successfully", flights));
    }

    @GetMapping("/flights/{flightCode}")
    public ResponseEntity<ResponseObject> getFlightByCode(@PathVariable String flightCode) {
        Flight flight =  flightRepository.findByFlightCode(flightCode);
        if (flight == null) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("error", "Flight not found",null));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success", "Successfully", flight));
    }


    @PostMapping("/flights")
    public ResponseEntity<ResponseObject> addFlight(@RequestBody Flight flight) {
        try {
        flightRepository.save(flight);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success", "Flight added successfully", flight));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("error", "An error occurred", flight));
        }
    }
}