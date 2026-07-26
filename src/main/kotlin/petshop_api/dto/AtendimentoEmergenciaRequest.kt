package petshop_api.dto

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.util.UUID

data class AtendimentoEmergenciaRequest(
    @field:NotNull(message = "ID do pet é obrigatório")
    val petId: UUID,

    @field:Size(max = 1000, message = "Descrição não pode ter mais que 1000 caracteres")
    val descricao: String
)