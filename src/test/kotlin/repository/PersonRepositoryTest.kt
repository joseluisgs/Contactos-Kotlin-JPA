import model.Address
import model.Person
import model.PhoneNumber
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import repository.PersonRepository
import java.sql.SQLException
import java.time.Instant


@DisplayName("Suite Test PersonRepository")
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // Para el Beforeall
class BinaryTest {
    // Mi repository
    val repository = PersonRepository()

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

    val p2 = Person(
        "Pepe ${Instant.now()}",
        "Pepe${Instant.now()}@Pepe.es",
        setOf(
            PhoneNumber("666-555-0171")
        ).toMutableSet(), // Son mutables porque los quiero cambiar
        setOf(
            Address("Zazaquemada${Instant.now()}", "28916", "Leganes")
        ).toMutableSet()
    )

    val p3 = Person(
        "NoExiste${Instant.now()}",
        null,
        null,
        null
    )

    @BeforeAll
    fun setUp() {
        // Le añado a cada dirección quien soy yo, por bidireccionalidad. En números no, porque no quiero eso.
        p1.myAddress?.forEach {
            it.person = p1
        }
        p2.myAddress?.forEach {
            it.person = p2
        }

        p3.id = -1
    }

    @Test
    @DisplayName("True is True")
    @Order(1)
    fun trueIsTrue() {
        // Estructura de un test
        // Arrange
        // Act
        // Assert

        assertTrue(true)
    }

    @Test
    @DisplayName("Repository FindAll Empty Test")
    @Order(2)
    fun findAllEmptyTest() {
        val res = repository.findAll()
        assertTrue(res.isEmpty())
    }

    @Test
    @DisplayName("Repository Save Test")
    @Order(3)
    fun saveTest() {
        // Salvamos
        val res1 = repository.save(p1)
        val res2 = repository.save(p2)
        // comprobamos que se ha salvado
        assertAll(
            // person 01
            { assertEquals(p1.name, res1.name) },
            { assertEquals(p1.email, res1.email) },
            { assertEquals(p1.myPhoneNumbers?.size, res1.myPhoneNumbers?.size) },
            { assertEquals(p1.myAddress?.size, res1.myAddress?.size) },
            // person 02
            { assertEquals(p2.name, res2.name) },
            { assertEquals(p2.email, res2.email) },
            { assertEquals(p2.myPhoneNumbers?.size, res2.myPhoneNumbers?.size) },
            { assertEquals(p2.myAddress?.size, res2.myAddress?.size) }
        )
    }


    @Test
    @DisplayName("Repository FindAll With Persons Test")
    @Order(4)
    fun findAllWithPersonsTest() {
        val res = repository.findAll()
        assertAll(
            { assertEquals(2, res.size) },
            { assertEquals(p1.name, res[0].name) },
            { assertEquals(p1.email, res[0].email) },
            { assertEquals(p1.myPhoneNumbers?.size, res[0].myPhoneNumbers?.size) },
            { assertEquals(p1.myAddress?.size, res[0].myAddress?.size) },
            { assertEquals(p2.name, res[1].name) },
            { assertEquals(p2.email, res[1].email) },
            { assertEquals(p2.myPhoneNumbers?.size, res[1].myPhoneNumbers?.size) },
            { assertEquals(p2.myAddress?.size, res[1].myAddress?.size) }
        )
    }

    @Test
    @DisplayName("Repository FindById Test")
    @Order(5)
    fun findByIdTest() {
        val res = repository.findById(p1.id)

        assertAll(
            { assertEquals(p1.name, res.name) },
            { assertEquals(p1.email, res.email) },
            { assertEquals(p1.myPhoneNumbers?.size, res.myPhoneNumbers?.size) },
            { assertEquals(p1.myAddress?.size, res.myAddress?.size) }
        )
    }

    @Test
    @DisplayName("Repository Update Test")
    @Order(6)
    fun updateTest() {
        // Update
        p1.email = "modificado${Instant.now()}@modificado.com"
        // Otra dirección
        val direccion = Address("Calle${Instant.now()}", "28000", "Madrid", p1)
        p1.myAddress?.add(direccion)

        val res = repository.update(p1)
        assertAll(
            { assertEquals(p1.name, res.name) },
            { assertEquals(p1.email, res.email) },
            { assertEquals(p1.myPhoneNumbers?.size, res.myPhoneNumbers?.size) },
            { assertEquals(p1.myAddress?.size, res.myAddress?.size) },
            { res.myAddress?.contains(direccion)?.let { assertTrue(it) } }
        )
    }

    @Test
    @DisplayName("Repository Find Address of a Person Test")
    @Order(7)
    fun findAddressOfPersonTest() {
        val res = repository.findAddress(p2)
        assertAll(
            { assertEquals(1, res.size) }

        )
    }

    @Test
    @DisplayName("Repository Delete Test")
    @Order(8)
    fun deleteTest() {
        val res = repository.delete(p2)
        assertAll(
            { assertEquals(p2.name, res.name) },
            { assertEquals(p2.email, res.email) },
            { assertEquals(p2.myPhoneNumbers?.size, res.myPhoneNumbers?.size) },
            { assertEquals(p2.myAddress?.size, res.myAddress?.size) }
        )
    }

    @Test
    @DisplayName("Repository Save Exception Test")
    @Order(9)
    fun saveExceptionTest() {
        // Excepción
        val ex = assertThrows<SQLException> {
            repository.save(p3)
        }.message
        ex?.contains("Error PersonRepository")?.let { assertTrue(it) }
    }

    @Test
    @DisplayName("Repository FindById Exception Test")
    @Order(10)
    fun findByIdExceptionTest() {
        // Excepción
        val ex = assertThrows<SQLException> {
            repository.findById(p3.id)
        }.message
        // La otra
        // val thrown = assertThrows(SQLException::class.java, { repository.findById(-11L) }, "SQL error was expected")
        //assertEquals("Error PersonRepository findById no existe Person con ID: -11", ex)
        ex?.contains("Error PersonRepository")?.let { assertTrue(it) }
    }

    @Test
    @DisplayName("Repository Update Exception Test")
    @Order(11)
    fun updateExceptionTest() {
        // Excepción
        p1.id = -11L
        val ex = assertThrows<SQLException> {
            repository.update(p1)
        }.message
        ex?.contains("Error PersonRepository")?.let { assertTrue(it) }
    }

    @Test
    @DisplayName("Repository Delete Exception Test")
    @Order(12)
    fun deleteExceptionTest() {
        // Excepción
        val ex = assertThrows<SQLException> {
            repository.delete(p3)
        }.message
        ex?.contains("Error PersonRepository")?.let { assertTrue(it) }
    }

   /* @Test
    @DisplayName("Repository findAddres Exception Test")
    @Order(13)
    fun findAddressExceptionTest() {
        // Excepción
        val ex = assertThrows<SQLException> {
            repository.findAddress(p1)
        }.message
        ex?.contains("Error PersonRepository")?.let { assertTrue(it) }
    }*/
}