ARG OPENJDK_TAG=8u181
FROM openjdk:${OPENJDK_TAG}

ARG SBT_VERSION=1.2.8

# Install sbt
RUN \
  apt-get update && \
  apt-get install sbt && \
  sbt sbtVersion

WORKDIR /room

COPY . /room

RUN sbt dist
RUN unzip target/universal/roomwithmontague-1.0.zip

CMD roomwithmontague-1.0/bin/roomwithmontague