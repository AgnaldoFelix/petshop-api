package petshop_api.dto

import java.time.LocalDateTime
import java.util.UUID

data class TutorResponse(
    val id: UUID,
    val nome: String,
    val telefone: String,
    val email: String,
    val createdAt: LocalDateTime
)