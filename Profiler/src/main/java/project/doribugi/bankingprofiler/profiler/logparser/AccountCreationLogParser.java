package project.doribugi.bankingprofiler.profiler.logparser;

import project.doribugi.bankingprofiler.profiler.banking.AccountCreation;

public class AccountCreationLogParser implements LogParser<AccountCreation> {

  /**
   * 계좌 생성 금융거래정보 구분을 위한 문자열을 반환.
   * @return 계좌 생성 금융거래정보 타입 구분자 (account_create).
   */
  @Override
  public String getLogType() {
    return "account_create";
  }

  /**
   * 로그 메시지 문자열을 파싱하여 계좌 생성 객체를 반환한다.
   * @param logMessage 계좌 생성 금융거래정보 로그 메시지
   * @return Parsing 된 계좌 생성 객체
   * @throws IllegalLogFormatException 로그 메시지가 정의된 형식에 맞지 않은 경우 예외 발생.
   */
  @Override
  public AccountCreation parse(String logMessage) throws IllegalLogFormatException {
    try {
      String[] contents = logMessage.split(",");
      long customerNumber = Long.parseLong(contents[1].trim());
      String accountNumber = contents[2].trim();
      String createDt = contents[3].trim();
      return new AccountCreation(customerNumber, accountNumber, createDt);
    } catch (Exception e) {
      String message = String.format("Illegal Format - %s", logMessage);
      throw new IllegalLogFormatException(message, e);
    }
  }
}
