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
  public void testService() {
    RepositoryService repositoryService = Mockito.mock(RepositoryService.class);
    LogParsingService logParsingService = new LogParsingService(repositoryService);
    logParsingService.start();

    logParsingService.parse("join, 1, 홍길동, 2018-06-30 13:00:00");
    logParsingService.parse("account_create, 1, 3333010001, 2018-06-30 13:15:00");
    logParsingService.parse("deposit, 1, 3333010001, 100000, 2018-06-30 13:20:00");
    logParsingService.parse("withdrawal, 1, 3333010001, 10000, 2018-06-30 13:30:00");
    logParsingService.parse(
        "transfer, 1, 3333010001, 카카오뱅크, 3333010002, 이순신, 30000, 2018-06-30 13:40:00");

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
        "2018-06-30 13:20:00"
    );
    Mockito.verify(repositoryService).update(expectedDeposit);

    Withdrawal expectedWithdrawal = new Withdrawal(
        1,
        "3333010001",
        10000,
        "2018-06-30 13:30:00"
    );
    Mockito.verify(repositoryService).update(expectedWithdrawal);

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
  }
}