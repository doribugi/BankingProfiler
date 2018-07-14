package project.doribugi.bankingprofiler.profiler.service;

import project.doribugi.bankingprofiler.profiler.banking.AccountCreation;
import project.doribugi.bankingprofiler.profiler.banking.BankingInfo;
import project.doribugi.bankingprofiler.profiler.banking.Deposit;
import project.doribugi.bankingprofiler.profiler.banking.Join;
import project.doribugi.bankingprofiler.profiler.banking.Transfer;
import project.doribugi.bankingprofiler.profiler.banking.Withdrawal;
import project.doribugi.bankingprofiler.profiler.profile.AccountProfile;
import project.doribugi.bankingprofiler.profiler.profile.CustomerProfile;
import project.doribugi.bankingprofiler.profiler.profile.Transaction;
import project.doribugi.bankingprofiler.profiler.repository.MemoryRepository;
import project.doribugi.bankingprofiler.profiler.repository.Repository;

public class RepositoryService implements Service {

  private final Repository<CustomerProfile> customerProfileRepository = new MemoryRepository<>();
  private final Repository<AccountProfile> accountProfileRepository = new MemoryRepository<>();

  @Override
  public void start() {
    // do nothing
  }

  public Repository<CustomerProfile> getCustomerProfileRepository() {
    return customerProfileRepository;
  }

  public Repository<AccountProfile> getAccountProfileRepository() {
    return accountProfileRepository;
  }

  public void update(BankingInfo bankingInfo) {
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
    String customerId = Long.toString(customerProfile.getCustomer_number());
    customerProfileRepository.create(customerId, customerProfile);
  }

  private void createAccountProfile(AccountCreation accountCreation) {
    AccountProfile accountProfile = new AccountProfile(
        accountCreation.getCustomerNumber(),
        accountCreation.getAccountNumber(),
        accountCreation.getCreateDt(),
        0);
    String accountId = Long.toString(accountProfile.getCustomer_number())
        + "_" + accountProfile.getAccount_number();
    accountProfileRepository.create(accountId, accountProfile);
  }

  private void updateDeposit(Deposit deposit) {
    String customerId = Long.toString(deposit.getCustomerNumber());
    CustomerProfile customerProfile = customerProfileRepository.read(customerId);
    long largestDeposit
        = Math.max(customerProfile.getLargest_deposit_acmount(), deposit.getDepositAmount());
    customerProfile.setLargest_deposit_acmount(largestDeposit);
    customerProfileRepository.update(customerId, customerProfile);

    String accountId = Long.toString(deposit.getCustomerNumber()) + "_" + deposit.getAccountNumber();
    AccountProfile accountProfile = accountProfileRepository.read(accountId);
    Transaction transaction = new Transaction(deposit.getDepositAmount(), deposit.getDepositDt());
    accountProfile.addDeposit(transaction);
    accountProfileRepository.update(accountId, accountProfile);
  }

  private void updateWithdrawal(Withdrawal withdrawal) {
    String customerId = Long.toString(withdrawal.getCustomerNumber());
    CustomerProfile customerProfile = customerProfileRepository.read(customerId);
    long largestDeposit
        = Math.max(customerProfile.getLargest_withdrawal_acmount(), withdrawal.getWithdrawalAmount());
    customerProfile.setLargest_withdrawal_acmount(largestDeposit);
    customerProfileRepository.update(customerId, customerProfile);

    String accountId = Long.toString(withdrawal.getCustomerNumber()) + "_" + withdrawal.getAccountNumber();
    AccountProfile accountProfile = accountProfileRepository.read(accountId);
    Transaction transaction = new Transaction(withdrawal.getWithdrawalAmount(), withdrawal.getWithdrawalDt());
    accountProfile.addWithdrawal(transaction);
    accountProfileRepository.update(accountId, accountProfile);
  }

  private void updateTransfer(Transfer transfer) {
    String customerId = Long.toString(transfer.getCustomerNumber());
    CustomerProfile customerProfile = customerProfileRepository.read(customerId);
    long largestDeposit
        = Math.max(customerProfile.getLargest_transfer_acmount(), transfer.getTransferAmount());
    customerProfile.setLargest_transfer_acmount(largestDeposit);
    customerProfileRepository.update(customerId, customerProfile);

    String accountId = Long.toString(transfer.getCustomerNumber()) + "_" + transfer.getAccountNumber();
    AccountProfile accountProfile = accountProfileRepository.read(accountId);
    Transaction transaction = new Transaction(transfer.getTransferAmount(), transfer.getTransferDt());
    accountProfile.addTransfer(transaction);
    accountProfileRepository.update(accountId, accountProfile);
  }
}