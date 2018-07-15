package project.doribugi.bankingprofiler.producer.loggenerator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class RandomLogGenerator implements LogGenerator {

  private ZonedDateTime logTime;
  private int intervalTimeMs;
  private final List<String> customerList;
  private final List<String> receivingList;
  private final Random random = new Random(System.currentTimeMillis());
  private final Map<String, Integer> customerMap = new HashMap<>();
  private final Map<String, String> accountMap = new HashMap<>();
  private final Map<String, Integer> balanceMap = new HashMap<>();
  private int lastCustomerNumber = 0;
  private long lastAccountNumber = 3333010000L;
  private final DateTimeFormatter dateTimeFormatter
      = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  public RandomLogGenerator(
      ZonedDateTime logTime,
      int intervalTimeMs,
      String customerFilePath,
      String receivingFilePath) throws IOException {

    this.logTime = logTime;
    this.intervalTimeMs = intervalTimeMs;

    this.customerList = Files.readAllLines(new File(customerFilePath).toPath());
    this.receivingList = Files.readAllLines(new File(receivingFilePath).toPath());
  }

  @Override
  public String generate() {
    String customerName = customerList.get(random.nextInt(customerList.size()));
    final String log;
    if (!customerMap.containsKey(customerName)) {
      log = generateJoinLog(customerName);
    } else if (!accountMap.containsKey(customerName)) {
      log = generateAccountCreateLog(customerName);
    } else if (accountMap.containsKey(customerName)
        && balanceMap.get(accountMap.get(customerName)) == 0) {
      log = generateDepositLog(customerName);
    } else {
      int logSelect = random.nextInt(3);
      if (logSelect == 0) {
        log = generateDepositLog(customerName);
      } else if (logSelect == 1) {
        log = generateWithdrawal(customerName);
      } else {
        log = generateTransfer(customerName);
      }
    }
    logTime = logTime.plusNanos(intervalTimeMs * 1000000);
    return log;
  }

  private String generateJoinLog(String customerName) {
    ++lastCustomerNumber;
    customerMap.put(customerName, lastCustomerNumber);
    return String.format("join, %d, %s, %s",
        lastCustomerNumber,
        customerName,
        logTime.format(dateTimeFormatter));
  }

  private String generateAccountCreateLog(String customerName) {
    ++lastAccountNumber;
    String accountNumber = Long.toString(lastAccountNumber);
    accountMap.put(customerName, accountNumber);
    balanceMap.put(accountNumber, 0);
    int customerNumber = customerMap.get(customerName);
    return String.format("account_create, %d, %s, %s",
        customerNumber,
        accountNumber,
        logTime.format(dateTimeFormatter));
  }

  private String generateDepositLog(String customerName) {
    int customerNumber = customerMap.get(customerName);
    String accountNumber = accountMap.get(customerName);
    int prevBalance = balanceMap.get(accountNumber);
    int deposit = random.nextInt(100000000) + 1;
    balanceMap.put(accountNumber, prevBalance + deposit);
    return String.format("deposit, %d, %s, %d, %s",
        customerNumber,
        accountNumber,
        deposit,
        logTime.format(dateTimeFormatter));
  }

  private String generateWithdrawal(String customerName) {
    int customerNumber = customerMap.get(customerName);
    String accountNumber = accountMap.get(customerName);
    int prevBalance = balanceMap.get(accountNumber);
    int withdrawal = random.nextInt(prevBalance) + 1;
    balanceMap.put(accountNumber, balanceMap.get(accountNumber) - withdrawal);
    return String.format("withdrawal, %d, %s, %d, %s",
        customerNumber,
        accountNumber,
        withdrawal,
        logTime.format(dateTimeFormatter));
  }

  private String generateTransfer(String customerName) {
    int customerNumber = customerMap.get(customerName);
    String accountNumber = accountMap.get(customerName);
    int prevBalance = balanceMap.get(accountNumber);
    String receivingInfo = receivingList.get(random.nextInt(receivingList.size()));
    int transfer = random.nextInt(prevBalance) + 1;
    balanceMap.put(accountNumber, balanceMap.get(accountNumber) - transfer);
    return String.format("transfer, %d, %s, %s, %d, %s",
        customerNumber,
        accountNumber,
        receivingInfo,
        transfer,
        logTime.format(dateTimeFormatter));
  }
}
