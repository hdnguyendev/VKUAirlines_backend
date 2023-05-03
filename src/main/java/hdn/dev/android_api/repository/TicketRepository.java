package hdn.dev.android_api.repository;

import hdn.dev.android_api.model.Flight;
import hdn.dev.android_api.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Ticket findByTicketId(Long id);

    List<Ticket> findByUserId(Long id);
}
