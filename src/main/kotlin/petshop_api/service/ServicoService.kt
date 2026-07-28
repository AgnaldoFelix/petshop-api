package petshop_api.service

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import petshop_api.dto.ServicoRequest
import petshop_api.dto.ServicoResponse
import petshop_api.mapper.ServicoMapper
import petshop_api.repository.ServicoRepository
import java.util.ArrayList
import java.util.UUID

@Service
class ServicoService (
    private val servicoRepository: ServicoRepository
) {

    fun adicionarServico(request: ServicoRequest): ServicoResponse {
        val servicoExistente = servicoRepository.findByNome(request.nome)

        if (servicoExistente != null) {
            throw Exception("Já existe um serviço com o nome: ${request.nome}")
        }

        val servico = ServicoMapper.toEntity(request)

        val servicoSalvo = servicoRepository.save(servico)

        return ServicoMapper.toResponse(servicoSalvo)
    }

    @Transactional
    fun editarServico(id: UUID, novosDados: ServicoRequest): ServicoResponse {
        val servico = servicoRepository.findById(id)
            .orElseThrow { Exception("Serviço não encontrado") }

        servico.nome = novosDados.nome
        servico.descricao = novosDados.descricao
        servico.valor = novosDados.valor

        val servicoAtualizado = servicoRepository.save(servico)
        return ServicoMapper.toResponse(servicoAtualizado)
    }


    fun buscarPorNome(nome: String): List<ServicoResponse> {
        val servicos = servicoRepository.findByNome(nome)

        if (servicos.isEmpty()) {
            throw Exception("Serviço não encontrado: ${nome}")
        }

        val response = ArrayList<ServicoResponse>()

        for (servico in servicos) {
            response.add(ServicoMapper.toResponse(servico))
        }

        return response
    }

    @Transactional
    fun deletarServico(id: UUID) {
        val servicoExists = servicoRepository.findById(id)

        if (servicoExists == null) {
            throw Exception("Serviço não encontrado")
        }

        servicoRepository.deleteById(id)
    }



    fun listarServicos(): List<ServicoResponse> {

        val servicos = servicoRepository.findAll()

        if (servicos.isEmpty()) {
            throw Exception("Nenhum serviço encontrado")
        }

        val response = ArrayList<ServicoResponse>()

        for (servico in servicos) {
            response.add(ServicoMapper.toResponse((servico)))
        }

        return response
    }
}