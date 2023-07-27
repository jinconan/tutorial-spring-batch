package com.github.jinconan.tutorialspringbatch.framework.batch;

import com.github.jinconan.tutorialspringbatch.domain.entity.StudentEntity;
import com.github.jinconan.tutorialspringbatch.domain.model.StudentDto;
import jakarta.persistence.EntityManagerFactory;
import java.util.StringJoiner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
@Slf4j
public class StepConfiguration {

  @Bean
  public ItemReader<StudentEntity> studentReader(EntityManagerFactory entityManagerFactory) {
    return new JpaPagingItemReaderBuilder<StudentEntity>()
        .name("studentReader")
        .entityManagerFactory(entityManagerFactory)
        .queryString("select s from StudentEntity s")
        .pageSize(2)
        .build();
  }

  @Bean
  public ItemProcessor<StudentEntity, StudentDto> youngStudentFilterProcessor() {
    return (studentEntity) -> {

      log.debug("student : {}", studentEntity);
      if (studentEntity.getAge() < 20) {
        return StudentDto.of(studentEntity);
      } else if (studentEntity.getAge() >= 40) {
        throw new IllegalArgumentException();
      }

      return null;
    };
  }

  @Bean
  public ItemWriter<StudentDto> studentWriter() {
    return new FlatFileItemWriterBuilder<StudentDto>()
        .name("studentWriter")
        .resource(new FileSystemResource("out/result.csv"))
        .lineSeparator("\n")
        .lineAggregator(studentEntity -> new StringJoiner("\t")
            .add(studentEntity.name())
            .add(Integer.toString(studentEntity.age()))
            .toString())
        .build();

  }
}
