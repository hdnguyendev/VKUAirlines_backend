package hdn.dev.android_api.controller;

import hdn.dev.android_api.model.ResponseObject;
import hdn.dev.android_api.model.Seat;
import hdn.dev.android_api.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/")
public class SeatController {
    @Autowired
    private SeatRepository seatRepository;

    @GetMapping("/seats/{flight_code}")
    private ResponseEntity<ResponseObject> getSeatByFlight(@PathVariable("flight_code") String flight_code) {
        List<Seat> seat =  seatRepository.findByFlightCode(flight_code);
        if (seat == null) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("error", "Seat not found",null));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success", "Successfully", seat));
    }
}
