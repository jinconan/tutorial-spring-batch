package com.github.jinconan.tutorialspringbatch.common.sse;

import java.io.IOException;
import java.lang.System.Logger.Level;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Component
@Slf4j
public class SseEmitters {
  private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();
  private static final long TIMEOUT = 30000L;

  public SseEmitter createEmitter() {
    SseEmitter emitter = new SseEmitter(TIMEOUT);
    this.emitters.add(emitter);
    log.info("new emitter added: {}, emitter list size: {}", emitter, emitters.size());

    emitter.onCompletion(() -> {
      log.info("SseEmitter.onCompletion() callback ===> {}", emitter);
      this.emitters.remove(emitter);    // 만료되면 리스트에서 삭제
    });
    emitter.onTimeout(() -> {
      log.info("SseEmitter.onTimeout() callback ===> {}", emitter);
      emitter.complete();
    });

    return emitter;
  }

  public void remove(SseEmitter emitter) {
    this.emitters.remove(emitter);
  }

  public void sendMessage(SseMessageType type, String message) {
    sendMessage(type, Level.INFO, message, null);
  }

  public void sendMessage(SseMessageType type, String message, Object payload) {
    sendMessage(type, Level.INFO, message, payload);
  }

  public void sendMessage(SseMessageType type, Level level, String message, Object payload) {
    emitters.forEach(emitters -> {
      try {

        emitters.send(SseEmitter.event()
            .name("message")
            .data(new SseMessage(type, level, message, payload)));
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
  }


}
