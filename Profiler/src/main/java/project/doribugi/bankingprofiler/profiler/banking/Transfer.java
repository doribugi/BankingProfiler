package project.doribugi.bankingprofiler.profiler.banking;

import java.util.Objects;

public class Transfer implements BankingInfo {

  private long customerNumber;
  private String accountNumber;
  private String receivingBank;
  private String receivingAccountNumber;
  private String receivingAccountOwner;
  private long transferAmount;
  private String transferDt;

  public Transfer(
      long customerNumber,
      String accountNumber,
      String receivingBank,
      String receivingAccountNumber,
      String receivingAccountOwner,
      long transferAmount,
      String transferDt) {
    this.customerNumber = customerNumber;
    this.accountNumber = accountNumber;
    this.receivingBank = receivingBank;
    this.receivingAccountNumber = receivingAccountNumber;
    this.receivingAccountOwner = receivingAccountOwner;
    this.transferAmount = transferAmount;
    this.transferDt = transferDt;
  }

  public long getCustomerNumber() {
    return customerNumber;
  }

  public String getAccountNumber() {
    return accountNumber;
  }

  public String getReceivingBank() {
    return receivingBank;
  }

  public String getReceivingAccountNumber() {
    return receivingAccountNumber;
  }

  public String getReceivingAccountOwner() {
    return receivingAccountOwner;
  }

  public long getTransferAmount() {
    return transferAmount;
  }

  public String getTransferDt() {
    return transferDt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Transfer transfer = (Transfer) o;
    return customerNumber == transfer.customerNumber &&
        transferAmount == transfer.transferAmount &&
        Objects.equals(accountNumber, transfer.accountNumber) &&
        Objects.equals(receivingBank, transfer.receivingBank) &&
        Objects.equals(receivingAccountNumber, transfer.receivingAccountNumber) &&
        Objects.equals(receivingAccountOwner, transfer.receivingAccountOwner) &&
        Objects.equals(transferDt, transfer.transferDt);
  }

  @Override
  public int hashCode() {

    return Objects.hash(customerNumber, accountNumber, receivingBank, receivingAccountNumber,
        receivingAccountOwner, transferAmount, transferDt);
  }
}
