package petshop_api.repository

import org.springframework.data.jpa.repository.JpaRepository
import petshop_api.entity.Atendimento
import java.util.UUID

interface AtendimentoRepository : JpaRepository<Atendimento, UUID> {

}