import random
from datetime import date, timedelta
from faker import Faker

NIVEIS = ["BAIXO", "MEDIO", "ALTO"]


def generate_atendimento(pet_id, faker: Faker, today: date, max_dias: int) -> dict:
    data_atendimento = today + timedelta(days=random.randint(0, max_dias))
    emergencia = random.choices(NIVEIS, weights=[0.6, 0.3, 0.1], k=1)[0]
    return {
        "petId": pet_id,
        "dataAtendimento": data_atendimento.isoformat(),
        "nivelEmergencia": emergencia,
        "observacao": faker.sentence(nb_words=8),
    }
