FROM python:3

WORKDIR /app

COPY requirements.txt requirements.txt

RUN pip install -r requirements.txt
RUN pip install opentelemetry-distro \
        opentelemetry-exporter-otlp

COPY server.py server.py

RUN opentelemetry-bootstrap -a install

ENV OTEL_TRACES_EXPORTER=otlp

EXPOSE 16101

CMD [ "opentelemetry-instrument", "python", "server.py" ]