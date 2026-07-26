package petshop_api.repository

import org.springframework.data.jpa.repository.JpaRepository
import petshop_api.entity.Servico
import java.util.UUID

interface ServicoRepository : JpaRepository<Servico, UUID> {

}