package petshop_api.mapper

import petshop_api.dto.TutorResponse
import petshop_api.entity.Tutor

object TutorMapper {

    fun toEntity(request: Tutor): Tutor {
        return Tutor(
            nome = request.nome,
            telefone = request.telefone,
            email = request.email
        )
    }

    fun toResponse(tutor: Tutor): TutorResponse {
        return TutorResponse(
            id = tutor.id,
            nome = tutor.nome,
            telefone = tutor.telefone,
            email = tutor.email
        )
    }
}