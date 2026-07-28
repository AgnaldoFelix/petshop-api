package petshop_api.dto

import java.time.LocalDateTime
import java.util.UUID

data class VacinaResponse(
    val id: UUID,
    var nome: String,
    var descricao: String,
    var createdAt: LocalDateTime = LocalDateTime.now(),
    var updatedAt: LocalDateTime = LocalDateTime.now()
)
