package project.doribugi.bankingprofiler.profiler.profile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AccountProfile {
  private long customer_number;
  private String account_number;
  private String create_dt;
  private long balance;
  private List<Transaction> deposits = new ArrayList<>();
  private List<Transaction> withdrawals = new ArrayList<>();
  private List<Transaction> transfers = new ArrayList<>();

  public AccountProfile(
      long customer_number,
      String account_number,
      String create_dt,
      long balance) {
    this.customer_number = customer_number;
    this.account_number = account_number;
    this.create_dt = create_dt;
    this.balance = balance;
  }

  public long getCustomer_number() {
    return customer_number;
  }

  public String getAccount_number() {
    return account_number;
  }

  public long getBalance() {
    return balance;
  }

  public void addDeposit(Transaction deposit) {
    this.balance += deposit.getAmount();
    this.deposits.add(deposit);
  }

  public void addWithdrawal(Transaction withdrawal) {
    this.balance -= withdrawal.getAmount();
    this.withdrawals.add(withdrawal);
  }

  public void addTransfer(Transaction transfer) {
    this.balance -= transfer.getAmount();
    this.transfers.add(transfer);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AccountProfile that = (AccountProfile) o;
    return customer_number == that.customer_number &&
        balance == that.balance &&
        Objects.equals(account_number, that.account_number) &&
        Objects.equals(create_dt, that.create_dt) &&
        Objects.equals(deposits, that.deposits) &&
        Objects.equals(withdrawals, that.withdrawals) &&
        Objects.equals(transfers, that.transfers);
  }

  @Override
  public int hashCode() {

    return Objects
        .hash(customer_number, account_number, create_dt, balance, deposits, withdrawals,
            transfers);
  }
}
