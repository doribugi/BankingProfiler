package project.doribugi.bankingprofiler.profiler.logparser;

public interface LogParser<T> {

  String topic();

  T parse(String logMessage);
}
