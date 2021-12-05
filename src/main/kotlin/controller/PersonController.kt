package controller

import converter.PersonJsonConverter
import converter.ResponseJsonConverter
import dto.AddressDTO
import dto.PersonDTO
import mapper.PersonMapper
import model.Person
import org.json.JSONObject
import repository.PersonRepository
import response.Response
import service.PersonService

object PersonController : IController<PersonService> {
    // Mi servicio principal
    override val service = PersonService(PersonRepository())

    private val mapper: PersonMapper = PersonMapper()
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

    fun findAddress(person: Person): List<AddressDTO>? {
        return try {
            service.findAddress(person)
        } catch (e: Exception) {
            System.err.println(e.localizedMessage)
            null
        }
    }

    // JSON
    fun findAllJson(): String? {
        return try {
            ResponseJsonConverter.toJson(Response(200, service.findAll()))
        } catch (e: Exception) {
            ResponseJsonConverter.toJson(Response(500, e.localizedMessage))
        }
    }

    fun findByIdJson(id: Long): String? {
        return try {
            ResponseJsonConverter.toJson(Response(200, service.findById(id)))
        } catch (e: Exception) {
            ResponseJsonConverter.toJson(Response(500, e.localizedMessage))
        }
    }

    fun saveJson(person: Person): String? {
        return try {
            ResponseJsonConverter.toJson(Response(201, service.save(person)))
        } catch (e: Exception) {
            ResponseJsonConverter.toJson(Response(500, e.localizedMessage))
        }
    }

    fun updateJson(person: Person): String? {
        return try {
            ResponseJsonConverter.toJson(Response(200, service.update(person)))
        } catch (e: Exception) {
            ResponseJsonConverter.toJson(Response(500, e.localizedMessage))
        }
    }

    fun deleteJson(person: Person): String? {
        return try {
            ResponseJsonConverter.toJson(Response(200, service.delete(person)))
        } catch (e: Exception) {
            ResponseJsonConverter.toJson(Response(500, e.localizedMessage))
        }
    }

    fun findAddressJson(person: Person): String? {
        return try {
            ResponseJsonConverter.toJson(Response(200, service.findAddress(person)))
        } catch (e: Exception) {
            ResponseJsonConverter.toJson(Response(500, e.localizedMessage))
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
            ResponseJsonConverter.toJson(Response(201, service.save(person)))
        } catch (e: Exception) {
            ResponseJsonConverter.toJson(Response(500, e.localizedMessage))
        }
    }

    fun findByIdInputJson(id: String): String? {
        return try {
            // transformamos el ID
            val json = JSONObject(id).toMap()
            val personId = (json["id"] as Int).toLong()
            ResponseJsonConverter.toJson(Response(200, service.findById(personId)))
        } catch (e: Exception) {
            ResponseJsonConverter.toJson(Response(500, e.localizedMessage))
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
            ResponseJsonConverter.toJson(Response(200, service.update(person)))
        } catch (e: Exception) {
            ResponseJsonConverter.toJson(Response(500, e.localizedMessage))
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
            ResponseJsonConverter.toJson(Response(200, service.delete(person)))
        } catch (e: Exception) {
            ResponseJsonConverter.toJson(Response(500, e.localizedMessage))
        }
    }

    fun findAddressInputJson(json: String): String? {
        val personDTO = PersonJsonConverter.fromJson(json)
        // Podríamos buscarlo y actualizarlo.... Mira Delete
        val person = mapper.fromDTO(personDTO)
        person.myAddress = null
        // person.myPhoneNumbers = null
        return try {
            ResponseJsonConverter.toJson(Response(200, service.findAddress(person)))
        } catch (e: Exception) {
            ResponseJsonConverter.toJson(Response(500, e.localizedMessage))
        }
    }
}