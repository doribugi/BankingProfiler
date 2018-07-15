package project.doribugi.bankingprofiler.producer.service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 로그를 파일에 출력하는 서비스 클래스.
 */
public class LogWritingService implements Service {
  private BufferedWriter writer;
  private String filepath;

  public LogWritingService(String filepath) {
    this.filepath = filepath;
  }

  /**
   * 서비스 시작.
   */
  @Override
  public void start() {
    try {
      writer = new BufferedWriter(new FileWriter(filepath));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * 서비스 중지.
   */
  @Override
  public void stop() {
    try {
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * 금융거래정보 로그를 파일에 출력한다.
   * @param log 금융거래정보 로그
   */
  public void write(String log) {
    try {
      writer.write(log);
      writer.newLine();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
