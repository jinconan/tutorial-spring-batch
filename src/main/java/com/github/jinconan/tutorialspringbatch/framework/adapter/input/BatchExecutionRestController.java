package com.github.jinconan.tutorialspringbatch.framework.adapter.input;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/batch")
@RequiredArgsConstructor
public class BatchExecutionRestController {

    private final JobLauncher jobRepository;
    private final Job studentJob;


    @PostMapping("/run")
    public void run() {
        try {
            jobRepository.run(studentJob, new JobParameters());
        } catch (JobExecutionException e) {
            throw new RuntimeException(e);
        }
    }

}
