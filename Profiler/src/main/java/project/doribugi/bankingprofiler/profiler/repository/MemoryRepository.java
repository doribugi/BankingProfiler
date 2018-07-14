package project.doribugi.bankingprofiler.profiler.repository;

import java.util.HashMap;
import java.util.Map;

public class MemoryRepository<T> implements Repository<T> {

  private Map<String, T> dataMap = new HashMap<>();

  @Override
  public T read(String id) {
    return dataMap.getOrDefault(id, null);
  }

  @Override
  public void create(String id, T t) {
    if (dataMap.containsKey(id)) {
      throw new IllegalArgumentException();
    }
    dataMap.put(id, t);
  }

  @Override
  public void update(String id, T t) {
    if (!dataMap.containsKey(id)) {
      throw new IllegalArgumentException();
    }
    dataMap.put(id, t);
  }
}
