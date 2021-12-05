package mapper

import model.Address
import model.Person
import model.PhoneNumber
import org.junit.jupiter.api.*
import java.time.Instant

@DisplayName("Suite Test PersonMapper")
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // Para el Beforeall
class PersonMapperTest {

    val mapper = PersonMapper()

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

    @BeforeAll
    fun setUp() {
        // Le añado a cada dirección quien soy yo, por bidireccionalidad. En números no, porque no quiero eso.
        p1.myAddress?.forEach {
            it.person = p1
        }
    }

    @DisplayName("Test Object Person to DTO")
    @Order(1)
    @Test
    fun testObjectToDTOTest() {
        val res = mapper.toDTO(p1)
        assertAll(
            { Assertions.assertEquals(p1.name, res.name) },
            { Assertions.assertEquals(p1.email, res.email) },
            { Assertions.assertEquals(p1.myPhoneNumbers?.size, res.telephone?.size) },
            { Assertions.assertEquals(p1.myAddress?.size, res.address?.size) }
        )
    }

    @DisplayName("Test List Person to DTO")
    @Order(2)
    @Test
    fun testListToDTOTest() {
        val list = listOf(p1)
        val res = mapper.toDTO(list)
        assertAll(
            { Assertions.assertEquals(list.size, res.size) },
            { Assertions.assertEquals(p1.name, res[0].name) },
            { Assertions.assertEquals(p1.email, res[0].email) },
            { Assertions.assertEquals(p1.myPhoneNumbers?.size, res[0].telephone?.size) },
            { Assertions.assertEquals(p1.myAddress?.size, res[0].address?.size) }
        )
    }

    @DisplayName("Test Object DTO from DTO")
    @Order(3)
    @Test
    fun testDTOFromDTOTest() {
        val dto = mapper.toDTO(p1)
        val res = mapper.fromDTO(dto)
        assertAll(
            { Assertions.assertEquals(p1.name, res.name) },
            { Assertions.assertEquals(p1.email, res.email) },
            { Assertions.assertEquals(p1.myPhoneNumbers?.size, res.myPhoneNumbers?.size) },
            { Assertions.assertEquals(p1.myAddress?.size, res.myAddress?.size) }
        )
    }

    @DisplayName("Test List DTO from DTO")
    @Order(4)
    @Test
    fun testListDTOFromDTOTest() {
        val list = listOf(p1)
        val dto = mapper.toDTO(list)
        val res = mapper.fromDTO(dto)
        assertAll(
            { Assertions.assertEquals(list.size, res.size) },
            { Assertions.assertEquals(p1.name, res[0].name) },
            { Assertions.assertEquals(p1.email, res[0].email) },
            { Assertions.assertEquals(p1.myPhoneNumbers?.size, res[0].myPhoneNumbers?.size) },
            { Assertions.assertEquals(p1.myAddress?.size, res[0].myAddress?.size) }
        )
    }
}