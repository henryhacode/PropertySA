#!/bin/sh

kubectl apply -f PropertyConfig.yml
kubectl apply -f PropertyDeployment.yml
kubectl apply -f PropertyService.yml