package hibernate

import javax.persistence.EntityManager
import javax.persistence.EntityTransaction
import javax.persistence.Persistence
import javax.persistence.Table
import javax.persistence.metamodel.Metamodel
import javax.transaction.Transactional

object HibernateController {
    // Creamos las EntityManagerFactory para manejar las entidades y transacciones
    private val entityManagerFactory = Persistence.createEntityManagerFactory("default")
    val manager: EntityManager = entityManagerFactory.createEntityManager()
    val transaction: EntityTransaction = manager.transaction


    fun cleanAllTables() {
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

//    fun cleanTable(tableName: String) {
//        transaction.begin()
//        manager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate()
//        manager.createNativeQuery("TRUNCATE TABLE " + tableName).executeUpdate()
//        manager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate()
//        transaction.commit()
//    }
}