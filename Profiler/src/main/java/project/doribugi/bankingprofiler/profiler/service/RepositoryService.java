package project.doribugi.bankingprofiler.profiler.service;

import java.sql.SQLException;
import java.util.Properties;
import project.doribugi.bankingprofiler.profiler.banking.AccountCreation;
import project.doribugi.bankingprofiler.profiler.banking.BankingInfo;
import project.doribugi.bankingprofiler.profiler.banking.Deposit;
import project.doribugi.bankingprofiler.profiler.banking.Join;
import project.doribugi.bankingprofiler.profiler.banking.Transfer;
import project.doribugi.bankingprofiler.profiler.banking.Withdrawal;
import project.doribugi.bankingprofiler.profiler.profile.AccountProfile;
import project.doribugi.bankingprofiler.profiler.profile.CustomerProfile;
import project.doribugi.bankingprofiler.profiler.profile.Transaction;
import project.doribugi.bankingprofiler.profiler.repository.AccountProfileRepository;
import project.doribugi.bankingprofiler.profiler.repository.CustomerProfileRepository;
import project.doribugi.bankingprofiler.profiler.repository.MemoryAccountProfileRepository;
import project.doribugi.bankingprofiler.profiler.repository.MemoryCustomerProfileRespository;
import project.doribugi.bankingprofiler.profiler.repository.MysqlAccountProfileRepository;
import project.doribugi.bankingprofiler.profiler.repository.MysqlCustomerProfileRepository;
import project.doribugi.bankingprofiler.profiler.repository.Repository;

/**
 * Repository 와의 인터페이스를 수행하는 서비스 클래스.
 */
public class RepositoryService implements Service {

  private final CustomerProfileRepository customerProfileRepository;
  private final AccountProfileRepository accountProfileRepository;

  public RepositoryService(Properties properties)
      throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
    String repositoryType = properties.getProperty("repository");
    if (repositoryType.equals("mysql")) {
      String ipAddress = properties.getProperty("mysql.ip");
      String id = properties.getProperty("mysql.id");
      String password = properties.getProperty("mysql.password");
      customerProfileRepository = new MysqlCustomerProfileRepository(ipAddress, id, password);
      accountProfileRepository = new MysqlAccountProfileRepository(ipAddress, id, password);
    } else {
      customerProfileRepository = new MemoryCustomerProfileRespository();
      accountProfileRepository = new MemoryAccountProfileRepository();
    }
  }

  /**
   * 서비스 시작.
   */
  @Override
  public void start() {
    // do nothing
  }

  /**
   * 서비스 중지.
   */
  @Override
  public void stop() {
    // do nothing
  }

  public Repository<CustomerProfile> getCustomerProfileRepository() {
    return customerProfileRepository;
  }

  public Repository<AccountProfile> getAccountProfileRepository() {
    return accountProfileRepository;
  }

  public synchronized void update(BankingInfo bankingInfo) {
    if (bankingInfo instanceof Join) {
      createCustomerProfile((Join)bankingInfo);
    } else if (bankingInfo instanceof AccountCreation) {
      createAccountProfile((AccountCreation)bankingInfo);
    } else if (bankingInfo instanceof Deposit) {
      updateDeposit((Deposit)bankingInfo);
    } else if (bankingInfo instanceof Transfer) {
      updateTransfer((Transfer)bankingInfo);
    } else if (bankingInfo instanceof Withdrawal) {
      updateWithdrawal((Withdrawal)bankingInfo);
    } else {
      throw new IllegalArgumentException("Unknown type: " + bankingInfo.getClass().getTypeName());
    }
  }

  private void createCustomerProfile(Join join) {
    CustomerProfile customerProfile = new CustomerProfile(
        join.getCustomerNumber(),
        join.getCustomerName(),
        join.getJoinDt(),
        0,
        0,
        0);
    String customerId = Long.toString(customerProfile.getCustomerNumber());
    customerProfileRepository.save(customerId, customerProfile);
  }

  private void createAccountProfile(AccountCreation accountCreation) {
    AccountProfile accountProfile = new AccountProfile(
        accountCreation.getCustomerNumber(),
        accountCreation.getAccountNumber(),
        accountCreation.getCreateDt());
    String accountId = Long.toString(accountProfile.getCustomerNumber())
        + "_" + accountProfile.getAccountNumber();
    accountProfileRepository.save(accountId, accountProfile);
  }

  private void updateDeposit(Deposit deposit) {
    String customerId = Long.toString(deposit.getCustomerNumber());
    CustomerProfile customerProfile = customerProfileRepository.read(customerId);
    long largestDeposit
        = Math.max(customerProfile.getLargestDepositAmount(), deposit.getDepositAmount());
    customerProfileRepository.updateLargestDepositAmount(customerId, largestDeposit);

    String accountId = deposit.getAccountNumber();
    Transaction transaction = new Transaction(deposit.getDepositAmount(), deposit.getDepositDt());
    accountProfileRepository.updateDeposit(accountId, transaction);
  }

  private void updateWithdrawal(Withdrawal withdrawal) {
    String customerId = Long.toString(withdrawal.getCustomerNumber());
    CustomerProfile customerProfile = customerProfileRepository.read(customerId);
    long largestWithdrawal
        = Math.max(customerProfile.getLargestWithdrawalAmount(), withdrawal.getWithdrawalAmount());
    customerProfileRepository.updateLargestWithdrawalAmount(customerId, largestWithdrawal);

    String accountId = Long.toString(withdrawal.getCustomerNumber()) + "_" + withdrawal.getAccountNumber();
    Transaction transaction = new Transaction(withdrawal.getWithdrawalAmount(), withdrawal.getWithdrawalDt());
    accountProfileRepository.updateWithdrawal(accountId, transaction);
  }

  private void updateTransfer(Transfer transfer) {
    String customerId = Long.toString(transfer.getCustomerNumber());
    CustomerProfile customerProfile = customerProfileRepository.read(customerId);
    long largestTransfer
        = Math.max(customerProfile.getLargestTransferAmount(), transfer.getTransferAmount());
    customerProfile.setLargestTransferAmount(largestTransfer);
    customerProfileRepository.updateLargestTransferAmount(customerId, largestTransfer);

    String accountId = Long.toString(transfer.getCustomerNumber()) + "_" + transfer.getAccountNumber();
    Transaction transaction = new Transaction(transfer.getTransferAmount(), transfer.getTransferDt());
    accountProfileRepository.updateTransfer(accountId, transaction);
  }
}
