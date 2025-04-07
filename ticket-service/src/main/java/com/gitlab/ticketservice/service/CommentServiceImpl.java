package com.gitlab.ticketservice.service;

import com.gitlab.ticketservice.dto.comment.CommentCreateRequestDto;
import com.gitlab.ticketservice.entity.Comment;
import com.gitlab.ticketservice.entity.Ticket;
import com.gitlab.ticketservice.entity.TicketLog;
import com.gitlab.ticketservice.entity.TicketStatus;
import com.gitlab.ticketservice.exception.EntityNotFoundException;
import com.gitlab.ticketservice.exception.TicketSendMessageException;
import com.gitlab.ticketservice.repository.TicketRepository;
import com.gitlab.ticketservice.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

  private static final String COMMENT_ADD_LOG = "Added comment";
  private static final String COMMENT_EDIT_LOG = "Edited comment";
  private static final String COMMENT_DELETE_LOG = "Deleted comment";
  private static final String TICKET_NOT_FOUND = "Ticket not found";
  private static final String COMMENT_NOT_FOUND = "Comment not found";
  private static final String TICKET_CLOSED = "Ticket is closed";
  private static final String NOT_COMMENT_OWNER = "You can't edit or delete comments that don't belong to you";

  private final TicketRepository ticketRepository;

  @Override
  public void addComment(String ticketId, CommentCreateRequestDto requestDto) {
    processTicket(ticketId, ticket -> {
      String userId = UserUtil.getCurrentUserId();
      LocalDateTime now = LocalDateTime.now();

      Comment comment = new Comment();
      comment.setText(requestDto.getComment());
      comment.setAuthorId(userId);
      comment.setCreatedDate(now);
      ensureCommentsList(ticket).add(comment);

      addLogEntry(ticket, COMMENT_ADD_LOG, userId, now);
    });
  }

  @Override
  public void editComment(String ticketId, String commentId, CommentCreateRequestDto requestDto) {
    processTicket(ticketId, ticket -> {
      String userId = UserUtil.getCurrentUserId();

      Comment comment = findComment(ticket, commentId);
      validateCommentOwnership(comment, userId);

      comment.setText(requestDto.getComment());

      addLogEntry(ticket, COMMENT_EDIT_LOG, userId, LocalDateTime.now());
    });
  }

  @Override
  public void deleteComment(String ticketId, String commentId) {
    processTicket(ticketId, ticket -> {
      String userId = UserUtil.getCurrentUserId();

      Comment comment = findComment(ticket, commentId);
      validateCommentOwnership(comment, userId);

      ticket.getComments().remove(comment);

      addLogEntry(ticket, COMMENT_DELETE_LOG, userId, LocalDateTime.now());
    });
  }

  private void processTicket(String ticketId, Consumer<Ticket> action) {
    Ticket ticket = ticketRepository.findById(ticketId)
        .orElseThrow(() -> new EntityNotFoundException(TICKET_NOT_FOUND));

    if (ticket.getStatus() == TicketStatus.CLOSED) {
      throw new TicketSendMessageException(TICKET_CLOSED);
    }

    action.accept(ticket);
    ticketRepository.save(ticket);
  }

  private Comment findComment(Ticket ticket, String commentId) {
    return ticket.getComments().stream()
        .filter(c -> Objects.equals(c.getId(), commentId))
        .findFirst()
        .orElseThrow(() -> new EntityNotFoundException(COMMENT_NOT_FOUND));
  }

  private void validateCommentOwnership(Comment comment, String userId) {
    if (!Objects.equals(comment.getAuthorId(), userId)) {
      throw new TicketSendMessageException(NOT_COMMENT_OWNER);
    }
  }

  private ArrayList<Comment> ensureCommentsList(Ticket ticket) {
    if (ticket.getComments() == null) {
      ticket.setComments(new ArrayList<>());
    }
    return (ArrayList<Comment>) ticket.getComments();
  }

  private void addLogEntry(Ticket ticket, String action, String userId, LocalDateTime timestamp) {
    TicketLog log = new TicketLog();
    log.setAction(action);
    log.setActionById(userId);
    log.setActionDate(timestamp);

    if (ticket.getLogs() == null) {
      ticket.setLogs(new ArrayList<>());
    }
    ticket.getLogs().add(log);
  }
}