import os

# ==========================
# API
# ==========================

BASE_URL = os.getenv("BASE_URL", "http://localhost:8081")
REQUEST_DELAY = float(os.getenv("REQUEST_DELAY", "0.1"))

# ==========================
# MASSA DE DADOS
# ==========================

DEFAULT_TUTORES = int(os.getenv("DEFAULT_TUTORES", "100"))
DEFAULT_PETS = int(os.getenv("DEFAULT_PETS", "230"))
DEFAULT_SERVICOS = int(os.getenv("DEFAULT_SERVICOS", "15"))
DEFAULT_VACINAS = int(os.getenv("DEFAULT_VACINAS", "10"))
DEFAULT_ATENDIMENTOS = int(os.getenv("DEFAULT_ATENDIMENTOS", "420"))
DEFAULT_VACINAS_APLICADAS = int(os.getenv("DEFAULT_VACINAS_APLICADAS", "650"))
DEFAULT_ATENDIMENTOS_INICIADOS = int(os.getenv("DEFAULT_ATENDIMENTOS_INICIADOS", "180"))
DEFAULT_ATENDIMENTOS_FINALIZADOS = int(os.getenv("DEFAULT_ATENDIMENTOS_FINALIZADOS", "140"))
DEFAULT_ATENDIMENTOS_CANCELADOS = int(os.getenv("DEFAULT_ATENDIMENTOS_CANCELADOS", "35"))

# ==========================
# ATENDIMENTO
# ==========================

MAX_ATENDIMENTO_DIAS = int(os.getenv("MAX_ATENDIMENTO_DIAS", "30"))
MAX_VACINA_DIAS = int(os.getenv("MAX_VACINA_DIAS", "365"))
