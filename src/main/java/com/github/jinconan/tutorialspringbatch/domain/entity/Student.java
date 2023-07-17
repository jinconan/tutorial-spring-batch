package com.github.jinconan.tutorialspringbatch.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;
import org.springframework.batch.item.file.transform.LineAggregator;

import java.text.MessageFormat;


@Entity
@Getter
@ToString
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer age;

}
