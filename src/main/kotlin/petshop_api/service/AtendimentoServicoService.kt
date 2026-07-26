package petshop_api.service

import org.springframework.stereotype.Service
import petshop_api.entity.AtendimentoServico
import petshop_api.repository.AtendimentoServicoRepository
import java.util.UUID

@Service
class AtendimentoServicoService(
    private val atendimentoServicoRepository: AtendimentoServicoRepository
) {


    fun vincularServico(atendimentoId: UUID, servicoId: UUID): AtendimentoServico {
        val vínculo = AtendimentoServico(
            atendimentoId = atendimentoId,
            servicoId = servicoId
        )
        return atendimentoServicoRepository.save(vínculo)
    }


    fun listarPorAtendimento(atendimentoId: UUID): List<AtendimentoServico> {
        return atendimentoServicoRepository.findByAtendimentoId(atendimentoId)
    }

    fun removerServicoAtendimento(id: UUID) {
        atendimentoServicoRepository.deleteById(id)
    }
}