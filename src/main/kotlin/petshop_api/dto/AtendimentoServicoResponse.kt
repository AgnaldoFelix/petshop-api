package petshop_api.dto

import java.util.UUID

data class AtendimentoServicoResponse(
    val id: UUID,
    val atendimentoId: UUID,
    val servicoId: UUID,
    val servicoNome: String,
    val servicoValor: Double
)