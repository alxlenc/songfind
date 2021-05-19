#!/bin/bash

set -x

realm_file=music-realm.json
client_name=frontend

#jq ".clients[] | select(.clientId==\"$client_name\")" $realm_file
jq "{clients: [.clients[] |  select(.clientId==\"$client_name\") ] }" $realm_file
