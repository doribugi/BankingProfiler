package project.doribugi.bankingprofiler.profiler.logparser;

import project.doribugi.bankingprofiler.profiler.banking.Deposit;

public class DepositLogParser implements LogParser<Deposit> {

  @Override
  public String topic() {
    return "deposit";
  }

  @Override
  public Deposit parse(String logMessage) throws IllegalLogFormatException {
    try {
      String[] contents = logMessage.split(",");
      long customerNumber = Long.parseLong(contents[1].trim());
      String accountNumber = contents[2].trim();
      long depositAmount = Long.parseLong(contents[3].trim());
      String depositDt = contents[4].trim();
      return new Deposit(customerNumber, accountNumber, depositAmount, depositDt);
    } catch (Exception e) {
      String message = String.format("Illegal Format - %s", logMessage);
      throw new IllegalLogFormatException(message, e);
    }
  }
}
