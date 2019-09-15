FROM mysql:8
ENV MYSQL_DATABASE db-progetto
ENV MYSQL_ROOT_PASSWORD root
RUN SLEEP 60;
RUN docker ps -q | xargs -n 1 docker inspect --format '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}} {{ .Name }}' | sed 's/ \// /'