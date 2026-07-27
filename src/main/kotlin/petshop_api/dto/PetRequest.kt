package petshop_api.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Past
import jakarta.validation.constraints.Size
import java.time.LocalDate
import java.util.UUID

data class PetRequest(
    @field:NotNull(message = "ID do tutor é obrigatório")
    val tutorId: UUID,

    @field:NotBlank(message = "Nome é obrigatório")
    @field:Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    val nome: String,

    @field:Size(max = 2, message = "Idade deve ter no máximo 2 caracteres")
    val idade: String? = null,

    @field:NotBlank(message = "Espécie é obrigatória")
    @field:Size(max = 100, message = "Espécie deve ter no máximo 100 caracteres")
    val especie: String,

    @field:Size(max = 100, message = "Raça deve ter no máximo 100 caracteres")
    val raca: String? = null,

    @field:Past(message = "Data de nascimento deve ser no passado")
    val dataNascimento: LocalDate? = null
)