package petshop_api.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class TutorRequest(

    @field:NotBlank(message = "O nome é obrigatório")
    @field:Size(max = 150, message = "O nome deve ter no máximo 150 caracteres")
    val nome: String,

    @field:NotBlank(message = "O telefone é obrigatório")
    @field:Size(max = 20, message = "O telefone deve ter no máximo 20 caracteres")
    val telefone: String,

    @field:Email(message = "E-mail inválido")
    @field:Size(max = 150, message = "O e-mail deve ter no máximo 150 caracteres")
    val email: String?
)