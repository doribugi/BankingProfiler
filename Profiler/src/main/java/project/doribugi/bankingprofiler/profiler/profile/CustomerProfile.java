package project.doribugi.bankingprofiler.profiler.profile;

import com.google.gson.annotations.SerializedName;
import java.util.Objects;

/**
 * REST API 로 제공하기 위한 Customer Profile Entity 클래스.
 */
public class CustomerProfile {
  @SerializedName("customer_number")
  private long customerNumber;
  private String name;
  @SerializedName("join_dt")
  private String joinDt;
  @SerializedName("largest_deposit_acmount")
  private long largestDepositAmount;
  @SerializedName("largest_withdrawal_acmount")
  private long largestWithdrawalAmount;
  @SerializedName("largest_transfer_acmount")
  private long largestTransferAmount;

  public CustomerProfile(
      long customerNumber,
      String name,
      String joinDt,
      long largestDepositAmount,
      long largestWithdrawalAmount,
      long largestTransferAmount) {
    this.customerNumber = customerNumber;
    this.name = name;
    this.joinDt = joinDt;
    this.largestDepositAmount = largestDepositAmount;
    this.largestWithdrawalAmount = largestWithdrawalAmount;
    this.largestTransferAmount = largestTransferAmount;
  }

  public long getCustomerNumber() {
    return customerNumber;
  }

  public String getName() {
    return name;
  }

  public String getJoinDt() {
    return joinDt;
  }

  public long getLargestDepositAmount() {
    return largestDepositAmount;
  }

  public void setLargestDepositAmount(long largestDepositAmount) {
    this.largestDepositAmount = largestDepositAmount;
  }

  public long getLargestWithdrawalAmount() {
    return largestWithdrawalAmount;
  }

  public void setLargestWithdrawalAmount(long largestWithdrawalAmount) {
    this.largestWithdrawalAmount = largestWithdrawalAmount;
  }

  public long getLargestTransferAmount() {
    return largestTransferAmount;
  }

  public void setLargestTransferAmount(long largestTransferAmount) {
    this.largestTransferAmount = largestTransferAmount;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CustomerProfile that = (CustomerProfile) o;
    return customerNumber == that.customerNumber &&
        largestDepositAmount == that.largestDepositAmount &&
        largestWithdrawalAmount == that.largestWithdrawalAmount &&
        largestTransferAmount == that.largestTransferAmount &&
        Objects.equals(name, that.name) &&
        Objects.equals(joinDt, that.joinDt);
  }

  @Override
  public int hashCode() {

    return Objects
        .hash(customerNumber, name, joinDt, largestDepositAmount, largestWithdrawalAmount,
            largestTransferAmount);
  }
}
