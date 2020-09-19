package com.github.kaellybot.portals.model.entity;

import com.github.kaellybot.portals.model.constants.Privilege;
import com.github.kaellybot.portals.model.constants.UserType;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Data
@Builder
@Document(collection = "tokens")
public class Token {

    @Id
    private String id;

    @Indexed(unique = true)
    private String username;
    private String password;
    private UserType userType;
    private List<Privilege> privileges;
}