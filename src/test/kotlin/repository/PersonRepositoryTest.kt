import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

@DisplayName("Suite Test PersonRepository")
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class BinaryTest {

    @Test
    @DisplayName("True is True")
    @Order(1)
    fun trueIsTrue() {
      assertTrue(true)
    }

}