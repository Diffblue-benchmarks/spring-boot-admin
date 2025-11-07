package de.codecentric.boot.admin.server.web.client;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.function.Function;
import org.junit.Test;
import org.junit.experimental.categories.Category;
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
import reactor.test.StepVerifier.FirstStep;

@ContextConfiguration(classes = {LegacyEndpointConverter.class, String.class})
@RunWith(SpringJUnit4ClassRunner.class)
@DisabledInAotMode
public class LegacyEndpointConverterDiffblueTest {
  @MockBean
  private Function<Flux<DataBuffer>, Flux<DataBuffer>> function;

  @Autowired
  private LegacyEndpointConverter legacyEndpointConverter;

  /**
   * Test {@link LegacyEndpointConverter#canConvert(Object)}.
   * <ul>
   *   <li>When empty string.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LegacyEndpointConverter#canConvert(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean LegacyEndpointConverter.canConvert(Object)"})
  public void testCanConvert_whenEmptyString_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue(legacyEndpointConverter.canConvert(""));
  }

  /**
   * Test {@link LegacyEndpointConverter#canConvert(Object)}.
   * <ul>
   *   <li>When {@code Endpoint Id}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LegacyEndpointConverter#canConvert(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean LegacyEndpointConverter.canConvert(Object)"})
  public void testCanConvert_whenEndpointId_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(legacyEndpointConverter.canConvert("Endpoint Id"));
  }

  /**
   * Test {@link LegacyEndpointConverter#convert(Flux)}.
   * <p>
   * Method under test: {@link LegacyEndpointConverter#convert(Flux)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Flux LegacyEndpointConverter.convert(Flux)"})
  public void testConvert() throws AssertionError {
    // Arrange
    Flux<DataBuffer> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(function.apply(Mockito.<Flux<DataBuffer>>any())).thenReturn(fromIterableResult);
    Flux<DataBuffer> body = Flux.fromIterable(new ArrayList<>());

    // Act and Assert
    FirstStep<DataBuffer> createResult = StepVerifier.create(legacyEndpointConverter.convert(body));
    createResult.expectComplete().verify();
    verify(function).apply(isA(Flux.class));
  }
}
