package com.github.jinconan.tutorialspringbatch.framework.batch.listener;

import com.github.jinconan.tutorialspringbatch.common.sse.SseEmitters;
import com.github.jinconan.tutorialspringbatch.common.sse.SseMessageType;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StudentJobListener implements JobExecutionListener {
  private final SseEmitters sseEmitters;
  @Override
  public void beforeJob(JobExecution jobExecution) {
    sseEmitters.sendMessage(SseMessageType.JOB, "beforeJob(jobId="+ jobExecution.getJobId() + ")");
  }

  @Override
  public void afterJob(JobExecution jobExecution) {
    sseEmitters.sendMessage(SseMessageType.JOB, "afterJob(jobId="+ jobExecution.getJobId() + ")");
  }
}
