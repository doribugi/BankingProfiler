package project.doribugi.bankingprofiler.producer.service;

/**
 * Service 인터페이스.
 * Service 는 주로 프로그램에 상주하여 기능을 수행하는 클래스이다.
 */
public interface Service {

  /**
   * 서비스 시작.
   */
  void start();

  /**
   * 서비스 중지.
   */
  void stop();
}
