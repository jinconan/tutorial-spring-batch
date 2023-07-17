package com.github.jinconan.tutorialspringbatch.framework.adapter.output;

import com.github.jinconan.tutorialspringbatch.domain.entity.Student;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

import java.util.StringJoiner;

@Configuration
public class ItemWriterConfiguration {

    @Bean
    public ItemWriter<Student> studentWriter() {
        return new FlatFileItemWriterBuilder<Student>()
                .name("studentWriter")
                .resource(new FileSystemResource("output/result.csv"))
                .lineSeparator("\n")
                .lineAggregator(student -> new StringJoiner("\t")
                        .add(Long.toString(student.getId()))
                        .add(student.getName())
                        .add(Integer.toString(student.getAge()))
                        .toString())
                .build();

    }
}
