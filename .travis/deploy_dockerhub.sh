#!/bin/sh
docker login -e $DOCKER_EMAIL -u $DOCKER_USER -p $DOCKER_PASSWORD
if [ "$TRAVIS_BRANCH" = "master" ]; then
    TAG="latest_"$TRAVIS_BUILD_NUMBER
else
    TAG=$TRAVIS_BRANCH$TRAVIS_BUILD_NUMBER
fi
docker build -f Dockerfile -t axvad/gitsnap:$TAG .
docker push axvad/gitsnap