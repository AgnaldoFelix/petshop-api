package petshop_api.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import petshop_api.enums.NivelEmergencia
import petshop_api.enums.StatusAtendimento
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "atendimento")
data class Atendimento(
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    val id: UUID = UUID.randomUUID(),

    @Column(name = "pet_id", nullable = false)
    val petId: UUID,

    @Column(name = "data_atendimento", nullable = false)
    val dataAtendimento: LocalDate = LocalDate.now(),

    @Enumerated(EnumType.STRING)
    @Column(name = "nivel_emergencia", nullable = false, length = 20)
    val nivelEmergencia: NivelEmergencia? = null,

    @Column(name = "observacao", nullable = false, columnDefinition = "TEXT")
    var observacao: String? = null,

    @Column(name = "decisao", nullable = false, length = 100)
    val decisao: String? = null,

    @Column(name = "status", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    var status: StatusAtendimento = StatusAtendimento.AGUARDANDO,

    @Column(name = "finalizado", nullable = false)
    var finalizado: Boolean = false,

    @Column(name = "data_finalizacao")
    var dataFinalizacao: LocalDate? = null,

    @Column(name = "data_cancelamento")
    var dataCancelamento: LocalDate? = null,

    @Column(name = "motivo_cancelamento", length = 500)
    var motivoCancelamento: String? = null,

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now()
)