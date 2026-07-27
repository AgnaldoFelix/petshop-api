package petshop_api.mapper

import Pet
import petshop_api.dto.PetRequest
import petshop_api.dto.PetResponse
import java.time.LocalDateTime

object PetMapper {

    fun toEntity(request: PetRequest): Pet {
        return Pet(
            tutor_id = request.tutorId,
            nome = request.nome,
            idade = request.idade.toString(),
            especie = request.especie,
            raca = request.raca.toString(),
            data_nascimento = request.dataNascimento,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )
    }

    fun toResponse(pet: Pet, tutorNome: String): PetResponse {
        return PetResponse(
            id = pet.id,
            tutorId = pet.tutor_id,
            tutorNome = tutorNome,
            nome = pet.nome,
            idade = pet.idade,
            especie = pet.especie,
            raca = pet.raca,
            dataNascimento = pet.data_nascimento,
            createdAt = pet.createdAt,
            updatedAt = pet.updatedAt
        )
    }
}