#!/bin/sh
curl -u $DOCKER_API_KEY https://cloud.docker.com/api/app/v1/service/
if [ "$TRAVIS_BRANCH" = "master" ]; then
    TAG="latest"+$TRAVIS_BUILD_NUMBER
else
    TAG="$TRAVIS_BRANCH"+$TRAVIS_BUILD_NUMBER
fi
docker build -f Dockerfile -t axvad/gitsnap:$TAG .
docker push axvad/gitsnap