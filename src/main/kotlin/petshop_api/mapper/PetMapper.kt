package petshop_api.mapper

import petshop_api.dto.PetRequest
import petshop_api.dto.PetResponse
import petshop_api.entity.Pet
import java.time.LocalDateTime

object PetMapper {

    fun toEntity(request: PetRequest): Pet {
        return Pet(
            tutorId = request.tutorId,
            nome = request.nome,
            idade = request.idade,
            especie = request.especie,
            raca = request.raca,
            dataNascimento = request.dataNascimento,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )
    }

    fun toResponse(pet: Pet, tutorNome: String): PetResponse {
        return PetResponse(
            id = pet.id,
            tutorId = pet.tutorId,
            tutorNome = tutorNome,
            nome = pet.nome,
            idade = pet.idade,
            especie = pet.especie,
            raca = pet.raca,
            dataNascimento = pet.dataNascimento,
            createdAt = pet.createdAt,
            updatedAt = pet.updatedAt
        )
    }
}