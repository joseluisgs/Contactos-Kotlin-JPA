package hibernate

import javax.persistence.EntityManager
import javax.persistence.EntityTransaction
import javax.persistence.Persistence

object HibernateController {
    // Creamos las EntityManagerFactory para manejar las entidades y transacciones
    private val entityManagerFactory = Persistence.createEntityManagerFactory("default")
    val manager: EntityManager = entityManagerFactory.createEntityManager()
    val transaction: EntityTransaction = manager.transaction


    fun truncateAllTables() {
        val tableNames = manager.metamodel.entities.map { it.name }
        //tableNames.forEach { println(it) }
        transaction.begin()
        manager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate()
        tableNames.forEach { tableName ->
            manager.createNativeQuery("TRUNCATE TABLE " + tableName).executeUpdate()
        }
        manager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate()
        transaction.commit()
    }

//    fun truncateTable(tableName: String) {
//        transaction.begin()
//        manager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate()
//        manager.createNativeQuery("TRUNCATE TABLE " + tableName).executeUpdate()
//        manager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate()
//        transaction.commit()
//    }
}