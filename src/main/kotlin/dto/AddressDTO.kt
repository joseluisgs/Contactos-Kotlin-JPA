package dto

data class AddressDTO(
    val id : Long,
    val street : String,
    val postalCode : String,
    val city : String,
    // Con esto rompemos la recursividad de la bidireccionalidad
    val personID: Long,
)
