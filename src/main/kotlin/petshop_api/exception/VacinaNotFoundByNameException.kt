package petshop_api.exception

class VacinaNotFoundByNameException(
    nome: String
) : BusinessException("Vacina com nome $nome não encontrada")
