ARG OPENJDK_TAG=8u181
FROM openjdk:${OPENJDK_TAG}

ARG SBT_VERSION=1.2.8
ARG ROOM_VERSION=1.0

WORKDIR /room

COPY . /room

# Install sbt
RUN \
  curl -L -o sbt-$SBT_VERSION.deb https://dl.bintray.com/sbt/debian/sbt-$SBT_VERSION.deb && \
  dpkg -i sbt-$SBT_VERSION.deb && \
  rm sbt-$SBT_VERSION.deb && \
  apt-get update && \
  apt-get install sbt && \
  sbt sbtVersion

RUN \
  sbt dist \
  unzip target/universal/roomwithmontague-$ROOM_VERSION.zip

CMD roomwithmontague-${ROOM_VERSION}/bin/roomwithmontague