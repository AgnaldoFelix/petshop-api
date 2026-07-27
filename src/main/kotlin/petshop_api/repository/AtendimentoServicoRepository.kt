package petshop_api.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import petshop_api.entity.AtendimentoServico
import java.util.UUID

@Repository
interface AtendimentoServicoRepository : JpaRepository<AtendimentoServico, UUID> {

    fun findByAtendimentoId(atendimentoId: UUID): List<AtendimentoServico>
}