package project.doribugi.bankingprofiler.profiler.service;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

/**
 * KAFKA 를 통해 로그를 수집하는 서비스 클래스.
 */
public class LogCollectingService implements Service {
  private final static String KAFKA_PROPERTY_PATH = "kafka.properties";

  private KafkaConsumer<String, String> consumer;
  private Thread logCollectingThread;
  private boolean onLogCollection = false;
  private final Properties kafkaProperties = new Properties();
  private final LogParsingService logParsingService;

  public LogCollectingService(LogParsingService logParsingService) throws IOException {
    final FileReader reader = new FileReader(KAFKA_PROPERTY_PATH);
    kafkaProperties.load(reader);
    kafkaProperties.put("key.deserializer", StringDeserializer.class.getCanonicalName());
    kafkaProperties.put("value.deserializer", StringDeserializer.class.getCanonicalName());

    this.logParsingService = logParsingService;
  }

  /**
   * 서비스 시작.
   */
  @Override
  public void start() {
    consumer = new KafkaConsumer<>(kafkaProperties);
    consumer.subscribe(Collections.singletonList("banking_profile"));

    logCollectingThread = new Thread(this::collectLog, "LogCollectingThread");
    logCollectingThread.setDaemon(true);
    logCollectingThread.start();
  }

  /**
   * 서비스 중지.
   */
  @Override
  public void stop() {
    try {
      onLogCollection = false;
      if (logCollectingThread != null) {
        consumer.wakeup();
        logCollectingThread.join();
        logCollectingThread = null;
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    consumer.close();
  }

  private void collectLog() {
    onLogCollection = true;
    while (onLogCollection) {
      final ConsumerRecords<String, String> records = consumer.poll(1500);
      records.forEach(record -> {
        System.out.println("Collect log - " + record.value());
        if (record.topic().equals("banking_profile")) {
          logParsingService.parse(record.value());
        } else {
          System.out.println("Invalid log topic: " + record.topic());
        }
      });
    }
  }
}
