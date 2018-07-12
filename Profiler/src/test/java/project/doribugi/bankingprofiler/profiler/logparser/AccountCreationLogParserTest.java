package project.doribugi.bankingprofiler.profiler.logparser;

import org.junit.Assert;
import org.junit.Test;
import project.doribugi.bankingprofiler.profiler.banking.AccountCreation;

public class AccountCreationLogParserTest {

  @Test
  public void testParse() throws IllegalLogFormatException {
    String logMessage = "account create, 1, 3333010001, 2018-06-30 13:15:00";
    AccountCreation expected = new AccountCreation(
        1,
        "3333010001",
        "2018-06-30 13:15:00"
    );
    AccountCreationLogParser parser = new AccountCreationLogParser();
    AccountCreation actual = parser.parse(logMessage);
    Assert.assertEquals(expected, actual);
  }

  @Test(expected = NumberFormatException.class)
  public void testParseException() throws IllegalLogFormatException {
    String logMessage = "account create, asd234, 3333010001, 2018-06-30 13:15:00";
    AccountCreationLogParser parser = new AccountCreationLogParser();
    parser.parse(logMessage);
  }
}