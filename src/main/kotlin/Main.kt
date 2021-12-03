import controller.PersonController
import model.Address
import model.Person
import model.PhoneNumber
import java.time.Instant

fun main(args: Array<String>) {
    println("Kotlin JPA")

    // Para meter las cosas usaremos el objeto normal, las salidas siempre DTO
    // Comenta todas y ejecuta solo una!!!

    // LLamadas normales. Ojo no se ve por como está formado el string
    //runControllerToString()

    // Pasandole JSON como entrada de Datos
    //runControllerIOJson()

    runControllerJson()
}

fun runControllerToString() {
    println("--> toString: Ojo no se ve por los formatos de las cadenas")
    val p1 = Person(
        "Juan",
        "juan@juan.es",
        setOf(
            PhoneNumber("202-555-0171"),
            PhoneNumber("202-555-0102")
        ).toMutableSet(), // Son mutables porque los quiero cambiar
        setOf(
            Address("Calle Luis Vives", "28918", "Leganes")
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
            Address("Calle Zazaquemada", "28916", "Leganes")
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
    val json = PersonController.findAllJson()
    println(json)
    println()

    println("Find by ID")
    dto = PersonController.findById(list!![0].id)
    println(dto)
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

    println("Find Address by Person")
    val addressList = PersonController.findAddress(p1)
    addressList?.forEach { println(it) }
    println()
}

fun runControllerJson() {
    println("--> toJSON: Salida en JSON")
    // Vamos a pasar los datos como JSON para simular que nos llegan así
    val p1 = Person(
        "Juan${Instant.now()}",
        "juan${Instant.now()}@juan.es",
        setOf(
            PhoneNumber("202-555-0171"),
            PhoneNumber("202-555-0102")
        ).toMutableSet(), // Son mutables porque los quiero cambiar
        setOf(
            Address("Luis Vives${Instant.now()}", "28819", "Leganes")
        ).toMutableSet(), //
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
            Address("Zazaquemada${Instant.now()}", "28916", "Leganes")
        ).toMutableSet()
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
    p1.myAddress?.add(Address("Dolor Gigante${Instant.now()}", "28918", "Leganes", p1))
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

    println("Find Address by Person JSON")
    json = PersonController.findAddressJson(p1)
    println(json)
    println()
}

fun runControllerIOJson() {
    println("--> InputJSON toJSON: Entrada/Salida en JSON")
    // Vamos a pasar los datos como JSON para simular que nos llegan así
    var p = """
        {
          "name": "Json${Instant.now()}",
          "email": "Json${Instant.now()}@jsondomain.es",
          "telephone": [
            {
              "number": "666-555-9999"
            }
          ],
          "address": [
            {
              "street": "Json Street ${Instant.now()}",
              "postalCode": "28080",
              "city": "Leganes"
            }
          ]
        }
        """.trimIndent()

    println("Save INPUT JSON")
    var json = PersonController.saveInputJson(p)
    println(json)
    println()

    println("Find All JSON")
    json = PersonController.findAllJson()
    println(json)
    println()

    println("Find by ID INPUT JSON")
    // Realmente aquí nos llegan los datos como JSON, pero no sabemos que ID vamos a buscar
    val id = """
        {
          "id": 1
        }
    """.trimIndent()

    json = PersonController.findByIdInputJson(id)
    println(json)
    println()

    println("Find Address by Person JSON")
    json = PersonController.findAddressInputJson(p)
    println(json)
    println()

    println("Update INPUT JSON")
    // Update
    p = """
        {
          "id": 1,
          "name": "ModJson${Instant.now()}",
          "email": "Json${Instant.now()}@jsondomain.es",
          "telephone": [
            {
              "id": 1,
              "number": "666-555-9999"
            },
            {
              "id": 2,
              "number": "666-555-8888"
            }
          ]
        }
        """.trimIndent()
    // Otra dirección
    json = PersonController.updateInputJson(p)
    println(json)
    println()

    println("Delete INPUT JSON")
    json = PersonController.deleteInputJson(p)
    println(json)
    println()

    println("Find All JSON")
    println()



}
