#!/bin/sh

kubectl delete -f PropertyService.yml
kubectl delete -f PropertyDeployment.yml
kubectl delete -f PropertyConfig.yml