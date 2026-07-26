package petshop_api.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.time.LocalDate
import java.util.UUID

data class PetRequest (
    @field:NotBlank(message = "O nome é obrigatório")
    @field:Size(max = 150, message = "O nome deve ter no máximo 150 caracteres")
    val nome: String,


    @field:Size(max = 2, message = "A idade deve ter no máximo 2 caracteres")
    val idade: String,

    @field:NotBlank(message = "A especie é obrigatório")
    @field:Size(max = 100, message = "A especie deve ter no máximo 100 caracteres")
    val especie: String,

    @field:NotBlank(message = "O tutor é obrigatório")
    @field:Size(max = 100, message = "O tutor deve ter no máximo 100 caracteres")
    val tutor_id: UUID,

    @field:Size(max = 20, message = "A data de nascimento deve ter no máximo 20 caracteres")
    val data_nascimento: LocalDate,
)