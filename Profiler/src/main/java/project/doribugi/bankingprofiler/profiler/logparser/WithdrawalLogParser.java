package project.doribugi.bankingprofiler.profiler.logparser;

import project.doribugi.bankingprofiler.profiler.banking.Withdrawal;

public class WithdrawalLogParser implements LogParser<Withdrawal> {

  /**
   * 출금 금융거래정보 구분을 위한 문자열을 반환.
   * @return 출금 금융거래정보 타입 구분자 (withdrawal).
   */
  @Override
  public String getLogType() {
    return "withdrawal";
  }

  /**
   * 로그 메시지 문자열을 파싱하여 출금 객체를 반환한다.
   * @param logMessage 출금 금융거래정보 로그 메시지
   * @return Parsing 된 출금 객체
   * @throws IllegalLogFormatException 로그 메시지가 정의된 형식에 맞지 않은 경우 예외 발생.
   */
  @Override
  public Withdrawal parse(String logMessage) throws IllegalLogFormatException {
    try {
      String[] contents = logMessage.split(",");
      long customerNumber = Long.parseLong(contents[1].trim());
      String accountNumber = contents[2].trim();
      long withdrawalAmount = Long.parseLong(contents[3].trim());
      String withdrawalDt = contents[4].trim();
      return new Withdrawal(customerNumber, accountNumber, withdrawalAmount, withdrawalDt);
    } catch (Exception e) {
      String message = String.format("Illegal Format - %s", logMessage);
      throw new IllegalLogFormatException(message, e);
    }
  }
}
