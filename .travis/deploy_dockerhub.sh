#!/bin/sh
docker login -e $DOCKER_EMAIL -u $DOCKER_USER -p $DOCKER_PASS
if [ "$TRAVIS_BRANCH" = "master" ]; then
    TAG="latest_travis"
else
    TAG="$TRAVIS_BRANCH"
fi
docker build -f Dockerfile -t axvad/gitsnap:$TAG .
docker push axvad/gitsnap