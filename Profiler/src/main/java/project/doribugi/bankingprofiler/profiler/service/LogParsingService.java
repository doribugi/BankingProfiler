package project.doribugi.bankingprofiler.profiler.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;
import kafka.common.UnknownException;
import project.doribugi.bankingprofiler.profiler.banking.BankingInfo;
import project.doribugi.bankingprofiler.profiler.logparser.AccountCreationLogParser;
import project.doribugi.bankingprofiler.profiler.logparser.DepositLogParser;
import project.doribugi.bankingprofiler.profiler.logparser.IllegalLogFormatException;
import project.doribugi.bankingprofiler.profiler.logparser.JoinLogParser;
import project.doribugi.bankingprofiler.profiler.logparser.LogParser;
import project.doribugi.bankingprofiler.profiler.logparser.TransferLogParser;
import project.doribugi.bankingprofiler.profiler.logparser.WithdrawalLogParser;

public class LogParsingService implements Service {

  private final List<LogParser<? extends BankingInfo>> logParserList = new ArrayList<>();
  private final RepositoryService repositoryService;
  private final BlockingQueue<LogInfo> queue = new LinkedBlockingQueue<>();
  private Thread logParsingThread;
  private boolean onLogParse = false;

  public LogParsingService(RepositoryService repositoryService) {

    this.repositoryService = repositoryService;

    logParserList.add(new JoinLogParser());
    logParserList.add(new AccountCreationLogParser());
    logParserList.add(new DepositLogParser());
    logParserList.add(new TransferLogParser());
    logParserList.add(new WithdrawalLogParser());
  }

  @Override
  public void start() {
    logParsingThread = new Thread(this::parse, "LogParsingThread");
    logParsingThread.setDaemon(true);
    logParsingThread.start();
  }

  @Override
  public void stop() {
    while (!queue.isEmpty()) {
      try {
        Thread.sleep(500);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    try {
      onLogParse = false;
      if (logParsingThread != null) {
        logParsingThread.join();
        logParsingThread = null;
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public List<String> getTopicList() {
    return logParserList
        .stream()
        .map(LogParser::topic)
        .collect(Collectors.toList());
  }

  public void parse(String topic, String logMessage) {
    try {
      queue.put(new LogInfo(topic, logMessage));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private void parse() {
    onLogParse = true;
    while (onLogParse) {
      LogInfo logInfo = queue.poll();
      if (logInfo != null) {
        try {
          BankingInfo bankingInfo = parse(logInfo);
          repositoryService.update(bankingInfo);
        } catch (UnknownException e) {
          System.out.println("Invalid log topic:" + logInfo.topic);
        } catch (IllegalLogFormatException e) {
          e.printStackTrace();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
  }

  private BankingInfo parse(LogInfo logInfo) throws IllegalLogFormatException {
    LogParser<? extends BankingInfo> logParser = logParserList
        .stream()
        .filter(parser -> logInfo.topic.equals(parser.topic()))
        .findFirst()
        .orElseThrow(UnknownException::new);

    return logParser.parse(logInfo.logMessage);
  }

  private class LogInfo {
    private final String topic;
    private final String logMessage;

    LogInfo(String topic, String logMessage) {
      this.topic = topic;
      this.logMessage = logMessage;
    }
  }
}
