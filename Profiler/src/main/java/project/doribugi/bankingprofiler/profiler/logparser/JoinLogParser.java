package project.doribugi.bankingprofiler.profiler.logparser;

import project.doribugi.bankingprofiler.profiler.banking.Join;

public class JoinLogParser implements LogParser<Join> {

  @Override
  public String topic() {
    return "join";
  }

  @Override
  public Join parse(String logMessage) {
    String[] contents = logMessage.split(",");
    long accountNumber = Long.parseLong(contents[1].trim());
    String customerName = contents[2].trim();
    String joinDt = contents[3].trim();
    return new Join(accountNumber, customerName, joinDt);
  }
}
