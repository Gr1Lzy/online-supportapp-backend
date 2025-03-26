package com.gitlab.microservice.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Getter
@Setter
public class Comment {

    @Field("text")
    private String text;

    @Field("author_id")
    private String authorId;

    @Field("created_date")
    private LocalDateTime createdDate;
}
