package com.github.jinconan.tutorialspringbatch;

import com.github.jinconan.tutorialspringbatch.application.job.JobConfiguration;
import com.github.jinconan.tutorialspringbatch.domain.processor.ItemProcessorConfiguration;
import com.github.jinconan.tutorialspringbatch.framework.adapter.input.ItemReaderConfiguration;
import com.github.jinconan.tutorialspringbatch.framework.adapter.output.ItemWriterConfiguration;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.NestedTestConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBatchTest
@ContextConfiguration(classes = {
    JobConfiguration.class,
    ItemProcessorConfiguration.class,
    ItemReaderConfiguration.class,
    ItemWriterConfiguration.class
})
@SpringBootTest
class TutorialSpringBatchApplicationTests {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private Job studentJob;

    @Test
    void contextLoads() throws Exception {
        jobLauncherTestUtils.setJob(studentJob);
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(new JobParametersBuilder().toJobParameters());

        Assertions.assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);
    }
}
