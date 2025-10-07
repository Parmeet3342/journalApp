package com.edigest.Journal.entity;

import com.mongodb.lang.NonNull;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "user")
@Data
public class User {

    @Id
    private ObjectId objectId;

    @NonNull
    private String userName;

    @NonNull
    private String password;

    private List<String> roles;

    @DBRef
    private List<JournalEntry> journals = new ArrayList<>();
}
