import random
import uuid
from datetime import date, timedelta
from faker import Faker

SPECIES = [
    ("Cachorro", ["Labrador", "Poodle", "Golden Retriever", "Bulldog", "Pastor Alemão"]),
    ("Gato", ["Siamês", "Persa", "Maine Coon", "SRD", "Ragdoll"]),
    ("Coelho", ["Anão", "Lop", "Nova Zelândia", "Angorá"]),
    ("Ave", ["Calopsita", "Periquito", "Canário"]),
]


def generate_pet(tutor_id, faker: Faker, index: int) -> dict:
    especie, racas = random.choice(SPECIES)
    base_name = faker.first_name()
    nome = f"{base_name} {index} {uuid.uuid4().hex[:4]}"
    idade = random.randint(1, 15)
    nascimento = faker.date_of_birth(minimum_age=1, maximum_age=15)
    return {
        "tutorId": tutor_id,
        "nome": nome,
        "idade": str(idade),
        "especie": especie,
        "raca": random.choice(racas),
        "dataNascimento": nascimento.isoformat(),
    }
