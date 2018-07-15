package project.doribugi.bankingprofiler.profiler.logparser;

import project.doribugi.bankingprofiler.profiler.banking.AccountCreation;

public class AccountCreationLogParser implements LogParser<AccountCreation> {

  @Override
  public String getLogType() {
    return "account_create";
  }

  @Override
  public AccountCreation parse(String logMessage) throws IllegalLogFormatException {
    try {
      String[] contents = logMessage.split(",");
      long customerNumber = Long.parseLong(contents[1].trim());
      String accountNumber = contents[2].trim();
      String createDt = contents[3].trim();
      return new AccountCreation(customerNumber, accountNumber, createDt);
    } catch (Exception e) {
      String message = String.format("Illegal Format - %s", logMessage);
      throw new IllegalLogFormatException(message, e);
    }
  }
}
