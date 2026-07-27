package petshop_api.entity

import StatusAtendimento
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import petshop_api.enums.NivelEmergencia
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "atendimentos")
data class Atendimento(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID = UUID.randomUUID(),

    @Column(name = "pet_id", nullable = false)
    val petId: UUID,

    @Column(name = "data_atendimento")
    val dataAtendimento: LocalDate = LocalDate.now(),

    @Enumerated(EnumType.STRING)
    @Column(name = "nivel_emergencia")
    val nivelEmergencia: NivelEmergencia? = null,

    @Column(name = "observacao", length = 1000)
    var observacao: String? = null,  // var para permitir alteração

    @Column(name = "decisao")
    val decisao: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    var status: String = StatusAtendimento.AGUARDANDO.toString(),

    @Column(name = "finalizado")
    var finalizado: Boolean = false,

    @Column(name = "data_finalizacao")
    var dataFinalizacao: LocalDate? = null,

    @Column(name = "data_cancelamento")
    var dataCancelamento: LocalDate? = null,

    @Column(name = "motivo_cancelamento", length = 500)
    var motivoCancelamento: String? = null,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at", nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now()
)