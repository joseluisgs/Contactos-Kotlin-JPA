package converter

import org.json.JSONObject
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import response.Response

@DisplayName("Suite Test ResponseJsonConverter")
//@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
//@TestInstance(TestInstance.Lifecycle.PER_CLASS) // Para el Beforeall
class ResponseJsonConverterTest {

    @DisplayName("Test Response to Json")
    @Test
    fun testResponseJsonConverter() {
        val jsonString = """
            {
             "status": 1,
             "data": "OK"
            }
        """.trimIndent()
        val response = Response(1, "OK")
        val resString = ResponseJsonConverter.toJson(response)

        // Si lo comparamos como cadenas, no saldrá por los tabuladores
        val json = JSONObject(jsonString).toMap()
        val res = JSONObject(resString).toMap()

        assertEquals(json, res)
    }

    @DisplayName("Test json to Response")
    @Test
    fun jsonToObject() {
        val jsonString = """
            {
             "status": 1,
             "data": "OK"
            }
        """.trimIndent()
        val dto = Response(1, "OK")
        val resDTO = ResponseJsonConverter.fromJson(jsonString)

        Assertions.assertAll(
            { assertEquals(dto, resDTO) },
            { assertEquals(dto.status, resDTO.status) },
            { assertEquals(dto.data, resDTO.data) }
        )
    }

}