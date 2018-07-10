package project.doribugi.bankingprofiler.profiler.rest;

import com.google.gson.GsonBuilder;
import project.doribugi.bankingprofiler.profiler.profile.AccountProfile;
import project.doribugi.bankingprofiler.profiler.repository.Repository;
import spark.Request;
import spark.Response;
import spark.Route;

public class AccountProfileRouter implements Route {

  private final Repository<AccountProfile> repository;

  public AccountProfileRouter(Repository<AccountProfile> repository) {
    this.repository = repository;
  }

  @Override
  public Object handle(Request request, Response response) throws Exception {
    String id = request.params(":customer_number") + "_" + request.params(":account_number");
    AccountProfile profile = repository.read(id);
    return new GsonBuilder().setPrettyPrinting().create().toJson(profile);
  }
}
