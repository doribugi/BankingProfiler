package project.doribugi.bankingprofiler.profiler.logparser;

import project.doribugi.bankingprofiler.profiler.banking.Join;

public class JoinLogParser implements LogParser<Join> {

  /**
   * 고객 가입 금융거래정보 구분을 위한 문자열을 반환.
   * @return 고객 가입 금융거래정보 타입 구분자 (join).
   */
  @Override
  public String getLogType() {
    return "join";
  }

  /**
   * 로그 메시지 문자열을 파싱하여 고객 가입 객체를 반환한다.
   * @param logMessage 고객 가입 금융거래정보 로그 메시지
   * @return Parsing 된 고객 가입 객체
   * @throws IllegalLogFormatException 로그 메시지가 정의된 형식에 맞지 않은 경우 예외 발생.
   */
  @Override
  public Join parse(String logMessage) throws IllegalLogFormatException {
    try {
      String[] contents = logMessage.split(",");
      long accountNumber = Long.parseLong(contents[1].trim());
      String customerName = contents[2].trim();
      String joinDt = contents[3].trim();
      return new Join(accountNumber, customerName, joinDt);
    } catch (Exception e) {
      String message = String.format("Illegal Format - %s", logMessage);
      throw new IllegalLogFormatException(message, e);
    }
  }
}
