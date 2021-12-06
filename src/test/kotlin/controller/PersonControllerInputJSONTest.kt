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
class PersonControllerInputJSONTest{
    // Mi variable de prueba
    private lateinit var personStringJson: String
    private lateinit var personJson: JSONObject

    private fun initMyData() {
        personStringJson = """
        {
          "name": "Json${Instant.now()}",
          "email": "Json${Instant.now()}@jsondomain.es",
          "telephone": [
            {
              "number": "666-555-9999"
            }
          ],
          "address": [
            {
              "street": "Json Street ${Instant.now()}",
              "postalCode": "28080",
              "city": "Leganes"
            }
          ]
        }
        """.trimIndent()
        personJson = JSONObject(personStringJson)
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

    @DisplayName("Controller FindAll Input JSON Empty Test")
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
    @DisplayName("Controller Save Input JSON Test")
    @Order(3)
    fun saveTest() {
        // Salvamos
        val res = PersonController.saveInputJson(personStringJson)

        val resJson = JSONObject(res)

        val status = resJson.getInt("status")
        val data = resJson.getJSONObject("data")

        // comprobamos que se ha salvado
        assertAll(
            { assertEquals(201, status) },
            { assertEquals(personJson.getString("name"), data.getString("name")) },
            { assertEquals(personJson.getString("email"),data.getString("email")) },
        )
    }


    @Test
    @DisplayName("Repository FindAll With Persons Input JSON Test")
    @Order(4)
    fun findAllWithPersonsTest() {
        val res = PersonController.findAllJson()

        val resJson = JSONObject(res)

        val status = resJson.getInt("status")
        val data = resJson.getJSONArray("data")

        assertAll(
            { assertEquals(200, status) },
            { assertEquals(1, data.length()) },
            { assertEquals(personJson.getString("name"), data.getJSONObject(0).getString("name")) },
            { assertEquals(personJson.getString("email"), data.getJSONObject(0).getString("email")) }
        )
    }

    @Test
    @DisplayName("Controller FindById Input JSON Test")
    @Order(5)
    fun findByIdTest() {
        val id = """
            {
              "id": 1
            }
        """.trimIndent()
        val res = PersonController.findByIdInputJson(id)

        val resJson = JSONObject(res)

        val status = resJson.getInt("status")
        val data = resJson.getJSONObject("data")

        assertAll(
            { assertEquals(200, status) },
            { assertEquals(personJson.getString("name"), data.getString("name")) },
            { assertEquals(personJson.getString("email"),data.getString("email")) },

        )
    }

    @Test
    @DisplayName("Controller Find Address of a Person Input JSON Test")
    @Order(6)
    fun findAddressOfPersonTest() {
        val res = PersonController.findAddressInputJson(personStringJson)

        val resJson = JSONObject(res)

        val status = resJson.getInt("status")
        val data = resJson.getJSONArray("data")

        assertAll(
            { assertEquals(200, status) },
        )
    }

     @Test
     @DisplayName("Controller Update Input JSON Test")
     @Order(7)
     fun updateTest() {
         val update = """
            {
              "id": 1,
              "name": "ModJson${Instant.now()}",
              "email": "Json${Instant.now()}@jsondomain.es",
              "telephone": [
                {
                  "id": 1,
                  "number": "666-555-9999"
                },
                {
                  "id": 2,
                  "number": "666-555-8888"
                }
              ]
            }
            """.trimIndent()

         val res = PersonController.updateInputJson(update)

         val resJson = JSONObject(res)
         val status = resJson.getInt("status")
         val data = resJson.getJSONObject("data")

         assertAll(
             { assertEquals(200, status) }
         )
     }

    @Test
    @DisplayName("Controller Delete Input JSON Test")
    @Order(8)
    fun deleteTest() {
        val personDeleteJson = """
        {
          "id": 1,
          "name": "Json${Instant.now()}",
          "email": "Json${Instant.now()}@jsondomain.es"
        }
        """.trimIndent()
        val res =  PersonController.deleteInputJson(personDeleteJson)

        val resJson = JSONObject(res)
        val status = resJson.getInt("status")
        //val data = resJson.getJSONObject("data")

        assertAll(
            { assertEquals(200, status) }
        )
    }
}