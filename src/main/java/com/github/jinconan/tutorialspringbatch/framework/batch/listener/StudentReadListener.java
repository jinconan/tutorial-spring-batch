package com.github.jinconan.tutorialspringbatch.framework.batch.listener;

import com.github.jinconan.tutorialspringbatch.common.sse.SseEmitters;
import com.github.jinconan.tutorialspringbatch.common.sse.SseMessageType;
import com.github.jinconan.tutorialspringbatch.domain.entity.StudentEntity;
import java.lang.System.Logger.Level;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class StudentReadListener implements ItemReadListener<StudentEntity> {

  private final SseEmitters sseEmitters;
  @Override
  public void beforeRead() {
    sseEmitters.sendMessage(SseMessageType.STEP, "beforeRead()");
  }

  @Override
  public void afterRead(StudentEntity item) {
    sseEmitters.sendMessage(SseMessageType.STEP, "afterRead("+ item + ")", item);
  }

  @Override
  public void onReadError(Exception ex) {
    sseEmitters.sendMessage(SseMessageType.STEP, Level.ERROR, "onReadError("+ ex.getMessage() + ")", ex);
  }
}
