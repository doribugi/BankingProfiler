package project.doribugi.bankingprofiler.profiler.banking;

import java.util.Objects;

public class Withdrawal implements BankingInfo {

  private long customerNumber;
  private String accountNumber;
  private long withdrawalAmount;
  private String withdrawalDt;

  public Withdrawal(
      long customerNumber,
      String accountNumber,
      long withdrawalAmount,
      String withdrawalDt) {
    this.customerNumber = customerNumber;
    this.accountNumber = accountNumber;
    this.withdrawalAmount = withdrawalAmount;
    this.withdrawalDt = withdrawalDt;
  }

  public long getCustomerNumber() {
    return customerNumber;
  }

  public String getAccountNumber() {
    return accountNumber;
  }

  public long getWithdrawalAmount() {
    return withdrawalAmount;
  }

  public String getWithdrawalDt() {
    return withdrawalDt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Withdrawal that = (Withdrawal) o;
    return customerNumber == that.customerNumber &&
        withdrawalAmount == that.withdrawalAmount &&
        Objects.equals(accountNumber, that.accountNumber) &&
        Objects.equals(withdrawalDt, that.withdrawalDt);
  }

  @Override
  public int hashCode() {

    return Objects.hash(customerNumber, accountNumber, withdrawalAmount, withdrawalDt);
  }
}
