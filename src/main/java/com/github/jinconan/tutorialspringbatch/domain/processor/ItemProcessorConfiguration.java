package com.github.jinconan.tutorialspringbatch.domain.processor;

import com.github.jinconan.tutorialspringbatch.domain.entity.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class ItemProcessorConfiguration {

    @Bean
    public ItemProcessor<Student, Student> youngStudentFilterProcessor() {
        return (student) -> {

            log.debug("student : {}", student);
            if (student.getAge() < 20) {
                return student;
            } else if (student.getAge() >= 40) {
                throw new IllegalArgumentException();
            }

            return null;
        };
    }
}
