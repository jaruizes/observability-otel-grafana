#!/bin/bash
set -e

destroy() {
  docker-compose down --rmi all --volumes
}

destroy


