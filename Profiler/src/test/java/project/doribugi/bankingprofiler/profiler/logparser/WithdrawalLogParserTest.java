package project.doribugi.bankingprofiler.profiler.logparser;

import org.junit.Assert;
import org.junit.Test;
import project.doribugi.bankingprofiler.profiler.banking.Withdrawal;

public class WithdrawalLogParserTest {

  @Test
  public void testParse() {
    String logMessage = "withdrawal, 1, 3333010001, 10000, 2018-06-30 13:30:00";
    Withdrawal expected = new Withdrawal(
        1,
        "3333010001",
        10000,
        "2018-06-30 13:30:00"
    );
    WithdrawalLogParser parser = new WithdrawalLogParser();
    Withdrawal actual = parser.parse(logMessage);
    Assert.assertEquals(expected, actual);
  }

  @Test(expected = NumberFormatException.class)
  public void testParseException1() {
    String logMessage = "withdrawal, abc, 3333010001, 10000, 2018-06-30 13:30:00";
    WithdrawalLogParser parser = new WithdrawalLogParser();
    parser.parse(logMessage);
  }

  @Test(expected = NumberFormatException.class)
  public void testParseException2() {
    String logMessage = "withdrawal, 1, 3333010001, 10000원, 2018-06-30 13:30:00";
    WithdrawalLogParser parser = new WithdrawalLogParser();
    parser.parse(logMessage);
  }
}