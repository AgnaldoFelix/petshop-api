package petshop_api.repository

import org.springframework.data.jpa.repository.JpaRepository
import petshop_api.entity.PetVacina
import java.util.UUID

interface PetVacinaRepository : JpaRepository<PetVacina, UUID> {

}