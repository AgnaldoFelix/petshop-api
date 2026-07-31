package petshop_api.controller

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import petshop_api.dto.PetResponse
import petshop_api.dto.TutorRequest
import petshop_api.dto.TutorResponse
import petshop_api.service.PetService
import petshop_api.service.TutorService
import java.util.UUID

@RestController
@RequestMapping("/api/tutores")
class TutorController(
    private val tutorService: TutorService,
    private val petService: PetService
) {

    @PostMapping
    fun cadastrarTutor(
        @Valid @RequestBody request: TutorRequest
    ): ResponseEntity<TutorResponse> {
        val response = tutorService.cadastrarTutor(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @GetMapping
    fun listarTutores(): ResponseEntity<List<TutorResponse>> {
        val response = tutorService.listarTutores()
        return ResponseEntity.ok(response)
    }

    @GetMapping("/buscar")
    fun buscarTutorPorNome(
        @RequestParam(name = "nome") nome: String
    ): ResponseEntity<List<TutorResponse>> {
        val response = tutorService.buscarTutorPorNome(nome)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/{id}")
    fun buscarTutorPorId(
        @PathVariable id: UUID
    ): ResponseEntity<TutorResponse> {
        val response = tutorService.buscarTutorPorId(id)
        return ResponseEntity.ok(response)
    }

    @PutMapping("/{id}")
    fun editarTutor(
        @PathVariable id: UUID,
        @Valid @RequestBody request: TutorRequest
    ): ResponseEntity<TutorResponse> {
        val response = tutorService.editarTutor(id, request)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/{id}/pets")
    fun listarPetsDoTutor(
        @PathVariable id: UUID
    ): ResponseEntity<List<PetResponse>> {
        val response = petService.buscarPetsPorTutor(id)
        return ResponseEntity.ok(response)
    }
}