package petshop_api.entity

import jakarta.persistence.Column
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import petshop_api.enums.NivelEmergencia
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID
import java.util.UUID.randomUUID

class Atendimento (
    @Id
    val id: UUID = randomUUID(),

    @Column(name = "pet_id", nullable = false)
    @NotBlank
    val petId: UUID,

    @Column(name = "data_atendimento", nullable = false)
    @NotBlank
    val dataAtendimento: LocalDate,

    @Column(name = "nivel_emergencia", nullable = false)
    @NotBlank
    @Enumerated(EnumType.STRING)
    val nivelEmergencia: NivelEmergencia,

    @Column(nullable = true,  length = 150)
    @Size(max = 150)
    val observacao: String,

    @Column(nullable = false, length = 50)
    @NotBlank
    @Size(max = 50)
    val decisao: String,

    @Column(nullable = false, length = 50)
    @NotBlank
    @Size(max = 50)
    var finalizado: Boolean,

    @Column(nullable = false, length = 50)
    @NotBlank
    @Size(max = 50)
    var dataFinalizacao: LocalDate = LocalDate.now(),

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @UpdateTimestamp
    @Column(nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now()


)