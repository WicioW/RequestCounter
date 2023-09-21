package com.wiciow.requestcounter.model;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@Getter
@Builder
public class User {
    @Id
    private String login;
    @Default
    private Integer requestCount = 0;

}
