package service

import dto.AddressDTO
import dto.PersonDTO
import mapper.AddressMapper
import mapper.PersonMapper
import model.Person
import repository.CrudRespository
import repository.PersonRepository
import java.sql.SQLException

// Podríamos inyectar la dependencia en el constructor o con setter, pero se la meteré en el servicio

class PersonService(val repository: CrudRespository<Person, Long>) : CrudService<Person, PersonDTO> {

    val mapper: PersonMapper = PersonMapper()

    // De la misma manera, si queremos devolver excepciones tipificadas para java las anotamos
    // De nuevo no es obligatorio anotarlas
    @Throws(SQLException::class)
    override fun findAll(): List<PersonDTO> {
        return mapper.toDTO(repository.findAll())
    }

    @Throws(SQLException::class)
    override fun findById(id: Long): PersonDTO {
        return repository.findById(id).let { mapper.toDTO(it) }
    }

    @Throws(SQLException::class)
    override fun save(item: Person): PersonDTO {
        return mapper.toDTO(repository.save(item))
    }

    @Throws(SQLException::class)
    override fun update(item: Person): PersonDTO {
        return mapper.toDTO(repository.update(item))
    }

    @Throws(SQLException::class)
    override fun delete(item: Person): PersonDTO {
        return mapper.toDTO(repository.delete(item))
    }

    @Throws(SQLException::class)
    fun findAddress(item: Person): List<AddressDTO> {
        val addressMapper = AddressMapper()
        val repo = repository as PersonRepository
        return addressMapper.toDTO(repository.findAddress(item))
    }
}