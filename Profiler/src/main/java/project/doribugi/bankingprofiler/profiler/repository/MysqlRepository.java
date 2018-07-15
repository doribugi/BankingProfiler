package project.doribugi.bankingprofiler.profiler.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class MysqlRepository<T> implements Repository<T> {
  private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
  protected Connection connection;

  public MysqlRepository(String ipAddress, String id, String password)
      throws ClassNotFoundException, SQLException, IllegalAccessException, InstantiationException {
    Class.forName(JDBC_DRIVER);
    String dbUrl = String.format("jdbc:mysql://%s?serverTimezone=UTC", ipAddress);
    this.connection = DriverManager.getConnection(dbUrl, id, password);
  }

  public void disconnect() {
    try {
      connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
