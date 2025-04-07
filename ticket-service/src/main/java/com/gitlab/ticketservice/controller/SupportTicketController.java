package com.gitlab.ticketservice.controller;

import com.gitlab.ticketservice.dto.ticket.StatusRequestDto;
import com.gitlab.ticketservice.dto.user.UserIdRequestDto;
import com.gitlab.ticketservice.service.SupportTicketService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/support/tickets")
public class SupportTicketController {

  private final SupportTicketService supportTicketService;

  @Operation(description = "Assign ticket on user")
  @PatchMapping("/{ticketId}")
  public void assignOnUser(@PathVariable String ticketId, UserIdRequestDto requestDto) {
    supportTicketService.assignOnUser(ticketId, requestDto);
  }

  @Operation(description = "Unassign ticket")
  @PatchMapping("/{ticketId}/unassign")
  public void unassignUser(@PathVariable String ticketId) {
    supportTicketService.unassignUser(ticketId);
  }

  @Operation(description = "Update ticket status")
  @PatchMapping("/{ticketId}/status-update")
  public void updateStatus(@PathVariable String ticketId, StatusRequestDto status) {
    supportTicketService.updateStatus(ticketId, status);
  }
}
