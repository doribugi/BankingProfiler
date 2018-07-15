package project.doribugi.bankingprofiler.producer.service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class LogWritingService implements Service {
  private BufferedWriter writer;
  private String filepath;

  public LogWritingService(String filepath) {
    this.filepath = filepath;
  }

  @Override
  public void start() {
    try {
      writer = new BufferedWriter(new FileWriter(filepath));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void stop() {
    try {
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void write(String log) {
    try {
      writer.write(log);
      writer.newLine();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
