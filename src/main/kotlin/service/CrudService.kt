package service

import java.sql.SQLException

// En este caso, trabajando con genéricos, voy a meterle el servicio principal
// Para ahorrarme hacerlo en el constructor
interface CrudService<Repo, T, DTO, Mapper> {

    val repository: Repo // Solo si inyectamos con genréricos
    val mapper: Mapper // Solo si inyectamos con genréricos

    @Throws(SQLException::class)
    fun findAll(): List<DTO>?

    @Throws(SQLException::class)
    fun findById(id: Long): DTO?

    @Throws(SQLException::class)
    fun save(item: T): DTO?

    @Throws(SQLException::class)
    fun update(item: T): DTO?

    @Throws(SQLException::class)
    fun delete(item: T): DTO?


}