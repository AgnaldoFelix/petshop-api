package petshop_api.repository

import Pet
import org.springframework.data.jpa.repository.JpaRepository
import petshop_api.entity.PetVacina
import java.util.UUID

interface PetVacinaRepository : JpaRepository<PetVacina, UUID> {
    fun findByPetId(petId: UUID): List<PetVacina>
    fun findByVacinaId(vacinaId: UUID): List<PetVacina>
}