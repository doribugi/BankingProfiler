package project.doribugi.bankingprofiler.profiler.service;

import project.doribugi.bankingprofiler.profiler.banking.BankingInfo;
import project.doribugi.bankingprofiler.profiler.profile.AccountProfile;
import project.doribugi.bankingprofiler.profiler.profile.CustomerProfile;
import project.doribugi.bankingprofiler.profiler.repository.MemoryRepository;
import project.doribugi.bankingprofiler.profiler.repository.Repository;

public class RepositoryService implements Service {

  private final Repository<CustomerProfile> customerProfileRepository = new MemoryRepository<>();
  private final Repository<AccountProfile> accountProfileRepository = new MemoryRepository<>();

  @Override
  public void start() {

  }

  public void update(BankingInfo bankingInfo) {

  }

  public Repository<CustomerProfile> getCustomerProfileRepository() {
    return customerProfileRepository;
  }

  public Repository<AccountProfile> getAccountProfileRepository() {
    return accountProfileRepository;
  }
}
