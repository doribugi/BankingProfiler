package project.doribugi.bankingprofiler.profiler.repository;

public interface Repository<T> {
  T read(String id);

  void update(String id, T t);
}
