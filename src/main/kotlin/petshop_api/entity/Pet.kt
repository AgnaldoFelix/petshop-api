package petshop_api.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID
import java.util.UUID.randomUUID

@Entity
class Pet(

    @Id
    val id: UUID = randomUUID(),

    @Column(nullable = false)
    var tutor_id: UUID,

    @Column(nullable = false, length = 100)
    @NotBlank
    @Size(max = 100)
    var nome: String,

    @Column(nullable = true, length = 2)
    @Size(max = 2)
    var idade: String? = null,

    @Column(nullable = false, length = 100)
    @NotBlank
    @Size(max = 100)
    var especie: String,

    @Column(nullable = true, length = 100)
    @Size(max = 100)
    var raca: String? = null,

    @Column(nullable = true, length = 20)
    var data_nascimento: LocalDate? = null,

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now()
)