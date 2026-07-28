package petshop_api.mapper

import petshop_api.dto.ServicoRequest
import petshop_api.dto.ServicoResponse
import petshop_api.entity.Servico
import java.time.LocalDateTime

object ServicoMapper {

    fun toEntity(request: ServicoRequest): Servico {
        return Servico(
            nome = request.nome,
            descricao = request.descricao,
            valor = request.valor
        )
    }

    fun toResponse(servico: Servico): ServicoResponse {
        return ServicoResponse(
            id = servico.id,
            nome = servico.nome,
            descricao = servico.descricao,
            valor = servico.valor,
            createdAt = servico.createdAt,
            updatedAt = servico.updatedAt
        )
    }
}