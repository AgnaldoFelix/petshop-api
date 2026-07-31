import uuid
from faker import Faker

VACINAS = [
    ("Antirrábica", "Vacina antirrábica anual"),
    ("V4", "Vacina múltipla para cães e gatos"),
    ("V5", "Vacina polivalente com reforço"),
    ("Leucemia Felina", "Vacina contra leucemia felina"),
    ("Giárdia", "Vacina contra giardíase"),
    ("Cinomose", "Vacina preventiva contra cinomose"),
    ("Parvovirose", "Vacina contra parvovirose"),
    ("Hepatite", "Vacina contra hepatite canina"),
]


def generate_vacina(faker: Faker, index: int) -> dict:
    nome, descricao = faker.random_element(VACINAS)
    nome_unico = f"{nome} {index} {uuid.uuid4().hex[:4]}"
    return {
        "id": str(uuid.uuid4()),
        "nome": nome_unico,
        "descricao": descricao,
    }
