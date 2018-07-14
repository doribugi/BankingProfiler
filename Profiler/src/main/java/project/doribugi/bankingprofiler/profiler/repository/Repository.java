package project.doribugi.bankingprofiler.profiler.repository;

public interface Repository<T> {
  T read(String id);

  void create(String id, T t);

  void update(String id, T t);
}
