package de.codecentric.boot.admin.client.registration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.HashMap;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

class DefaultApplicationRegistratorDiffblueTest {
  /**
   * Method under test: {@link DefaultApplicationRegistrator#register()}
   */
  @Test
  void testRegister() throws RestClientException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ApplicationFactory applicationFactory = mock(ApplicationFactory.class);
    when(applicationFactory.createApplication()).thenReturn(new Application("Name", "https://example.org/example",
        "https://example.org/example", "https://example.org/example", new HashMap<>()));
    RestTemplate restTemplate = mock(RestTemplate.class);
    when(restTemplate.exchange(Mockito.<String>any(), Mockito.<HttpMethod>any(), Mockito.<HttpEntity<Object>>any(),
        Mockito.<ParameterizedTypeReference<Object>>any(), isA(Object[].class)))
        .thenReturn(new ResponseEntity<>(HttpStatusCode.valueOf(200)));

    // Act
    boolean actualRegisterResult = (new DefaultApplicationRegistrator(applicationFactory,
        new BlockingRegistrationClient(restTemplate), new String[]{"https://example.org/example"}, true)).register();

    // Assert
    verify(applicationFactory).createApplication();
    verify(restTemplate).exchange(eq("https://example.org/example"), isA(HttpMethod.class), isA(HttpEntity.class),
        isA(ParameterizedTypeReference.class), isA(Object[].class));
    assertFalse(actualRegisterResult);
  }

  /**
   * Method under test: {@link DefaultApplicationRegistrator#register()}
   */
  @Test
  void testRegister2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ApplicationFactory applicationFactory = mock(ApplicationFactory.class);
    when(applicationFactory.createApplication()).thenReturn(new Application("Name", "https://example.org/example",
        "https://example.org/example", "https://example.org/example", new HashMap<>()));
    RegistrationClient registrationClient = mock(RegistrationClient.class);
    when(registrationClient.register(Mockito.<String>any(), Mockito.<Application>any())).thenReturn("Register");
    DefaultApplicationRegistrator defaultApplicationRegistrator = new DefaultApplicationRegistrator(applicationFactory,
        registrationClient, new String[]{"https://example.org/example"}, true);

    // Act
    boolean actualRegisterResult = defaultApplicationRegistrator.register();

    // Assert
    verify(applicationFactory).createApplication();
    verify(registrationClient).register(eq("https://example.org/example"), isA(Application.class));
    assertEquals("Register", defaultApplicationRegistrator.getRegisteredId());
    assertTrue(actualRegisterResult);
  }

  /**
   * Method under test: {@link DefaultApplicationRegistrator#register()}
   */
  @Test
  void testRegister3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ApplicationFactory applicationFactory = mock(ApplicationFactory.class);
    when(applicationFactory.createApplication()).thenReturn(new Application("Name", "https://example.org/example",
        "https://example.org/example", "https://example.org/example", new HashMap<>()));
    RegistrationClient registrationClient = mock(RegistrationClient.class);
    when(registrationClient.register(Mockito.<String>any(), Mockito.<Application>any())).thenReturn("Register");
    DefaultApplicationRegistrator defaultApplicationRegistrator = new DefaultApplicationRegistrator(applicationFactory,
        registrationClient, new String[]{"https://example.org/example"}, false);

    // Act
    boolean actualRegisterResult = defaultApplicationRegistrator.register();

    // Assert
    verify(applicationFactory).createApplication();
    verify(registrationClient).register(eq("https://example.org/example"), isA(Application.class));
    assertEquals("Register", defaultApplicationRegistrator.getRegisteredId());
    assertTrue(actualRegisterResult);
  }

  /**
   * Method under test: {@link DefaultApplicationRegistrator#register()}
   */
  @Test
  void testRegister4() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ApplicationFactory applicationFactory = mock(ApplicationFactory.class);
    when(applicationFactory.createApplication()).thenReturn(new Application("Name", "https://example.org/example",
        "https://example.org/example", "https://example.org/example", new HashMap<>()));
    RegistrationClient registrationClient = mock(RegistrationClient.class);
    when(registrationClient.register(Mockito.<String>any(), Mockito.<Application>any())).thenReturn("Register");
    DefaultApplicationRegistrator defaultApplicationRegistrator = new DefaultApplicationRegistrator(applicationFactory,
        registrationClient, new String[]{"https://example.org/example", "Application registered itself as {}"}, false);

    // Act
    boolean actualRegisterResult = defaultApplicationRegistrator.register();

    // Assert
    verify(applicationFactory).createApplication();
    verify(registrationClient, atLeast(1)).register(Mockito.<String>any(), isA(Application.class));
    assertEquals("Register", defaultApplicationRegistrator.getRegisteredId());
    assertTrue(actualRegisterResult);
  }

  /**
   * Method under test: {@link DefaultApplicationRegistrator#register()}
   */
  @Test
  void testRegister5() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ApplicationFactory applicationFactory = mock(ApplicationFactory.class);
    when(applicationFactory.createApplication()).thenReturn(new Application("Name", "https://example.org/example",
        "https://example.org/example", "https://example.org/example", new HashMap<>()));
    RegistrationClient registrationClient = mock(RegistrationClient.class);
    when(registrationClient.register(Mockito.<String>any(), Mockito.<Application>any())).thenReturn("Register");
    DefaultApplicationRegistrator defaultApplicationRegistrator = new DefaultApplicationRegistrator(applicationFactory,
        registrationClient, new String[]{"https://example.org/example", "Application registered itself as {}",
            "https://example.org/example"},
        false);

    // Act
    boolean actualRegisterResult = defaultApplicationRegistrator.register();

    // Assert
    verify(applicationFactory).createApplication();
    verify(registrationClient, atLeast(1)).register(Mockito.<String>any(), isA(Application.class));
    assertEquals("Register", defaultApplicationRegistrator.getRegisteredId());
    assertTrue(actualRegisterResult);
  }

  /**
   * Method under test:
   * {@link DefaultApplicationRegistrator#register(Application, String, boolean)}
   */
  @Test
  void testRegister6() throws RestClientException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    RestTemplate restTemplate = mock(RestTemplate.class);
    when(restTemplate.exchange(Mockito.<String>any(), Mockito.<HttpMethod>any(), Mockito.<HttpEntity<Object>>any(),
        Mockito.<ParameterizedTypeReference<Object>>any(), isA(Object[].class)))
        .thenReturn(new ResponseEntity<>(HttpStatusCode.valueOf(200)));
    DefaultApplicationRegistrator defaultApplicationRegistrator = new DefaultApplicationRegistrator(
        mock(ApplicationFactory.class), new BlockingRegistrationClient(restTemplate),
        new String[]{"https://example.org/example"}, true);

    // Act
    boolean actualRegisterResult = defaultApplicationRegistrator.register(new Application("Name",
        "https://example.org/example", "https://example.org/example", "https://example.org/example", new HashMap<>()),
        "https://example.org/example", true);

    // Assert
    verify(restTemplate).exchange(eq("https://example.org/example"), isA(HttpMethod.class), isA(HttpEntity.class),
        isA(ParameterizedTypeReference.class), isA(Object[].class));
    assertFalse(actualRegisterResult);
  }

  /**
   * Method under test:
   * {@link DefaultApplicationRegistrator#register(Application, String, boolean)}
   */
  @Test
  void testRegister7() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    RegistrationClient registrationClient = mock(RegistrationClient.class);
    when(registrationClient.register(Mockito.<String>any(), Mockito.<Application>any())).thenReturn("Register");
    DefaultApplicationRegistrator defaultApplicationRegistrator = new DefaultApplicationRegistrator(
        mock(ApplicationFactory.class), registrationClient, new String[]{"https://example.org/example"}, true);

    // Act
    boolean actualRegisterResult = defaultApplicationRegistrator.register(new Application("Name",
        "https://example.org/example", "https://example.org/example", "https://example.org/example", new HashMap<>()),
        "https://example.org/example", true);

    // Assert
    verify(registrationClient).register(eq("https://example.org/example"), isA(Application.class));
    assertEquals("Register", defaultApplicationRegistrator.getRegisteredId());
    assertTrue(actualRegisterResult);
  }

  /**
   * Method under test: {@link DefaultApplicationRegistrator#getRegisteredId()}
   */
  @Test
  void testGetRegisteredId() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ApplicationFactory applicationFactory = mock(ApplicationFactory.class);

    // Act and Assert
    assertNull(
        (new DefaultApplicationRegistrator(applicationFactory, new BlockingRegistrationClient(mock(RestTemplate.class)),
            new String[]{"https://example.org/example"}, true)).getRegisteredId());
  }

  /**
   * Method under test:
   * {@link DefaultApplicationRegistrator#DefaultApplicationRegistrator(ApplicationFactory, RegistrationClient, String[], boolean)}
   */
  @Test
  void testNewDefaultApplicationRegistrator() {
    // Arrange
    ApplicationFactory applicationFactory = mock(ApplicationFactory.class);

    // Act and Assert
    assertNull(
        (new DefaultApplicationRegistrator(applicationFactory, new BlockingRegistrationClient(mock(RestTemplate.class)),
            new String[]{"https://example.org/example"}, true)).getRegisteredId());
  }
}
