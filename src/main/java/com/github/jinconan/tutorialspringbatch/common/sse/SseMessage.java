package com.github.jinconan.tutorialspringbatch.common.sse;

import java.io.Serial;
import java.io.Serializable;
import java.lang.System.Logger.Level;
import lombok.Getter;

@Getter
public class SseMessage implements Serializable {
  @Serial
  private static final long serialVersionUID = 42L;

  private final SseMessageType type;
  private final String level;
  private final String message;
//  private final Object payload;

  public SseMessage(SseMessageType type, Level level, String message, Object payload) {
    this.type = type;
    this.level = level.name();
    this.message = message;
//    if (payload == null) {
//      payload = new Object();
//    }
//    this.payload = payload;

  }

}
