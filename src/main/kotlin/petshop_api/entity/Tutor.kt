package petshop_api.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.util.UUID
import java.time.LocalDateTime

@Entity
class Tutor(

    @Id
    val id: UUID = UUID.randomUUID(),

    @Column(nullable = false, length = 150)
    @NotBlank
    @Size(max = 150)
    var nome: String,

    @Column(nullable = false, length = 20)
    @NotBlank
    @Size(max = 20)
    var telefone: String,

    @Column(nullable = false, length = 150, unique = true)
    @Email
    @Size(max = 150)
    var email: String?,


    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @UpdateTimestamp
    @Column(nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now()
)