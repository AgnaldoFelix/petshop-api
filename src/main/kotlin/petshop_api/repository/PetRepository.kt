package petshop_api.repository


import petshop_api.entity.Pet
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface PetRepository : JpaRepository<Pet, UUID> {
    fun findByNome(nome: String): List<Pet>

    fun findByTutorId(tutorId: UUID): List<Pet>

    fun findByNomeAndTutorId(nome: String, tutorId: UUID): List<Pet>
}