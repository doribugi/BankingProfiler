package project.doribugi.bankingprofiler.producer.service;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

public class TransferService implements Service {
  private final static String KAFKA_PROPERTY_PATH = "kafka.properties";
  private Producer<String, String> producer;
  private final Properties kafkaProperties = new Properties();

  public TransferService() throws IOException {
    final FileReader reader = new FileReader(KAFKA_PROPERTY_PATH);
    kafkaProperties.load(reader);
    kafkaProperties.put("key.serializer", StringSerializer.class.getCanonicalName());
    kafkaProperties.put("value.serializer", StringSerializer.class.getCanonicalName());
  }

  @Override
  public void start() {
    producer = new KafkaProducer<>(kafkaProperties);
  }

  @Override
  public void stop() {
    producer.close();
  }

  public void send(String log) {
    String topic = log.split(",")[0].trim();
    producer.send(new ProducerRecord<>(topic, log));
  }
}
