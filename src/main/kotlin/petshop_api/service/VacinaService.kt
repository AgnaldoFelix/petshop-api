package petshop_api.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import petshop_api.dto.VacinaRequest
import petshop_api.dto.VacinaResponse
import petshop_api.entity.Vacina
import petshop_api.exception.VacinaAlreadyExistsException
import petshop_api.exception.VacinaNotFoundByNameException
import petshop_api.exception.VacinaNotFoundException
import petshop_api.mapper.VacinaMapper
import petshop_api.repository.VacinaRepository
import java.util.ArrayList
import java.util.UUID

@Service
class VacinaService(
    private val vacinaRepository: VacinaRepository
) {

    @Transactional
    fun cadastrarVacina(request: VacinaRequest): VacinaResponse {
        val vacinaExistente = vacinaRepository.findByNome(request.nome)

        if (vacinaExistente.isPresent) {
            throw VacinaAlreadyExistsException(request.nome)
        }

        val vacina = VacinaMapper.toEntity(request)
        val vacinaSalva = vacinaRepository.save(vacina)
        return VacinaMapper.toResponse(vacinaSalva)
    }

    fun listarVacinas(): List<VacinaResponse> {
        val vacinas = vacinaRepository.findAll()
        val resposta = ArrayList<VacinaResponse>()

        for (vacina in vacinas) {
            resposta.add(VacinaMapper.toResponse(vacina))
        }

        return resposta
    }

    fun buscarVacinaPorId(id: UUID): VacinaResponse {
        val vacina = vacinaRepository.findById(id)
            .orElseThrow { VacinaNotFoundException(id) }
        return VacinaMapper.toResponse(vacina)
    }

    fun buscarVacinaPorNome(nome: String): VacinaResponse {
        val vacina = vacinaRepository.findByNome(nome)
            .orElseThrow { VacinaNotFoundByNameException(nome) }
        return VacinaMapper.toResponse(vacina)
    }

    @Transactional
    fun editarVacina(id: UUID, request: VacinaRequest): VacinaResponse {
        val vacinaExistente = vacinaRepository.findById(id)
            .orElseThrow { VacinaNotFoundException(id) }

        vacinaExistente.nome = request.nome
        vacinaExistente.descricao = request.descricao

        val vacinaAtualizada = vacinaRepository.save(vacinaExistente)
        return VacinaMapper.toResponse(vacinaAtualizada)
    }

    @Transactional
    fun deletarVacina(id: UUID) {
        if (!vacinaRepository.existsById(id)) {
            throw VacinaNotFoundException(id)
        }
        vacinaRepository.deleteById(id)
    }
}