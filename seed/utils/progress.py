def print_progress(current: int, total: int, label: str = "") -> None:
    percent = 100 if total == 0 else current * 100 // total
    bar_length = 30
    filled = int(bar_length * current / total) if total else bar_length
    bar = "#" * filled + "-" * (bar_length - filled)
    print(f"{label}: [{bar}] {current}/{total} ({percent}%)", end="\r")
    if current == total:
        print()
