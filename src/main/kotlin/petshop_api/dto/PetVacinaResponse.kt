package petshop_api.dto

import java.time.LocalDate
import java.util.UUID

data class PetVacinaResponse(
    val id: UUID,
    val vacinaId: UUID,
    val vacinaNome: String,
    val petId: UUID,
    val petNome: String,
    val dataAplicacao: LocalDate
)