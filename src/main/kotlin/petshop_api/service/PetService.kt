import org.springframework.stereotype.Service
import petshop_api.repository.PetRepository
import java.util.UUID

@Service
class PetService(
    private val petRepository: PetRepository
) {
    fun adicionarPet(pet: Pet): Pet {
        val petExists = petRepository.findByName(pet.nome.toString())

        if (petExists == null) {
            return petRepository.save(pet)
        }
            return throw Exception("Pet já cadastrado no sistema")
    }

    fun deletarPet(pet: Pet) {
        if ( petRepository.existsById(pet.id)) {
            petRepository.deleteById(pet.id)
        }

        return throw Exception("Pet não exencontrado")
    }

    fun listarPets(pet: Pet): List<Pet> {
        return petRepository.findAll()
    }

    fun atualizarPet(id: UUID, novosDados: Pet): Pet {
        val petExistente = petRepository.findById(id)
            .orElseThrow { Exception("Pet não encontrado") }

        petExistente.nome = novosDados.nome
        petExistente.idade = novosDados.idade

        return petRepository.save(petExistente)
    }
}