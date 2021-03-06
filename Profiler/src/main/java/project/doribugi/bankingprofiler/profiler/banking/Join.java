package project.doribugi.bankingprofiler.profiler.banking;

import java.util.Objects;

/**
 * 고객 가입에 대한 금융 거래 정보 Entity 클래스.
 */
public class Join implements BankingInfo {

  private long customerNumber;
  private String customerName;
  private String joinDt;

  public Join(long customerNumber, String customerName, String joinDt) {
    this.customerNumber = customerNumber;
    this.customerName = customerName;
    this.joinDt = joinDt;
  }

  public long getCustomerNumber() {
    return customerNumber;
  }

  public String getCustomerName() {
    return customerName;
  }

  public String getJoinDt() {
    return joinDt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Join join = (Join) o;
    return customerNumber == join.customerNumber &&
        Objects.equals(customerName, join.customerName) &&
        Objects.equals(joinDt, join.joinDt);
  }

  @Override
  public int hashCode() {

    return Objects.hash(customerNumber, customerName, joinDt);
  }
}
