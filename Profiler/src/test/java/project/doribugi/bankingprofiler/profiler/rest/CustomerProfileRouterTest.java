package project.doribugi.bankingprofiler.profiler.rest;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import project.doribugi.bankingprofiler.profiler.entity.CustomerProfile;
import project.doribugi.bankingprofiler.profiler.repository.Repository;
import spark.Request;

public class CustomerProfileRouterTest {
  private Repository<CustomerProfile> repository;

  @Before
  public void setUp() {
    CustomerProfile customerProfile =
        new CustomerProfile(
            1,
            "홍길동",
            "2018-06-30 13:00:00",
            100000,
            20000,
            40000
        );

    repository = Mockito.mock(Repository.class);
    Mockito.when(repository.read("1")).thenReturn(customerProfile);
  }

  @Test
  public void testHandle() throws Exception {
    CustomerProfileRouter router = new CustomerProfileRouter(repository);
    Request request = Mockito.mock(Request.class);
    Mockito.when(request.params(":customer_number")).thenReturn("1");

    URI uri = AccountProfileRouterTest.class.getResource("CustomerProfile.json").toURI();
    String expected = new String(Files.readAllBytes(Paths.get(uri)), StandardCharsets.UTF_8);
    Assert.assertEquals(expected, router.handle(request, null));
  }
}