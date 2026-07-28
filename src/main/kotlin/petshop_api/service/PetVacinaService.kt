package petshop_api.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import petshop_api.dto.PetVacinaRequest
import petshop_api.dto.PetVacinaResponse
import petshop_api.mapper.PetVacinaMapper
import petshop_api.repository.PetRepository
import petshop_api.repository.PetVacinaRepository
import petshop_api.repository.VacinaRepository
import java.util.ArrayList
import java.util.UUID

@Service
class PetVacinaService(
    private val petVacinaRepository: PetVacinaRepository,
    private val petRepository: PetRepository,
    private val vacinaRepository: VacinaRepository
) {

    fun listarTodasAplicacoes(): List<PetVacinaResponse> {
        val aplicacoes = petVacinaRepository.findAll()
        val resposta = ArrayList<PetVacinaResponse>()

        for (aplicacao in aplicacoes) {
            val pet = petRepository.findById(aplicacao.petId)
                .orElseThrow { Exception("Pet não encontrado") }
            val vacina = vacinaRepository.findById(aplicacao.vacinaId)
                .orElseThrow { Exception("Vacina não encontrada") }

            resposta.add(PetVacinaMapper.toResponse(aplicacao, pet.nome, vacina.nome))
        }

        return resposta
    }

    fun listarVacinasPorPet(petId: UUID): List<PetVacinaResponse> {
        petRepository.findById(petId)
            .orElseThrow { Exception("Pet não encontrado") }

        val aplicacoes = petVacinaRepository.findByPetId(petId)
        val resposta = ArrayList<PetVacinaResponse>()

        for (aplicacao in aplicacoes) {
            val pet = petRepository.findById(aplicacao.petId)
                .orElseThrow { Exception("Pet não encontrado") }
            val vacina = vacinaRepository.findById(aplicacao.vacinaId)
                .orElseThrow { Exception("Vacina não encontrada") }

            resposta.add(PetVacinaMapper.toResponse(aplicacao, pet.nome, vacina.nome))
        }

        return resposta
    }

    fun listarPetsPorVacina(vacinaId: UUID): List<PetVacinaResponse> {
        vacinaRepository.findById(vacinaId)
            .orElseThrow { Exception("Vacina não encontrada") }

        val aplicacoes = petVacinaRepository.findByVacinaId(vacinaId)
        val resposta = ArrayList<PetVacinaResponse>()

        for (aplicacao in aplicacoes) {
            val pet = petRepository.findById(aplicacao.petId)
                .orElseThrow { Exception("Pet não encontrado") }
            val vacina = vacinaRepository.findById(aplicacao.vacinaId)
                .orElseThrow { Exception("Vacina não encontrada") }

            resposta.add(PetVacinaMapper.toResponse(aplicacao, pet.nome, vacina.nome))
        }

        return resposta
    }

    @Transactional
    fun aplicarVacina(request: PetVacinaRequest): PetVacinaResponse {

        val pet = petRepository.findById(request.petId)
            .orElseThrow { Exception("Pet não encontrado") }

        val vacina = vacinaRepository.findById(request.vacinaId)
            .orElseThrow { Exception("Vacina não encontrada") }

        val existeAplicacao = petVacinaRepository.findByPetIdAndVacinaId(request.petId, request.vacinaId)
        if (existeAplicacao != null) {
            throw Exception("Esta vacina já foi aplicada para este pet")
        }

        val petVacina = PetVacinaMapper.toEntity(request)

        val petVacinaSalvo = petVacinaRepository.save(petVacina)

        return PetVacinaMapper.toResponse(petVacinaSalvo, pet.nome, vacina.nome)
    }
}