package petshop_api.exception

import java.util.UUID

class ServicoNotFoundException(
    id: UUID
): BusinessException("Servico $id não encontrado")