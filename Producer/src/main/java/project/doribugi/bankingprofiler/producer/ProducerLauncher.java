package project.doribugi.bankingprofiler.producer;

import java.io.FileReader;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.naming.ConfigurationException;
import project.doribugi.bankingprofiler.producer.loggenerator.FileLogGenerator;
import project.doribugi.bankingprofiler.producer.loggenerator.LogGenerator;
import project.doribugi.bankingprofiler.producer.loggenerator.RandomLogGenerator;
import project.doribugi.bankingprofiler.producer.service.LogGenerationService;
import project.doribugi.bankingprofiler.producer.service.LogWritingService;
import project.doribugi.bankingprofiler.producer.service.Service;
import project.doribugi.bankingprofiler.producer.service.TransferService;

public class ProducerLauncher {
  private static final String PROPERTIES_FILE_PATH = "producer.properties";

  public static void main(String[] args) throws IOException, ConfigurationException {
    Properties properties = new Properties();
    FileReader reader = new FileReader(PROPERTIES_FILE_PATH);
    properties.load(reader);

    TransferService transferService = new TransferService();
    LogWritingService logWritingService
        = new LogWritingService(properties.getProperty("log.output.filepath"));

    String logGenerationMethod = properties.getProperty("log.generation.method").toLowerCase();
    final LogGenerator logGenerator;
    switch (logGenerationMethod) {
      case "file":
        String inputPath = properties.getProperty("log.generation.file.input");
        logGenerator = new FileLogGenerator(inputPath);
        break;
      case "random":
        String startTimeStr = properties.getProperty("log.generation.random.time.start");
        DateTimeFormatter dateTimeFormatter
            = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault());
        ZonedDateTime logStartTime = ZonedDateTime.parse(startTimeStr, dateTimeFormatter);
        String intervalTimeMsStr = properties.getProperty("log.generation.random.time.interval.ms");
        int intervalTimeMs = Integer.parseInt(intervalTimeMsStr);
        String customerFilePath = properties.getProperty("log.generation.random.customer");
        String receivingFilePath = properties.getProperty("log.generation.random.receiving");
        logGenerator = new RandomLogGenerator(
            logStartTime,
            intervalTimeMs,
            customerFilePath,
            receivingFilePath);
        break;
      default:
        throw new ConfigurationException("Invalid log.generation.method in configuration file"
            + " (input the string among file, random)");
    }
    LogGenerationService logGenerationService = new LogGenerationService(
        transferService,
        logWritingService,
        Integer.parseInt(properties.getProperty("log.generation.interval.ms")),
        Integer.parseInt(properties.getProperty("log.generation.count")),
        logGenerator);

    System.out.println("Started to produce logs");

    List<Service> serviceList = new ArrayList<>();
    serviceList.add(transferService);
    serviceList.add(logWritingService);
    serviceList.add(logGenerationService);
    serviceList.forEach(Service::start);

    while (logGenerationService.isOnLogGeneration()) {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    serviceList.forEach(Service::stop);
    System.out.println("Completed to produce logs");
  }
}
