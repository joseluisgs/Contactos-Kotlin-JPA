package controller

import converter.PersonJsonConverter
import converter.ResponseJsonConverter
import dto.PersonDTO
import mapper.PersonMapper
import model.Person
import response.Response
import org.json.JSONObject
import service.PersonService

object PersonController: IController<PersonService> {
    // Mi servicio principal
    override val service = PersonService()
    val mapper: PersonMapper = PersonMapper()
    // Obtienes todos

    // Este lo dejo porque lo necesito
    fun findAll(): List<PersonDTO>? {
        return try {
            service.findAll()
        } catch (e: Exception) {
            System.err.println(e.localizedMessage)
            null
        }
    }

    fun findById(id: Long): PersonDTO? {
        return try {
            service.findById(id)
        } catch (e: Exception) {
            System.err.println(e.localizedMessage)
            null
        }
    }

    fun save(person: Person): PersonDTO? {
        return try {
            service.save(person)
        } catch (e: Exception) {
            System.err.println(e.localizedMessage)
            null
        }
    }

    fun update(person: Person): PersonDTO? {
        return try {
            service.update(person)
        } catch (e: Exception) {
            System.err.println(e.localizedMessage)
            null
        }
    }

    fun delete(person: Person): PersonDTO? {
        return try {
            service.delete(person)
        } catch (e: Exception) {
            System.err.println(e.localizedMessage)
            null
        }
    }

    // JSON
    fun findAllJson(): String? {
        return try {
            ResponseJsonConverter.toJson(service.findAll()?.let { Response(200, it) })
        } catch (e: Exception) {
            ResponseJsonConverter.toJson(Response(500,e.localizedMessage))
        }
    }

    fun findByIdJson(id: Long): String? {
        return try {
            ResponseJsonConverter.toJson(service.findById(id)?.let { Response(200, it) })
        } catch (e: Exception) {
            ResponseJsonConverter.toJson(Response(500,e.localizedMessage))
        }
    }

    fun saveJson(person: Person): String? {
        return try {
            ResponseJsonConverter.toJson(service.save(person)?.let { Response(201, it) })
        } catch (e: Exception) {
            ResponseJsonConverter.toJson(Response(500,e.localizedMessage))
        }
    }

    fun updateJson(person: Person): String? {
        return try {
            ResponseJsonConverter.toJson(service.update(person)?.let { Response(200, it) })
        } catch (e: Exception) {
            ResponseJsonConverter.toJson(Response(500,e.localizedMessage))
        }
    }

    fun deleteJson(person: Person): String? {
        return try {
            ResponseJsonConverter.toJson(service.delete(person)?.let { Response(200, it) })
        } catch (e: Exception) {
            ResponseJsonConverter.toJson(Response(500,e.localizedMessage))
        }
    }

    fun saveInputJson(json: String): String? {
        return try {
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
            ResponseJsonConverter.toJson(service.save(person)?.let { Response(201, it) })
        } catch (e: Exception) {
            ResponseJsonConverter.toJson(Response(500,e.localizedMessage))
        }
    }

    fun findByIdInputJson(id: String): String? {
        return try {
            // transformamos el ID
            val jsonObj = JSONObject(id)
            val map = jsonObj.toMap()
            val personId = (map["id"] as Int).toLong()
            ResponseJsonConverter.toJson(service.findById(personId)?.let { Response(200, it) })
        } catch (e: Exception) {
            ResponseJsonConverter.toJson(Response(500,e.localizedMessage))
        }
    }

    fun updateInputJson(json: String): String? {
        return try {
        // idem a save
        val personDTO = PersonJsonConverter.fromJson(json)
        // Podríamos buscarlo y actualizarlo.... Mira Delete
        val person = mapper.fromDTO(personDTO)
        person.myAddress = null
        // person.myPhoneNumbers = null
            ResponseJsonConverter.toJson(service.update(person)?.let { Response(200, it) })
        } catch (e: Exception) {
            ResponseJsonConverter.toJson(Response(500,e.localizedMessage))
        }
    }

    fun deleteInputJson(json: String): String? {
        return try {
            // idem a save
            val personDTO = PersonJsonConverter.fromJson(json)
            // Podríamos buscarlo y actualizarlo.... Mira Delete
            val person = mapper.fromDTO(personDTO)
            person.myAddress = null
            // person.myPhoneNumbers = null
            ResponseJsonConverter.toJson(service.delete(person)?.let { Response(200, it) })
        } catch (e: Exception) {
            ResponseJsonConverter.toJson(Response(500,e.localizedMessage))
        }
    }
}