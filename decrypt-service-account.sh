#!/bin/sh 

gpg --quiet --batch --yes --decrypt --passphrase="$LARGE_SECRET_PASSPHRASE"  --output service-account.json service-account.json.gpg
