package petshop_api.exception

import java.util.UUID

class AtendimentoNotFoundException(
    id: UUID
) : BusinessException("Atendimento $id não encontrado")
