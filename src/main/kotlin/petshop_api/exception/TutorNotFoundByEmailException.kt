package petshop_api.exception

class TutorNotFoundByEmailException(
    email: String
) : BusinessException("Tutor com email $email não encontrado")
