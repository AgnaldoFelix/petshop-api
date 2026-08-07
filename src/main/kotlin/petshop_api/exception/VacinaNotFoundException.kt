package petshop_api.exception

import java.util.UUID

class VacinaNotFoundException(
    id: UUID
): BusinessException("Vacina $id não encontrada")