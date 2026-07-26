package petshop_api.dto

import jakarta.validation.constraints.FutureOrPresent
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import petshop_api.enums.NivelEmergencia
import java.time.LocalDate
import java.util.UUID

data class AtendimentoRequest(
    @field:NotNull(message = "ID do pet é obrigatório")
    val petId: UUID,

    @field:FutureOrPresent(message = "Data não pode ser no passado")
    val dataAtendimento: LocalDate? = null,

    @field:NotNull(message = "Nível de emergência é obrigatório")
    val nivelEmergencia: NivelEmergencia,

    @field:Size(max = 1000, message = "Observação não pode ter mais que 1000 caracteres")
    val observacao: String? = null
)