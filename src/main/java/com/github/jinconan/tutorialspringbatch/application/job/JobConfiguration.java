package com.github.jinconan.tutorialspringbatch.application.job;

import com.github.jinconan.tutorialspringbatch.domain.entity.Student;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class JobConfiguration {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    @Bean
    public Job studentJob(Step studentStep) {
        return new JobBuilder("studentJob", jobRepository)
                .start(studentStep)
                .build();
    }

    @Bean
    @JobScope
    public Step studentStep(
            ItemReader<Student> studentReader,
            ItemProcessor<Student, Student> youngStudentFilterProcessor,
            ItemWriter<Student> studentWriter
    ) {
        return new StepBuilder("studentStep", jobRepository)
                .<Student, Student>chunk(5, transactionManager)
                .listener(new ItemProcessListener<>() {

                    @Override
                    public void beforeProcess(Student item) {
                        log.debug("beforeProcess : student : {}", item);
                    }

                    @Override
                    public void afterProcess(Student item, Student result) {
                        log.debug("afterProcess : student : {}", item);
                    }

                    @Override
                    public void onProcessError(Student item, Exception e) {
                        log.error("onProcessError : student : " + item, e);
                    }
                })
                .reader(studentReader)
                .processor(youngStudentFilterProcessor)
                .writer(studentWriter)
                .build();
    }
}
