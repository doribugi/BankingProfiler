package project.doribugi.bankingprofiler.profiler.entity;

import java.util.List;

public class AccountProfile {
  private long customer_number;
  private long account_number;
  private String create_dt;
  private long balance;
  private List<Transaction> deposits;
  private List<Transaction> withdrawals;
  private List<Transaction> transfers;

  public AccountProfile(long customer_number,
      long account_number,
      String create_dt,
      long balance,
      List<Transaction> deposits,
      List<Transaction> withdrawals,
      List<Transaction> transfers) {
    this.customer_number = customer_number;
    this.account_number = account_number;
    this.create_dt = create_dt;
    this.balance = balance;
    this.deposits = deposits;
    this.withdrawals = withdrawals;
    this.transfers = transfers;
  }
}
