#!/usr/bin/env bash

SCRIPT_PATH="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

cd ${SCRIPT_PATH}

source ./curl-config.sh

FILE_DIR=resources/es-model/6

#clean up
curl -XDELETE "${ES_URL}/twitter?pretty" -H 'Content-Type: application/json' > /dev/null


echo create
curl  -XPUT "${ES_URL}/twitter?pretty" -H 'Content-Type: application/json' -d @${FILE_DIR}/request/create-index.json > ${FILE_DIR}/response/index-created.json

curl  -XPUT "${ES_URL}/twitter?pretty" -H 'Content-Type: application/json' -d @${FILE_DIR}/request/create-index.json > ${FILE_DIR}/response/index-already-exists.json

eco delete
curl  -XDELETE "${ES_URL}/twitter?pretty" -H 'Content-Type: application/json' > ${FILE_DIR}/response/index-deleted.json

echo missing
curl  -XDELETE "${ES_URL}/twitter?pretty" -H 'Content-Type: application/json' > ${FILE_DIR}/response/index-not-found.json

#
curl  -XPUT "${ES_URL}/twitter?pretty" -H 'Content-Type: application/json' -d @${FILE_DIR}/request/create-index.json > ${FILE_DIR}/response/index-created.json

echo get index information
curl  -XGET "${ES_URL}/twitter?pretty" -H 'Content-Type: application/json' > ${FILE_DIR}/response/index-details.json

echo "check if index exists curl -XHEAD \"${ES_URL}/twitter?pretty\""
curl -I "${ES_URL}/twitter?pretty"
# Status code - 404 not exists - 200 exists

echo get mappings
curl -XGET "${ES_URL}/twitter/_mapping?pretty" -H 'Content-Type: application/json' > ${FILE_DIR}/response/index-mappings.json

echo add mappings
curl -XPUT "${ES_URL}/twitter/_mapping/_doc?pretty" -H 'Content-Type: application/json' -d @${FILE_DIR}/request/add-mappings.json > ${FILE_DIR}/response/added-mappings.json


#index document
echo adding new document
curl -XPUT "${ES_URL}/twitter/_doc/1?pretty" -H 'Content-Type: application/json' -d'
{
    "user" : "kimchy",
    "post_date" : "2009-11-15T14:12:12",
    "message" : "trying out Elasticsearch"
}
' > ${FILE_DIR}/response/document-index.json


echo overriding docuemnt
curl -XPUT "${ES_URL}/twitter/_doc/1?pretty" -H 'Content-Type: application/json' -d'
{
    "user" : "kimchy v2",
    "post_date" : "2009-11-15T14:12:13",
    "message" : "trying out Elasticsearch"
}
' > ${FILE_DIR}/response/document-override.json


curl -XDELETE "${ES_URL}/twitter/_doc/2?pretty"

