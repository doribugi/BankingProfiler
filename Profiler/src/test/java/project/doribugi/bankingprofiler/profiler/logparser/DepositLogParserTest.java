package project.doribugi.bankingprofiler.profiler.logparser;

import org.junit.Assert;
import org.junit.Test;
import project.doribugi.bankingprofiler.profiler.banking.Deposit;

public class DepositLogParserTest {

  @Test
  public void testParse() {
    String logMessage = "deposit, 1, 3333010001, 100000, 2018-06-30 13:20:00";
    Deposit expected = new Deposit(
        1,
        "3333010001",
        100000,
        "2018-06-30 13:20:00"
    );
    DepositLogParser parser = new DepositLogParser();
    Deposit actual = parser.parse(logMessage);
    Assert.assertEquals(expected, actual);
  }

  @Test(expected = NumberFormatException.class)
  public void testParseException() {
    String logMessage = "deposit, abc, 3333010001, 100000, 2018-06-30 13:20:00";
    DepositLogParser parser = new DepositLogParser();
    parser.parse(logMessage);
  }

  @Test(expected = NumberFormatException.class)
  public void testParseException2() {
    String logMessage = "deposit, 1, 3333010001, 100000원, 2018-06-30 13:20:00";
    DepositLogParser parser = new DepositLogParser();
    parser.parse(logMessage);
  }
}