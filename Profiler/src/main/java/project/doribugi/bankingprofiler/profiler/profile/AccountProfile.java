package project.doribugi.bankingprofiler.profiler.profile;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AccountProfile {
  @SerializedName("customer_number")
  private long customerNumber;
  @SerializedName("account_number")
  private String accountNumber;
  @SerializedName("create_dt")
  private String createDt;
  private long balance;
  private List<Transaction> deposits = new ArrayList<>();
  private List<Transaction> withdrawals = new ArrayList<>();
  private List<Transaction> transfers = new ArrayList<>();

  public AccountProfile(
      long customerNumber,
      String accountNumber,
      String createDt,
      long balance) {
    this.customerNumber = customerNumber;
    this.accountNumber = accountNumber;
    this.createDt = createDt;
    this.balance = balance;
  }

  public long getCustomerNumber() {
    return customerNumber;
  }

  public String getAccountNumber() {
    return accountNumber;
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
    return customerNumber == that.customerNumber &&
        balance == that.balance &&
        Objects.equals(accountNumber, that.accountNumber) &&
        Objects.equals(createDt, that.createDt) &&
        Objects.equals(deposits, that.deposits) &&
        Objects.equals(withdrawals, that.withdrawals) &&
        Objects.equals(transfers, that.transfers);
  }

  @Override
  public int hashCode() {

    return Objects
        .hash(customerNumber, accountNumber, createDt, balance, deposits, withdrawals,
            transfers);
  }
}
