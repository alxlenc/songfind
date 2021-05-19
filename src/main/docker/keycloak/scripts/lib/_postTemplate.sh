#!/bin/bash

function post_template() {

    [[ -z "$TKN" ]] && echo "[ERROR] FUNCNAME[0]: TKN var is empty" && exit 1

    TARGET_URL="$1"
    DATA="$2"

    echo "$(curl --insecure -s -X POST "$TARGET_URL" \
    -H "Content-Type: application/json" \
    -H "Authorization: Bearer $TKN" \
    --data "$DATA "
    )"

}

