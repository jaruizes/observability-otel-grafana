#!/bin/bash
set -e

printf "Generando datos.."

i=0
while [ $i -ne "$1" ]
do
  RANDOM_NUMBER=$(($RANDOM%201-100))
  curl -s --location --request POST 'http://localhost:8080/' --header 'Content-Type: application/json' --data-raw '{"initialValue":"'"$RANDOM_NUMBER"'"}' > /dev/null
  i=$(($i+1))

  sleep 0.1

  if [ $((i % 10)) == 0 ]; then
     printf "."
  fi

done
