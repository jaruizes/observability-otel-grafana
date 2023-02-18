#!/bin/bash
set -e

ROOT_FOLDER="$(pwd)"

removeLastDockerImages() {
  docker rmi node/service-a:latest spring/service-b:latest quarkus/service-c:latest quarkus/service-d:latest --force
}

buildServiceA() {
  cd "$ROOT_FOLDER"/service-a
  sh build.sh
}

buildServiceB() {
  cd "$ROOT_FOLDER"/service-b
  sh build.sh
}

buildServiceC() {
  cd "$ROOT_FOLDER"/service-c
  sh build.sh
}

buildServiceD() {
  cd "$ROOT_FOLDER"/service-d
  sh build.sh
}

buildServices() {
  buildServiceA
  buildServiceB
  buildServiceC
  buildServiceD
}

generateData() {
  printf "Generando datos.."
  until curl -s -f -o /dev/null "http://localhost:8080/metrics/prometheus"
  do
    sleep 5
  done

  i=0
  while [ $i -ne 100 ]
  do
    RANDOM_NUMBER=$(($RANDOM%201-100))
    curl --silent --location --request POST 'http://localhost:8080/' --header 'Content-Type: application/json' --data-raw '{"initialValue":"'"$RANDOM_NUMBER"'"}' > /dev/null
    i=$(($i+1))

    sleep 0.1

    if [ $((i % 10)) == 0 ]; then
       printf "."
    fi

  done

  echo "Datos generados!"
}

showInfo() {
  echo ""
  echo ""
  echo "Entorno listo!"
  echo "- Dashboard: http://localhost:3000/d/metrics/dashboard-post"
  echo ""
  echo ""
}

start() {
  removeLastDockerImages
  buildServices
  docker-compose up -d
  generateData
  showInfo
}

start


