package project.doribugi.bankingprofiler.profiler.profile;

import java.util.Objects;

/**
 * Account Profile 의 금융 거래 공통 정보 Entity 클래스.
 */
public class Transaction {
  private long amount;
  private String datetime;

  public Transaction(long amount, String datetime) {
    this.amount = amount;
    this.datetime = datetime;
  }

  public long getAmount() {
    return amount;
  }

  public String getDatetime() {
    return datetime;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Transaction that = (Transaction) o;
    return amount == that.amount &&
        Objects.equals(datetime, that.datetime);
  }

  @Override
  public int hashCode() {

    return Objects.hash(amount, datetime);
  }
}
