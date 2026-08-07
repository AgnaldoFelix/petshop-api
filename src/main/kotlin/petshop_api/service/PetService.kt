package petshop_api.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import petshop_api.dto.PetRequest
import petshop_api.dto.PetResponse
import petshop_api.entity.Pet
import petshop_api.exception.PetAlreadyExistsException
import petshop_api.exception.PetNotFoundException
import petshop_api.exception.PetsNotFoundByNameException
import petshop_api.exception.TutorNotFoundException
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
        val petExistente = petRepository.findByNomeAndTutorId(request.nome, request.tutorId)

        if (!petExistente.isEmpty()) {
            throw PetAlreadyExistsException(request.nome, request.tutorId)
        }

        val pet = PetMapper.toEntity(request)
        val petSalvo = petRepository.save(pet)

        val tutor = tutorRepository.findById(request.tutorId)
            .orElseThrow { TutorNotFoundException(request.tutorId) }

        return PetMapper.toResponse(petSalvo, tutor.nome)
    }

    fun listarPets(): List<PetResponse> {
        val pets = petRepository.findAll()
        val resposta = ArrayList<PetResponse>()

        for (pet in pets) {
            val tutor = tutorRepository.findById(pet.tutorId)
                .orElseThrow { TutorNotFoundException(pet.tutorId) }
            resposta.add(PetMapper.toResponse(pet, tutor.nome))
        }

        return resposta
    }

    fun buscarPetPorId(id: UUID): PetResponse {
        val pet = petRepository.findById(id)
            .orElseThrow { PetNotFoundException(id) }

        val tutor = tutorRepository.findById(pet.tutorId)
            .orElseThrow { TutorNotFoundException(pet.tutorId) }

        return PetMapper.toResponse(pet, tutor.nome)
    }

    fun buscarPetsPorTutor(tutorId: UUID): List<PetResponse> {
        val tutor = tutorRepository.findById(tutorId)
            .orElseThrow { TutorNotFoundException(tutorId) }

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
            throw PetsNotFoundByNameException(nome)
        }

        for (pet in pets) {
            val tutor = tutorRepository.findById(pet.tutorId)
                .orElseThrow { TutorNotFoundException(pet.tutorId) }
            resposta.add(PetMapper.toResponse(pet, tutor.nome))
        }

        return resposta
    }

    @Transactional
    fun atualizarPet(id: UUID, request: PetRequest): PetResponse {
        val petExistente = petRepository.findById(id)
            .orElseThrow { PetNotFoundException(id) }

        petExistente.nome = request.nome
        petExistente.idade = request.idade
        petExistente.especie = request.especie
        petExistente.raca = request.raca
        petExistente.dataNascimento = request.dataNascimento

        val petAtualizado = petRepository.save(petExistente)

        val tutor = tutorRepository.findById(petAtualizado.tutorId)
            .orElseThrow { TutorNotFoundException(petAtualizado.tutorId) }

        return PetMapper.toResponse(petAtualizado, tutor.nome)
    }

    @Transactional
    fun deletarPet(id: UUID) {
        if (!petRepository.existsById(id)) {
            throw PetNotFoundException(id)
        }
        petRepository.deleteById(id)
    }
}