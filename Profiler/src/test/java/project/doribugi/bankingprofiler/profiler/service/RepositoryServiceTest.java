package project.doribugi.bankingprofiler.profiler.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import project.doribugi.bankingprofiler.profiler.banking.AccountCreation;
import project.doribugi.bankingprofiler.profiler.banking.Deposit;
import project.doribugi.bankingprofiler.profiler.banking.Join;
import project.doribugi.bankingprofiler.profiler.banking.Transfer;
import project.doribugi.bankingprofiler.profiler.banking.Withdrawal;
import project.doribugi.bankingprofiler.profiler.profile.AccountProfile;
import project.doribugi.bankingprofiler.profiler.profile.CustomerProfile;
import project.doribugi.bankingprofiler.profiler.profile.Transaction;

public class RepositoryServiceTest {
  private RepositoryService repositoryService ;
  private Join join = new Join(1, "홍길동", "2018-06-30 13:00:00");
  private AccountCreation accountCreation = new AccountCreation(
      1,
      "3333010001",
      "2018-06-30 13:15:00");
  private Deposit deposit = new Deposit(
      1,
      "3333010001",
      100000,
      "2018-06-30 13:15:00");
  private Withdrawal withdrawal = new Withdrawal(
      1,
      "3333010001",
      10000,
      "2018-06-30 13:30:00");
  private Transfer transfer = new Transfer(
      1,
      "3333010001",
      "카카오뱅크",
      "3333010002",
      "김대석",
      30000,
      "2018-06-30 13:40:00");

  @Before
  public void setUp() {
    repositoryService = new RepositoryService();
  }

  @Test
  public void testCreateCustomerProfile() {
    repositoryService.update(join);

    CustomerProfile actual = repositoryService.getCustomerProfileRepository().read("1");
    CustomerProfile expected = new CustomerProfile(
        1,
        "홍길동",
        "2018-06-30 13:00:00",
        0,
        0,
        0);
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void testCreateAccountProfile() {
    repositoryService.update(join);
    repositoryService.update(accountCreation);

    AccountProfile actual = repositoryService.getAccountProfileRepository().read("1_3333010001");
    AccountProfile expected = new AccountProfile(
        1,
        "3333010001",
        "2018-06-30 13:15:00",
        0);
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void testUpdateDeposit() {
    repositoryService.update(join);
    repositoryService.update(accountCreation);
    repositoryService.update(deposit);

    CustomerProfile actualCustomerProfile = repositoryService.getCustomerProfileRepository().read("1");
    CustomerProfile expectedCustomerProfile = new CustomerProfile(
        1,
        "홍길동",
        "2018-06-30 13:00:00",
        100000,
        0,
        0);
    Assert.assertEquals(expectedCustomerProfile, actualCustomerProfile);

    AccountProfile actualAccountProfile
        = repositoryService.getAccountProfileRepository().read("1_3333010001");
    AccountProfile expectedAccountProfile = new AccountProfile(
        1,
        "3333010001",
        "2018-06-30 13:15:00",
        0);
    expectedAccountProfile.addDeposit(new Transaction(100000, "2018-06-30 13:15:00"));
    Assert.assertEquals(actualAccountProfile, expectedAccountProfile);
    Assert.assertEquals(100000, expectedAccountProfile.getBalance());
  }

  @Test
  public void testUpdateWithdrawal() {
    repositoryService.update(join);
    repositoryService.update(accountCreation);
    repositoryService.update(deposit);
    repositoryService.update(withdrawal);

    CustomerProfile actualCustomerProfile = repositoryService.getCustomerProfileRepository().read("1");
    CustomerProfile expectedCustomerProfile = new CustomerProfile(
        1,
        "홍길동",
        "2018-06-30 13:00:00",
        100000,
        10000,
        0);
    Assert.assertEquals(expectedCustomerProfile, actualCustomerProfile);

    AccountProfile actualAccountProfile
        = repositoryService.getAccountProfileRepository().read("1_3333010001");
    AccountProfile expectedAccountProfile = new AccountProfile(
        1,
        "3333010001",
        "2018-06-30 13:15:00",
        0);
    expectedAccountProfile.addDeposit(new Transaction(100000, "2018-06-30 13:15:00"));
    expectedAccountProfile.addWithdrawal(new Transaction(10000, "2018-06-30 13:30:00"));
    Assert.assertEquals(actualAccountProfile, expectedAccountProfile);
    Assert.assertEquals(90000, expectedAccountProfile.getBalance());
  }

  @Test
  public void testUpdateTransfer() {
    repositoryService.update(join);
    repositoryService.update(accountCreation);
    repositoryService.update(deposit);
    repositoryService.update(transfer);

    CustomerProfile actualCustomerProfile = repositoryService.getCustomerProfileRepository().read("1");
    CustomerProfile expectedCustomerProfile = new CustomerProfile(
        1,
        "홍길동",
        "2018-06-30 13:00:00",
        100000,
        0,
        30000);
    Assert.assertEquals(expectedCustomerProfile, actualCustomerProfile);

    AccountProfile actualAccountProfile
        = repositoryService.getAccountProfileRepository().read("1_3333010001");
    AccountProfile expectedAccountProfile = new AccountProfile(
        1,
        "3333010001",
        "2018-06-30 13:15:00",
        0);
    expectedAccountProfile.addDeposit(new Transaction(100000, "2018-06-30 13:15:00"));
    expectedAccountProfile.addTransfer(new Transaction(30000, "2018-06-30 13:40:00"));
    Assert.assertEquals(actualAccountProfile, expectedAccountProfile);
    Assert.assertEquals(70000, expectedAccountProfile.getBalance());
  }
}