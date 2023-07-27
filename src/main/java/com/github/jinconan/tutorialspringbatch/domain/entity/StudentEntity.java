package com.github.jinconan.tutorialspringbatch.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;


@Entity
@Table(name = "STUDENT")
@Getter
public class StudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer age;

    @Override
    public String toString() {
        return "Student{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", age=" + age +
            '}';
    }
}
