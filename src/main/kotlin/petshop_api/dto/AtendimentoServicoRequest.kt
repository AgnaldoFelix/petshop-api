package petshop_api.dto

import jakarta.validation.constraints.NotNull
import java.util.UUID

data class AtendimentoServicoRequest(
    @field:NotNull(message = "ID do atendimento é obrigatório")
    val atendimentoId: UUID,

    @field:NotNull(message = "ID do serviço é obrigatório")
    val servicoId: UUID
)