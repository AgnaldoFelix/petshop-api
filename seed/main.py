import argparse
import random
import sys
import time
from datetime import date, timedelta
from pathlib import Path

import requests
from faker import Faker

ROOT = Path(__file__).resolve().parent
sys.path.insert(0, str(ROOT))

from config import (
    BASE_URL,
    DEFAULT_ATENDIMENTOS,
    DEFAULT_ATENDIMENTOS_CANCELADOS,
    DEFAULT_ATENDIMENTOS_FINALIZADOS,
    DEFAULT_ATENDIMENTOS_INICIADOS,
    DEFAULT_PETS,
    DEFAULT_SERVICOS,
    DEFAULT_TUTORES,
    DEFAULT_VACINAS,
    DEFAULT_VACINAS_APLICADAS,
    MAX_ATENDIMENTO_DIAS,
    MAX_VACINA_DIAS,
    REQUEST_DELAY,
)
from generators.atendimento_generator import generate_atendimento
from generators.pet_generator import generate_pet
from generators.pet_vacina_generator import generate_pet_vacina
from generators.servico_generator import generate_servico
from generators.tutor_generator import generate_tutor
from generators.vacina_generator import generate_vacina
from utils.progress import print_progress


def request_json(method: str, path: str, payload: dict | None = None, params: dict | None = None) -> dict | None:
    url = f"{BASE_URL.rstrip('/')}{path}"
    headers = {"Content-Type": "application/json"}
    response = requests.request(method, url, json=payload, params=params, headers=headers, timeout=20)
    try:
        response.raise_for_status()
    except requests.HTTPError as exc:
        content = response.text or response.reason
        raise RuntimeError(f"HTTP {response.status_code} error for {method} {url}: {content}") from exc
    if response.text:
        return response.json()
    return None


def create_tutores(count: int, faker: Faker) -> list[dict]:
    print(f"Criando {count} tutores...")
    tutores = []
    for index in range(1, count + 1):
        payload = generate_tutor(faker)
        tutor = request_json("POST", "/api/tutores", payload)
        tutores.append(tutor)
        print_progress(index, count, "Tutores")
        time.sleep(REQUEST_DELAY)
    return tutores


def create_pets(tutores: list[dict], total_pets: int, faker: Faker) -> list[dict]:
    print(f"Criando {total_pets} pets para {len(tutores)} tutores...")
    pets = []
    base_pets = total_pets // len(tutores)
    extra_pets = total_pets % len(tutores)

    for index, tutor in enumerate(tutores, start=1):
        pet_count = base_pets + (1 if index <= extra_pets else 0)
        for pet_index in range(1, pet_count + 1):
            payload = generate_pet(tutor["id"], faker, pet_index)
            pet = request_json("POST", "/api/pets", payload)
            pets.append(pet)
            print_progress(len(pets), total_pets, "Pets")
            time.sleep(REQUEST_DELAY)

    return pets


def create_servicos(count: int, faker: Faker) -> list[dict]:
    print(f"Criando {count} serviços...")
    servicos = []
    for index in range(1, count + 1):
        payload = generate_servico(faker, index)
        servico = request_json("POST", "/api/servicos", payload)
        servicos.append(servico)
        print_progress(index, count, "Serviços")
        time.sleep(REQUEST_DELAY)
    return servicos


def create_vacinas(count: int, faker: Faker) -> list[dict]:
    print(f"Criando {count} vacinas...")
    vacinas = []
    for index in range(1, count + 1):
        payload = generate_vacina(faker, index)
        vacina = request_json("POST", "/api/vacina/cadastrar", payload)
        vacinas.append(vacina)
        print_progress(index, count, "Vacinas")
        time.sleep(REQUEST_DELAY)
    return vacinas


def create_pet_vacinas(pets: list[dict], vacinas: list[dict], total_aplicacoes: int, faker: Faker) -> list[dict]:
    print(f"Criando {total_aplicacoes} vacinas aplicadas...")
    if total_aplicacoes <= 0:
        return []

    combinations = [(pet["id"], vacina["id"]) for pet in pets for vacina in vacinas]
    total_aplicacoes = min(total_aplicacoes, len(combinations))
    sample_pairs = random.sample(combinations, total_aplicacoes)

    aplicacoes = []
    for index, (pet_id, vacina_id) in enumerate(sample_pairs, start=1):
        payload = generate_pet_vacina(pet_id, vacina_id, faker)
        aplicacao = request_json("POST", "/api/pet-vacinas", payload)
        aplicacoes.append(aplicacao)
        print_progress(index, total_aplicacoes, "Vacinas aplicadas")
        time.sleep(REQUEST_DELAY)

    return aplicacoes


def create_atendimentos(pets: list[dict], total_atendimentos: int, faker: Faker) -> list[dict]:
    print(f"Criando {total_atendimentos} atendimentos...")
    atendimentos = []
    for index in range(1, total_atendimentos + 1):
        pet = random.choice(pets)
        payload = generate_atendimento(pet["id"], faker, date.today(), MAX_ATENDIMENTO_DIAS)
        atendimento = request_json("POST", "/api/atendimentos", payload)
        atendimentos.append(atendimento)
        print_progress(index, total_atendimentos, "Atendimentos")
        time.sleep(REQUEST_DELAY)
    return atendimentos


def iniciar_atendimentos(atendimentos: list[dict], count: int) -> list[dict]:
    print(f"Iniciando {count} atendimentos...")
    selecionados = random.sample(atendimentos, min(count, len(atendimentos)))
    iniciados = []
    for index, atendimento in enumerate(selecionados, start=1):
        iniciado = request_json("POST", f"/api/atendimentos/{atendimento['id']}/iniciar")
        iniciados.append(iniciado)
        print_progress(index, len(selecionados), "Atendimentos iniciados")
        time.sleep(REQUEST_DELAY)
    return iniciados


def finalizar_atendimentos(atendimentos: list[dict], count: int, faker: Faker) -> list[dict]:
    print(f"Finalizando {count} atendimentos...")
    selecionados = random.sample(atendimentos, min(count, len(atendimentos)))
    finalizados = []
    for index, atendimento in enumerate(selecionados, start=1):
        params = {"observacaoFinal": faker.sentence(nb_words=10)}
        finalizado = request_json("PATCH", f"/api/atendimentos/{atendimento['id']}/finalizar", params=params)
        finalizados.append(finalizado)
        print_progress(index, len(selecionados), "Atendimentos finalizados")
        time.sleep(REQUEST_DELAY)
    return finalizados


def cancelar_atendimentos(atendimentos: list[dict], count: int, faker: Faker) -> list[dict]:
    print(f"Cancelando {count} atendimentos...")
    selecionados = random.sample(atendimentos, min(count, len(atendimentos)))
    cancelados = []
    for index, atendimento in enumerate(selecionados, start=1):
        params = {"motivo": faker.sentence(nb_words=6)}
        cancelado = request_json("PATCH", f"/api/atendimentos/{atendimento['id']}/cancelar", params=params)
        cancelados.append(cancelado)
        print_progress(index, len(selecionados), "Atendimentos cancelados")
        time.sleep(REQUEST_DELAY)
    return cancelados


def main() -> None:
    parser = argparse.ArgumentParser(description="Gerador de massa de dados para o Petshop API")
    parser.add_argument("--tutores", type=int, default=DEFAULT_TUTORES)
    parser.add_argument("--pets", type=int, default=DEFAULT_PETS)
    parser.add_argument("--servicos", type=int, default=DEFAULT_SERVICOS)
    parser.add_argument("--vacinas", type=int, default=DEFAULT_VACINAS)
    parser.add_argument("--atendimentos", type=int, default=DEFAULT_ATENDIMENTOS)
    parser.add_argument("--vacinas-aplicadas", type=int, default=DEFAULT_VACINAS_APLICADAS)
    parser.add_argument("--iniciados", type=int, default=DEFAULT_ATENDIMENTOS_INICIADOS)
    parser.add_argument("--finalizados", type=int, default=DEFAULT_ATENDIMENTOS_FINALIZADOS)
    parser.add_argument("--cancelados", type=int, default=DEFAULT_ATENDIMENTOS_CANCELADOS)
    args = parser.parse_args()

    faker = Faker("pt_BR")
    random.seed()

    print("Iniciando geração de massa de dados para o Petshop API")
    print(f"Base URL: {BASE_URL}")

    tutores = create_tutores(args.tutores, faker)
    pets = create_pets(tutores, args.pets, faker)
    servicos = create_servicos(args.servicos, faker)
    vacinas = create_vacinas(args.vacinas, faker)
    aplicacoes = create_pet_vacinas(pets, vacinas, args.vacinas_aplicadas, faker)

    atendimentos = create_atendimentos(pets, args.atendimentos, faker)

    available = [a for a in atendimentos]
    finalizados = []
    cancelados = []
    iniciados = []

    if args.finalizados > 0:
        candidatos = available[:]
        selecionados = random.sample(candidatos, min(args.finalizados, len(candidatos)))
        finalizados = finalizar_atendimentos(selecionados, len(selecionados), faker)
        available = [a for a in available if a not in selecionados]

    if args.cancelados > 0:
        candidatos = available[:]
        selecionados = random.sample(candidatos, min(args.cancelados, len(candidatos)))
        cancelados = cancelar_atendimentos(selecionados, len(selecionados), faker)
        available = [a for a in available if a not in selecionados]

    if args.iniciados > 0:
        candidatos = available[:]
        selecionados = random.sample(candidatos, min(args.iniciados, len(candidatos)))
        iniciados = iniciar_atendimentos(selecionados, len(selecionados))
        available = [a for a in available if a not in selecionados]

    print("\nResumo de massa gerada:")
    print(f"✔ {len(tutores)} Tutores")
    print(f"✔ {len(pets)} Pets")
    print(f"✔ {len(servicos)} Serviços")
    print(f"✔ {len(vacinas)} Vacinas")
    print(f"✔ {len(atendimentos)} Atendimentos")
    print(f"✔ {len(aplicacoes)} Vacinas Aplicadas")
    print(f"✔ {len(iniciados)} Atendimentos iniciados")
    print(f"✔ {len(finalizados)} Atendimentos finalizados")
    print(f"✔ {len(cancelados)} Atendimentos cancelados")


if __name__ == "__main__":
    main()
