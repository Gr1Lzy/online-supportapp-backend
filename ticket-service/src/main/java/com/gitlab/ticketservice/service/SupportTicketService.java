package com.gitlab.ticketservice.service;

import com.gitlab.ticketservice.dto.ticket.StatusRequestDto;
import com.gitlab.ticketservice.dto.user.UserIdRequestDto;

public interface SupportTicketService {

  void assignOnUser(String ticketId, UserIdRequestDto requestDto);

  void unassignUser(String ticketId);

  void updateStatus(String ticketId, StatusRequestDto status);
}
