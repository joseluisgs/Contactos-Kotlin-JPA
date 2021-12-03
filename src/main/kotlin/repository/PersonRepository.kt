package repository

import hibernate.HibernateController
import model.Address
import model.Person
import java.sql.SQLException


class PersonRepository : CrudRespository<Person, Long> {
    // Si quiero devolver la excepcion debo anotarlas como throws
    @Throws(SQLException::class)
    override fun findAll(): List<Person> {
        try {
            val query = HibernateController.manager
                .createNamedQuery(
                    "Person.findAll",
                    Person::class.java
                )
            return query.resultList
        } catch (e: Exception) {
            throw SQLException("Error PersonRepository findAll")
        }
    }

    @Throws(SQLException::class) // No es obligatorio, pero es util para compatibilidad con Java
    override fun findById(id: Long): Person {
        return HibernateController.manager
            .find(Person::class.java, id)
            ?: throw SQLException("Error PersonRepository findById no existe Person con ID: $id")
    }

    @Throws(SQLException::class)
    override fun save(item: Person): Person {
        try {
            HibernateController.transaction.begin()
            HibernateController.manager.persist(item)
            HibernateController.transaction.commit()
            return item
        } catch (e: Exception) {
            HibernateController.transaction.rollback()
            throw SQLException("Error PersonRepository al insertar Person en BD: ${e.message}: ${e.message}")
        }
    }

    @Throws(SQLException::class)
    override fun update(item: Person): Person {
        try {
            HibernateController.transaction.begin()
            HibernateController.manager.merge(item)
            HibernateController.transaction.commit()
            return item
        } catch (e: Exception) {
            HibernateController.transaction.rollback()
            throw SQLException("Error PersonRepository update al actualizar Person ID: ${item.id}: ${e.message}")
        }
    }

    @Throws(SQLException::class)
    override fun delete(item: Person): Person {
        try {
            // Debemos buscarlo priomero para evitar
            // Exception Removing a detached instance model.
            // Deben estar en la misma sesion lo que buscas y borras
            val personToDelete = HibernateController.manager.find(Person::class.java, item.id)
            HibernateController.transaction.begin()
            HibernateController.manager.remove(personToDelete)
            HibernateController.transaction.commit()
            return personToDelete
        } catch (e: Exception) {
            HibernateController.transaction.rollback()
            throw SQLException("Error PersonRepository delete al actualizar Person ID: ${item.id}: ${e.message}")
        }
    }

    @Throws(SQLException::class)
    fun findAddress(person: Person): List<Address> {
        try {
            val query = HibernateController.manager
                .createNamedQuery(
                    "Address.findByPerson",
                    Address::class.java
                )
            query.setParameter("userId", person.id)
            return query.resultList
        } catch (e: Exception) {
            throw SQLException("Error PersonRepository findAddress:  ${e.message}")
        }
    }
}