package petshop_api.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID
import java.util.UUID.randomUUID

@Entity
@Table(name = "pet")
class Pet(

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    val id: UUID = randomUUID(),

    @Column(name = "tutor_id", nullable = false)
    var tutorId: UUID,

    @Column(name = "nome", nullable = false, length = 100)
    @NotBlank
    @Size(max = 100)
    var nome: String,

    @Column(name = "idade", length = 2)
    @Size(max = 2)
    var idade: String? = null,

    @Column(name = "especie", nullable = false, length = 50)
    @NotBlank
    @Size(max = 50)
    var especie: String,

    @Column(name = "raca", length = 100)
    @Size(max = 100)
    var raca: String? = null,

    @Column(name = "data_nascimento")
    var dataNascimento: LocalDate? = null,

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now()
)