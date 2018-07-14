package project.doribugi.bankingprofiler.profiler.logparser;

import org.junit.Assert;
import org.junit.Test;
import project.doribugi.bankingprofiler.profiler.banking.Join;

public class JoinLogParserTest {

  @Test
  public void testParse() {
    String logMessage = "join, 1, 홍길동, 2018-06-30 13:00:00";
    Join expected = new Join(
        1,
        "홍길동",
        "2018-06-30 13:00:00"
    );
    JoinLogParser parser = new JoinLogParser();
    Join actual = parser.parse(logMessage);
    Assert.assertEquals(expected, actual);
  }

  @Test(expected = IllegalLogFormatException.class)
  public void testParseException() {
    String logMessage = "join, abc, 3333010001, 2018-06-30 13:00:00";
    JoinLogParser parser = new JoinLogParser();
    parser.parse(logMessage);
  }
}