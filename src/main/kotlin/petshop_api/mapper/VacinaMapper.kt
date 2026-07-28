package petshop_api.mapper

import petshop_api.dto.VacinaRequest
import petshop_api.dto.VacinaResponse
import petshop_api.entity.Vacina

object VacinaMapper {

    fun toEntity(request: VacinaRequest): Vacina {
        return Vacina(
            id = request.id,
            nome = request.nome,
            descricao = request.descricao,
        )
    }

    fun toResponse(vacina: Vacina ):VacinaResponse {
        return VacinaResponse(
            id = vacina.id,
            nome = vacina.nome,
            descricao = vacina.descricao,
            createdAt = vacina.createdAt,
            updatedAt = vacina.updatedAt
        )
    }
}