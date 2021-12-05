package service

import org.junit.jupiter.api.*
import org.mockito.Mockito.mock
import repository.PersonRepository

@DisplayName("Suite Test PersonRepository")
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // Para el Beforeall
class PersonServiceTest {

    // Dependencia
    private lateinit var personRepository: PersonRepository
    // SUT: System Under Test
    private lateinit var personService: PersonService

    @BeforeAll
    fun setUp() {
        // Creamos el mock... Sintáxis mock con Mockito y
        personRepository = mock(PersonRepository::class.java)
    }

    @Test
    @DisplayName("True is True")
    @Order(1)
    fun trueIsTrue() {
        Assertions.assertTrue(true)
    }
}