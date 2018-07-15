package project.doribugi.bankingprofiler.producer.loggenerator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * 파일에서 금융거래정보 로그를 읽어 전달하는 LogGenerator.
 */
public class FileLogGenerator implements LogGenerator {
  private final BufferedReader reader;

  public FileLogGenerator(String filePath) throws FileNotFoundException {
    reader = new BufferedReader(new FileReader(filePath));
  }

  /**
   * 금융거래정보 로그를 한 개 생성하여 반환.
   * @return 금융거래정보 로그
   */
  @Override
  public String generate() {
    try {
      return reader.readLine();
    } catch (IOException e) {
      throw new LogGenerationException(e);
    }
  }
}
