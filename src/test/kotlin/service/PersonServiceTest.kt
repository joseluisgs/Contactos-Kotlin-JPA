package service

import dto.PersonDTO
import mapper.PersonMapper
import model.Address
import model.Person
import model.PhoneNumber
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.mockito.Mock
import org.mockito.Mockito
import repository.PersonRepository
import java.sql.SQLException
import java.time.Instant

@DisplayName("Suite Test PersonService")
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // Para el Beforeall
class PersonServiceTest {

    // Dependencia
    private lateinit var personRepository: PersonRepository
    // SUT: System Under Test
    private lateinit var personService: PersonService

    // Mi variable de prueba
    val p1 = Person(
        "Juan${Instant.now()}",
        "juan${Instant.now()}@juan.es",
        setOf(
            PhoneNumber("202-555-0171"),
            PhoneNumber("202-555-0102")
        ).toMutableSet(), // Son mutables porque los quiero cambiar
        setOf(
            Address("Luis Vives${Instant.now()}", "28819", "Leganes")
        ).toMutableSet(), //
    )

    // Mapper
    //val mapper = PersonMapper()


    @BeforeAll
    fun setUp() {
        //Creamos el mock... Sintáxis mock con Mockito
        personRepository = Mockito.mock(PersonRepository::class.java)
        // Creamos el SUT con su mock
        personService = PersonService(personRepository)



        // Le añado a cada dirección quien soy yo, por bidireccionalidad. En números no, porque no quiero eso.
        p1.id = 1
        p1.myAddress?.forEach {
            it.person = p1
        }

    }

    @Test
    @DisplayName("True is True")
    @Order(1)
    fun trueIsTrue() {
        // Estructura de un test mockeado. Suponemos el escenario
        // Given
        // When
        // Then
        assertTrue(true)
    }

    @Test
    @DisplayName("Service FindAll Empty Test")
    @Order(2)
    fun findAllEmptyTest() {
        // Given: Dado el escenario Mockeamos el resultado
        Mockito.`when`(personRepository.findAll())
            .thenReturn(emptyList())
        // When: Actuamos
        val res=this.personService.findAll()
        // Then, assert de JUnit
        assertTrue(res.isEmpty())

        // Extra, comprobamos que se ha llamado dicho método del mock (repsositorio)
        Mockito.verify(personRepository, Mockito.atLeastOnce()).findAll()
    }

    @Test
    @DisplayName("Service FindAll")
    @Order(3)
    fun findAllTest() {
        val list = listOf(p1)
        // Given: Dado el escenario Mockeamos el resultado
        Mockito.`when`(personRepository.findAll())
            .thenReturn(list)
        // When: Actuamos
        val res=this.personService.findAll()
        // Then, assert de JUnit
        assertAll(
            { assertEquals(1, res.size) },
            { assertEquals(p1.name, res[0].name) },
            { assertEquals(p1.email, res[0].email) },
            { assertEquals(p1.myPhoneNumbers?.size, res[0].telephone?.size) },
            { assertEquals(p1.myAddress?.size, res[0].address?.size) },
        )

        // Extra, comprobamos que se ha llamado dicho método del mock (repsositorio)
        Mockito.verify(personRepository, Mockito.atLeastOnce()).findAll()
    }

    @Test
    @DisplayName("Service FindbyId")
    @Order(4)
    fun findbyIdTest() {
        // Given: Dado el escenario Mockeamos el resultado
        Mockito.`when`(personRepository.findById(p1.id))
            .thenReturn(p1)
        // When: Actuamos
        val res=this.personService.findById(p1.id)
        // Then, assert de JUnit
        assertAll(
            { assertEquals(p1.name, res.name) },
            { assertEquals(p1.email, res.email) },
            { assertEquals(p1.myPhoneNumbers?.size, res.telephone?.size) },
            { assertEquals(p1.myAddress?.size, res.address?.size) },
        )

        // Extra, comprobamos que se ha llamado dicho método del mock (repsositorio)
        Mockito.verify(personRepository, Mockito.atLeastOnce()).findById(p1.id)
    }

    @Test
    @DisplayName("Service Save Test")
    @Order(5)
    fun saveTest() {
        // Given: Dado el escenario Mockeamos el resultado
        Mockito.`when`(personRepository.save(p1))
            .thenReturn(p1)
        // When: Actuamos
        val res=this.personService.save(p1)
        // Then, assert de JUnit
        assertAll(
            { assertEquals(p1.name, res.name) },
            { assertEquals(p1.email, res.email) },
            { assertEquals(p1.myPhoneNumbers?.size, res.telephone?.size) },
            { assertEquals(p1.myAddress?.size, res.address?.size) },
        )

        // Extra, comprobamos que se ha llamado dicho método del mock (repsositorio)
        Mockito.verify(personRepository, Mockito.atLeastOnce()).save(p1)
    }

    @Test
    @DisplayName("Service Update Test")
    @Order(6)
    fun updateTest() {
        // Given: Dado el escenario Mockeamos el resultado
        Mockito.`when`(personRepository.update(p1))
            .thenReturn(p1)
        // When: Actuamos
        val res=this.personService.update(p1)
        // Then, assert de JUnit
        assertAll(
            { assertEquals(p1.name, res.name) },
            { assertEquals(p1.email, res.email) },
            { assertEquals(p1.myPhoneNumbers?.size, res.telephone?.size) },
            { assertEquals(p1.myAddress?.size, res.address?.size) },
        )

        // Extra, comprobamos que se ha llamado dicho método del mock (repsositorio)
        Mockito.verify(personRepository, Mockito.atLeastOnce()).update(p1)
    }

    @Test
    @DisplayName("Service Delete Test")
    @Order(7)
    fun deleteTest() {
        // Given: Dado el escenario Mockeamos el resultado
        Mockito.`when`(personRepository.delete(p1))
            .thenReturn(p1)
        // When: Actuamos
        val res=this.personService.delete(p1)
        // Then, assert de JUnit
        assertAll(
            { assertEquals(p1.name, res.name) },
            { assertEquals(p1.email, res.email) },
            { assertEquals(p1.myPhoneNumbers?.size, res.telephone?.size) },
            { assertEquals(p1.myAddress?.size, res.address?.size) },
        )

        // Extra, comprobamos que se ha llamado dicho método del mock (repsositorio)
        Mockito.verify(personRepository, Mockito.atLeastOnce()).delete(p1)
    }

    @Test
    @DisplayName("Service Find Address of a Person Test")
    @Order(8)
    fun findAddressOfPersonTest() {
        Mockito.`when`(personRepository.findAddress(p1))
            .thenReturn(p1.myAddress?.toList())

        val res = personService.findAddress(p1)

        assertAll(
            { assertEquals(1, res.size) }

        )

        // Extra, comprobamos que se ha llamado dicho método del mock (repsositorio)
        Mockito.verify(personRepository, Mockito.atLeastOnce()).findAddress(p1)
    }

    @Test
    @DisplayName("Service FindAll Exception Test")
    @Order(9)
    fun findAllExceptionTest() {
        // Given: Dado el escenario Mockeamos el resultado
        Mockito.`when`(personRepository.findAll())
            .thenThrow(SQLException("Error PersonRepository FindAll"))

        // Extra, comprobamos que se ha llamado dicho método del mock (repsositorio)
        Mockito.verify(personRepository, Mockito.atLeastOnce()).findAll()

        // When Excepción
        val ex = assertThrows<SQLException> {
            personService.findAll()
        }.message
        ex?.contains("Error PersonRepository")?.let { assertTrue(it) }
    }

    @Test
    @DisplayName("Service FindById Exception Test")
    @Order(10)
    fun findByIdExceptionTest() {
        // Given: Dado el escenario Mockeamos el resultado
        Mockito.`when`(personRepository.findById(p1.id))
            .thenThrow(SQLException("Error PersonRepository FindById"))

        // Extra, comprobamos que se ha llamado dicho método del mock (repsositorio)
        Mockito.verify(personRepository, Mockito.atLeastOnce()).findById(p1.id)

        // When Excepción
        val ex = assertThrows<SQLException> {
            personService.findById(p1.id)
        }.message
        ex?.contains("Error PersonRepository")?.let { assertTrue(it) }
    }

    @Test
    @DisplayName("Service Save Exception Test")
    @Order(11)
    fun saveExceptionTest() {
        // Given: Dado el escenario Mockeamos el resultado
        Mockito.`when`(personRepository.save(p1))
            .thenThrow(SQLException("Error PersonRepository save"))

        // Extra, comprobamos que se ha llamado dicho método del mock (repsositorio)
        Mockito.verify(personRepository, Mockito.atLeastOnce()).save(p1)

        // When Excepción
        val ex = assertThrows<SQLException> {
            personService.save(p1)
        }.message
        ex?.contains("Error PersonRepository")?.let { assertTrue(it) }
    }

    @Test
    @DisplayName("Service Update Exception Test")
    @Order(12)
    fun updateExceptionTest() {
        // Given: Dado el escenario Mockeamos el resultado
        Mockito.`when`(personRepository.update(p1))
            .thenThrow(SQLException("Error PersonRepository update"))

        // Extra, comprobamos que se ha llamado dicho método del mock (repsositorio)
        Mockito.verify(personRepository, Mockito.atLeastOnce()).update(p1)

        // When Excepción
        val ex = assertThrows<SQLException> {
            personService.update(p1)
        }.message
        ex?.contains("Error PersonRepository")?.let { assertTrue(it) }
    }

    @Test
    @DisplayName("Service Delete Exception Test")
    @Order(13)
    fun deleteExceptionTest() {
        // Given: Dado el escenario Mockeamos el resultado
        Mockito.`when`(personRepository.delete(p1))
            .thenThrow(SQLException("Error PersonRepository delete"))

        // Extra, comprobamos que se ha llamado dicho método del mock (repsositorio)
        Mockito.verify(personRepository, Mockito.atLeastOnce()).delete(p1)

        // When Excepción
        val ex = assertThrows<SQLException> {
            personService.delete(p1)
        }.message
        ex?.contains("Error PersonRepository")?.let { assertTrue(it) }
    }

    @Test
    @DisplayName("Service findAddress Exception Test")
    @Order(15)
    fun findAddressException() {
        // Given: Dado el escenario Mockeamos el resultado
        Mockito.`when`(personRepository.findAddress(p1))
            .thenThrow(SQLException("Error PersonRepository finAddress"))

        // Extra, comprobamos que se ha llamado dicho método del mock (repsositorio)
        Mockito.verify(personRepository, Mockito.atLeastOnce()).findAddress(p1)

        // When Excepción
        val ex = assertThrows<SQLException> {
            personService.findAddress(p1)
        }.message
        ex?.contains("Error PersonRepository")?.let { assertTrue(it) }
    }
}