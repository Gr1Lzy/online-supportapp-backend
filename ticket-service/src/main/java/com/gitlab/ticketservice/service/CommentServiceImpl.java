package com.gitlab.ticketservice.service;

import com.gitlab.ticketservice.dto.comment.CommentCreateRequestDto;
import com.gitlab.ticketservice.entity.Comment;
import com.gitlab.ticketservice.entity.Ticket;
import com.gitlab.ticketservice.entity.TicketStatus;
import com.gitlab.ticketservice.exception.EntityNotFoundException;
import com.gitlab.ticketservice.exception.TicketMessageException;
import com.gitlab.ticketservice.repository.CommentRepository;
import com.gitlab.ticketservice.repository.TicketRepository;
import com.gitlab.ticketservice.util.TicketEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static com.gitlab.ticketservice.mapper.CommentMapper.COMMENT_MAPPER;
import static com.gitlab.ticketservice.util.UserUtil.getCurrentUserId;

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

  private final CommentRepository commentRepository;
  private final TicketRepository ticketRepository;
  private final ApplicationEventPublisher eventPublisher;

  @Override
  @Transactional
  public void addComment(String ticketId, CommentCreateRequestDto requestDto) {
    Ticket ticket = getTicketIfExist(ticketId);
    checkTicketStatus(ticket);

    Comment comment = COMMENT_MAPPER.toEntity(requestDto).init(ticketId);

    Comment savedComment = commentRepository.save(comment);

    ticket.getComments().add(savedComment);
    ticketRepository.save(ticket);

    eventPublisher.publishEvent(
        new TicketEvent(this, ticket.getId(), COMMENT_ADD_LOG)
    );
  }

  @Override
  @Transactional
  public void editComment(String commentId, CommentCreateRequestDto requestDto) {
    Comment comment = getCommentIfExist(commentId);
    checkCommentOwner(comment, getCurrentUserId());

    Ticket ticket = getTicketIfExist(comment.getTicketId());
    checkTicketStatus(ticket);

    comment.setText(requestDto.getText());
    commentRepository.save(comment);

    eventPublisher.publishEvent(
        new TicketEvent(this, comment.getTicketId(), COMMENT_EDIT_LOG)
    );
  }

  @Override
  @Transactional
  public void deleteComment(String commentId) {
    Comment comment = getCommentIfExist(commentId);
    String ticketId = comment.getTicketId();
    checkCommentOwner(comment, getCurrentUserId());

    Ticket ticket = getTicketIfExist(ticketId);
    checkTicketStatus(ticket);

    commentRepository.delete(comment);

    eventPublisher.publishEvent(
        new TicketEvent(this, ticketId, COMMENT_DELETE_LOG)
    );
  }

  private void checkCommentOwner(Comment comment, String userId) {
    if (!Objects.equals(comment.getAuthorId(), userId)) {
      throw new TicketMessageException(NOT_COMMENT_OWNER);
    }
  }

  private Ticket getTicketIfExist(String ticketId) {
    return ticketRepository.findById(ticketId)
        .orElseThrow(() -> new EntityNotFoundException(TICKET_NOT_FOUND));
  }

  private Comment getCommentIfExist(String commentId) {
    return commentRepository.findById(commentId)
        .orElseThrow(() -> new EntityNotFoundException(COMMENT_NOT_FOUND));
  }

  private void checkTicketStatus(Ticket ticket) {
    if (ticket.getStatus() == TicketStatus.CLOSED) {
      throw new TicketMessageException(TICKET_CLOSED);
    }
  }
}