package petshop_api.controller

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import petshop_api.dto.PetRequest
import petshop_api.dto.PetResponse
import petshop_api.service.PetService
import java.util.UUID

@RestController
@RequestMapping("/api/pets")
class PetController(
    private val petService: PetService
) {

    @PostMapping
    fun adicionarPet(
        @Valid @RequestBody request: PetRequest
    ): ResponseEntity<PetResponse> {
        val response = petService.adicionarPet(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @GetMapping
    fun listarPets(): ResponseEntity<List<PetResponse>> {
        val response = petService.listarPets()
        return ResponseEntity.ok(response)
    }

    @GetMapping("/{id}")
    fun buscarPetPorId(
        @PathVariable id: UUID
    ): ResponseEntity<PetResponse> {
        val response = petService.buscarPetPorId(id)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/buscar")
    fun buscarPetsPorNome(
        @RequestParam(name = "nome") nome: String
    ): ResponseEntity<List<PetResponse>> {
        val response = petService.buscarPetsPorNome(nome)
        return ResponseEntity.ok(response)
    }

    @PutMapping("/{id}")
    fun atualizarPet(
        @PathVariable id: UUID,
        @Valid @RequestBody request: PetRequest
    ): ResponseEntity<PetResponse> {
        val response = petService.atualizarPet(id, request)
        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/{id}")
    fun deletarPet(
        @PathVariable id: UUID
    ): ResponseEntity<Void> {
        petService.deletarPet(id)
        return ResponseEntity.noContent().build()
    }
}