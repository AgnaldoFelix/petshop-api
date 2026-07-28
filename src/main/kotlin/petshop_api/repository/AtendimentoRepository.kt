package petshop_api.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import petshop_api.entity.Atendimento
import petshop_api.enums.NivelEmergencia
import petshop_api.enums.StatusAtendimento
import java.time.LocalDate
import java.util.UUID

interface AtendimentoRepository : JpaRepository<Atendimento, UUID> {


    // Buscas básicas
    fun findByPetIdOrderByCreatedAtDesc(petId: UUID): List<Atendimento>
    fun findByStatus(status: StatusAtendimento): List<Atendimento>
    fun findByStatusNot(status: StatusAtendimento): List<Atendimento>
    fun findByDataAtendimentoBetween(inicio: LocalDate, fim: LocalDate): List<Atendimento>
    fun countByPetId(petId: UUID): Int
    fun countByDataAtendimentoBetween(inicio: LocalDate, fim: LocalDate): Int

    // Busca por emergência
    fun findByNivelEmergenciaAndStatusNot(
        nivelEmergencia: NivelEmergencia,
        status: StatusAtendimento
    ): List<Atendimento>

    // Top N mais recentes
    @Query("SELECT a FROM Atendimento a ORDER BY a.createdAt DESC")
    fun findTopNByOrderByCreatedAtDesc(@Param("limit") limit: Int): List<Atendimento>

    // Busca por período e pet
    fun findByPetIdAndDataAtendimentoBetween(
        petId: UUID,
        inicio: LocalDate,
        fim: LocalDate
    ): List<Atendimento>
}