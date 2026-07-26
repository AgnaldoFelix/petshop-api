package petshop_api.dto

import java.time.LocalDate
import java.util.UUID

data class AtendimentoHistoricoResponse(
    val id: UUID,
    val petNome: String,
    val dataAtendimento: LocalDate,
    val nivelEmergencia: String?,
    val decisao: String?,
    val status: String,
    val dataFinalizacao: LocalDate?,
    val duracao: String?
)