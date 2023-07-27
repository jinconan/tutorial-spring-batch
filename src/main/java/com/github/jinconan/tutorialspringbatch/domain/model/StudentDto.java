package com.github.jinconan.tutorialspringbatch.domain.model;

import com.github.jinconan.tutorialspringbatch.domain.entity.StudentEntity;

public record StudentDto(
    String name,
    Integer age
) {
  public static StudentDto of(StudentEntity entity) {
    return new StudentDto(entity.getName(), entity.getAge());
  }
}
