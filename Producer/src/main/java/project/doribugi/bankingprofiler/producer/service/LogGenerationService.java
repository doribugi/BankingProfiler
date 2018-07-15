package project.doribugi.bankingprofiler.producer.service;

import project.doribugi.bankingprofiler.producer.loggenerator.LogGenerator;

/**
 * 로그 생성 서비스 클래스.
 * 일정 시간 간격으로 로그를 생성하여 TransferService 와 LogWritingService 로 전달한다.
 */
public class LogGenerationService implements Service {
  private Thread logGeneratingThread;
  private boolean onLogGeneration = false;
  private final TransferService transferService;
  private final LogWritingService logWritingService;
  private final int logGenerationIntervalMilliSec;
  private int logGenerationCount;
  private final LogGenerator logGenerator;

  public LogGenerationService(
      TransferService transferService,
      LogWritingService logWritingService,
      int logGenerationIntervalMilliSec,
      int logGenerationCount,
      LogGenerator logGenerator) {
    this.transferService = transferService;
    this.logWritingService = logWritingService;
    this.logGenerationIntervalMilliSec = logGenerationIntervalMilliSec;
    this.logGenerationCount = logGenerationCount;
    this.logGenerator = logGenerator;
  }

  /**
   * 서비스 시작.
   */
  @Override
  public void start() {
    logGeneratingThread = new Thread(this::generate, "LogGeneratingThread");
    logGeneratingThread.setDaemon(true);
    logGeneratingThread.start();
  }

  /**
   * 서비스 중지.
   */
  @Override
  public void stop() {
    try {
      onLogGeneration = false;
      if (logGeneratingThread != null) {
        logGeneratingThread.join();
        logGeneratingThread = null;
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public boolean isOnLogGeneration() {
    return onLogGeneration;
  }

  private void generate() {
    try {
      int logCount = 0;
      onLogGeneration = true;
      while (onLogGeneration && logCount < logGenerationCount) {
        String log = logGenerator.generate();
        transferService.send(log);
        logWritingService.write(log);
        ++logCount;
        String logMessage = String.format("Generated log #%d - %s", logCount, log);
        System.out.println(logMessage);
        Thread.sleep(logGenerationIntervalMilliSec);
      }
      onLogGeneration = false;
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
