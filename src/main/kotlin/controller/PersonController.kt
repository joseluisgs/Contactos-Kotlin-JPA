package controller

import converter.PersonJsonConverter
import dto.PersonDTO
import model.Person
import service.PersonService

object PersonController: IController<PersonService> {
    // Mi servicio principal
    override val service = PersonService()
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

}