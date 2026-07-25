package petshop_api.dto

import java.time.LocalDate
import java.util.UUID


class PetResponse (

    val id: UUID,

    val tutorId: UUID,

    var nome: String,

    var idade: String,

    val especie: String,

    val raca: String,

    val dataNascimento: LocalDate?,
)