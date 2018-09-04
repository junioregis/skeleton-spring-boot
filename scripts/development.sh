#!/bin/bash

PROFILE=development
SERVICES=db_admin

function start {
    docker-compose -f docker-compose.yml -f docker-compose.${PROFILE}.yml build ${SERVICES}
    docker-compose -f docker-compose.yml -f docker-compose.${PROFILE}.yml up -d ${SERVICES}
}

function stop {
    docker-compose -f docker-compose.yml -f docker-compose.${PROFILE}.yml down
}

function logs {
    docker-compose -f docker-compose.yml -f docker-compose.${PROFILE}.yml logs
}

case "$1" in
    start)
        echo –n "Starting"
        start
        ;;
    stop) 
        echo –n "Stopping"
        stop
        ;;
    logs) 
        logs
        ;;
    *)
        echo "Usage: bash development.sh start|stop|logs"
        exit 1
    ;;
esac