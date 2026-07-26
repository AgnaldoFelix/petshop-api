package petshop_api.service

import petshop_api.repository.ServicoRepository

class ServicoService (
    private val servicoRepository: ServicoRepository
) {
    fun listarServicos() {
        servicoRepository.findAll()
    }
}