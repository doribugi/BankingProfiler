package project.doribugi.bankingprofiler.profiler.banking;

import java.util.Objects;

public class AccountCreation implements BankingInfo {

  private long customerNumber;
  private String accountNumber;
  private String createDt;

  public AccountCreation(long customerNumber, String accountNumber, String createDt) {
    this.customerNumber = customerNumber;
    this.accountNumber = accountNumber;
    this.createDt = createDt;
  }

  public long getCustomerNumber() {
    return customerNumber;
  }

  public String getAccountNumber() {
    return accountNumber;
  }

  public String getCreateDt() {
    return createDt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AccountCreation that = (AccountCreation) o;
    return customerNumber == that.customerNumber &&
        Objects.equals(accountNumber, that.accountNumber) &&
        Objects.equals(createDt, that.createDt);
  }

  @Override
  public int hashCode() {

    return Objects.hash(customerNumber, accountNumber, createDt);
  }
}
