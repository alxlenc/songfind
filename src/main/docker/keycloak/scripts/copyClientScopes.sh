#!/bin/bash

set -e

source ./lib/getToken.sh
source ./lib/getClientScope.sh
source ./lib/postClientScope.sh

KCLK_BASE=http://localhost:8080/auth
CLIENT_SCOPE="spring-front"

TKN=$(get_admin_token $KCLC_BASE)

BODY=$(get_client_scope $CLIENT_SCOPE)

echo $BODY

KCLK_BASE=https://192.168.1.203:30278/auth
TKN=$(get_admin_token $KCLC_BASE)

post_client_scope "$BODY"

echo "[INFO] Done!"
