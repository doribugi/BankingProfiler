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

/**
 * 로그의 Parsing 을 수행하는 서비스 클래스.
 */
public class LogParsingService implements Service {

  private final List<LogParser<? extends BankingInfo>> logParserList = new ArrayList<>();
  private final RepositoryService repositoryService;
  private final BlockingQueue<String> queue = new LinkedBlockingQueue<>();
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

  /**
   * 서비스 시작.
   */
  @Override
  public void start() {
    logParsingThread = new Thread(this::parse, "LogParsingThread");
    logParsingThread.setDaemon(true);
    logParsingThread.start();
  }

  /**
   * 서비스 중지.
   */
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

  public void parse(String logMessage) {
    try {
      queue.put(logMessage);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private void parse() {
    onLogParse = true;
    while (onLogParse) {
      String logMessage = queue.poll();
      if (logMessage != null) {
        try {
          LogParser<? extends BankingInfo> logParser = logParserList
              .stream()
              .filter(parser -> logMessage.split(",")[0].trim().equals(parser.getLogType()))
              .findFirst()
              .orElseThrow(UnknownException::new);
          BankingInfo bankingInfo = logParser.parse(logMessage);
          repositoryService.update(bankingInfo);
        } catch (IllegalLogFormatException e) {
          e.printStackTrace();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
  }
}
