package project.doribugi.bankingprofiler.profiler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import project.doribugi.bankingprofiler.profiler.service.LogCollectingService;
import project.doribugi.bankingprofiler.profiler.service.LogParsingService;
import project.doribugi.bankingprofiler.profiler.service.RepositoryService;
import project.doribugi.bankingprofiler.profiler.service.RestService;
import project.doribugi.bankingprofiler.profiler.service.Service;

public class ProfilerLauncher {
  public static void main(String[] args) throws IOException {
    RepositoryService repositoryService = new RepositoryService();
    LogParsingService logParsingService = new LogParsingService(repositoryService);
    LogCollectingService logCollectingService = new LogCollectingService(logParsingService);
    RestService restService = new RestService(repositoryService);

    List<Service> serviceList = new ArrayList<>();
    serviceList.add(repositoryService);
    serviceList.add(logParsingService);
    serviceList.add(logCollectingService);
    serviceList.add(restService);
    serviceList.forEach(Service::start);
  }
}
