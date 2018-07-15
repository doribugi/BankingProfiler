package project.doribugi.bankingprofiler.profiler.repository;

import project.doribugi.bankingprofiler.profiler.profile.AccountProfile;
import project.doribugi.bankingprofiler.profiler.profile.Transaction;

public interface AccountProfileRepository extends Repository<AccountProfile> {

  /**
   * 지정된 id의 deposit 필드 값을 업데이트 한다.
   * @param id 업데이트 할 레코드 id
   * @param deposit 업데이트 할 deposit
   */
  void updateDeposit(String id, Transaction deposit);

  /**
   * 지정된 id의 withdrawal 필드 값을 업데이트 한다.
   * @param id 업데이트 할 레코드 id
   * @param withdrawal 업데이트 할 withdrawal
   */
  void updateWithdrawal(String id, Transaction withdrawal);

  /**
   * 지정된 id의 transfer 필드 값을 업데이트 한다.
   * @param id id 업데이트 할 레코드 id
   * @param transfer 업데이트 할 transfer
   */
  void updateTransfer(String id, Transaction transfer);
}
