package project.doribugi.bankingprofiler.producer.loggenerator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileLogGenerator implements LogGenerator {
  private final BufferedReader reader;

  public FileLogGenerator(String filePath) throws FileNotFoundException {
    reader = new BufferedReader(new FileReader(filePath));
  }

  @Override
  public String generate() {
    try {
      return reader.readLine();
    } catch (IOException e) {
      throw new LogGenerationException(e);
    }
  }
}
