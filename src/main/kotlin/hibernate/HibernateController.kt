package hibernate

import javax.persistence.EntityManager
import javax.persistence.EntityTransaction
import javax.persistence.Persistence

object HibernateController {
    // Creamos las EntityManagerFactory para manejar las entidades y transacciones
    private val entityManagerFactory = Persistence.createEntityManagerFactory("default")
    val manager: EntityManager = entityManagerFactory.createEntityManager()
    val transaction: EntityTransaction = manager.transaction

}