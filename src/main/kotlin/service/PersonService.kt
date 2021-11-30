package service

import dto.PersonDTO
import mapper.PersonMapper
import model.Person
import repository.PersonRepository
import java.sql.SQLException

// Podríamos inyectar la dependencia en el constructor o con setter, pero se la meteré en el servicio
// class PersonService(val repository: PersonRepository): CrudService<Person>
class PersonService(): CrudService<PersonRepository, Person, PersonDTO, PersonMapper> {

    override val repository: PersonRepository = PersonRepository()
    override val mapper: PersonMapper = PersonMapper()

    // De la misma manera, si queremos devolver excepciones tipificadas para java las anotamos
    // De nuevo no es obligatorio anotarlas
    @Throws(SQLException::class)
    override fun findAll(): List<PersonDTO>? {
        return repository.findAll()?.let { mapper.toDTO(it) }
    }

    @Throws(SQLException::class)
    override fun findById(id: Long): PersonDTO? {
        return repository.findById(id)?.let { mapper.toDTO(it) }
    }

    @Throws(SQLException::class)
    override fun save(item: Person): PersonDTO? {
        return repository
            .save(item).let { mapper.toDTO(it) }
    }

    @Throws(SQLException::class)
    override fun update(item: Person): PersonDTO? {
        return repository.update(item).let { mapper.toDTO(it) }
    }

    @Throws(SQLException::class)
    override fun delete(item: Person): PersonDTO? {
        return repository.delete(item).let { mapper.toDTO(it) }
    }
}