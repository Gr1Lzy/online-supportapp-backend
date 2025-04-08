package com.gitlab.ticketservice.mapper;

import com.gitlab.ticketservice.dto.comment.CommentResponseDto;
import com.gitlab.ticketservice.dto.log.LogResponseDto;
import com.gitlab.ticketservice.dto.ticket.TicketRequestDto;
import com.gitlab.ticketservice.dto.ticket.TicketResponseDto;
import com.gitlab.ticketservice.entity.Comment;
import com.gitlab.ticketservice.entity.Log;
import com.gitlab.ticketservice.entity.Ticket;
import com.gitlab.ticketservice.mapper.context.UserMappingContext;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring")
public interface TicketMapper {

  TicketMapper TICKET_MAPPER = Mappers.getMapper(TicketMapper.class);

  @Mapping(target = "assignee", expression = "java(context != null " +
      "? context.getUserService().safeFindById(ticket.getAssigneeId()) " +
      ": null)")
  @Mapping(target = "reporter", expression = "java(context != null " +
      "? context.getUserService().safeFindById(ticket.getReporterId()) " +
      ": null)")
  TicketResponseDto toDto(Ticket ticket, @Context UserMappingContext context);

  Ticket toEntity(TicketRequestDto requestDto);

  default List<CommentResponseDto> mapComments(List<Comment> comments, @Context UserMappingContext context) {
    if (comments == null) {
      return Collections.emptyList();
    }

    return comments.stream()
        .map(comment -> mapComment(comment, context))
        .toList();
  }

  default List<LogResponseDto> mapLogs(List<Log> logs, @Context UserMappingContext context) {
    if (logs == null) {
      return Collections.emptyList();
    }

    return logs.stream()
        .map(log -> mapLog(log, context))
        .toList();
  }

  @Named("mapComment")
  default CommentResponseDto mapComment(Comment comment, @Context UserMappingContext context) {
    if (comment == null) {
      return null;
    }

    CommentResponseDto dto = new CommentResponseDto();
    dto.setText(comment.getText());
    dto.setCreatedDate(comment.getCreatedAt().toString());

    if (context != null && comment.getAuthorId() != null) {
      dto.setAuthor(context.getUserService().safeFindById(comment.getAuthorId()));
    }

    return dto;
  }

  @Named("mapLog")
  default LogResponseDto mapLog(Log log, @Context UserMappingContext context) {
    if (log == null) {
      return null;
    }

    LogResponseDto dto = new LogResponseDto();
    dto.setAction(log.getAction());
    dto.setActionDate(String.valueOf(log.getActionDate()));

    if (context != null && log.getActionById() != null) {
      dto.setActionBy(context.getUserService().safeFindById(log.getActionById()));
    }

    return dto;
  }
}