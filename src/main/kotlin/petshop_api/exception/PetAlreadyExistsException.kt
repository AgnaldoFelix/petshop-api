package petshop_api.exception

import java.util.UUID

class PetAlreadyExistsException(
    nome: String,
    tutorId: UUID
) : BusinessException("Pet $nome já cadastrado para este tutor $tutorId")
