package converter

import com.google.gson.GsonBuilder
import response.Response

object ResponseJsonConverter: IJSonConverter<Response, String> {
    override fun convertTo(item: Response?): String {
        val gson = GsonBuilder().setPrettyPrinting().create()
        return gson.toJson(item)
    }

    fun toJson(item: Response?): String {
        return ResponseJsonConverter.convertTo(item)
    }

    override fun convertFrom(json: String): Response {
        TODO("Not yet implemented")
    }
}