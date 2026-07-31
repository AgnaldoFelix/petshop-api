package petshop_api.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID
import java.util.UUID.randomUUID

@Entity
@Table(name = "pet_vacina")
class PetVacina(
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    val id: UUID = randomUUID(),

    @Column(name = "vacina_id", nullable = false)
    @NotNull
    val vacinaId: UUID,

    @Column(name = "pet_id", nullable = false)
    @NotNull
    val petId: UUID,

    @Column(name = "data_aplicacao", nullable = false)
    @NotNull
    val dataAplicacao: LocalDate,

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now()
)