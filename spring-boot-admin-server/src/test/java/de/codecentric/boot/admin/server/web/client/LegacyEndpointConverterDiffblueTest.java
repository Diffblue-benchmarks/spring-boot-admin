package de.codecentric.boot.admin.server.web.client;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.function.Function;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@ContextConfiguration(classes = {LegacyEndpointConverter.class, String.class})
@RunWith(SpringJUnit4ClassRunner.class)
@DisabledInAotMode
public class LegacyEndpointConverterDiffblueTest {
  @MockBean
  private Function<Flux<DataBuffer>, Flux<DataBuffer>> function;

  @Autowired
  private LegacyEndpointConverter legacyEndpointConverter;

  /**
   * Method under test: {@link LegacyEndpointConverter#canConvert(Object)}
   */
  @Test
  public void testCanConvert() {
    // Arrange, Act and Assert
    assertFalse(legacyEndpointConverter.canConvert("Endpoint Id"));
    assertTrue(legacyEndpointConverter.canConvert(""));
  }

  /**
   * Method under test: {@link LegacyEndpointConverter#convert(Flux)}
   */
  @Test
  public void testConvert() throws AssertionError {
    // Arrange
    Flux<DataBuffer> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(function.apply(Mockito.<Flux<DataBuffer>>any())).thenReturn(fromIterableResult);
    Flux<DataBuffer> body = Flux.fromIterable(new ArrayList<>());

    // Act
    Flux<DataBuffer> actualPublisher = legacyEndpointConverter.convert(body);

    // Assert
    StepVerifier.FirstStep<DataBuffer> createResult = StepVerifier.create(actualPublisher);
    createResult.expectComplete().verify();
    verify(function).apply(isA(Flux.class));
    assertSame(fromIterableResult, actualPublisher);
  }
}
