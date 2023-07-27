package com.github.jinconan.tutorialspringbatch.framework.adapter.input;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/batch")
@RequiredArgsConstructor
public class BatchExecutionRestController {

    private final JobLauncher jobLauncher;
    private final Job studentJob;


    @PostMapping("/run")
    public String run() {
        try {
            jobLauncher.run(studentJob, new JobParameters());
        } catch (JobExecutionException e) {
            throw new RuntimeException(e);
        }

        return "job을 실행하였습니다";
    }

}
