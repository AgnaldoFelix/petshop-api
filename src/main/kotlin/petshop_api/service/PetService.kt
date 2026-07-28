package petshop_api.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import petshop_api.dto.PetRequest
import petshop_api.dto.PetResponse
import petshop_api.entity.Pet
import petshop_api.mapper.PetMapper
import petshop_api.repository.PetRepository
import petshop_api.repository.TutorRepository
import java.util.ArrayList
import java.util.UUID

@Service
class PetService(
    private val petRepository: PetRepository,
    private val tutorRepository: TutorRepository
) {

    @Transactional
    fun adicionarPet(request: PetRequest): PetResponse {
        val petExistente = petRepository.findByNomeAndTutor_id(request.nome, request.tutorId)

        if (!petExistente.isEmpty()) {
            throw Exception("Pet já cadastrado para este tutor")
        }

        val pet = PetMapper.toEntity(request)
        val petSalvo = petRepository.save(pet)

        val tutor = tutorRepository.findById(request.tutorId)
            .orElseThrow { Exception("Tutor não encontrado") }

        return PetMapper.toResponse(petSalvo, tutor.nome)
    }

    fun listarPets(): List<PetResponse> {
        val pets = petRepository.findAll()
        val resposta = ArrayList<PetResponse>()

        for (pet in pets) {
            val tutor = tutorRepository.findById(pet.tutor_id)
                .orElseThrow { Exception("Tutor não encontrado") }
            resposta.add(PetMapper.toResponse(pet, tutor.nome))
        }

        return resposta
    }

    fun buscarPetPorId(id: UUID): PetResponse {
        val pet = petRepository.findById(id)
            .orElseThrow { Exception("Pet com ID $id não encontrado") }

        val tutor = tutorRepository.findById(pet.tutor_id)
            .orElseThrow { Exception("Tutor não encontrado") }

        return PetMapper.toResponse(pet, tutor.nome)
    }

    fun buscarPetsPorTutor(tutorId: UUID): List<PetResponse> {
        val tutor = tutorRepository.findById(tutorId)
            .orElseThrow { Exception("Tutor não encontrado") }

        val pets = petRepository.findByTutorId(tutorId)
        val resposta = ArrayList<PetResponse>()

        for (pet in pets) {
            resposta.add(PetMapper.toResponse(pet, tutor.nome))
        }

        return resposta
    }

    fun buscarPetsPorNome(nome: String): List<PetResponse> {
        val pets = petRepository.findByNome(nome)
        val resposta = ArrayList<PetResponse>()

        if (pets.isEmpty()) {
            throw Exception("Nenhum pet encontrado com o nome: $nome")
        }

        for (pet in pets) {
            val tutor = tutorRepository.findById(pet.tutor_id)
                .orElseThrow { Exception("Tutor não encontrado") }
            resposta.add(PetMapper.toResponse(pet, tutor.nome))
        }

        return resposta
    }

    @Transactional
    fun atualizarPet(id: UUID, request: PetRequest): PetResponse {
        val petExistente = petRepository.findById(id)
            .orElseThrow { Exception("Pet com ID $id não encontrado") }

        petExistente.nome = request.nome
        petExistente.idade = request.idade
        petExistente.especie = request.especie
        petExistente.raca = request.raca
        petExistente.data_nascimento = request.dataNascimento

        val petAtualizado = petRepository.save(petExistente)

        val tutor = tutorRepository.findById(petAtualizado.tutor_id)
            .orElseThrow { Exception("Tutor não encontrado") }

        return PetMapper.toResponse(petAtualizado, tutor.nome)
    }

    @Transactional
    fun deletarPet(id: UUID) {
        if (!petRepository.existsById(id)) {
            throw Exception("Pet com ID $id não encontrado")
        }
        petRepository.deleteById(id)
    }
}