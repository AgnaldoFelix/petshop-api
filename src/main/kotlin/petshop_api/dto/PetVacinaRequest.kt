package petshop_api.dto

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.PastOrPresent
import java.time.LocalDate
import java.util.UUID

data class PetVacinaRequest(
    @field:NotNull(message = "ID da vacina é obrigatório")
    val vacinaId: UUID,

    @field:NotNull(message = "ID do pet é obrigatório")
    val petId: UUID,

    @field:NotNull(message = "Data de aplicação é obrigatória")
    @field:PastOrPresent(message = "Data de aplicação não pode ser no futuro")
    val dataAplicacao: LocalDate
)