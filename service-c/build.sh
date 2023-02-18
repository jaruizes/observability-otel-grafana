mvn clean package
docker build -f src/main/docker/Dockerfile.jvm -t quarkus/service-c:base-inst .
