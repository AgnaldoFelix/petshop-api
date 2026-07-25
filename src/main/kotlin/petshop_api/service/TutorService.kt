import org.springframework.stereotype.Service
import petshop_api.dto.TutorRequest
import petshop_api.dto.TutorResponse
import petshop_api.entity.Tutor
import petshop_api.mapper.TutorMapper
import petshop_api.repository.TutorRepository

@Service
class TutorService(
    private val tutorRepository: TutorRepository
) {

    fun criarTutor(request: TutorRequest): TutorResponse {

        val tutor = TutorMapper.toEntity(request)

        val tutorSalvo = tutorRepository.save(tutor)

        return TutorMapper.toResponse(tutorSalvo)
    }

    fun buscarTutor(nome: String): Tutor {
        val tutorEncontrado = tutorRepository.findByName(nome)

        if (tutorEncontrado == null) {
            throw Exception("Tutor não encontrado")
        }
        return tutorEncontrado
    }

    fun listarTutores() {
        tutorRepository.findAll()
    }
}