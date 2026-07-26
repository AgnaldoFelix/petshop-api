import org.springframework.stereotype.Service
import petshop_api.dto.TutorResponse
import petshop_api.entity.Tutor
import petshop_api.mapper.TutorMapper
import petshop_api.repository.TutorRepository
import java.util.UUID

@Service
class TutorService(
    private val tutorRepository: TutorRepository
) {

    fun cadastrarTutor(request: Tutor): TutorResponse {

        val tutor = TutorMapper.toEntity(request)

        val tutorSalvo = tutorRepository.save(tutor)

        return TutorMapper.toResponse(tutorSalvo)
    }

    fun buscarTutorPorNome(nome: String): Tutor {
        val tutorEncontrado = tutorRepository.findByName(nome)

        if (tutorEncontrado == null) {
            throw Exception("Tutor não encontrado")
        }
        return tutorEncontrado
    }

    fun buscarPorId(id: UUID): Tutor {
        return tutorRepository.findById(id)
            .orElseThrow { Exception("Tutor não encontrado") }
    }

    fun editarTutor(id: UUID, novosDados: Tutor): Tutor {
        val tutorExistente = tutorRepository.findById(id)
            .orElseThrow { Exception("Tutor não encontrado") }

        val tutorAtualizado = Tutor(
            id = tutorExistente.id, // ⚠️ Mantém o mesmo ID!
            nome = novosDados.nome,
            telefone = novosDados.telefone,
            email = novosDados.email,
            createdAt = tutorExistente.createdAt // Preserva a data de criação original
        )

        return tutorRepository.save(tutorAtualizado)
    }

    fun listarTutores() {
        tutorRepository.findAll()
    }

    fun buscarTutorEmail(tutor: Tutor) {
        tutorRepository.findByEmail(tutor.email.toString())
    }



}