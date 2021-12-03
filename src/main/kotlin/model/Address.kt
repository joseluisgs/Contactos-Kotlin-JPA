package model

import javax.persistence.*

@Entity
data class Address(
    // Mi constructor primario
    @Column(nullable = false)
    var street: String,

    @Column(nullable = false)
    var postalCode: String,

    @Column(nullable = false)
    var city: String,

    ) {
    // Otros datos que no costruyo
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    @JoinColumn(
        name = "address_id",
        referencedColumnName = "id",
        nullable = false
    )
    @ManyToOne
    lateinit var person: Person

    // Constructor secundario
    constructor(street: String, postalCode: String, city: String, person: Person) : this(street, postalCode, city) {
        this.person = person
    }

    // LA sobrescribo para evitar recursividad
    override fun toString(): String {
        return "Address(id=$id, street='$street', postalCode='$postalCode', city='$city', person=${person.id})"
    }


}