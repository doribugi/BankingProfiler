package project.doribugi.bankingprofiler.profiler.logparser;

import project.doribugi.bankingprofiler.profiler.banking.Transfer;

public class TransferLogParser implements LogParser<Transfer> {

  @Override
  public String topic() {
    return "transfer";
  }

  @Override
  public Transfer parse(String logMessage) throws IllegalLogFormatException {
    try {
      String[] contents = logMessage.split(",");
      long customerNumber = Long.parseLong(contents[1].trim());
      String accountNumber = contents[2].trim();
      String receivingBank = contents[3].trim();
      String receivingAccountNumber = contents[4].trim();
      String receivingAccountOwner = contents[5].trim();
      long transferAmount = Long.parseLong(contents[6].trim());
      String transferDt = contents[7].trim();
      return new Transfer(
          customerNumber,
          accountNumber,
          receivingBank,
          receivingAccountNumber,
          receivingAccountOwner,
          transferAmount,
          transferDt
      );
    } catch (Exception e) {
      String message = String.format("Illegal Format - %s", logMessage);
      throw new IllegalLogFormatException(message, e);
    }
  }
}
