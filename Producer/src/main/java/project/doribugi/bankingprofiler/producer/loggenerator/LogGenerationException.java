package project.doribugi.bankingprofiler.producer.loggenerator;

/**
 * Log Generator 에서 로그 생성 실패 시 발생하는 예외.
 */
public class LogGenerationException extends RuntimeException {

  public LogGenerationException(Throwable cause) {
    super(cause);
  }
}
