package controller

import hibernate.HibernateController
import model.Address
import model.Person
import model.PhoneNumber
import org.json.JSONObject
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import java.time.Instant
import javax.transaction.Transactional

@DisplayName("Suite Test PersonController Output JSON")
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // Para el Beforeall
@Transactional
class PersonControllerJSONTest{
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

    @DisplayName("Controller FindAll Output JSON Empty Test")
    @Order(2)
    @Test
    fun controllerFindAllEmptyTest(){
        val res = PersonController.findAllJson()
        val resJson = JSONObject(res)

        val status = resJson.getInt("status")
        val data = resJson.getJSONArray("data")

        assertAll(
            { assertEquals(200, status) },
            { assertEquals(0, data.length()) }
        )
    }

    @Test
    @DisplayName("Controller Save Output JSON Test")
    @Order(3)
    fun saveTest() {
        // Salvamos
        val res = PersonController.saveJson(p1)

        val resJson = JSONObject(res)

        val status = resJson.getInt("status")
        val data = resJson.getJSONObject("data")

        // comprobamos que se ha salvado
        assertAll(
            { assertEquals(201, status) },
            { assertEquals(p1.name, data.getString("name")) },
            { assertEquals(p1.email,data.getString("email")) },
            { assertEquals(p1.myPhoneNumbers?.size, data.getJSONArray("telephone").length()) },
            { assertEquals(p1.myAddress?.size, data.getJSONArray("address").length()) },
        )
        // Insertamos otro dato
        PersonController.saveJson(p2)
    }


    @Test
    @DisplayName("Repository FindAll With Persons Output JSON Test")
    @Order(4)
    fun findAllWithPersonsTest() {
        val res = PersonController.findAllJson()

        val resJson = JSONObject(res)

        val status = resJson.getInt("status")
        val data = resJson.getJSONArray("data")

        assertAll(
            { assertEquals(200, status) },
            { assertEquals(2, data.length()) },
            { assertEquals(p1.name, data.getJSONObject(0).getString("name")) },
            { assertEquals(p1.email, data.getJSONObject(0).getString("email")) },
            { assertEquals(p1.myPhoneNumbers?.size, data.getJSONObject(0).getJSONArray("telephone").length()) },
            { assertEquals(p1.myAddress?.size, data.getJSONObject(0).getJSONArray("address").length()) },
            { assertEquals(p2.name, data.getJSONObject(1).getString("name")) },
            { assertEquals(p2.email, data.getJSONObject(1).getString("email")) },
            { assertEquals(p2.myPhoneNumbers?.size, data.getJSONObject(1).getJSONArray("telephone").length()) },
            { assertEquals(p2.myAddress?.size, data.getJSONObject(1).getJSONArray("address").length()) },
        )
    }


    @Test
    @DisplayName("Controller FindById Output JSON Test")
    @Order(5)
    fun findByIdTest() {
        val res = PersonController.findByIdJson(p1.id)

        val resJson = JSONObject(res)

        val status = resJson.getInt("status")
        val data = resJson.getJSONObject("data")

        assertAll(
            { assertEquals(200, status) },
            { assertEquals(p1.name, data.getString("name")) },
            { assertEquals(p1.email,data.getString("email")) },
            { assertEquals(p1.myPhoneNumbers?.size, data.getJSONArray("telephone").length()) },
            { assertEquals(p1.myAddress?.size, data.getJSONArray("address").length()) }
        )
    }


     @Test
     @DisplayName("Controller Update Output JSON Test")
     @Order(6)
     fun updateTest() {
         // Update
         p1.email = "modificado${Instant.now()}@modificado.com"
         // Otra dirección
         val direccion = Address("Calle${Instant.now()}", "28000", "Madrid", p1)
         p1.myAddress?.add(direccion)

         val res = PersonController.updateJson(p1)

         val resJson = JSONObject(res)

         val status = resJson.getInt("status")
         val data = resJson.getJSONObject("data")

         assertAll(
             { assertEquals(200, status) },
             { assertEquals(p1.name, data.getString("name")) },
             { assertEquals(p1.email,data.getString("email")) },
             { assertEquals(p1.myPhoneNumbers?.size, data.getJSONArray("telephone").length()) },
             { assertEquals(p1.myAddress?.size, data.getJSONArray("address").length()) }
         )
     }



    @Test
    @DisplayName("Controller Find Address of a Person Output JSON Test")
    @Order(7)
    fun findAddressOfPersonTest() {
        val res = PersonController.findAddressJson(p2)

        val resJson = JSONObject(res)

        val status = resJson.getInt("status")
        val data = resJson.getJSONArray("data")

        assertAll(
            { assertEquals(200, status) },
            { assertEquals(1, data.length()) },
        )
    }

    @Test
    @DisplayName("Controller Delete Output JSON Test")
    @Order(8)
    fun deleteTest() {
        val res = PersonController.deleteJson(p2)
        val resJson = JSONObject(res)

        val status = resJson.getInt("status")
        val data = resJson.getJSONObject("data")

        assertAll(
            { assertEquals(200, status) },
            { assertEquals(p2.name, data.getString("name")) },
            { assertEquals(p2.email,data.getString("email")) },
            { assertEquals(p2.myPhoneNumbers?.size, data.getJSONArray("telephone").length()) },
            { assertEquals(p2.myAddress?.size, data.getJSONArray("address").length()) }
        )
    }

}