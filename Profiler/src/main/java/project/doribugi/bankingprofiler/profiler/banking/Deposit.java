package project.doribugi.bankingprofiler.profiler.banking;

import java.util.Objects;

public class Deposit implements BankingInfo {

  private long customerNumber;
  private String accountNumber;
  private long depositAmount;
  private String depositDt;

  public Deposit(long customerNumber, String accountNumber, long depositAmount, String depositDt) {
    this.customerNumber = customerNumber;
    this.accountNumber = accountNumber;
    this.depositAmount = depositAmount;
    this.depositDt = depositDt;
  }

  public long getCustomerNumber() {
    return customerNumber;
  }

  public String getAccountNumber() {
    return accountNumber;
  }

  public long getDepositAmount() {
    return depositAmount;
  }

  public String getDepositDt() {
    return depositDt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Deposit deposit = (Deposit) o;
    return customerNumber == deposit.customerNumber &&
        depositAmount == deposit.depositAmount &&
        Objects.equals(accountNumber, deposit.accountNumber) &&
        Objects.equals(depositDt, deposit.depositDt);
  }

  @Override
  public int hashCode() {

    return Objects.hash(customerNumber, accountNumber, depositAmount, depositDt);
  }
}
