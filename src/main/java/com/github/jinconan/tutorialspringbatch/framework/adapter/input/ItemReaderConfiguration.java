package com.github.jinconan.tutorialspringbatch.framework.adapter.input;

import com.github.jinconan.tutorialspringbatch.domain.entity.Student;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ItemReaderConfiguration {

    @Bean
    public ItemReader<Student> studentReader(EntityManagerFactory entityManagerFactory) {
        return new JpaPagingItemReaderBuilder<Student>()
                .name("studentReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("select s from Student s")
                .pageSize(2)
                .build();
    }

}
