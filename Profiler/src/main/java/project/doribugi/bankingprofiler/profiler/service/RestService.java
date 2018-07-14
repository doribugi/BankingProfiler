package project.doribugi.bankingprofiler.profiler.service;

import project.doribugi.bankingprofiler.profiler.profile.AccountProfile;
import project.doribugi.bankingprofiler.profiler.profile.CustomerProfile;
import project.doribugi.bankingprofiler.profiler.repository.MemoryRepository;
import project.doribugi.bankingprofiler.profiler.repository.Repository;
import project.doribugi.bankingprofiler.profiler.rest.AccountProfileRouter;
import project.doribugi.bankingprofiler.profiler.rest.CustomerProfileRouter;
import spark.Spark;

public class RestService implements Service {
  private static final String API_CUSTOMER_PROFILE = "/api/customer/:customer_number";
  private static final String API_ACCOUNT_PROFILE
      = "/api/customer/:customer_number/account/:account_number";

  private final RepositoryService repositoryService;

  public RestService(String ipAddress, int port, RepositoryService repositoryService) {
    this.repositoryService = repositoryService;

    Spark.ipAddress(ipAddress);
    Spark.port(port);
  }

  @Override
  public void start() {
    Repository<CustomerProfile> customerProfileRepository
        = repositoryService.getCustomerProfileRepository();
    Spark.get(API_CUSTOMER_PROFILE, new CustomerProfileRouter(customerProfileRepository));

    Repository<AccountProfile> accountProfileRepository
        = repositoryService.getAccountProfileRepository();
    Spark.get(API_ACCOUNT_PROFILE, new AccountProfileRouter(accountProfileRepository));
  }
}
