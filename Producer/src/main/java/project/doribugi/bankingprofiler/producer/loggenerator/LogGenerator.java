package project.doribugi.bankingprofiler.producer.loggenerator;

/**
 * 금융거래정보 로그 생성 인터페이스.
 */
public interface LogGenerator {

  /**
   * 금융거래정보 로그를 한 개 생성하여 반환.
   * @return 금융거래정보 로그
   */
  String generate();
}
