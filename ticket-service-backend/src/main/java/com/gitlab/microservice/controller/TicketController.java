package com.gitlab.microservice.controller;

import com.gitlab.microservice.dto.TicketRequestDto;
import com.gitlab.microservice.dto.TicketResponseDto;
import com.gitlab.microservice.service.TicketService;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ticket")
public class TicketController {

  private final TicketService ticketService;

  @PostMapping
  public ResponseEntity<Object> create(@RequestBody @Valid TicketRequestDto requestDto) {
    ticketService.create(requestDto);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @GetMapping
  public ResponseEntity<Page<TicketResponseDto>> findAll(@RequestParam(defaultValue = "0") Integer page,
                                                         @RequestParam(defaultValue = "10") Integer size) {
    return ResponseEntity.ok(ticketService.findAll(page, size));
  }

  @GetMapping("/{id}")
  public ResponseEntity<TicketResponseDto> findById(@PathVariable String id) {
    return ResponseEntity.ok(ticketService.findById(id));
  }
}
