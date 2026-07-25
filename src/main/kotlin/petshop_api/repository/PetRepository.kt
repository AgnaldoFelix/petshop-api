package petshop_api.repository

import Pet
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface PetRepository : JpaRepository<Pet, UUID> {
    fun findByName(name: String): List<Pet>
}