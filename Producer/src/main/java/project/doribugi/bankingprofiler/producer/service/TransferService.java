package project.doribugi.bankingprofiler.producer.service;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

/**
 * 로그를 KAFKA를 통해 전달하는 서비스 클래스.
 */
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

  /**
   * 서비스 시작.
   */
  @Override
  public void start() {
    producer = new KafkaProducer<>(kafkaProperties);
  }

  /**
   * 서비스 중지.
   */
  @Override
  public void stop() {
    producer.close();
  }

  /**
   * 금융거래정보 로그를 KAFKA로 전송한다.
   * @param log 금융거래정보 로그
   */
  public void send(String log) {
    producer.send(new ProducerRecord<>("banking_profile", log));
  }
}
