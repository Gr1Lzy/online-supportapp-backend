package com.gitlab.ticketservice.mapper;

import com.gitlab.ticketservice.dto.comment.CommentCreateRequestDto;
import com.gitlab.ticketservice.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CommentMapper {
  CommentMapper COMMENT_MAPPER = Mappers.getMapper(CommentMapper.class);

  Comment toEntity(CommentCreateRequestDto requestDto);
}
