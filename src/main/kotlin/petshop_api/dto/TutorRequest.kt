package petshop_api.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class TutorRequest(
    @field:NotBlank(message = "Nome é obrigatório")
    @field:Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    val nome: String,

    @field:NotBlank(message = "Telefone é obrigatório")
    @field:Size(max = 20, message = "Telefone deve ter no máximo 20 caracteres")
    val telefone: String,

    @field:NotBlank(message = "Email é obrigatório")
    @field:Email(message = "Email inválido")
    @field:Size(max = 100, message = "Email deve ter no máximo 100 caracteres")
    val email: String
)