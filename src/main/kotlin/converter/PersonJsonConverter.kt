package converter

import com.google.gson.GsonBuilder
import dto.PersonDTO

object PersonJsonConverter: IJSonConverter<PersonDTO, String> {
    override fun convertTo(item: PersonDTO): String {
        val gson = GsonBuilder().setPrettyPrinting().create()
        return gson.toJson(item)
    }

    fun toJson(item: PersonDTO): String {
        return convertTo(item)
    }

    fun convertTo(item: List<PersonDTO>): String {
        val gson = GsonBuilder().setPrettyPrinting().create()
        return gson.toJson(item)
    }
    fun toJson(item: List<PersonDTO>): String {
        return convertTo(item)
    }

    override fun convertFrom(json: String): PersonDTO {
        val gson = GsonBuilder().setPrettyPrinting().create()
        return gson.fromJson(json, PersonDTO::class.java)
    }

    fun fromJson(json: String): PersonDTO {
        return convertFrom(json)
    }


}