package petshop_api.exception

import java.util.UUID

class TutorNotFoundException(
    id: UUID
): BusinessException("Tutor $id Não encontrado")