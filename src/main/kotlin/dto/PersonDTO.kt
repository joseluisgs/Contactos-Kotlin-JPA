package dto

import model.Address
import model.PhoneNumber

// Solo lectura una vez inicializado
// Vamos a tener un problema de recurssividado

data class PersonDTO(
    val id: Long,
    val name: String,
    var email: String?,
    val telephone: List<PhoneNumber>?,
    val address: List<AddressDTO>?,
)