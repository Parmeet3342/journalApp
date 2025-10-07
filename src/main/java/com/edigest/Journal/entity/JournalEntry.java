package com.edigest.Journal.entity;

import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "journal_entries")
@Data
public class JournalEntry {

    @Id
    private ObjectId objectId;

    @NonNull
    private String title;

    private String content;
}
