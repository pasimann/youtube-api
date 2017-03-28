package com.pasimann.app.service;

import java.util.concurrent.ThreadLocalRandom;
import java.lang.StringBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ChatMessageService {

  private String[] subject = {"I", "one", "I", "you", "somebody", "user", "they", "customer", "people", "those", "he/she", "them"};
  private String[] verb    = {"broke", "fix", "tested", "interprete", "tried", "did", "be done", "corrected", "refactorized", "send", "follow", "breaks", "occuring"};
  private String[] object  = {"code", "issue", "code", "package", "software", "memory", "template", "UI", "tool", "component", "ticket", "JIRA"};
  private String[] filling = {"crusial", "difference", "often", "accurate", "situation", "maybe", "propable", "wonder", "with", "excited", "daily"};

  public ChatMessageService() {}

  public String generateChatMessage(String receiver) {

    StringBuffer sb = new StringBuffer();

    sb.append(receiver);
    sb.append(", ");

    sb.append(subject[ThreadLocalRandom.current().nextInt(0, subject.length)]);
    sb.append(" ");
    sb.append(verb[ThreadLocalRandom.current().nextInt(0, verb.length)]);
    sb.append(" ");
    sb.append(object[ThreadLocalRandom.current().nextInt(0, object.length)]);
    sb.append(" ");
    sb.append(filling[ThreadLocalRandom.current().nextInt(0, filling.length)]);

    int more = ThreadLocalRandom.current().nextInt(0,10);

    if ((more % 2) == 0) {
      sb.append(" ");
      sb.append(object[ThreadLocalRandom.current().nextInt(0, object.length)]);
      sb.append(" ");
      sb.append(verb[ThreadLocalRandom.current().nextInt(0, verb.length)]);

    }

    if (more % 2 == 0) {
      if (more % 6 == 0) {
        sb.append("???!!!");
      } else {
        sb.append("!");
      }
    } else {
      sb.append(".");
    }

    return sb.toString();
  }

}
