#!/bin/sh 

gpg --quiet --batch --yes --decrypt --passphrase="$LARGE_SECRET_PASSPHRASE"  --output keycloak.json keycloak.json.gpg
