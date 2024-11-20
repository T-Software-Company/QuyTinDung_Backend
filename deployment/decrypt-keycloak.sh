#!/bin/sh 

gpg --quiet --batch --yes --decrypt --passphrase="$LARGE_SECRET_PASSPHRASE"  --output tsoftware-realm.json tsoftware-realm.json.gpg
