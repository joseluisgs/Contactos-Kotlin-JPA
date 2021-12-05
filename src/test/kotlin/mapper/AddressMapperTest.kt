package mapper

import model.Address
import model.Person
import model.PhoneNumber
import java.time.Instant
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals

@DisplayName("Suite Test AddressMapper")
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // Para el Beforeall
class AddressMapperTest {

    private val mapper = AddressMapper()

    private val p1 = Person(
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

    @BeforeAll
    fun setUp() {
        // Le añado a cada dirección quien soy yo, por bidireccionalidad. En números no, porque no quiero eso.
        p1.myAddress?.forEach {
            it.person = p1
        }
    }

    @DisplayName("Test Object Address to DTO")
    @Order(1)
    @Test
    fun testObjectToDTOTest() {
        val address = p1.myAddress!!.first()
        val res = mapper.toDTO(address)

        assertAll(
            { assertEquals(address?.street, res.street) },
            { assertEquals(address?.city, res.city) },
            { assertEquals(address?.postalCode, res.postalCode) },
        )
    }

   @DisplayName("Test List Address to DTO")
    @Order(2)
    @Test
    fun testListToDTOTest() {
       val list = p1.myAddress?.toList()!!
       val address = p1.myAddress!!.first()

        val res = mapper.toDTO(list)
        assertAll(
            { assertEquals(list.size, res.size) },
            { assertEquals(address.street, res[0].street) },
            { assertEquals(address.city, res[0].city) },
            { assertEquals(address.postalCode, res[0].postalCode) },
        )
    }

    @DisplayName("Test Address DTO from DTO")
    @Order(3)
    @Test
    fun testDTOFromDTOTest() {
        val address = p1.myAddress?.first()!!
        val dto = mapper.toDTO(address)
        val res = mapper.fromDTO(dto)
        assertAll(
            { assertEquals(address.street, res.street) },
            { assertEquals(address.city, res.city) },
            { assertEquals(address.postalCode, res.postalCode) },
        )
    }

    @DisplayName("Test List DTO from DTO")
    @Order(4)
    @Test
    fun testListDTOFromDTOTest() {
        val address = p1.myAddress?.first()!!
        val list = listOf(address)
        val dto = mapper.toDTO(list)
        val res = mapper.fromDTO(dto)
        assertAll(
            { assertEquals(list.size, res.size) },
            { assertEquals(address.street, res[0].street) },
            { assertEquals(address.city, res[0].city) },
            { assertEquals(address.postalCode, res[0].postalCode) },
        )
    }
}