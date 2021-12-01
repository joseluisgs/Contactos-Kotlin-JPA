package controller

import converter.PersonJsonConverter
import dto.PersonDTO
import mapper.PersonMapper
import model.Person
import org.json.JSONObject
import service.PersonService

object PersonController: IController<PersonService> {
    // Mi servicio principal
    override val service = PersonService()
    val mapper: PersonMapper = PersonMapper()
    // Obtienes todos

    // Este lo dejo porque lo necesito
    fun findAll(): List<PersonDTO>? {
        return service.findAll()
    }

    fun findById(id: Long): PersonDTO? {
        return service.findById(id)
    }

    fun save(person: Person): PersonDTO? {
        return service.save(person)
    }

    fun update(person: Person): PersonDTO? {
        return service.update(person)
    }

    fun delete(person: Person): PersonDTO? {
        return service.delete(person)
    }

    // JSON
    fun findAllJson(): String? {
        return service.findAll()?.let { PersonJsonConverter.toJson(it) }
    }

    fun findByIdJson(id: Long): String? {
        return service.findById(id)?.let { PersonJsonConverter.toJson(it) }
    }

    fun saveJson(person: Person): String? {
        return service.save(person)?.let { PersonJsonConverter.toJson(it) }
    }

    fun updateJson(person: Person): String? {
        return service.update(person)?.let { PersonJsonConverter.toJson(it) }
    }

    fun deleteJson(person: Person): String? {
        return service.delete(person)?.let { PersonJsonConverter.toJson(it) }
    }

    fun saveInputJson(json: String): String? {
        // Lo primero es leer el JSON y transformalo en objetos
        //println("JSON: $json")
        // Trasformamos a DTO
        val personDTO = PersonJsonConverter.fromJson(json)
        // Luego a objeto
        val person = mapper.fromDTO(personDTO)
        // Realmente lo de pasarle todos los datos es una locura :)
        // Así que despues de leerlo, vamos a eliminar su dirección, pues tenemos una relación ahí
        // Lo ideal seria dar de alta la dirección y luego asociarla a la persona
        // Por eso lo pongo a null
        // Salvamos
        person.myAddress = null
        // person.myPhoneNumbers = null
        return service.save(person)?.let { PersonJsonConverter.toJson(it) }
    }

    fun findByIdInputJson(id: String): String? {
        // transformamos el ID
        val jsonObj = JSONObject(id)
        val map = jsonObj.toMap()
        val personId = (map["id"] as Int).toLong()
        return service.findById(personId)?.let { PersonJsonConverter.toJson(it) }
    }

    fun updateInputJson(json: String): String? {
        // idem a save
        val personDTO = PersonJsonConverter.fromJson(json)
        // Podríamos buscarlo y actualizarlo.... Mira Delete
        val person = mapper.fromDTO(personDTO)
        person.myAddress = null
        // person.myPhoneNumbers = null
        return service.update(person)?.let { PersonJsonConverter.toJson(it) }
    }

    fun deleteInputJson(json: String): String? {
        // idem a save
        var personDTO = PersonJsonConverter.fromJson(json)
        // Lo buscamos
        val person = mapper.fromDTO(personDTO)
        return service.delete(person)?.let { PersonJsonConverter.toJson(it) }
    }
}