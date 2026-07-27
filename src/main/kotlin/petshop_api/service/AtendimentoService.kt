package petshop_api.service

import StatusAtendimento
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import petshop_api.dto.AtendimentoRequest
import petshop_api.dto.AtendimentoResponse
import petshop_api.entity.Atendimento
import petshop_api.enums.NivelEmergencia
import petshop_api.mapper.AtendimentoMapper
import petshop_api.repository.AtendimentoRepository
import petshop_api.repository.PetRepository
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID
import kotlin.collections.*
import java.util.*

@Service
class AtendimentoService(
    private val atendimentoRepository: AtendimentoRepository,
    private val petRepository: PetRepository
) {

    @Transactional
    fun realizarAtendimento(request: AtendimentoRequest): AtendimentoResponse {
        val petExistente = petRepository.findById(request.petId)
            .orElseThrow { Exception("Pet não encontrado") }

        val decisaoTomada = when (request.nivelEmergencia) {
            NivelEmergencia.BAIXO -> "Banho e tosa"
            NivelEmergencia.MEDIO -> "Tosa e acompanhamento clínico"
            NivelEmergencia.ALTO -> "Direto para o atendimento clínico"
            null -> "Atendimento padrão"
        }

        val novoAtendimento = Atendimento(
            petId = petExistente.id,
            dataAtendimento = request.dataAtendimento ?: LocalDate.now(),
            nivelEmergencia = request.nivelEmergencia,
            observacao = request.observacao,
            decisao = decisaoTomada,
            status = StatusAtendimento.AGUARDANDO.toString(),
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

        atendimentos.forEach { atendimento ->

            val pet = petRepository.findById(atendimento.petId)
                .orElseThrow { Exception("Pet não encontrado") }

            val atendimentoResponse = AtendimentoMapper.toResponse(
                atendimento,
                pet.nome
            )

            resposta.add(atendimentoResponse)
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
            .orElseThrow { Exception("Atendimento não encontrado") }

        if (atendimento.finalizado) {
            throw Exception("Atendimento já está finalizado")
        }

        if (atendimento.status == StatusAtendimento.CANCELADO.toString()) {
            throw Exception("Atendimento cancelado não pode ser finalizado")
        }

        atendimento.finalizado = true
        atendimento.status = StatusAtendimento.FINALIZADO.toString()
        atendimento.dataFinalizacao = LocalDate.now()
        atendimento.updatedAt = LocalDateTime.now()

        val atendimentoFinalizado = atendimentoRepository.save(atendimento)

        if (observacaoFinal != null) {
            atendimento.observacao = (atendimento.observacao ?: "") +
                    "\n\n--- OBSERVAÇÃO FINAL ---\n$observacaoFinal"
        }

        return AtendimentoMapper.toResponse(
            atendimentoFinalizado,
            observacaoFinal.toString(),
        )
    }

    @Transactional
    fun iniciarAtendimento(atendimentoId: UUID): AtendimentoResponse {

        val atendimento = atendimentoRepository.findById(atendimentoId)
            .orElseThrow { Exception("Atendimento não encontrado") }

        if (atendimento.status != StatusAtendimento.AGUARDANDO.toString()) {
            throw Exception("Atendimento não pode ser iniciado. Status atual: ${atendimento.status}")
        }

        if (atendimento.finalizado) {
            throw Exception("Atendimento já foi finalizado")
        }

        atendimento.status = StatusAtendimento.EM_ANDAMENTO.toString()
        atendimento.updatedAt = LocalDateTime.now()

        val atendimentoSalvo = atendimentoRepository.save(atendimento)

        val pet = petRepository.findById(atendimentoSalvo.petId)
            .orElseThrow { Exception("Pet não encontrado") }

        return AtendimentoMapper.toResponse(
            atendimentoSalvo,
            pet.nome
        )
    }

    @Transactional
    fun cancelarAtendimento(atendimentoId: UUID, motivo: String): AtendimentoResponse {
        val atendimento = atendimentoRepository.findById(atendimentoId)
            .orElseThrow { Exception("Atendimento não encontrado") }

        if (atendimento.finalizado) {
            throw Exception("Atendimento finalizado não pode ser cancelado")
        }

        if (atendimento.status == StatusAtendimento.CANCELADO.toString()) {
            throw Exception("Atendimento já está cancelado")
        }

        atendimento.status = StatusAtendimento.CANCELADO.toString()
        atendimento.finalizado = true
        atendimento.dataCancelamento = LocalDate.now()
        atendimento.motivoCancelamento = motivo
        atendimento.updatedAt = LocalDateTime.now()

        val atendimentoCancelado = atendimentoRepository.save(atendimento)

        val pet = petRepository.findById(atendimentoCancelado.petId)
            .orElseThrow { Exception("Pet não encontrado") }

        return AtendimentoMapper.toResponse(
            atendimentoCancelado,
            pet.nome
        )
    }

    @Transactional
    fun registrarEmergencia(petId: UUID, descricao: String): AtendimentoResponse {
        val pet = petRepository.findById(petId)
            .orElseThrow { Exception("Pet não encontrado") }

        val atendimentoEmergencia = Atendimento(
            petId = pet.id,
            nivelEmergencia = NivelEmergencia.ALTO,
            observacao = "🔴 EMERGÊNCIA: $descricao",
            decisao = "Direto para o atendimento clínico - PRIORIDADE MÁXIMA",
            status = StatusAtendimento.EM_ANDAMENTO.toString(),
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
            .orElseThrow { Exception("Atendimento não encontrado") }
        val pet = petRepository.findById(atendimento.petId)
            .orElseThrow { Exception("Pet não encontrado") }
        return AtendimentoMapper.toResponse(atendimento, pet.nome)
    }

    fun buscarHistoricoPorPet(petId: UUID): List<AtendimentoResponse> {
        val pet = petRepository.findById(petId)
            .orElseThrow { Exception("Pet não encontrado") }

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
            throw Exception("Data de início não pode ser maior que data de fim")
        }

        val atendimentos = atendimentoRepository.findByDataAtendimentoBetween(dataInicio, dataFim)
        val resposta = ArrayList<AtendimentoResponse>()

        for (atendimento in atendimentos) {
            val pet = petRepository.findById(atendimento.petId)
                .orElseThrow { Exception("Pet não encontrado") }
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
                .orElseThrow { Exception("Pet não encontrado") }
            resposta.add(AtendimentoMapper.toResponse(atendimento, pet.nome))
        }

        return resposta
    }

    fun contarAtendimentosPorPet(petId: UUID): Int {
        petRepository.findById(petId)
            .orElseThrow { Exception("Pet não encontrado") }
        return atendimentoRepository.countByPetId(petId)
    }

    fun contarAtendimentosPorPeriodo(
        dataInicio: LocalDate,
        dataFim: LocalDate
    ): Int {
        if (dataInicio.isAfter(dataFim)) {
            throw Exception("Data de início não pode ser maior que data de fim")
        }
        return atendimentoRepository.countByDataAtendimentoBetween(dataInicio, dataFim)
    }

    fun buscarUltimosAtendimentos(limite: Int = 10): List<AtendimentoResponse> {
        val atendimentos = atendimentoRepository.findTopNByOrderByCreatedAtDesc(limite)
        val resposta = ArrayList<AtendimentoResponse>()

        for (atendimento in atendimentos) {
            val pet = petRepository.findById(atendimento.petId)
                .orElseThrow { Exception("Pet não encontrado") }
            resposta.add(AtendimentoMapper.toResponse(atendimento, pet.nome))
        }

        return resposta
    }
}
