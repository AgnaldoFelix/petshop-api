package petshop_api.mapper

import petshop_api.dto.AtendimentoServicoRequest
import petshop_api.dto.AtendimentoServicoResponse
import petshop_api.entity.AtendimentoServico

object AtendimentoServicoMapper {

    fun toEntity(request: AtendimentoServicoRequest): AtendimentoServico {
        return AtendimentoServico(
            atendimentoId = request.atendimentoId,
            servicoId = request.servicoId
        )
    }

    fun toResponse(
        atendimentoServico: AtendimentoServico,
        servicoNome: String,
        servicoValor: Double
    ): AtendimentoServicoResponse {
        return AtendimentoServicoResponse(
            id = atendimentoServico.id,
            atendimentoId = atendimentoServico.atendimentoId,
            servicoId = atendimentoServico.servicoId,
            servicoNome = servicoNome,
            servicoValor = servicoValor
        )
    }
}