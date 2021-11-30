import controller.PersonController
import model.Address
import model.Person
import model.PhoneNumber
import java.time.Instant

fun main(args: Array<String>) {
    println("Kotlin JPA")

    // Para meter las cosas usaremos el objeto normal, las salidas siempre DTO

    // LLamadas normales

    runControllerToString()
    runControllerJson()
}

fun runControllerJson() {
    val p1 = Person(
        "Juan${Instant.now()}",
        "juan${Instant.now()}@juan.es",
        setOf(
            PhoneNumber("202-555-0171"),
            PhoneNumber("202-555-0102")
        ).toMutableSet(), // Son mutables porque los quiero cambiar
        setOf(
            Address("Luis Vives${Instant.now()}", "Leganes", "28918")
        ).toHashSet()
    )

    // Le añado a cada dirección quien soy yo, por bidireccionalidad. En números no, porque no quiero eso.
    p1.myAddress?.forEach {
        it.person = p1
    }

    val p2 = Person(
        "Pepe ${Instant.now()}",
        "Pepe${Instant.now()}@Pepe.es",
        setOf(
            PhoneNumber("666-555-0171")
        ).toMutableSet(), // Son mutables porque los quiero cambiar
        setOf(
            Address("Zazaquemada${Instant.now()}", "Leganes", "28916")
        ).toHashSet()
    )

    // Le añado a cada dirección quien soy yo, por bidireccionalidad. En números no, porque no quiero eso.
    p2.myAddress?.forEach {
        it.person = p2
    }

    println("Save JSON")
    var json = PersonController.saveJson(p1)
    println(json)
    json = PersonController.saveJson(p2)
    println(json)
    println()

    println("Find All JSON")
    json = PersonController.findAllJson()
    println(json)
    println()

    println("Find by ID JSON")
    json = PersonController.findByIdJson(p1.id)
    println(json)
    println()

    // Update
    p1.email = "modificado${Instant.now()}@modificado.com"
    // Otra dirección
    p1.myAddress?.add(Address("Dolor Gigante${Instant.now()}", "Leganes", "28918", p1))
    println("Update JSON")
    json = PersonController.updateJson(p1)
    println(json)
    println()

    println("Delete JSON")
    json = PersonController.deleteJson(p2)
    println(json)
    println()

    println("Find All JSON")
    json = PersonController.findAllJson()
    println(json)
    println()
}

fun runControllerToString() {
    val p1 = Person(
        "Juan",
        "juan@juan.es",
        setOf(
            PhoneNumber("202-555-0171"),
            PhoneNumber("202-555-0102")
        ).toMutableSet(), // Son mutables porque los quiero cambiar
        setOf(
            Address("Calle Luis Vives", "Leganes", "28918")
        ).toHashSet()
    )

    // Le añado a cada dirección quien soy yo, por bidireccionalidad. En números no, porque no quiero eso.
    p1.myAddress?.forEach {
        it.person = p1
    }

    val p2 = Person(
        "Pepe",
        "Pepe@Pepe.es",
        setOf(
            PhoneNumber("666-555-0171")
        ).toMutableSet(), // Son mutables porque los quiero cambiar
        setOf(
            Address("Calle Zazaquemada", "Leganes", "28916")
        ).toHashSet()
    )

    // Le añado a cada dirección quien soy yo, por bidireccionalidad. En números no, porque no quiero eso.
    p2.myAddress?.forEach {
        it.person = p2
    }

    println("Save")
    var dto = PersonController.save(p1)
    println(dto)
    dto = PersonController.save(p2)
    println(dto)
    println()

    println("Find All")
    var list = PersonController.findAll()
    list?.forEach { println(it) }
    println()

    println("Find All JSON")
    var json = PersonController.findAllJson()
    println(json)
    println()

    println("Find by ID")
    dto = PersonController.findById(list!![0].id)
    println(dto)
    println()

    println("Find by ID JSON")
    json = PersonController.findByIdJson(list[0].id)
    println(json)
    println()

    println("Save JSON")
    json = PersonController.saveJson(p1)
    println(json)
    println()

    // Update
    p1.email = "modificado@modificado.com"
    // Otra dirección
    p1.myAddress?.add(Address("Calle Dolor Gigante", "Leganes", "28918", p1))
    println("Update")
    dto = PersonController.update(p1)
    println(dto)
    println()

    println("Delete")
    dto = PersonController.delete(p2)
    println(dto)
    println()

    println("Find All")
    list = PersonController.findAll()
    list?.forEach { println(it) }
    println()
}
