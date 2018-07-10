package project.doribugi.bankingprofiler.profiler.profile;

public class Transaction {
  private long amount;
  private String datetime;

  public Transaction(long amount, String datetime) {
    this.amount = amount;
    this.datetime = datetime;
  }
}
