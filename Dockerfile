FROM 3.7-slim

WORKDIR /app

COPY . /app

RUN pip install --trusted-host pypi.pyhon.org -r requirements.txt

CMD ["python", "app.py"]
