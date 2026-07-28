package petshop_api.mapper

import petshop_api.dto.PetVacinaRequest
import petshop_api.dto.PetVacinaResponse
import petshop_api.entity.PetVacina

object PetVacinaMapper {

    fun toEntity(request: PetVacinaRequest): PetVacina {
        return PetVacina(
            vacinaId = request.vacinaId,
            petId = request.petId,
            dataAplicacao = request.dataAplicacao
        )
    }

    fun toResponse(
        petVacina: PetVacina,
        petNome: String,
        vacinaNome: String
    ): PetVacinaResponse {
        return PetVacinaResponse(
            id = petVacina.id,
            vacinaId = petVacina.vacinaId,
            vacinaNome = vacinaNome,
            petId = petVacina.petId,
            petNome = petNome,
            dataAplicacao = petVacina.dataAplicacao
        )
    }
}