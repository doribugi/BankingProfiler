package project.doribugi.bankingprofiler.profiler.repository;

import java.util.HashMap;
import java.util.Map;

/**
 * 간단하게 Repository 를 메모리에 저장하는 형태로 구현한 클래스.
 * @param <T> 저장 객체 타입
 */
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
