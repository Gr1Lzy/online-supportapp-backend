package com.gitlab.ticketservice.controller;

import com.gitlab.ticketservice.dto.ticket.StatusRequestDto;
import com.gitlab.ticketservice.dto.user.UserIdRequestDto;
import com.gitlab.ticketservice.service.SupportTicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/support/tickets")
public class SupportTicketController {

  private final SupportTicketService supportTicketService;

  @PostMapping("/{ticketId}")
  public void assignOnUser(@PathVariable String ticketId, UserIdRequestDto requestDto) {
    supportTicketService.assignOnUser(ticketId, requestDto);
  }

  @PostMapping("/{ticketId}/unassign")
  public void unassignUser(@PathVariable String ticketId) {
    supportTicketService.unassignUser(ticketId);
  }

  @PostMapping("/{ticketId}/status-update")
  public void updateStatus(@PathVariable String ticketId, StatusRequestDto status) {
    supportTicketService.updateStatus(ticketId, status);
  }
}
