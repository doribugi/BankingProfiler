package project.doribugi.bankingprofiler.profiler.profile;

import java.util.Objects;

public class CustomerProfile {
  private long customer_number;
  private String name;
  private String join_dt;
  private long largest_deposit_acmount;
  private long largest_withdrawal_acmount;
  private long largest_transfer_acmount;

  public CustomerProfile(
      long customer_number,
      String name,
      String join_dt,
      long largest_deposit_acmount,
      long largest_withdrawal_acmount,
      long largest_transfer_acmount) {
    this.customer_number = customer_number;
    this.name = name;
    this.join_dt = join_dt;
    this.largest_deposit_acmount = largest_deposit_acmount;
    this.largest_withdrawal_acmount = largest_withdrawal_acmount;
    this.largest_transfer_acmount = largest_transfer_acmount;
  }

  public long getCustomer_number() {
    return customer_number;
  }

  public long getLargest_deposit_acmount() {
    return largest_deposit_acmount;
  }

  public void setLargest_deposit_acmount(long largest_deposit_acmount) {
    this.largest_deposit_acmount = largest_deposit_acmount;
  }

  public long getLargest_withdrawal_acmount() {
    return largest_withdrawal_acmount;
  }

  public void setLargest_withdrawal_acmount(long largest_withdrawal_acmount) {
    this.largest_withdrawal_acmount = largest_withdrawal_acmount;
  }

  public long getLargest_transfer_acmount() {
    return largest_transfer_acmount;
  }

  public void setLargest_transfer_acmount(long largest_transfer_acmount) {
    this.largest_transfer_acmount = largest_transfer_acmount;
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
    return customer_number == that.customer_number &&
        largest_deposit_acmount == that.largest_deposit_acmount &&
        largest_withdrawal_acmount == that.largest_withdrawal_acmount &&
        largest_transfer_acmount == that.largest_transfer_acmount &&
        Objects.equals(name, that.name) &&
        Objects.equals(join_dt, that.join_dt);
  }

  @Override
  public int hashCode() {

    return Objects
        .hash(customer_number, name, join_dt, largest_deposit_acmount, largest_withdrawal_acmount,
            largest_transfer_acmount);
  }
}
