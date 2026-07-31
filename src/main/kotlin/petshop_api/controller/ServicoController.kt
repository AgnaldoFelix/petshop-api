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
import petshop_api.dto.ServicoRequest
import petshop_api.dto.ServicoResponse
import petshop_api.service.ServicoService
import java.util.UUID

@RestController
@RequestMapping("/api/servicos")
class ServicoController(
    private val servicoService: ServicoService
) {

    @PostMapping
    fun adicionarServico(
        @Valid @RequestBody request: ServicoRequest
    ): ResponseEntity<ServicoResponse> {
        val response = servicoService.adicionarServico(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @GetMapping
    fun listarServicos(): ResponseEntity<List<ServicoResponse>> {
        val response = servicoService.listarServicos()
        return ResponseEntity.ok(response)
    }

    @GetMapping("/buscar")
    fun buscarPorNome(
        @RequestParam(name = "nome") nome: String
    ): ResponseEntity<List<ServicoResponse>> {
        val response = servicoService.buscarPorNome(nome)
        return ResponseEntity.ok(response)
    }

    @PutMapping("/{id}")
    fun editarServico(
        @PathVariable id: UUID,
        @Valid @RequestBody request: ServicoRequest
    ): ResponseEntity<ServicoResponse> {
        val response = servicoService.editarServico(id, request)
        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/{id}")
    fun deletarServico(
        @PathVariable id: UUID
    ): ResponseEntity<Void> {
        servicoService.deletarServico(id)
        return ResponseEntity.noContent().build()
    }
}


























