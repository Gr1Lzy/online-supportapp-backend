package com.gitlab.ticketservice.controller;

import com.gitlab.ticketservice.dto.comment.CommentCreateRequestDto;
import com.gitlab.ticketservice.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Comment Controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {

  private final CommentService commentService;

  @Operation(description = "Add comment to ticket")
  @PostMapping("/{ticketId}")
  public ResponseEntity<Object> addComment(@PathVariable String ticketId,
                                           @RequestBody CommentCreateRequestDto requestDto) {
    commentService.addComment(ticketId, requestDto);
    return ResponseEntity.ok().build();
  }

  @Operation(description = "Update comment")
  @PatchMapping("/{commentId}")
  public ResponseEntity<Object> updateComment(@PathVariable String commentId,
                                              @RequestBody CommentCreateRequestDto requestDto) {
    commentService.editComment(commentId, requestDto);
    return ResponseEntity.ok().build();
  }

  @Operation(description = "Delete comment")
  @DeleteMapping("/{commentId}")
  public ResponseEntity<Object> deleteComment(@PathVariable String commentId) {
    commentService.deleteComment(commentId);
    return ResponseEntity.ok().build();
  }
}
