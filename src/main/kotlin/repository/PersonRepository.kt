package repository

import hibernate.HibernateController
import model.Person
import java.sql.SQLException


class PersonRepository: CrudRespository<Person, Long> {
    // Si quiero devolver la excepcion debo anotarlas como throws
    @Throws(SQLException::class)
    override fun findAll(): List<Person>? {
        try {
            val query = HibernateController.manager
                .createNamedQuery(
                    "Person.findAll",
                    Person::class.java
                )
            //HibernateController.close()
            return query.resultList
        } catch (e: Exception) {
            throw SQLException("Error PersonRepository findAll")
        }
    }

    @Throws(SQLException::class) // No es obligatorio, pero es util para compatibilidad con Java
    override fun findById(id: Long): Person? {
        return HibernateController.manager
            .find(Person::class.java, id) ?:
            throw SQLException("Error PersonRepository findById no existe Person con ID: $id")
    }

    @Throws(SQLException::class)
    override fun save(person: Person): Person {
        try {
            HibernateController.transaction.begin();
            HibernateController.manager.persist(person)
            HibernateController.transaction.commit();
            return person
        } catch (e: Exception) {
            HibernateController.transaction.rollback();
            throw SQLException("Error PersonRepository al insertar Person en BD");
        }
    }

    @Throws(SQLException::class)
    override fun update(person: Person): Person {
        try {
            HibernateController.transaction.begin();
            HibernateController.manager.merge(person)
            HibernateController.transaction.commit();
            return person
        } catch (e: Exception) {
            HibernateController.transaction.rollback();
            println(e.stackTraceToString())
            throw SQLException("Error PersonRepository update al actualizar Person ID: ${person.id}");
        }
    }

    @Throws(SQLException::class)
    override fun delete(person: Person): Person {
        try {
            HibernateController.transaction.begin();
            val ret = HibernateController.manager.remove(person)
            HibernateController.transaction.commit();
            return person
        } catch (e: Exception) {
            HibernateController.transaction.rollback();
            throw SQLException("Error PersonRepository delete al actualizar Person ID: ${person.id}");
        }
    }
}