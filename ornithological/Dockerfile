FROM swipl
COPY . /app
CMD ["swipl", "/app/TheGuideToNonexistentBirds.pl", "--user=www", "--no-fork", "--port=8080", "--pidfile=/var/run/http.pid", "--debut='http(request)'", "--syslog=http"]