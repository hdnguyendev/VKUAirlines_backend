package hdn.dev.android_api.repository;

import hdn.dev.android_api.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, String> {
    Flight findByFlightCode(String flightCode);
    List<Flight> findAll();


}