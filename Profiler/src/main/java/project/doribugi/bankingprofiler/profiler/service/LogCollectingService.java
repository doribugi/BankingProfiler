package project.doribugi.bankingprofiler.profiler.service;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

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

  @Override
  public void start() {
    consumer = new KafkaConsumer<>(kafkaProperties);
    List<String> topicList = logParsingService.getTopicList();
    consumer.subscribe(topicList);

    logCollectingThread = new Thread(this::collectLog, "LogCollectingThread");
    logCollectingThread.setDaemon(true);
    logCollectingThread.start();
  }

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
        logParsingService.parse(record.topic(), record.value());
      });
    }
  }
}
