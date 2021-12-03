package model

import javax.persistence.*

@Entity
@NamedQueries(
    NamedQuery(name = "Person.findAll", query = "SELECT p FROM Person p"),
    NamedQuery(name = "Person.findByName", query = "SELECT p FROM Person p WHERE p.name = :name"),
)
data class Person(
    // Mis constructor primario

    @Column(nullable = false)
    var name: String,

    @Column(nullable = true)
    var email: String?,

    @Column(nullable = true)
    @OneToMany(
        cascade = [CascadeType.ALL],
        orphanRemoval = true
    )
    var myPhoneNumbers: MutableSet<PhoneNumber>?,

    @Column(nullable = false)
    @OneToMany(
        cascade = [CascadeType.ALL],
        fetch = FetchType.LAZY,
        orphanRemoval = true,
        mappedBy = "person"
    )
    var myAddress: MutableSet<Address>?
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    constructor(id: Long, name: String, email: String?, telephone: Set<PhoneNumber>?, address: Set<Address>?) :
            this(
                name,
                email,
                telephone?.toMutableSet(),
                address?.toMutableSet(),
            ) {
        this.id = id
    }
}