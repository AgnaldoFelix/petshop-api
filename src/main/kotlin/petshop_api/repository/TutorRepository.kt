package petshop_api.repository

import petshop_api.entity.Tutor
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface TutorRepository : JpaRepository<Tutor, UUID> {

    fun findByEmail(email: String): Tutor?

    fun findByName(nome: String): Tutor

}