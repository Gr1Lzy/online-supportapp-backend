package com.gitlab.ticketservice.service;

import com.gitlab.ticketservice.dto.comment.CommentCreateRequestDto;

public interface CommentService {

  void addComment(String ticketId, CommentCreateRequestDto requestDto);

  void editComment(String commentId, CommentCreateRequestDto requestDto);

  void deleteComment(String commentId);
}
