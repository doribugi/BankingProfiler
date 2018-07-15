package project.doribugi.bankingprofiler.profiler.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import project.doribugi.bankingprofiler.profiler.profile.CustomerProfile;

public class MysqlCustomerProfileRepository
    extends MysqlRepository<CustomerProfile>
    implements CustomerProfileRepository {

  private static final String TABLE_NAME = CustomerProfile.class.getSimpleName();

  public MysqlCustomerProfileRepository(String ipAddress, String id, String password)
      throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
    super(ipAddress, id, password);
  }

  @Override
  public CustomerProfile read(String id) {
    String sql = String.format("SELECT * FROM banking_profile.%s WHERE customerNumber = %s",
        TABLE_NAME, id);
    try (Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql)) {
      boolean existResult = resultSet.next();
      if (existResult) {
        return new CustomerProfile(
            resultSet.getLong("customerNumber"),
            resultSet.getString("name"),
            resultSet.getString("joinDt"),
            resultSet.getLong("largestDepositAmount"),
            resultSet.getLong("largestWithdrawalAmount"),
            resultSet.getLong("largestTransferAmount"));
      } else {
        return null;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public void save(String id, CustomerProfile customerProfile) {
    String sql
        = String.format("INSERT INTO banking_profile.%s values ('%s', '%s', '%s', '%d', '%d', '%d')",
        TABLE_NAME,
        id,
        customerProfile.getName(),
        customerProfile.getJoinDt(),
        customerProfile.getLargestDepositAmount(),
        customerProfile.getLargestWithdrawalAmount(),
        customerProfile.getLargestTransferAmount());
    try (Statement statement = connection.createStatement()) {
      statement.execute(sql);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void updateLargestDepositAmount(String id, long largestDepositAmount) {
    String sql = String.format(
        "UPDATE banking_profile.%s SET largestDepositAmount='%d' WHERE customerNumber='%d'",
        TABLE_NAME,
        largestDepositAmount,
        Long.parseLong(id));
    try (Statement statement = connection.createStatement()) {
      statement.execute(sql);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void updateLargestWithdrawalAmount(String id, long largestWithdrawalAmount) {
    String sql = String.format(
        "UPDATE banking_profile.%s SET largestWithdrawalAmount='%d' WHERE customerNumber='%d'",
        TABLE_NAME,
        largestWithdrawalAmount,
        Long.parseLong(id));
    try (Statement statement = connection.createStatement()) {
      statement.execute(sql);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void updateLargestTransferAmount(String id, long largestTransferAmount) {
    String sql = String.format(
        "UPDATE banking_profile.%s SET largestTransferAmount='%d' WHERE customerNumber='%d'",
        TABLE_NAME,
        largestTransferAmount,
        Long.parseLong(id));
    try (Statement statement = connection.createStatement()) {
      statement.execute(sql);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
