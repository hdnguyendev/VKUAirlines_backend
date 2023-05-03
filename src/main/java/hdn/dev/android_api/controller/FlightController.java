package hdn.dev.android_api.controller;

import hdn.dev.android_api.model.Flight;
import hdn.dev.android_api.model.ResponseObject;
import hdn.dev.android_api.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
    @GetMapping("/flights/departure")
    public ResponseEntity getDepartures(){
        List<String> list = List.of(flightRepository.findDistinctDepartures());
        return ResponseEntity.ok().body(list.toArray());
    }

    @GetMapping("/flights/destination")
    public ResponseEntity getDestinations() {
        List<String> list = List.of(flightRepository.findDistinctDestination());
        return ResponseEntity.ok().body(list.toArray());
    }

    @GetMapping("/flights")
    public ResponseEntity<ResponseObject> getFlights(@RequestParam Map<String, String> params) {
        System.out.println(params);
        Sort sort = Sort.by(Sort.Direction.ASC, "departureTime");
        List<Flight> flights = flightRepository.findAll(sort);
        if (params.isEmpty()) return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success", "Successfully", flights));
        if (flights == null) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("error", "Don't exists flights", null));
        }
        if (params.containsKey("date") && !params.get("date").isEmpty()) {
            String date = params.get("date");
            System.out.println(date);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
            flights = flights.stream()
                    .filter(flight -> flight.getDepartureTime().isAfter(dateTime)).collect(Collectors.toList());

        }
        if (params.containsKey("departure") && !params.get("departure").isEmpty()) {
            String departure = params.get("departure");
            System.out.println(departure);
            flights = flights.stream()
                    .filter(flight -> flight.getDeparture().equals(departure)).toList();
        }
        if (params.containsKey("destination") && !params.get("destination").isEmpty()) {
            String destination = params.get("destination");
            System.out.println(destination);
            flights = flights.stream()
                    .filter(flight -> flight.getDestination().equals(destination)).toList();
        }
        if (params.containsKey("time_departure") && !params.get("time_departure").isEmpty()) {
            String time_departure = params.get("time_departure").trim();
            System.out.println(time_departure);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            flights = flights.stream()
                    .filter(flight -> {
                        String formattedDate = flight.getDepartureTime().format(formatter);
                        return formattedDate.equals(time_departure);
                    }).toList();
        }
        if (params.containsKey("time_destination") && !(params.get("time_destination").isEmpty())) {
            String time_destination = params.get("time_destination").trim();
            System.out.println(time_destination);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            flights = flights.stream()
                    .filter(flight -> {
                        String formattedDate = flight.getArrivalTime().format(formatter);
                        return formattedDate.equals(time_destination);
                    }).toList();
        }
        if (params.containsKey("person") && !params.get("person").isEmpty()) {
            int person = Integer.parseInt(params.get("person"));
            flights = flights.stream()
                    .filter(flight -> flight.getRemaining_seat() >= person).toList();
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
