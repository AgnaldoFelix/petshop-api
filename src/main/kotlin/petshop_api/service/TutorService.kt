package petshop_api.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import petshop_api.dto.TutorRequest
import petshop_api.dto.TutorResponse
import petshop_api.entity.Tutor
import petshop_api.mapper.TutorMapper
import petshop_api.repository.TutorRepository
import java.util.ArrayList
import java.util.UUID

@Service
class TutorService(
    private val tutorRepository: TutorRepository
) {

    @Transactional
    fun cadastrarTutor(request: TutorRequest): TutorResponse {
        val tutor = TutorMapper.toEntity(request)
        val tutorSalvo = tutorRepository.save(tutor)
        return TutorMapper.toResponse(tutorSalvo)
    }

    fun buscarTutorPorId(id: UUID): TutorResponse {
        val tutor = tutorRepository.findById(id)
            .orElseThrow { Exception("Tutor com ID $id não encontrado") }
        return TutorMapper.toResponse(tutor)
    }

    fun buscarTutorPorNome(nome: String): List<TutorResponse> {
        val tutores = tutorRepository.findByName(nome)
        val resposta = ArrayList<TutorResponse>()

        for (tutor in tutores) {
            resposta.add(TutorMapper.toResponse(tutor))
        }

        return resposta
    }

    fun buscarTutorPorEmail(email: String): TutorResponse {
        val tutor = tutorRepository.findByEmail(email)
            ?: throw Exception("Tutor com email $email não encontrado")
        return TutorMapper.toResponse(tutor)
    }

    @Transactional
    fun editarTutor(id: UUID, request: TutorRequest): TutorResponse {
        val tutorExistente = tutorRepository.findById(id)
            .orElseThrow { Exception("Tutor com ID $id não encontrado") }

        tutorExistente.nome = request.nome
        tutorExistente.telefone = request.telefone
        tutorExistente.email = request.email

        val tutorAtualizado = tutorRepository.save(tutorExistente)
        return TutorMapper.toResponse(tutorAtualizado)
    }

    fun listarTutores(): List<TutorResponse> {
        val tutores = tutorRepository.findAll()
        val resposta = ArrayList<TutorResponse>()

        for (tutor in tutores) {
            resposta.add(TutorMapper.toResponse(tutor))
        }

        return resposta
    }
}