import json
import sys
import time
from urllib import request, error

BASE_URL = "http://localhost:8081"


def post_json(path: str, payload: dict):
    data = json.dumps(payload).encode("utf-8")
    req = request.Request(
        BASE_URL + path,
        data=data,
        headers={"Content-Type": "application/json"},
        method="POST",
    )
    try:
        with request.urlopen(req, timeout=10) as resp:
            body = resp.read().decode("utf-8")
            return resp.status, json.loads(body) if body else None
    except error.HTTPError as exc:
        body = exc.read().decode("utf-8", errors="ignore")
        print(f"ERRO HTTP {exc.code} em {path}: {body}")
        raise


def put_json(path: str, payload: dict):
    data = json.dumps(payload).encode("utf-8")
    req = request.Request(
        BASE_URL + path,
        data=data,
        headers={"Content-Type": "application/json"},
        method="PUT",
    )
    try:
        with request.urlopen(req, timeout=10) as resp:
            body = resp.read().decode("utf-8")
            return resp.status, json.loads(body) if body else None
    except error.HTTPError as exc:
        body = exc.read().decode("utf-8", errors="ignore")
        print(f"ERRO HTTP {exc.code} em {path}: {body}")
        raise


def main():
    print("Iniciando geração de massa de dados...\n")

    tutores = [
        {"nome": "Ana Souza", "telefone": "11911111111", "email": "ana@email.com"},
        {"nome": "Bruno Lima", "telefone": "11922222222", "email": "bruno@email.com"},
        {"nome": "Carla Mendes", "telefone": "11933333333", "email": "carla@email.com"},
    ]

    created_tutors = []
    for tutor in tutores:
        status, body = post_json("/api/tutores", tutor)
        print(f"Tutor criado: {body}")
        created_tutors.append(body)
        time.sleep(0.2)

    pets = [
        {"tutorId": created_tutors[0]["id"], "nome": "Rex", "idade": "3", "especie": "Cachorro", "raca": "Labrador", "dataNascimento": "2020-01-10"},
        {"tutorId": created_tutors[0]["id"], "nome": "Mia", "idade": "2", "especie": "Gato", "raca": "Siamês", "dataNascimento": "2021-03-15"},
        {"tutorId": created_tutors[1]["id"], "nome": "Thor", "idade": "4", "especie": "Cachorro", "raca": "Pastor Alemão", "dataNascimento": "2019-07-20"},
    ]

    created_pets = []
    for pet in pets:
        status, body = post_json("/api/pets", pet)
        print(f"Pet criado: {body}")
        created_pets.append(body)
        time.sleep(0.2)

    servicos = [
        {"nome": "Banho", "descricao": "Banho completo", "valor": 50.0},
        {"nome": "Tosa", "descricao": "Tosa higiênica", "valor": 35.0},
        {"nome": "Consulta", "descricao": "Consulta veterinária", "valor": 120.0},
    ]

    created_servicos = []
    for servico in servicos:
        status, body = post_json("/api/servicos", servico)
        print(f"Serviço criado: {body}")
        created_servicos.append(body)
        time.sleep(0.2)

    vacinas = [
        {"nome": "Antirrábica", "descricao": "Vacina anual"},
        {"nome": "V5", "descricao": "Vacina multivalente"},
    ]

    created_vacinas = []
    for vacina in vacinas:
        status, body = post_json("/api/vacinas", vacina)
        print(f"Vacina criada: {body}")
        created_vacinas.append(body)
        time.sleep(0.2)

    atendimentos = [
        {"petId": created_pets[0]["id"], "dataAtendimento": "2026-07-28", "nivelEmergencia": "BAIXO", "observacao": "Primeiro atendimento"},
        {"petId": created_pets[1]["id"], "dataAtendimento": "2026-07-29", "nivelEmergencia": "MEDIO", "observacao": "Acompanhamento"},
        {"petId": created_pets[2]["id"], "dataAtendimento": "2026-07-30", "nivelEmergencia": "ALTO", "observacao": "Emergência"},
    ]

    created_atendimentos = []
    for atendimento in atendimentos:
        status, body = post_json("/api/atendimentos", atendimento)
        print(f"Atendimento criado: {body}")
        created_atendimentos.append(body)
        time.sleep(0.2)

    for atendimento in created_atendimentos:
        post_json(f"/api/atendimentos/{atendimento['id']}/iniciar", {})
        print(f"Atendimento iniciado: {atendimento['id']}")
        time.sleep(0.2)

    print("\nMassa de dados gerada com sucesso!")


if __name__ == "__main__":
    main()
