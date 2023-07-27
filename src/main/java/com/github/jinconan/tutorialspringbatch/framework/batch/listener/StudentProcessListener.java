package com.github.jinconan.tutorialspringbatch.framework.batch.listener;

import com.github.jinconan.tutorialspringbatch.common.sse.SseEmitters;
import com.github.jinconan.tutorialspringbatch.common.sse.SseMessageType;
import com.github.jinconan.tutorialspringbatch.domain.entity.StudentEntity;
import com.github.jinconan.tutorialspringbatch.domain.model.StudentDto;
import java.lang.System.Logger.Level;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class StudentProcessListener implements ItemProcessListener<StudentEntity, StudentDto> {

  private final SseEmitters sseEmitters;

  @Override
  public void beforeProcess(StudentEntity item) {
    log.debug("studentStep beforeProcess : student : {}", item);
    sseEmitters.sendMessage(SseMessageType.STEP, "beforeProcess" + item + ")", item);
  }

  @Override
  public void afterProcess(StudentEntity item, StudentDto result) {
    log.debug("studentStep afterProcess : student : {}", item);
    sseEmitters.sendMessage(SseMessageType.STEP, "afterProcess" + item + ", " + result + ")", item);
  }

  @Override
  public void onProcessError(StudentEntity item, Exception e) {
    sseEmitters.sendMessage(SseMessageType.STEP, Level.ERROR,"onProcessError" + item + ", " + e.getMessage() + ")", item);
  }
}
