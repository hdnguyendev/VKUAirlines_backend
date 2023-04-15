package hdn.dev.android_api.controller;

import hdn.dev.android_api.model.Flight;
import hdn.dev.android_api.model.ResponseObject;
import hdn.dev.android_api.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/")
public class FlightController {
    @Autowired
    private FlightRepository flightRepository;

    @GetMapping("/flights/{flightCode}")
    public ResponseEntity<ResponseObject> getFlightByCode(@PathVariable String flightCode) {
        Flight flight =  flightRepository.findByFlightCode(flightCode);
        if (flight == null) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("error", "Flight not found",""));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success", "Successfully", flight));
    }

    @GetMapping("/flights")
    public ResponseEntity<ResponseObject> getAllFlight(){
        List<Flight> list= flightRepository.findAll();
        if (list.size() == 0) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("error", "Don't exists flights",""));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success", "Successfully", list));
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