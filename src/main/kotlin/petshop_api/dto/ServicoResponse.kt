package petshop_api.dto

import java.time.LocalDateTime
import java.util.UUID

data class ServicoResponse(
    val id: UUID,
    val nome: String,
    val descricao: String,
    val valor: Double,
    var createdAt: LocalDateTime,
    var updatedAt: LocalDateTime
)