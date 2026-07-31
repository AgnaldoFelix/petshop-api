import json
from urllib import request, error

from config import BASE_URL, TIMEOUT


class ApiClient:

    def request(self, method: str, endpoint: str, body=None):

        data = None

        if body is not None:
            data = json.dumps(body).encode("utf-8")

        req = request.Request(
            BASE_URL + endpoint,
            data=data,
            method=method,
            headers={
                "Content-Type": "application/json"
            }
        )

        try:

            with request.urlopen(req, timeout=TIMEOUT) as response:

                payload = response.read().decode()

                if payload:
                    payload = json.loads(payload)

                return response.status, payload

        except error.HTTPError as exc:

            payload = exc.read().decode(errors="ignore")

            raise Exception(
                f"""
HTTP {exc.code}

ENDPOINT:

{endpoint}

BODY:

{payload}
"""
            )