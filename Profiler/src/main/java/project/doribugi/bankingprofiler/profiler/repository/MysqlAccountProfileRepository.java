package project.doribugi.bankingprofiler.profiler.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import project.doribugi.bankingprofiler.profiler.profile.AccountProfile;
import project.doribugi.bankingprofiler.profiler.profile.Transaction;

public class MysqlAccountProfileRepository
    extends MysqlRepository<AccountProfile>
    implements AccountProfileRepository {

  private static final String ACCOUNT_PROFILE_TABLE_NAME = AccountProfile.class.getSimpleName();
  private static final String DEPOSIT_TABLE_NAME = "Deposit";
  private static final String WITHDRAWAL_TABLE_NAME = "Withdrawal";
  private static final String TRANSFER_TABLE_NAME = "Transfer";

  public MysqlAccountProfileRepository(String ipAddress, String id, String password)
      throws ClassNotFoundException, SQLException, IllegalAccessException, InstantiationException {
    super(ipAddress, id, password);
  }

  @Override
  public AccountProfile read(String id) {
    AccountProfile accountProfile;

    String sql = String.format("SELECT * FROM banking_profile.%s WHERE accountNumber = '%s'",
        ACCOUNT_PROFILE_TABLE_NAME, id);
    try (Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql)) {
      boolean existResult = resultSet.next();
      if (existResult) {
        accountProfile = new AccountProfile(
            resultSet.getLong("customerNumber"),
            resultSet.getString("accountNumber"),
            resultSet.getString("createDt"));
      } else {
        return null;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }

    String depositSql = String.format("SELECT * FROM banking_profile.%s WHERE accountNumber = '%s'",
        DEPOSIT_TABLE_NAME, id);
    List<Transaction> depositList = getTransactions(depositSql);
    depositList.forEach(accountProfile::addDeposit);

    String withdrawalSql = String.format("SELECT * FROM banking_profile.%s WHERE accountNumber = '%s'",
        WITHDRAWAL_TABLE_NAME, id);
    List<Transaction> withdrawalList = getTransactions(withdrawalSql);
    withdrawalList.forEach(accountProfile::addWithdrawal);

    String transferSql = String.format("SELECT * FROM banking_profile.%s WHERE accountNumber = '%s'",
        TRANSFER_TABLE_NAME, id);
    List<Transaction> transferList = getTransactions(transferSql);
    transferList.forEach(accountProfile::addTransfer);

    return accountProfile;
  }

  private List<Transaction> getTransactions(String depositSql) {
    List<Transaction> depositList = new ArrayList<>();
    try (Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(depositSql)) {
      while (resultSet.next()) {
        Transaction transaction = new Transaction(
            resultSet.getLong("amount"),
            resultSet.getString("datetime")
        );
        depositList.add(transaction);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return depositList;
  }

  @Override
  public void save(String id, AccountProfile accountProfile) {
    String sql = String.format("INSERT INTO banking_profile.%s values ('%s', '%d', '%s')",
        ACCOUNT_PROFILE_TABLE_NAME,
        id,
        accountProfile.getCustomerNumber(),
        accountProfile.getCreateDt());
    try (Statement statement = connection.createStatement()) {
      statement.execute(sql);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void updateDeposit(String id, Transaction deposit) {
    String sql = String.format("INSERT INTO banking_profile.%s values (default, '%s', '%d', '%s')",
        DEPOSIT_TABLE_NAME,
        id,
        deposit.getAmount(),
        deposit.getDatetime());
    try (Statement statement = connection.createStatement()) {
      statement.execute(sql);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void updateWithdrawal(String id, Transaction withdrawal) {
    String sql = String.format("INSERT INTO banking_profile.%s values (default, '%s', '%d', '%s')",
        WITHDRAWAL_TABLE_NAME,
        id,
        withdrawal.getAmount(),
        withdrawal.getDatetime());
    try (Statement statement = connection.createStatement()) {
      statement.execute(sql);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void updateTransfer(String id, Transaction transfer) {
    String sql = String.format("INSERT INTO banking_profile.%s values (default, '%s', '%d', '%s')",
        TRANSFER_TABLE_NAME,
        id,
        transfer.getAmount(),
        transfer.getDatetime());
    try (Statement statement = connection.createStatement()) {
      statement.execute(sql);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
