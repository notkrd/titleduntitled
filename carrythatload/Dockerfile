FROM nginx
COPY ./nginx.conf /etc/nginx/conf.d/nginx.conf
COPY hypertext_facade /usr/share/nginx/html

RUN apt update
RUN apt install -y curl
