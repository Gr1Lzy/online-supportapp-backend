package com.gitlab.ticketservice.repository;

import com.gitlab.ticketservice.entity.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface TicketRepository extends MongoRepository<Ticket, String> {

  @Query(value = "{ 'status' : { $ne : 'CLOSED' }, 'createdAt' : { $lt : ?0 } }")
  @Update(value = "{ '$set' : { 'status' : 'CLOSED' } }")
  void setStatusClosedOnTicketWhereCreatedAtMoreThanTwoWeeks(Date twoWeeksAgo, Date currentDate);

  Page<Ticket> findAllByAssigneeId(String userId, Pageable pageable);

  Page<Ticket> findAllByReporterId(String userId, Pageable pageable);
}
