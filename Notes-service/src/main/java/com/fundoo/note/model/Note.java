package com.fundoo.note.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer note_Id;

    private Integer userId;

    private String title;

    private String description;

    private LocalDateTime createdDate = LocalDateTime.now();

    private LocalDateTime editDate;

    private boolean isTrash;

    private boolean isPin;

    private boolean isArchive;

    private boolean isCollaborateNote;

    public String color;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm a")
    private LocalDateTime remainder;

}
