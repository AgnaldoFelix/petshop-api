package petshop_api.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import petshop_api.dto.AtendimentoServicoRequest
import petshop_api.dto.AtendimentoServicoResponse
import petshop_api.exception.ServicoNotFoundException
import petshop_api.mapper.AtendimentoServicoMapper
import petshop_api.repository.AtendimentoServicoRepository
import petshop_api.repository.ServicoRepository
import java.util.ArrayList
import java.util.UUID

@Service
class AtendimentoServicoService(
    private val atendimentoServicoRepository: AtendimentoServicoRepository,
    private val servicoRepository: ServicoRepository  // ← Novo
) {

    @Transactional
    fun vincularServico(request: AtendimentoServicoRequest): AtendimentoServicoResponse {
        val servico = servicoRepository.findById(request.servicoId)
            .orElseThrow { ServicoNotFoundException(request.servicoId) }

        val atendimentoServico = AtendimentoServicoMapper.toEntity(request)

        val atendimentoServicoSalvo = atendimentoServicoRepository.save(atendimentoServico)

        return AtendimentoServicoMapper.toResponse(
            atendimentoServicoSalvo,
            servico.nome,
            servico.valor
        )
    }

    fun listarPorAtendimento(atendimentoId: UUID): List<AtendimentoServicoResponse> {
        val lista = atendimentoServicoRepository.findByAtendimentoId(atendimentoId)
        val resposta = ArrayList<AtendimentoServicoResponse>()

        for (item in lista) {
            val servico = servicoRepository.findById(item.servicoId)
                .orElseThrow { ServicoNotFoundException(item.servicoId) }

            resposta.add(AtendimentoServicoMapper.toResponse(item, servico.nome, servico.valor))
        }

        return resposta
    }

    @Transactional
    fun removerServicoAtendimento(id: UUID) {
        if (!atendimentoServicoRepository.existsById(id)) {
            throw IllegalArgumentException("Vínculo não encontrado")
        }
        atendimentoServicoRepository.deleteById(id)
    }
}