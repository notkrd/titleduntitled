FROM hseeberger/scala-sbt:8u151-2.12.5-1.1.2

RUN sbt dist

RUN unzip target/universal/roomwithmontague-1.0.zip

CMD ["roomwithmontague-1.0/bin/roomwithmontague"]