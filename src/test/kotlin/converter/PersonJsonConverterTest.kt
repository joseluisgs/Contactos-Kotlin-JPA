package converter

import dto.PersonDTO
import mapper.PersonMapper
import model.Person
import org.json.JSONArray
import org.json.JSONObject
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Suite Test PersonJsonConverter")
//@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
//@TestInstance(TestInstance.Lifecycle.PER_CLASS) // Para el Beforeall
class PersonJsonConverterTest {

    val mapper = PersonMapper()

    @DisplayName("Test PersonDTO Object to Json")
    @Test
    fun objectToJson() {
        val p = Person(
            "Juan",
            "juan@juan.es",
            null,
            null
        )
        val jsonString = """
            {
              "id": 0,
              "name": "Juan",
              "email": "juan@juan.es"
            }
        """.trimIndent()
        val resString = PersonJsonConverter.toJson(mapper.toDTO(p))
        val json = JSONObject(jsonString).toMap()
        val res = JSONObject(resString).toMap()

        assertEquals(json, res)
    }

    @DisplayName("Test List PersonDTO to Json")
    @Test
    fun listToJson() {
        val p = Person(
            "Juan",
            "juan@juan.es",
            null,
            null
        )
        val list = listOf(p)
        val jsonString = """
            {
              "id": 0,
              "name": "Juan",
              "email": "juan@juan.es"
            }
        """.trimIndent()
        val resString = PersonJsonConverter.toJson(mapper.toDTO(list))
        val json = JSONObject(jsonString).toMap()
        val resArray = JSONArray(resString)
        val res = resArray.getJSONObject(0).toMap()

        assertEquals(json, res)
    }

    @DisplayName("Test json to PersonDTO")
    @Test
    fun jsonToObject() {
        val jsonString = """
            {
              "id": 0,
              "name": "Juan",
              "email": "juan@juan.es"
            }
        """.trimIndent()
        val dto = PersonDTO(
            0,
            "Juan",
            "juan@juan.es".toString(),
            null,
            null
        )
        val resDTO = PersonJsonConverter.fromJson(jsonString)

        assertAll(
            { assertEquals(dto, resDTO) },
            { assertEquals(dto.id, resDTO.id) },
            { assertEquals(dto.name, resDTO.name) },
            { assertEquals(dto.email, resDTO.email) }
        )
    }

}