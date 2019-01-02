package main;

import java.util.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class Message {
  public String id;
  public String content;
  public Date timestamp;
  public User author;

  public void cg_init_Message_1(
      final String msgID, final String cont, final User auth, final Date date) {

    id = msgID;
    content = cont;
    author = auth;
    timestamp = date;
    return;
  }

  public Message(final String msgID, final String cont, final User auth, final Date date) {

    cg_init_Message_1(msgID, cont, auth, date);
  }

  public Message() {}

  public String toString() {

    return "Message{"
        + "id := "
        + Utils.toString(id)
        + ", content := "
        + Utils.toString(content)
        + ", timestamp := "
        + Utils.toString(timestamp)
        + ", author := "
        + Utils.toString(author)
        + "}";
  }
}
