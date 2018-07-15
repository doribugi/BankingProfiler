package project.doribugi.bankingprofiler.profiler.repository;

import project.doribugi.bankingprofiler.profiler.profile.CustomerProfile;

public interface CustomerProfileRepository extends Repository<CustomerProfile> {

  /**
   * 지정된 id의 largestDepositAmount 필드 값을 업데이트 한다.
   * @param id 업데이트 할 레코드 id
   * @param largestDepositAmount 업데이트 할 largestDepositAmount 값
   */
  void updateLargestDepositAmount(String id, long largestDepositAmount);

  /**
   * 지정된 id의 largestWithdrawalAmount 필드 값을 업데이트 한다.
   * @param id 업데이트 할 레코드 id
   * @param largestWithdrawalAmount 업데이트 할 largestWithdrawalAmount 값
   */
  void updateLargestWithdrawalAmount(String id, long largestWithdrawalAmount);

  /**
   * 지정된 id의 largestTransferAmount 필드 값을 업데이트 한다.
   * @param id 업데이트 할 레코드 id
   * @param largestTransferAmount 업데이트 할 largestTransferAmount 값
   */
  void updateLargestTransferAmount(String id, long largestTransferAmount);
}
