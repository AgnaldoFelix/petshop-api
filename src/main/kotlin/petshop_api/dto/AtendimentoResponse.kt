package petshop_api.dto

import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

data class AtendimentoResponse (
    val id: UUID,
    val petId: UUID,
    val petNome: String,
    val dataAtendimento: LocalDate,
    val nivelEmergencia: String?,
    val observacao: String?,
    val decisao: String?,
    val status: String,
    val finalizado: Boolean,
    val dataFinalizacao: LocalDate?,
    val createdAt: LocalDateTime
)