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

  @Operation(summary = "Find all tickets")
  @GetMapping
  public ResponseEntity<Page<TicketResponseDto>> findAll(@RequestParam(defaultValue = "0") Integer page,
                                                         @RequestParam(defaultValue = "10") Integer size) {
    return ResponseEntity.ok(ticketService.findAll(page, size));
  }

  @Operation(summary = "Find ticket by id")
  @GetMapping("/{id}")
  public ResponseEntity<TicketResponseDto> findById(@PathVariable String id) {
    return ResponseEntity.ok(ticketService.findById(id));
  }
}
