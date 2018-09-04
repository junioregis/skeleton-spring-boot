#!/bin/bash

PROFILE=production
SERVICES=proxy server db_admin

function build {
    docker-compose build builder
    docker-compose run --rm builder
}

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
    build) 
        echo –n "Building"
        build
        ;;
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
        echo "Usage: bash production.sh build|start|stop|logs"
        exit 1
    ;;
esac