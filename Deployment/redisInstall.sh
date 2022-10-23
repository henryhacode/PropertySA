#!/bin/sh

helm repo add bitnami https://charts.bitnami.com/bitnami

helm install redis-test --set persistence.storageClass=nfs-client,redis.replicas.persistence.storageClass=nfs-client bitnami/redis --set volumePermissions.enabled=true
