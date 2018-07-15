package project.doribugi.bankingprofiler.profiler.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Get;
import java.lang.reflect.Modifier;

public class ElasticRepository<T> implements Repository<T> {

  private final JestClient client;

  public ElasticRepository(String ipAddress, int port) {
    GsonBuilder gsonBuilder = new GsonBuilder();
    Gson gson = gsonBuilder.excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.STATIC).create();
    String elasticIpAddress = String.format("http://%s:%d", ipAddress, port);
    HttpClientConfig.Builder builder = new HttpClientConfig.Builder(elasticIpAddress)
        .gson(gson)
        .connTimeout(1000 * 30)
        .readTimeout(1000 * 60 * 5)
        .multiThreaded(true);

    JestClientFactory factory = new JestClientFactory();
    factory.setHttpClientConfig(builder.build());
    this.client = factory.getObject();
  }

  @Override
  public T read(String id) {
    return null;
  }

  @Override
  public void save(String id, T t) {
  }
}
