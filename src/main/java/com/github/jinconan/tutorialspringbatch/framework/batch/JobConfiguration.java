package com.github.jinconan.tutorialspringbatch.framework.batch;

import com.github.jinconan.tutorialspringbatch.domain.entity.StudentEntity;
import com.github.jinconan.tutorialspringbatch.common.sse.SseEmitters;
import com.github.jinconan.tutorialspringbatch.domain.model.StudentDto;
import com.github.jinconan.tutorialspringbatch.framework.batch.listener.StudentProcessListener;
import com.github.jinconan.tutorialspringbatch.framework.batch.listener.StudentReadListener;
import com.github.jinconan.tutorialspringbatch.framework.batch.listener.StudentWriteListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
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
    public Job studentJob(
        Step studentStep,
        JobExecutionListener studentJobListener) {

        return new JobBuilder("studentJob", jobRepository)
                .start(studentStep)
                .incrementer(new RunIdIncrementer())
                .listener(studentJobListener)
                .build();
    }

    @Bean
    @JobScope
    public Step studentStep(
            ItemReader<StudentEntity> studentReader,
            ItemProcessor<StudentEntity, StudentDto> youngStudentFilterProcessor,
            ItemWriter<StudentDto> studentWriter,
            // listener
            StudentReadListener studentReadListener,
            StudentProcessListener studentProcessListener,
            StudentWriteListener studentWriteListener
    ) {
        return new StepBuilder("studentStep", jobRepository)
                .<StudentEntity, StudentDto>chunk(3, transactionManager)
                .reader(studentReader)
                .listener(studentReadListener)
                .processor(youngStudentFilterProcessor)
                .listener(studentProcessListener)
                .writer(studentWriter)
                .listener(studentWriteListener)
                .build();
    }
}
