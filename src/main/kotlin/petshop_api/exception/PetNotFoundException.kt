package petshop_api.exception

import java.util.UUID

class PetNotFoundException(
    id: UUID
): BusinessException("Pet $id não encontrado")