package project.doribugi.bankingprofiler.profiler.logparser;

import project.doribugi.bankingprofiler.profiler.banking.AccountCreation;

public class AccountCreationLogParser implements LogParser<AccountCreation> {

  @Override
  public String topic() {
    return "account create";
  }

  @Override
  public AccountCreation parse(String logMessage) {
    String[] contents = logMessage.split(",");
    long customerNumber = Long.parseLong(contents[1].trim());
    String accountNumber = contents[2].trim();
    String createDt = contents[3].trim();
    return new AccountCreation(customerNumber, accountNumber, createDt);
  }
}
