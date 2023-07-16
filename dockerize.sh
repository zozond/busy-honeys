#!/bin/bash

echo "###########################################"
echo "백엔드 도커 빌드 시작"
cd ./backend 
# ./gradlew bootJar
docker build --network=host -t backend:latest . --no-cache
echo "백엔드 도커 빌드 종료"
echo "###########################################"

echo ""
cd ../
echo ""

echo "###########################################"
echo "프론트엔드 도커 빌드 시작"
cd ./frontend
docker build --network=host -t frontend:latest . --no-cache
echo "프론트엔드 도커 빌드 종료"
echo "###########################################"
