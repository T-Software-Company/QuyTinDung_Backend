HOST=14.225.215.106
PORT=443
KEYSTOREFILE=$JAVA_HOME/lib/security/cacerts
KEYSTOREPASS=changeit

#keytool -delete -alias 14.225.215.106 -keystore $JAVA_HOME/lib/security/cacerts -storepass changeit
# getWorkflow the SSL certificate
openssl s_client -connect ${HOST}:${PORT} </dev/null \
    | sed -ne '/-BEGIN CERTIFICATE-/,/-END CERTIFICATE-/p' > ${HOST}.cert

# create a keystore and import certificate
keytool -import -noprompt -trustcacerts \
    -alias "1" -file ${HOST}.cert \
    -keystore ${KEYSTOREFILE} -storepass ${KEYSTOREPASS}

# verify we've got it.
keytool -list -v -keystore ${KEYSTOREFILE} -storepass ${KEYSTOREPASS} -alias ${HOST}