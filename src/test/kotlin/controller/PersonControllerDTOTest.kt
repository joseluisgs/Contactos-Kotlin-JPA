package controller

import hibernate.HibernateController
import model.Address
import model.Person
import model.PhoneNumber
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import java.time.Instant
import javax.transaction.Transactional

@DisplayName("Suite Test PersonController Output DTO")
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // Para el Beforeall
@Transactional
class PersonControllerDTOTest{
    // Mi variable de prueba
    private lateinit var p1: Person
    private lateinit var p2: Person
    private lateinit var p3: Person

    private fun initMyData() {
        p1 = Person(
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

        p2 = Person(
            "Pepe ${Instant.now()}",
            "Pepe${Instant.now()}@Pepe.es",
            setOf(
                PhoneNumber("666-555-0171")
            ).toMutableSet(), // Son mutables porque los quiero cambiar
            setOf(
                Address("Zazaquemada${Instant.now()}", "28916", "Leganes")
            ).toMutableSet()
        )

        p3 = Person(
            "NoExiste${Instant.now()}",
            null,
            null,
            null
        )
        // Le añado a cada dirección quien soy yo, por bidireccionalidad. En números no, porque no quiero eso.
        p1.myAddress?.forEach {
            it.person = p1
        }
        p2.myAddress?.forEach {
            it.person = p2
        }

        p3.id = -1
    }

    @BeforeAll
    fun setUp() {
        initMyData()
        // Limpio la base de datos para estar lista
        HibernateController.truncateAllTables()
    }

    @DisplayName("True is True")
    @Order(1)
    @Test
    fun trueIsTrue(){
        assertTrue(true)
    }

    @DisplayName("Controller FindAll Empty Test")
    @Order(2)
    @Test
    fun controllerFindAllEmptyTest(){
        val res = PersonController.findAll()
        assertTrue(res?.isEmpty() ?: false)

    }

    @Test
    @DisplayName("Controller Save Test")
    @Order(3)
    fun saveTest() {
        // Salvamos
        val res1 = PersonController.save(p1)
        val res2 = PersonController.save(p2)
        // comprobamos que se ha salvado
        assertAll(
            // person 01
            { assertEquals(p1.name, res1?.name) },
            { assertEquals(p1.email, res1?.email) },
            { assertEquals(p1.myPhoneNumbers?.size, res1?.telephone?.size) },
            { assertEquals(p1.myAddress?.size, res1?.address?.size) },
            // person 02
            { assertEquals(p2.name, res2?.name) },
            { assertEquals(p2.email, res2?.email) },
            { assertEquals(p2.myPhoneNumbers?.size, res2?.telephone?.size) },
            { assertEquals(p2.myAddress?.size, res2?.address?.size) }
        )
    }


    @Test
    @DisplayName("Repository FindAll With Persons Test")
    @Order(4)
    fun findAllWithPersonsTest() {
        val res = PersonController.findAll()
        assertAll(
            { assertTrue(res?.isNotEmpty() ?: false) },
            { assertEquals(p1.name, res?.get(0)?.name) },
            { assertEquals(p1.email, res?.get(0)?.email) },
            { assertEquals(p1.myPhoneNumbers?.size, res?.get(0)?.telephone?.size) },
            { assertEquals(p1.myAddress?.size, res?.get(0)?.address?.size) },
            { assertEquals(p2.name, res?.get(1)?.name) },
            { assertEquals(p2.email, res?.get(1)?.email) },
            { assertEquals(p2.myPhoneNumbers?.size, res?.get(1)?.telephone?.size) },
            { assertEquals(p2.myAddress?.size, res?.get(1)?.address?.size) },
        )
    }

    @Test
    @DisplayName("Controller FindById Test")
    @Order(5)
    fun findByIdTest() {
        val res = PersonController.findById(p1.id)

        assertAll(
            { assertEquals(p1.name, res?.name) },
            { assertEquals(p1.email, res?.email) },
            { assertEquals(p1.myPhoneNumbers?.size, res?.telephone?.size) },
            { assertEquals(p1.myAddress?.size, res?.address?.size) },
        )
    }

    @Test
    @DisplayName("Controller Update Test")
    @Order(6)
    fun updateTest() {
        // Update
        p1.email = "modificado${Instant.now()}@modificado.com"
        // Otra dirección
        val direccion = Address("Calle${Instant.now()}", "28000", "Madrid", p1)
        p1.myAddress?.add(direccion)

        val res = PersonController.update(p1)
        assertAll(
            { assertEquals(p1.name, res?.name) },
            { assertEquals(p1.email, res?.email) },
            { assertEquals(p1.myPhoneNumbers?.size, res?.telephone?.size) },
            { assertEquals(p1.myAddress?.size, res?.address?.size) },
        )
    }

    @Test
    @DisplayName("Controller Find Address of a Person Test")
    @Order(7)
    fun findAddressOfPersonTest() {
        val res = PersonController.findAddress(p2)
        assertAll(
            { assertEquals(1, res?.size) }

        )
    }

    @Test
    @DisplayName("Controller Delete Test")
    @Order(8)
    fun deleteTest() {
        val res = PersonController.delete(p2)
        assertAll(
            { assertEquals(p2.name, res?.name) },
            { assertEquals(p2.email, res?.email) },
            { assertEquals(p2.myPhoneNumbers?.size, res?.telephone?.size) },
            { assertEquals(p2.myAddress?.size, res?.address?.size) },
        )
    }

}