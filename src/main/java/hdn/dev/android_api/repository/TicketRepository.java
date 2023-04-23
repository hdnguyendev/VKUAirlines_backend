package hdn.dev.android_api.repository;

import hdn.dev.android_api.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Ticket findByTicketId(Long id);
}