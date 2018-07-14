package project.doribugi.bankingprofiler.profiler.service;

import org.junit.Test;
import org.mockito.Mockito;
import project.doribugi.bankingprofiler.profiler.banking.AccountCreation;
import project.doribugi.bankingprofiler.profiler.banking.Deposit;
import project.doribugi.bankingprofiler.profiler.banking.Join;
import project.doribugi.bankingprofiler.profiler.banking.Transfer;
import project.doribugi.bankingprofiler.profiler.banking.Withdrawal;

public class LogParsingServiceTest {

  @Test
  public void testService() throws InterruptedException {
    RepositoryService repositoryService = Mockito.mock(RepositoryService.class);
    LogParsingService logParsingService = new LogParsingService(repositoryService);
    logParsingService.start();

    logParsingService.put("join", "join, 1, 홍길동, 2018-06-30 13:00:00");
    logParsingService.put("account_create", "account_create, 1, 3333010001, 2018-06-30 13:15:00");
    logParsingService.put("deposit", "deposit, 1, 3333010001, 100000, 2018-06-30 13:15:00");
    logParsingService.put("transfer", "transfer, 1, 3333010001, 카카오뱅크, 3333010002, 이순신, 30000, 2018-06-30 13:40:00");
    logParsingService.put("withdrawal", "withdrawal, 1, 3333010001, 10000, 2018-06-30 13:30:00");

    logParsingService.stop();

    Join expectedJoin = new Join(1, "홍길동", "2018-06-30 13:00:00");
    Mockito.verify(repositoryService).update(expectedJoin);

    AccountCreation expectedAccountCreation = new AccountCreation(
        1,
        "3333010001",
        "2018-06-30 13:15:00"
    );
    Mockito.verify(repositoryService).update(expectedAccountCreation);

    Deposit expectedDeposit = new Deposit(
        1,
        "3333010001",
        100000,
        "2018-06-30 13:15:00"
    );
    Mockito.verify(repositoryService).update(expectedDeposit);

    Transfer expectedTransfer = new Transfer(
        1,
        "3333010001",
        "카카오뱅크",
        "3333010002",
        "이순신",
        30000,
        "2018-06-30 13:40:00"
    );
    Mockito.verify(repositoryService).update(expectedTransfer);

    Withdrawal expectedWithdrawal = new Withdrawal(
        1,
        "3333010001",
        10000,
        "2018-06-30 13:30:00"
    );
    Mockito.verify(repositoryService).update(expectedWithdrawal);
  }
}