package petshop_api.exception

class VacinaAlreadyExistsException(
    nome: String
) : BusinessException("Vacina já cadastrada com o nome: $nome")
