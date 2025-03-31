package com.gitlab.microservice.dto.page;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PageDto<D> {

  @JsonProperty("content")
  private List<D> content;

  @JsonProperty("page")
  private Integer page;

  @JsonProperty("size")
  private Integer size;

  @JsonProperty("has_next")
  private Boolean hasNext;
}
