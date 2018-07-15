package project.doribugi.bankingprofiler.profiler.logparser;

public interface LogParser<T> {

  String getLogType();

  T parse(String logMessage) throws IllegalLogFormatException;
}
