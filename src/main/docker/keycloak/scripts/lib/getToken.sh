#!/bin/bash

# requires https://stedolan.github.io/jq/download/

function get_admin_token() {
    
# config
KEYCLOAK_URL="${KCLK_BASE:-http://localhost:8080/auth}"
KEYCLOAK_REALM=master
KEYCLOAK_CLIENT_ID=admin
read -sp "$KEYCLOAK_URL pass: " KEYCLOAK_CLIENT_SECRET


echo "$(curl --insecure -s -X POST "${KEYCLOAK_URL}/realms/${KEYCLOAK_REALM}/protocol/openid-connect/token" \
 -H "Content-Type: application/x-www-form-urlencoded" \
 -d "username=${KEYCLOAK_CLIENT_ID}" \
 -d "password=${KEYCLOAK_CLIENT_SECRET}" \
 -d 'grant_type=password' \
 -d 'client_id=admin-cli' | jq -r '.access_token')"
 }

 