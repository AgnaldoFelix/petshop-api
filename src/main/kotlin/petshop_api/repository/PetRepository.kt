package petshop_api.repository

import Pet
import org.springframework.data.jpa.repository.JpaRepository
import petshop_api.dto.PetResponse
import petshop_api.entity.Tutor
import java.util.UUID

interface PetRepository : JpaRepository<Pet, UUID> {
    fun findByName(name: String): List<Pet>

    fun findByTutorId(tutorId: UUID): List<Pet>

    fun findByNomeAndTutorId(petNome: String, tutorId: UUID): List<Pet>

}