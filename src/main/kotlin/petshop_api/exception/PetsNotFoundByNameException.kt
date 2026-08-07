package petshop_api.exception

import petshop_api.exception.BusinessException

class PetsNotFoundByNameException(
    nome: String
) : BusinessException(
    "Nenhum pet encontrado com o nome: $nome"
)