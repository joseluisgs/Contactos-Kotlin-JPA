package repository

import dto.PersonDTO
import java.sql.SQLException

interface CrudRespository<T, ID> {
    // Operaciones CRUD
    // Obtiene todos
    @Throws(SQLException::class)
    fun findAll(): List<T>?

    // Obtiene por ID
    @Throws(SQLException::class)
    fun findById(id: Long): T?

    // Salva
    @Throws(SQLException::class)
    fun save(item: T): T?

    // Actualiza
    @Throws(SQLException::class)
    fun update(item: T): T?

    // Elimina
    @Throws(SQLException::class)
    fun delete(t: T): T?
}