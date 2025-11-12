#!/bin/bash
set -e

HOST=db
PORT=3306
MAX_RETRIES=60
RETRY=0

echo "Waiting for database ${HOST}:${PORT}..."
while ! timeout 1 bash -c "</dev/tcp/${HOST}/${PORT}" 2>/dev/null; do
  RETRY=$((RETRY+1))
  if [ "$RETRY" -ge "$MAX_RETRIES" ]; then
    echo "Timed out waiting for ${HOST}:${PORT} after ${MAX_RETRIES} seconds"
    break
  fi
  sleep 1
done

echo "Starting Tomcat..."
exec catalina.sh run
