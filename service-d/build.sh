mvn clean install
docker build -f src/main/docker/Dockerfile.jvm -t quarkus/service-d:base-inst .
