package petshop_api.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import petshop_api.dto.AtendimentoRequest
import petshop_api.dto.AtendimentoResponse
import petshop_api.entity.Atendimento
import petshop_api.enums.NivelEmergencia
import petshop_api.enums.StatusAtendimento
import petshop_api.exception.AtendimentoNotFoundException
import petshop_api.exception.AtendimentoInvalidStateException
import petshop_api.exception.InvalidDateRangeException
import petshop_api.exception.PetNotFoundException
import petshop_api.mapper.AtendimentoMapper
import petshop_api.repository.AtendimentoRepository
import petshop_api.repository.PetRepository
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.ArrayList
import java.util.UUID

@Service
class AtendimentoService(
    private val atendimentoRepository: AtendimentoRepository,
    private val petRepository: PetRepository
) {

    @Transactional
    fun realizarAtendimento(request: AtendimentoRequest): AtendimentoResponse {
        val petExistente = petRepository.findById(request.petId)
            .orElseThrow { PetNotFoundException(request.petId) }

        val decisaoTomada = when (request.nivelEmergencia) {
            NivelEmergencia.BAIXO -> "Banho e tosa"
            NivelEmergencia.MEDIO -> "Tosa e acompanhamento clínico"
            NivelEmergencia.ALTO -> "Direto para o atendimento clínico"
        }

        val novoAtendimento = Atendimento(
            petId = petExistente.id,
            dataAtendimento = request.dataAtendimento ?: LocalDate.now(),
            nivelEmergencia = request.nivelEmergencia,
            observacao = request.observacao,
            decisao = decisaoTomada,
            status = StatusAtendimento.AGUARDANDO,
            finalizado = false,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )

        val atendimentoSalvo = atendimentoRepository.save(novoAtendimento)
        return AtendimentoMapper.toResponse(atendimentoSalvo, petExistente.nome)
    }

    fun listarAtendimentos(): List<AtendimentoResponse> {
        val atendimentos = atendimentoRepository.findAll()
        val resposta = ArrayList<AtendimentoResponse>()

        for (atendimento in atendimentos) {
            val pet = petRepository.findById(atendimento.petId)
                .orElseThrow { PetNotFoundException(atendimento.petId) }
            resposta.add(AtendimentoMapper.toResponse(atendimento, pet.nome))
        }

        return resposta
    }

    fun listarAtendimentosPorStatus(status: StatusAtendimento): List<Atendimento> {
        return atendimentoRepository.findByStatus(status)
    }

    fun listarAtendimentosAtivos(): List<Atendimento> {
        return atendimentoRepository.findByStatusNot(StatusAtendimento.FINALIZADO)
    }

    @Transactional
    fun finalizarAtendimento(atendimentoId: UUID, observacaoFinal: String? = null): AtendimentoResponse {
        val atendimento = atendimentoRepository.findById(atendimentoId)
            .orElseThrow { AtendimentoNotFoundException(atendimentoId) }

        if (atendimento.finalizado) {
            throw AtendimentoInvalidStateException("Atendimento já está finalizado")
        }

        if (atendimento.status == StatusAtendimento.CANCELADO) {
            throw AtendimentoInvalidStateException("Atendimento cancelado não pode ser finalizado")
        }

        atendimento.finalizado = true
        atendimento.status = StatusAtendimento.FINALIZADO
        atendimento.dataFinalizacao = LocalDate.now()
        atendimento.updatedAt = LocalDateTime.now()

        if (observacaoFinal != null) {
            atendimento.observacao = (atendimento.observacao ?: "") +
                    "\n\n--- OBSERVAÇÃO FINAL ---\n$observacaoFinal"
        }

        val atendimentoFinalizado = atendimentoRepository.save(atendimento)

        val pet = petRepository.findById(atendimentoFinalizado.petId)
            .orElseThrow { PetNotFoundException(atendimentoFinalizado.petId) }

        return AtendimentoMapper.toResponse(atendimentoFinalizado, pet.nome)
    }

    @Transactional
    fun iniciarAtendimento(atendimentoId: UUID): AtendimentoResponse {
        val atendimento = atendimentoRepository.findById(atendimentoId)
            .orElseThrow { AtendimentoNotFoundException(atendimentoId) }

        if (atendimento.status != StatusAtendimento.AGUARDANDO) {
            throw AtendimentoInvalidStateException("Atendimento não pode ser iniciado. Status atual: ${atendimento.status}")
        }

        if (atendimento.finalizado) {
            throw AtendimentoInvalidStateException("Atendimento já foi finalizado")
        }

        atendimento.status = StatusAtendimento.EM_ANDAMENTO
        atendimento.updatedAt = LocalDateTime.now()

        val atendimentoSalvo = atendimentoRepository.save(atendimento)

        val pet = petRepository.findById(atendimentoSalvo.petId)
            .orElseThrow { PetNotFoundException(atendimentoSalvo.petId) }

        return AtendimentoMapper.toResponse(atendimentoSalvo, pet.nome)
    }

    @Transactional
    fun cancelarAtendimento(atendimentoId: UUID, motivo: String): AtendimentoResponse {
        val atendimento = atendimentoRepository.findById(atendimentoId)
            .orElseThrow { AtendimentoNotFoundException(atendimentoId) }

        if (atendimento.finalizado) {
            throw AtendimentoInvalidStateException("Atendimento finalizado não pode ser cancelado")
        }

        if (atendimento.status == StatusAtendimento.CANCELADO) {
            throw AtendimentoInvalidStateException("Atendimento já está cancelado")
        }

        atendimento.status = StatusAtendimento.CANCELADO
        atendimento.finalizado = true
        atendimento.dataCancelamento = LocalDate.now()
        atendimento.motivoCancelamento = motivo
        atendimento.updatedAt = LocalDateTime.now()

        val atendimentoCancelado = atendimentoRepository.save(atendimento)

        val pet = petRepository.findById(atendimentoCancelado.petId)
            .orElseThrow { PetNotFoundException(atendimentoCancelado.petId) }

        return AtendimentoMapper.toResponse(atendimentoCancelado, pet.nome)
    }

    @Transactional
    fun registrarEmergencia(petId: UUID, descricao: String): AtendimentoResponse {
        val pet = petRepository.findById(petId)
            .orElseThrow { PetNotFoundException(petId) }

        val atendimentoEmergencia = Atendimento(
            petId = pet.id,
            nivelEmergencia = NivelEmergencia.ALTO,
            observacao = "🔴 EMERGÊNCIA: $descricao",
            decisao = "Direto para o atendimento clínico - PRIORIDADE MÁXIMA",
            status = StatusAtendimento.EM_ANDAMENTO,
            finalizado = false,
            dataAtendimento = LocalDate.now(),
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )

        val atendimentoSalvo = atendimentoRepository.save(atendimentoEmergencia)
        return AtendimentoMapper.toResponse(atendimentoSalvo, pet.nome)
    }

    fun buscarAtendimentoPorId(id: UUID): AtendimentoResponse {
        val atendimento = atendimentoRepository.findById(id)
            .orElseThrow { AtendimentoNotFoundException(id) }
        val pet = petRepository.findById(atendimento.petId)
            .orElseThrow { PetNotFoundException(atendimento.petId) }
        return AtendimentoMapper.toResponse(atendimento, pet.nome)
    }

    fun buscarHistoricoPorPet(petId: UUID): List<AtendimentoResponse> {
        val pet = petRepository.findById(petId)
            .orElseThrow { PetNotFoundException(petId) }

        val atendimentos = atendimentoRepository.findByPetIdOrderByCreatedAtDesc(petId)
        val resposta = ArrayList<AtendimentoResponse>()

        for (atendimento in atendimentos) {
            resposta.add(AtendimentoMapper.toResponse(atendimento, pet.nome))
        }

        return resposta
    }

    fun buscarHistoricoPorPeriodo(
        dataInicio: LocalDate,
        dataFim: LocalDate
    ): List<AtendimentoResponse> {
        if (dataInicio.isAfter(dataFim)) {
            throw InvalidDateRangeException()
        }

        val atendimentos = atendimentoRepository.findByDataAtendimentoBetween(dataInicio, dataFim)
        val resposta = ArrayList<AtendimentoResponse>()

        for (atendimento in atendimentos) {
            val pet = petRepository.findById(atendimento.petId)
                .orElseThrow { PetNotFoundException(atendimento.petId) }
            resposta.add(AtendimentoMapper.toResponse(atendimento, pet.nome))
        }

        return resposta
    }

    fun buscarHistoricoEmergencias(): List<AtendimentoResponse> {
        val atendimentos = atendimentoRepository.findByNivelEmergenciaAndStatusNot(
            NivelEmergencia.ALTO,
            StatusAtendimento.CANCELADO
        )

        val resposta = ArrayList<AtendimentoResponse>()

        for (atendimento in atendimentos) {
            val pet = petRepository.findById(atendimento.petId)
                .orElseThrow { PetNotFoundException(atendimento.petId) }
            resposta.add(AtendimentoMapper.toResponse(atendimento, pet.nome))
        }

        return resposta
    }

    fun contarAtendimentosPorPet(petId: UUID): Int {
        petRepository.findById(petId)
            .orElseThrow { PetNotFoundException(petId) }
        return atendimentoRepository.countByPetId(petId)
    }

    fun contarAtendimentosPorPeriodo(
        dataInicio: LocalDate,
        dataFim: LocalDate
    ): Int {
        if (dataInicio.isAfter(dataFim)) {
            throw InvalidDateRangeException()
        }
        return atendimentoRepository.countByDataAtendimentoBetween(dataInicio, dataFim)
    }

    fun buscarUltimosAtendimentos(limite: Int = 10): List<AtendimentoResponse> {
        val atendimentos = atendimentoRepository.findTopNByOrderByCreatedAtDesc(limite)
        val resposta = ArrayList<AtendimentoResponse>()

        for (atendimento in atendimentos) {
            val pet = petRepository.findById(atendimento.petId)
                .orElseThrow { PetNotFoundException(atendimento.petId) }
            resposta.add(AtendimentoMapper.toResponse(atendimento, pet.nome))
        }

        return resposta
    }
}