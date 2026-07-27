package petshop_api.dto

import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

data class PetResponse(
    val id: UUID,
    val tutorId: UUID,
    val tutorNome: String,
    val nome: String,
    val idade: String?,
    val especie: String,
    val raca: String?,
    val dataNascimento: LocalDate?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)