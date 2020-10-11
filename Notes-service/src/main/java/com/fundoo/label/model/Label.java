package com.fundoo.label.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Label {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer label_Id;

    private String labelName;

    private Integer userId;

    @JsonIgnore
    private LocalDateTime createdDate=LocalDateTime.now();

    @JsonIgnore
    private LocalDateTime modifiedDate;

}
