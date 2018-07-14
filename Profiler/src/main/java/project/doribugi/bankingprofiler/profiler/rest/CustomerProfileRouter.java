package project.doribugi.bankingprofiler.profiler.rest;

import com.google.gson.GsonBuilder;
import project.doribugi.bankingprofiler.profiler.profile.CustomerProfile;
import project.doribugi.bankingprofiler.profiler.repository.Repository;
import spark.Request;
import spark.Response;
import spark.Route;

public class CustomerProfileRouter implements Route {

  private final Repository<CustomerProfile> repository;

  public CustomerProfileRouter(Repository<CustomerProfile> repository) {
    this.repository = repository;
  }

  @Override
  public Object handle(Request request, Response response) throws Exception {
    String customerNumber = request.params(":customer_number");
    String log
        = String.format("Customer profile is requested (customer number: %s)", customerNumber);
    System.out.println(log);
    CustomerProfile profile = repository.read(customerNumber);
    return new GsonBuilder().setPrettyPrinting().create().toJson(profile);
  }
}
