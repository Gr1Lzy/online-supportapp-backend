package com.gitlab.ticketservice.repository;

import com.gitlab.ticketservice.entity.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface TicketRepository extends MongoRepository<Ticket, String> {

  @Query(value = "{ 'status' : { $ne : 'CLOSED' }, 'createdAt' : { $lt : ?0 }, 'isTicketOlderThanTwoWeeks' : { $ne : true } }")
  @Update(value = "{ '$set' : { 'isTicketOlderThanTwoWeeks' : true } }")
  void setTicketOlderThanTwoWeeks(LocalDateTime twoWeeksAgo);

  @Query("{ 'isTicketOlderThanTwoWeeks': { $ne: true } }")
  Page<Ticket> findAllActiveTickets(Pageable pageable);

  @Query("{ 'isTicketOlderThanTwoWeeks': true }")
  Page<Ticket> findAllArchivedTickets(Pageable pageable);

  @Query("{ 'assigneeId': ?0, 'isTicketOlderThanTwoWeeks': { $ne: true } }")
  Page<Ticket> findActiveTicketsByAssigneeId(String userId, Pageable pageable);

  @Query("{ 'reporterId': ?0, 'isTicketOlderThanTwoWeeks': { $ne: true } }")
  Page<Ticket> findActiveTicketsByReporterId(String userId, Pageable pageable);

  @Query("{ 'assigneeId': ?0, 'isTicketOlderThanTwoWeeks': true }")
  Page<Ticket> findArchivedTicketsByAssigneeId(String userId, Pageable pageable);

  @Query("{ 'reporterId': ?0, 'isTicketOlderThanTwoWeeks': true }")
  Page<Ticket> findArchivedTicketsByReporterId(String userId, Pageable pageable);
}