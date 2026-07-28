package petshop_api.dto

import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class ServicoRequest(
    @field:NotBlank(message = "Nome é obrigatório")
    @field:Size(max = 50, message = "Nome deve ter no máximo 50 caracteres")
    var nome: String,

    @field:NotBlank(message = "Descrição é obrigatória")
    @field:Size(max = 100, message = "Descrição deve ter no máximo 100 caracteres")
    var descricao: String,

    @field:NotNull(message = "Valor é obrigatório")
    @field:DecimalMin(value = "0.01", message = "Valor deve ser maior que zero")
    var valor: Double
)