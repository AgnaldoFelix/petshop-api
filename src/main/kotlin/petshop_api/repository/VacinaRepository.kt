package petshop_api.repository

import org.springframework.data.jpa.repository.JpaRepository
import petshop_api.entity.Vacina
import java.util.UUID

interface VacinaRepository : JpaRepository<Vacina, UUID> {

}