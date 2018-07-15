package project.doribugi.bankingprofiler.profiler.repository;

import project.doribugi.bankingprofiler.profiler.profile.AccountProfile;
import project.doribugi.bankingprofiler.profiler.profile.Transaction;

public class MemoryAccountProfileRepository
    extends MemoryRepository<AccountProfile>
    implements AccountProfileRepository {

  @Override
  public void updateDeposit(String id, Transaction deposit) {
    dataMap.get(id).addDeposit(deposit);
  }

  @Override
  public void updateWithdrawal(String id, Transaction withdrawal) {
    dataMap.get(id).addWithdrawal(withdrawal);
  }

  @Override
  public void updateTransfer(String id, Transaction transfer) {
    dataMap.get(id).addTransfer(transfer);
  }
}
