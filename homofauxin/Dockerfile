ARG OPENJDK_TAG=8u181
FROM openjdk:${OPENJDK_TAG}

ARG SBT_VERSION=1.2.8

# Install sbt
RUN \
  curl -L -o sbt-$SBT_VERSION.deb https://dl.bintray.com/sbt/debian/sbt-$SBT_VERSION.deb && \
  dpkg -i sbt-$SBT_VERSION.deb && \
  rm sbt-$SBT_VERSION.deb && \
  apt-get update && \
  apt-get install sbt && \
  sbt sbtVersion

WORKDIR /homofauxin

COPY . /homofauxin

RUN sbt dist
RUN unzip target/universal/homofauxnighisthis-1.0.zip

CMD homofauxnighisthis-1.0/bin/homofauxnighisthis