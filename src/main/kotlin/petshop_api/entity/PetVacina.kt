package petshop_api.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.validation.constraints.NotBlank
import java.time.LocalDate
import java.util.UUID
import java.util.UUID.randomUUID

@Entity
class PetVacina (
    @Id
    val id: UUID = randomUUID(),

    @Column(name = "vacina_id", nullable = false)
    @NotBlank
    val vacinaId: UUID,

    @Column(name = "pet_id", nullable = false)
    @NotBlank
    val petId: UUID,

    @Column(nullable = false)
    @NotBlank
    val dataAplicacao: LocalDate
)