package com.github.jinconan.tutorialspringbatch.framework.batch.listener;

import com.github.jinconan.tutorialspringbatch.common.sse.SseEmitters;
import com.github.jinconan.tutorialspringbatch.common.sse.SseMessageType;
import com.github.jinconan.tutorialspringbatch.domain.model.StudentDto;
import java.lang.System.Logger.Level;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.item.Chunk;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class StudentWriteListener implements ItemWriteListener<StudentDto> {

  private final SseEmitters sseEmitters;
  @Override
  public void beforeWrite(Chunk<? extends StudentDto> items) {
    sseEmitters.sendMessage(SseMessageType.STEP, "beforeWrite(chunkSize = "+items.size()+")", items.getItems());
  }

  @Override
  public void afterWrite(Chunk<? extends StudentDto> items) {
    sseEmitters.sendMessage(SseMessageType.STEP, "afterWrite(chunkSize = "+items.size()+")", items.getItems());
  }

  @Override
  public void onWriteError(Exception exception, Chunk<? extends StudentDto> items) {
    sseEmitters.sendMessage(SseMessageType.STEP, Level.ERROR, "onWriteError(" + exception.getMessage() + ", chunkSize = "+items.size()+")", items.getItems());
  }
}
