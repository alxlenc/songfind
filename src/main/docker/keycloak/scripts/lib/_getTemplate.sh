#!/bin/bash

# requires https://stedolan.github.io/jq/download/


function get_template() {

    [[ -z "$TKN" ]] && echo "[ERROR] FUNCNAME[0]: TKN var is empty" && exit 1

    TARGET_URL="$1"


    KEYCLOAK_URL="${1:-http://localhost:8080/auth}"
    

    echo "$(curl --insecure -s -X GET "$TARGET_URL" \
    -H "Accept: application/json" \
    -H "Authorization: Bearer $TKN")"

}
