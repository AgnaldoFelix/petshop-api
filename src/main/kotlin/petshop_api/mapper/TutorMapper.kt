package petshop_api.mapper

import petshop_api.dto.TutorRequest
import petshop_api.dto.TutorResponse
import petshop_api.entity.Tutor
import java.time.LocalDateTime

object TutorMapper {

    fun toEntity(request: TutorRequest): Tutor {
        return Tutor(
            nome = request.nome,
            telefone = request.telefone,
            email = request.email,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )
    }

    fun toResponse(tutor: Tutor): TutorResponse {
        return TutorResponse(
            id = tutor.id,
            nome = tutor.nome,
            telefone = tutor.telefone,
            email = tutor.email.toString(),
            createdAt = tutor.createdAt
        )
    }
}