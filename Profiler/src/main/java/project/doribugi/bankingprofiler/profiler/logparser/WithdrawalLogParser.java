package project.doribugi.bankingprofiler.profiler.logparser;

import project.doribugi.bankingprofiler.profiler.banking.Withdrawal;

public class WithdrawalLogParser implements LogParser<Withdrawal> {

  @Override
  public String topic() {
    return "withdrawal";
  }

  @Override
  public Withdrawal parse(String logMessage) throws IllegalLogFormatException {
    try {
      String[] contents = logMessage.split(",");
      long customerNumber = Long.parseLong(contents[1].trim());
      String accountNumber = contents[2].trim();
      long withdrawalAmount = Long.parseLong(contents[3].trim());
      String withdrawalDt = contents[4].trim();
      return new Withdrawal(customerNumber, accountNumber, withdrawalAmount, withdrawalDt);
    } catch (Exception e) {
      String message = String.format("Illegal Format - %s", logMessage);
      throw new IllegalLogFormatException(message, e);
    }
  }
}
