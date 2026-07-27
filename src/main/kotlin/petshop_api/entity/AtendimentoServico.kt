package petshop_api.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID
import java.util.UUID.randomUUID

@Entity
@Table(name = "atendimento_servico")
class AtendimentoServico(
    @Id
    val id: UUID = randomUUID(),

    @Column(name = "atendimento_id", nullable = false)
    val atendimentoId: UUID,

    @Column(name = "servico_id", nullable = false)
    val servicoId: UUID
)