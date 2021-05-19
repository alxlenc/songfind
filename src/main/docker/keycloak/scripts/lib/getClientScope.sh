#!/bin/bash


source ./lib/_getTemplate.sh
source ./lib/_postTemplate.sh




function get_client_scope() {
    SCOPE_NAME=$1
    echo $(get_template "$KCLK_BASE/admin/realms/music/client-scopes/" | jq ".[] | select(.name==\"$SCOPE_NAME\")" )

}








