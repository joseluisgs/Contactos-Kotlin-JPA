package converter

import org.json.JSONObject
import org.junit.jupiter.api.*
import response.Response
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue

@DisplayName("Suite Test ResponseJsonConverter")
//@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
//@TestInstance(TestInstance.Lifecycle.PER_CLASS) // Para el Beforeall
class ResponseJsonConverterTest {

    @DisplayName("Test toJson")
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

}