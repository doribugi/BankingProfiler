package project.doribugi.bankingprofiler.profiler.repository;

/**
 * 각 객체 별 Repository 접근을 위한 인터페이스.
 * @param <T> 저장 객체 타입
 */
public interface Repository<T> {

  /**
   * id 에 해당하는 객체를 Repository 에서 읽어 반환한다.
   * @param id 레코드 id
   * @return 입력 id 로 저장된 객체
   */
  T read(String id);

  /**
   * 지정된 id 로 입력된 객체를 저장한다.
   * @param id 저장할 레코드의 id
   * @param t 저장할 객체
   */
  void save(String id, T t);
}
