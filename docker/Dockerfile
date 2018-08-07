FROM frolvlad/alpine-scala

WORKDIR /app

RUN apk add git maven
RUN git clone https://github.com/dariuszlee/scala-crypto-simulator
WORKDIR /app/scala-crypto-simulator

EXPOSE 8080
RUN mvn install && mvn dependency:copy-dependencies
RUN ./build_run.sh 

RUN ['./build_run.sh', '-c', 'WebServer' ]
