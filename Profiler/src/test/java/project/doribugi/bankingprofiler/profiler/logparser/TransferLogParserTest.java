package project.doribugi.bankingprofiler.profiler.logparser;

import org.junit.Assert;
import org.junit.Test;
import project.doribugi.bankingprofiler.profiler.banking.Transfer;

public class TransferLogParserTest {

  @Test
  public void testParse() {
    String logMessage = "transfer, 1, 3333010001, 카카오뱅크, 3333010002, 이순신, 30000, 2018-06-30 13:40:00";
    Transfer expected = new Transfer(
        1,
        "3333010001",
        "카카오뱅크",
        "3333010002",
        "이순신",
        30000,
        "2018-06-30 13:40:00"
    );
    TransferLogParser parser = new TransferLogParser();
    Transfer actual = parser.parse(logMessage);
    Assert.assertEquals(expected, actual);
  }

  @Test(expected = IllegalLogFormatException.class)
  public void testParseException() {
    String logMessage = "transfer, abc, 3333010001, 카카오뱅크, 3333010002, 이순신, 30000, 2018-06-30 13:40:00";
    TransferLogParser parser = new TransferLogParser();
    parser.parse(logMessage);
  }

  @Test(expected = IllegalLogFormatException.class)
  public void testParseException2() {
    String logMessage = "transfer, 1, 3333010001, 카카오뱅크, 3333010002, 이순신, 30000원, 2018-06-30 13:40:00";
    TransferLogParser parser = new TransferLogParser();
    parser.parse(logMessage);
  }
}