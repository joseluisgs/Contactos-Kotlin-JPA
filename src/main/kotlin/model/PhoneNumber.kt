package model

import javax.persistence.*

@Entity
data class PhoneNumber(
    @Column(nullable = false)
    var number: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
}