#!/usr/bin/env bash

set -e

ES_VERSION="5.6.7"
CONTAINER_NAME="elastic-${ES_VERSION}"

HOST_PORT_SUFFIX="00"
HTTP_PORT="92${HOST_PORT_SUFFIX}"
TRANSPORT_PORT="93${HOST_PORT_SUFFIX}"

INSTANCE="$(docker ps -q -f name="${CONTAINER_NAME}")"

IMAGE="$(docker ps -qa -f name="${CONTAINER_NAME}")"


echo " Starting container ${CONTAINER_NAME} HTTP:${HTTP_PORT} , TRANSPORT:${TRANSPORT_PORT}"

if [ -n "${INSTANCE}" ]; then
  echo "Instance of ${CONTAINER_NAME} - ${INSTANCE} is already running"
  exit 0
fi

if [ -n "${IMAGE}" ]; then
  echo "Instance of ${CONTAINER_NAME} - ${INSTANCE} is not running - starting instance"
  docker start ${CONTAINER_NAME}
  exit 0
fi


docker run -d --name ${CONTAINER_NAME} -p ${HTTP_PORT}:9200 -p ${TRANSPORT_PORT}:9300 -e "xpack.security.enabled=false" -e "discovery.type=single-node" docker.elastic.co/elasticsearch/elasticsearch:${ES_VERSION}
