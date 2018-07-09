package project.doribugi.bankingprofiler.profiler.rest;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import project.doribugi.bankingprofiler.profiler.entity.AccountProfile;
import project.doribugi.bankingprofiler.profiler.entity.Transaction;
import project.doribugi.bankingprofiler.profiler.repository.Repository;
import spark.Request;

public class AccountProfileRouterTest {

  private Repository<AccountProfile> repository;

  @Before
  public void setUp() {
    List<Transaction> deposits = new ArrayList<>();
    Transaction deposit1 = new Transaction(100000, "2018-06-30 13:15:00");
    deposits.add(deposit1);
    Transaction deposit2 = new Transaction(500000, "2018-06-30 13:25:00");
    deposits.add(deposit2);

    List<Transaction> withdrawals = new ArrayList<>();
    Transaction withdrawal1 = new Transaction(10000, "2018-06-30 13:30:00");
    withdrawals.add(withdrawal1);
    Transaction withdrawal2 = new Transaction(20000, "2018-06-30 13:35:00");
    withdrawals.add(withdrawal2);

    List<Transaction> transfers = new ArrayList<>();
    Transaction transfer1 = new Transaction(30000, "2018-06-30 13:40:00");
    transfers.add(transfer1);
    Transaction transfer2 = new Transaction(40000, "2018-06-30 13:45:00");
    transfers.add(transfer2);

    AccountProfile accountProfile =
        new AccountProfile(
            1,
            3333010001L,
            "2018-06-30 13:15:00",
            50000,
            deposits,
            withdrawals,
            transfers
            );

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