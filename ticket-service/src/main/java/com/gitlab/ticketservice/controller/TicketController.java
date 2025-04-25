package com.gitlab.ticketservice.controller;

import com.gitlab.ticketservice.dto.ticket.TicketRequestDto;
import com.gitlab.ticketservice.dto.ticket.TicketResponseDto;
import com.gitlab.ticketservice.service.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Ticket Controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tickets")
public class TicketController {

  private final TicketService ticketService;

  @Operation(summary = "Create ticket")
  @PostMapping
  public ResponseEntity<Object> create(@RequestBody @Valid TicketRequestDto requestDto) {
    ticketService.create(requestDto);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @Operation(summary = "Find all active tickets (not archived)")
  @GetMapping
  public ResponseEntity<Page<TicketResponseDto>> findAll(@RequestParam(defaultValue = "0") Integer page,
                                                         @RequestParam(defaultValue = "10") Integer size) {
    return ResponseEntity.ok(ticketService.findAll(page, size));
  }

  @Operation(summary = "Find archived tickets (auto-closed after 2 weeks)")
  @GetMapping("/archived")
  public ResponseEntity<Page<TicketResponseDto>> findArchivedTickets(@RequestParam(defaultValue = "0") Integer page,
                                                                     @RequestParam(defaultValue = "10") Integer size) {
    return ResponseEntity.ok(ticketService.findArchivedTickets(page, size));
  }

  @Operation(summary = "Find ticket by id")
  @GetMapping("/{ticketId}")
  public ResponseEntity<TicketResponseDto> findById(@PathVariable String ticketId) {
    return ResponseEntity.ok(ticketService.findById(ticketId));
  }

  @Operation(summary = "Assign ticket on me")
  @PatchMapping("/{ticketId}/assign-on-me")
  public ResponseEntity<Object> assignOnMe(@PathVariable String ticketId) {
    ticketService.assignOnMe(ticketId);
    return ResponseEntity.ok().build();
  }

  @Operation(summary = "Find active tickets created by current User")
  @GetMapping("/my-created")
  public ResponseEntity<Page<TicketResponseDto>> findAllCreatedByCurrentUser(
      @RequestParam(defaultValue = "0") Integer page,
      @RequestParam(defaultValue = "10") Integer size
  ) {
    return ResponseEntity.ok(ticketService.findAllCreatedByCurrentUser(page, size));
  }

  @Operation(summary = "Find archived tickets created by current User")
  @GetMapping("/my-created/archived")
  public ResponseEntity<Page<TicketResponseDto>> findArchivedTicketsCreatedByCurrentUser(
      @RequestParam(defaultValue = "0") Integer page,
      @RequestParam(defaultValue = "10") Integer size
  ) {
    return ResponseEntity.ok(ticketService.findArchivedTicketsCreatedByCurrentUser(page, size));
  }

  @Operation(summary = "Find active tickets assigned to current User")
  @GetMapping("/my-assigned")
  public ResponseEntity<Page<TicketResponseDto>> findAllAssignedOnCurrentUser(
      @RequestParam(defaultValue = "0") Integer page,
      @RequestParam(defaultValue = "10") Integer size
  ) {
    return ResponseEntity.ok(ticketService.findAllAssignedOnCurrentUser(page, size));
  }

  @Operation(summary = "Find archived tickets assigned to current User")
  @GetMapping("/my-assigned/archived")
  public ResponseEntity<Page<TicketResponseDto>> findArchivedTicketsAssignedOnCurrentUser(
      @RequestParam(defaultValue = "0") Integer page,
      @RequestParam(defaultValue = "10") Integer size
  ) {
    return ResponseEntity.ok(ticketService.findArchivedTicketsAssignedOnCurrentUser(page, size));
  }
}