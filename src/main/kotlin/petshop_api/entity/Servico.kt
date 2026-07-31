package petshop_api.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import java.util.UUID
import java.util.UUID.randomUUID

@Entity
@Table(name = "servico")
class Servico(
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    val id: UUID = randomUUID(),

    @Column(name = "nome", nullable = false, length = 100)
    @NotBlank
    @Size(max = 100)
    var nome: String,

    @Column(name = "descricao", nullable = false, columnDefinition = "TEXT")
    @NotBlank
    var descricao: String,

    @Column(name = "valor", nullable = false)
    @NotNull
    @DecimalMin(value = "0.01", message = "Valor deve ser maior que zero")
    var valor: Double,

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now()
)