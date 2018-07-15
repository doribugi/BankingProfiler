package project.doribugi.bankingprofiler.profiler.logparser;

public class IllegalLogFormatException extends RuntimeException {

  /**
   * 금융거래정보 로그 파싱 중 로그 메시지가 정의된 형식에 맞지 않은 경우 예외 발생
   * @param message 예외 메세지
   * @param cause 예외 원인
   */
  public IllegalLogFormatException(String message, Throwable cause) {
    super(message, cause);
  }
}
