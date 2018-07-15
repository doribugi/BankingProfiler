package project.doribugi.bankingprofiler.profiler.rest;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import project.doribugi.bankingprofiler.profiler.profile.AccountProfile;
import project.doribugi.bankingprofiler.profiler.profile.Transaction;
import project.doribugi.bankingprofiler.profiler.repository.Repository;
import spark.Request;

public class AccountProfileRouterTest {

  private Repository<AccountProfile> repository;

  @Before
  public void setUp() {
    AccountProfile accountProfile =
        new AccountProfile(
            1,
            "3333010001",
            "2018-06-30 13:15:00");
    Transaction deposit1 = new Transaction(100000, "2018-06-30 13:15:00");
    accountProfile.addDeposit(deposit1);
    Transaction deposit2 = new Transaction(50000, "2018-06-30 13:25:00");
    accountProfile.addDeposit(deposit2);
    Transaction withdrawal1 = new Transaction(10000, "2018-06-30 13:30:00");
    accountProfile.addWithdrawal(withdrawal1);
    Transaction withdrawal2 = new Transaction(20000, "2018-06-30 13:35:00");
    accountProfile.addWithdrawal(withdrawal2);
    Transaction transfer1 = new Transaction(30000, "2018-06-30 13:40:00");
    accountProfile.addTransfer(transfer1);
    Transaction transfer2 = new Transaction(40000, "2018-06-30 13:45:00");
    accountProfile.addTransfer(transfer2);

    repository = Mockito.mock(Repository.class);
    Mockito.when(repository.read("1_3333010001")).thenReturn(accountProfile);
  }

  @Test
  public void testHandle() throws Exception {
    AccountProfileRouter router = new AccountProfileRouter(repository);
    Request request = Mockito.mock(Request.class);
    Mockito.when(request.params(":customer_number")).thenReturn("1");
    Mockito.when(request.params(":account_number")).thenReturn("3333010001");

    URI uri = AccountProfileRouterTest.class.getResource("AccountProfile.json").toURI();
    String expected = new String(Files.readAllBytes(Paths.get(uri)), StandardCharsets.UTF_8);
    Assert.assertEquals(expected, router.handle(request, null));
  }
}