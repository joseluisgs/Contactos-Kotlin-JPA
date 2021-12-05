package converter

import com.google.gson.GsonBuilder
import response.Response

object ResponseJsonConverter : IJSonConverter<Response, String> {
    override fun convertTo(item: Response): String {
        val gson = GsonBuilder().setPrettyPrinting().create()
        return gson.toJson(item)
    }

    fun toJson(item: Response): String {
        return convertTo(item)
    }

    override fun convertFrom(json: String): Response {
        val gson = GsonBuilder().setPrettyPrinting().create()
        return gson.fromJson(json, Response::class.java)
    }

    fun fromJson(json: String): Response {
        return convertFrom(json)
    }
}