package project.doribugi.bankingprofiler.profiler.service;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

public class LogCollectingService implements Service {
  private final static String KAFKA_PROPERTY_PATH = "kafka.properties";

  private KafkaConsumer<String, String> consumer;
  private Thread logCollectingThread;
  private boolean onLogProcessing = false;
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

  private void collectLog() {
    onLogProcessing = true;
    while (onLogProcessing) {
      final ConsumerRecords<String, String> records = consumer.poll(1500);
      records.forEach(record -> {
        System.out.println("Collect log - " + record.value());
        logParsingService.put(record.topic(), record.value());
      });
    }
  }

  public void stop() throws InterruptedException {
    onLogProcessing = false;
    if (logCollectingThread != null) {
      consumer.wakeup();
      logCollectingThread.join();
      logCollectingThread = null;
    }
    consumer.close();
  }
}
