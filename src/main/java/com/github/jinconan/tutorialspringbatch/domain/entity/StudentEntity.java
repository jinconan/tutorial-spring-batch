package com.github.jinconan.tutorialspringbatch.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;


@Entity
@Table(name = "STUDENT")
@Getter
@ToString
public class StudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer age;

}
