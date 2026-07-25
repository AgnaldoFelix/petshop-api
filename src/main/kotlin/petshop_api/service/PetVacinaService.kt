package petshop_api.service

import petshop_api.entity.PetVacina
import petshop_api.repository.PetVacinaRepository
import java.util.UUID

class PetVacinaService (
    private val petVacinaRepository: PetVacinaRepository
) {
    fun listarVacinas() {
       petVacinaRepository.findAll()
    }
}