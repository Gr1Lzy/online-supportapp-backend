package com.gitlab.ticketservice.service;

public interface CommentService {

  void addComment(String ticketId, String comment);
}
