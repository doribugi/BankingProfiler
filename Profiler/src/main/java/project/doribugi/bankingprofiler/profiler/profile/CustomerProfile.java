package project.doribugi.bankingprofiler.profiler.profile;

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
}
