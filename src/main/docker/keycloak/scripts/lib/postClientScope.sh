#!/bin/bash
source ./lib/_postTemplate.sh

function post_client_scope() {
    DATA=$1
    echo $(post_template "$KCLK_BASE/admin/realms/music/client-scopes" "$DATA")
}