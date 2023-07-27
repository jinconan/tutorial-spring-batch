package com.github.jinconan.tutorialspringbatch.framework.adapter.input;

import com.github.jinconan.tutorialspringbatch.common.sse.SseEmitters;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
@Slf4j
public class SSERestController {
  private final SseEmitters sseEmitters;

  @GetMapping(
      value = "/connect",
      produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public ResponseEntity<SseEmitter> connect() {
    SseEmitter emitter = sseEmitters.createEmitter();

    try {
      emitter.send(SseEmitter.event()
          .name("connect")
          .data("connected!"));

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return ResponseEntity.ok(emitter);
  }
}


