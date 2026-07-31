package petshop_api.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.util.UUID

data class VacinaRequest(

    val id: UUID,

    @field:NotBlank(message = "Nome é obrigatório")
    @field:Size(max = 50, message = "Nome deve ter no máximo 50 caracteres")
    val nome: String,

    @field:NotBlank(message = "Descrição é obrigatório")
    @field:Size(max = 50, message = "Nome deve ter no máximo 50 caracteres")
    val descricao: String,
)
