package hdn.dev.android_api.repository;

import hdn.dev.android_api.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface FlightRepository extends JpaRepository<Flight, String> {
    Flight findByFlightCode(String flightCode);
    List<Flight> findAll();


}