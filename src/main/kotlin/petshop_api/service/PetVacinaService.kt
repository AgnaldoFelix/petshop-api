package petshop_api.service

import org.springframework.stereotype.Service
import petshop_api.entity.PetVacina
import petshop_api.repository.PetRepository
import petshop_api.repository.PetVacinaRepository
import petshop_api.repository.VacinaRepository
import java.util.UUID

@Service
class PetVacinaService(
    private val petVacinaRepository: PetVacinaRepository,
    private val petRepository: PetRepository,
    private val vacinaRepository: VacinaRepository
) {

    fun listarTodasAplicacoes(): List<PetVacina> {
        return petVacinaRepository.findAll()
    }

    fun listarVacinasPorPet(petId: UUID): List<PetVacina> {

        petRepository.findById(petId)
            .orElseThrow { Exception("Pet não encontrado") }

        return petVacinaRepository.findByPetId(petId)
    }

    fun listarPetsPorVacina(vacinaId: UUID): List<PetVacina> {

        vacinaRepository.findById(vacinaId)
            .orElseThrow { Exception("Vacina não encontrada") }

        return petVacinaRepository.findByVacinaId(vacinaId)
    }

    fun aplicarVacina(petVacina: PetVacina): PetVacina {
       val vacinaAplicada =  petVacinaRepository.existsById(petVacina.id)
        val petAplicado =  petVacinaRepository.existsById(petVacina.id)

        if(vacinaAplicada == true) {
            throw Exception("Vacina já aplicada")
        }

        petRepository.findById(petVacina.petId)
            .orElseThrow { Exception("Pet não encontrado") }

        vacinaRepository.findById(petVacina.vacinaId)
            .orElseThrow { Exception("Vacina não encontrada") }

        return petVacinaRepository.save(petVacina)
    }
}