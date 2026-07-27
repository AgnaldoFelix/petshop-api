package petshop_api.controller

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import petshop_api.dto.TutorRequest
import petshop_api.dto.TutorResponse
import petshop_api.entity.Tutor
import petshop_api.service.TutorService

import java.util.UUID

@RestController
@RequestMapping("/api/tutores")
class TutorController(
    private val tutorService: TutorService
) {

    @PostMapping
    fun cadastrarTutor(
        @Valid @RequestBody request: TutorRequest
    ): ResponseEntity<TutorResponse> {
        val response = tutorService.cadastrarTutor(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @GetMapping("/{id}")
    fun buscarTutorPorId(
        @PathVariable id: UUID
    ): ResponseEntity<TutorResponse> {
        val response = tutorService.buscarTutorPorId(id)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/buscar")
    fun buscarTutorPorNome(
        @RequestParam nome: String
    ): ResponseEntity<List<TutorResponse>> {
        val response = tutorService.buscarTutorPorNome(nome)
        return ResponseEntity.ok(response)
    }

    @GetMapping
    fun listarTutores(): ResponseEntity<List<TutorResponse>> {
        val response = tutorService.listarTutores()
        return ResponseEntity.ok(response)
    }

    @PutMapping("/editar")
    fun editarTutor(
        @PathVariable id: UUID, request: TutorRequest
    ): ResponseEntity<TutorResponse> {
        val response = tutorService.editarTutor(id, request)

        return ResponseEntity.ok(response)
    }
}