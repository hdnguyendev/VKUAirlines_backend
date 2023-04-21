package hdn.dev.android_api.repository;

import hdn.dev.android_api.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findAll();
    List<Seat> findByFlight_FlightCode(String flightCode);
}