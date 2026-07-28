package petshop_api.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import java.util.UUID
import java.util.UUID.randomUUID

@Entity
@Table(name = "atendimento_servico")
class AtendimentoServico(
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    val id: UUID = randomUUID(),

    @Column(name = "atendimento_id", nullable = false)
    val atendimentoId: UUID,

    @Column(name = "servico_id", nullable = false)
    val servicoId: UUID,

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now()
)