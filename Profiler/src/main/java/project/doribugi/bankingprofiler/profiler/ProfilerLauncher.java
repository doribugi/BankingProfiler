package project.doribugi.bankingprofiler.profiler;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import project.doribugi.bankingprofiler.profiler.service.LogCollectingService;
import project.doribugi.bankingprofiler.profiler.service.LogParsingService;
import project.doribugi.bankingprofiler.profiler.service.RepositoryService;
import project.doribugi.bankingprofiler.profiler.service.RestService;
import project.doribugi.bankingprofiler.profiler.service.Service;

/**
 * Profiler 실행 클래스.
 * profiler.properties 파일과 kafka.properties 파일이 작업 폴더에 있어야 실행된다.
 */
public class ProfilerLauncher {
  private static final String PROPERTIES_FILE_PATH = "profiler.properties";

  public static void main(String[] args) throws IOException {
    Properties properties = new Properties();
    FileReader reader = new FileReader(PROPERTIES_FILE_PATH);
    properties.load(reader);

    RepositoryService repositoryService = new RepositoryService();
    LogParsingService logParsingService = new LogParsingService(repositoryService);
    LogCollectingService logCollectingService = new LogCollectingService(logParsingService);

    String restIp = properties.getProperty("rest.ip");
    int restPort = Integer.parseInt(properties.getProperty("rest.port"));
    RestService restService = new RestService(restIp, restPort, repositoryService);

    List<Service> serviceList = new ArrayList<>();
    serviceList.add(repositoryService);
    serviceList.add(logParsingService);
    serviceList.add(logCollectingService);
    serviceList.add(restService);
    serviceList.forEach(Service::start);
  }
}
