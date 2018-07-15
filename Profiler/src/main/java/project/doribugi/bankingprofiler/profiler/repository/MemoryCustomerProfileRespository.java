package project.doribugi.bankingprofiler.profiler.repository;

import project.doribugi.bankingprofiler.profiler.profile.CustomerProfile;

public class MemoryCustomerProfileRespository
    extends MemoryRepository<CustomerProfile>
    implements CustomerProfileRepository {

  @Override
  public void updateLargestDepositAmount(String id, long largestDepositAmount) {
    dataMap.get(id).setLargestDepositAmount(largestDepositAmount);
  }

  @Override
  public void updateLargestWithdrawalAmount(String id, long largestWithdrawalAmount) {
    dataMap.get(id).setLargestWithdrawalAmount(largestWithdrawalAmount);
  }

  @Override
  public void updateLargestTransferAmount(String id, long largestTransferAmount) {
    dataMap.get(id).setLargestTransferAmount(largestTransferAmount);
  }
}
