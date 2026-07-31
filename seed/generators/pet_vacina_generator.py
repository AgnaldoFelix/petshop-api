import random
from datetime import date, timedelta
from faker import Faker


def generate_pet_vacina(pet_id, vacina_id, faker: Faker) -> dict:
    dias = random.randint(0, 365)
    data_aplicacao = date.today() - timedelta(days=dias)
    return {
        "petId": pet_id,
        "vacinaId": vacina_id,
        "dataAplicacao": data_aplicacao.isoformat(),
    }
