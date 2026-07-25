package petshop_api.entity

import jakarta.persistence.Column
import jakarta.persistence.Id
import jakarta.validation.constraints.NotBlank
import java.time.LocalDate
import java.util.UUID
import java.util.UUID.randomUUID

class PetVacina (
    @Id
    val id: UUID = randomUUID(),

    @Column(nullable = false)
    @NotBlank
    val vacina_id: UUID,

    @Column(nullable = false)
    @NotBlank
    val data_aplicacao: LocalDate
)