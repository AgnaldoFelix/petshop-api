import random
import uuid
from faker import Faker

SERVICO_TEMPLATES = [
    ("Banho", "Banho completo com shampoo especial"),
    ("Tosa", "Tosa higiênica e aparo de pelos"),
    ("Consulta", "Consulta veterinária com avaliação clínica"),
    ("Vacinação", "Aplicação de vacina conforme calendário"),
    ("Higiene Bucal", "Limpeza dental e prevenção"),
    ("Corte de Unhas", "Aparar unhas e cuidado com patinhas"),
    ("Acompanhamento", "Acompanhamento pós-operatório"),
]


def generate_servico(faker: Faker, index: int) -> dict:
    nome, descricao = random.choice(SERVICO_TEMPLATES)
    valor = round(random.uniform(30.0, 250.0), 2)
    suffix = faker.unique.word().capitalize()
    return {
        "nome": f"{nome} {suffix} {index} {uuid.uuid4().hex[:4]}",
        "descricao": descricao,
        "valor": valor,
    }
