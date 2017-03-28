#!/bin/bash

HOST=localhost
PORT=37017
DATABASE=smartcity

if [ -f 'country.json' ]; then
    mongoimport --host $HOST --port $PORT --collection $DATABASE --db country --drop --file country.json
fi

if [ -f 'regions.json' ]; then
    mongoimport --host $HOST --port $PORT --collection $DATABASE --db regions --drop --file regions.json
fi

if [ -f 'locality.json' ]; then
    mongoimport --host $HOST --port $PORT --collection $DATABASE --db locality --drop --file locality.json
fi

