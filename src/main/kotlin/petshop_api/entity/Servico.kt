package petshop_api.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.util.UUID
import java.util.UUID.randomUUID

@Entity
class Servico (
    @Id
    val id: UUID = randomUUID(),

    @Column(nullable = false, length = 50)
    @NotBlank
    @Size(max = 50)
    var nome: String,

    @Column(nullable = false, length = 100)
    @NotBlank
    @Size(max = 100)
    var descricao: String,

    @Column(nullable = false, length = 100)
    @NotBlank
    @Size(max = 100)
    var valor: Double

)