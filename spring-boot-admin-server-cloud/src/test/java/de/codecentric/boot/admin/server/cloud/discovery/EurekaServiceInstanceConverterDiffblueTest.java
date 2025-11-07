package de.codecentric.boot.admin.server.cloud.discovery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import com.netflix.appinfo.DataCenterInfo;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.LeaseInfo;
import java.util.HashMap;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.netflix.eureka.EurekaServiceInstance;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {EurekaServiceInstanceConverter.class})
@ExtendWith(SpringExtension.class)
class EurekaServiceInstanceConverterDiffblueTest {
  @Autowired
  private EurekaServiceInstanceConverter eurekaServiceInstanceConverter;

  /**
   * Method under test:
   * {@link EurekaServiceInstanceConverter#getHealthUrl(ServiceInstance)}
   */
  @Test
  void testGetHealthUrl() {
    // Arrange, Act and Assert
    assertEquals("https://localhost:8080/actuator/health",
        eurekaServiceInstanceConverter.getHealthUrl(new DefaultServiceInstance("42", "42", "localhost", 8080, true))
            .toString());
  }

  /**
   * Method under test:
   * {@link EurekaServiceInstanceConverter#getHealthUrl(ServiceInstance)}
   */
  @Test
  void testGetHealthUrl2() {
    // Arrange
    InstanceInfo.PortWrapper port = new InstanceInfo.PortWrapper(true, 8080);

    InstanceInfo.PortWrapper securePort = new InstanceInfo.PortWrapper(true, 8080);

    DataCenterInfo dataCenterInfo = mock(DataCenterInfo.class);
    LeaseInfo leaseInfo = new LeaseInfo(42, 2, 2L, 2L, 2L, 2L, 2L);

    // Act and Assert
    assertEquals("https://example.org/example", eurekaServiceInstanceConverter
        .getHealthUrl(new EurekaServiceInstance(new InstanceInfo("42", "localhost", "localhost", "42 Main St",
            "localhost", port, securePort, "https://example.org/example", "https://example.org/example",
            "https://example.org/example", "https://example.org/example", "42 Main St", "42 Main St", 1, dataCenterInfo,
            "localhost", InstanceInfo.InstanceStatus.UP, InstanceInfo.InstanceStatus.UP, InstanceInfo.InstanceStatus.UP,
            leaseInfo, true, new HashMap<>(), 2L, 2L, InstanceInfo.ActionType.ADDED, "localhost")))
        .toString());
  }

  /**
   * Method under test:
   * {@link EurekaServiceInstanceConverter#getHealthUrl(ServiceInstance)}
   */
  @Test
  void testGetHealthUrl3() {
    // Arrange
    InstanceInfo.PortWrapper port = new InstanceInfo.PortWrapper(true, 8080);

    InstanceInfo.PortWrapper securePort = new InstanceInfo.PortWrapper(true, 8080);

    DataCenterInfo dataCenterInfo = mock(DataCenterInfo.class);
    LeaseInfo leaseInfo = new LeaseInfo(42, 2, 2L, 2L, 2L, 2L, 2L);

    // Act and Assert
    assertEquals("https://example.org/example",
        eurekaServiceInstanceConverter
            .getHealthUrl(new EurekaServiceInstance(new InstanceInfo("42", "localhost", "localhost", "42 Main St",
                "localhost", port, securePort, "https://example.org/example", "https://example.org/example",
                "https://example.org/example", "", "42 Main St", "42 Main St", 1, dataCenterInfo, "localhost",
                InstanceInfo.InstanceStatus.UP, InstanceInfo.InstanceStatus.UP, InstanceInfo.InstanceStatus.UP,
                leaseInfo, true, new HashMap<>(), 2L, 2L, InstanceInfo.ActionType.ADDED, "localhost")))
            .toString());
  }

  /**
   * Method under test: default or parameterless constructor of
   * {@link EurekaServiceInstanceConverter}
   */
  @Test
  void testNewEurekaServiceInstanceConverter() {
    // Arrange and Act
    EurekaServiceInstanceConverter actualEurekaServiceInstanceConverter = new EurekaServiceInstanceConverter();

    // Assert
    assertEquals("/actuator", actualEurekaServiceInstanceConverter.getManagementContextPath());
    assertEquals("health", actualEurekaServiceInstanceConverter.getHealthEndpointPath());
  }
}
