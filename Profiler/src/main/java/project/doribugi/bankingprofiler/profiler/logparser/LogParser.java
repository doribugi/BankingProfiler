package project.doribugi.bankingprofiler.profiler.logparser;

public interface LogParser<T> {

  /**
   * 금융거래정보 구분을 위한 문자열을 반환.
   * @return 금융거래정보 타입 구분자.
   */
  String getLogType();

  /**
   * 로그 메시지 문자열을 파싱하여 금융거래정보 객체를 반환한다.
   * @param logMessage 금융거래정보 로그 메시지
   * @return Parsing 된 금융거래정보 객체
   * @throws IllegalLogFormatException 로그 메시지가 정의된 형식에 맞지 않은 경우 예외 발생.
   */
  T parse(String logMessage) throws IllegalLogFormatException;
}
