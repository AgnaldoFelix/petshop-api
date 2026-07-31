import uuid
from faker import Faker


def generate_tutor(faker: Faker) -> dict:
    email = faker.safe_email()
    if "@" in email:
        local, domain = email.split("@", 1)
        email = f"{local}.{uuid.uuid4().hex[:8]}@{domain}"

    return {
        "nome": faker.name(),
        "telefone": faker.numerify("11#########"),
        "email": email,
    }
