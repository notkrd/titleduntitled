FROM swipl
COPY . /app
CMD ["swipl", "/app/TheGuideToNonexistentBirds.pl", "--user=daemon", "--no-fork", "--port=8080", "--debut='http(request)'", "--pidfile=/var/run/http.pid", "--syslog=http"]