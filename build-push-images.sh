#!/bin/bash

OCI_RUNTIME=$(which docker || which podman)

IMAGE_REGISTRY="${IMAGE_REGISTRY:-quay.io}"
IMAGE_NAMESPACE="${IMAGE_NAMESPACE:-train_ticket}"

JAVA_MICROSERVICES=("ts-admin-basic-info" "ts-admin-order" "ts-admin-route" "ts-admin-travel" \
                    "ts-admin-user" "ts-assurance" "ts-auth" "ts-basic" "ts-cancel" "ts-config" \
                    "ts-consign-price" "ts-consign" "ts-contacts" "ts-execute" "ts-food-map" "ts-food" \
                    "ts-inside-payment" "ts-notification" "ts-order-other" "ts-order" "ts-payment" \
                    "ts-preserve-other" "ts-preserve" "ts-price" "ts-rebook" "ts-route-plan" "ts-route" \
                    "ts-seat" "ts-security" "ts-station" "ts-ticketinfo" "ts-train" "ts-travel-plan" \
                    "ts-travel" "ts-travel2" "ts-user" "ts-verification-code")

image="$IMAGE_REGISTRY/$IMAGE_NAMESPACE/ts-news-service-with-opentelemetry:latest"
$OCI_RUNTIME build -f ts-news-service/Dockerfile -t $image --platform=linux/amd64
$OCI_RUNTIME push $image

image="$IMAGE_REGISTRY/$IMAGE_NAMESPACE/ts-ticket-office-service-with-opentelemetry:latest"
$OCI_RUNTIME build -f ts-ticket-office-service/Dockerfile -t $image --platform=linux/amd64
$OCI_RUNTIME push $image

image="$IMAGE_REGISTRY/$IMAGE_NAMESPACE/ts-ui-dashboard-with-opentelemetry:latest"
$OCI_RUNTIME build -f ts-ui-dashboard/Dockerfile -t $image --platform=linux/amd64
$OCI_RUNTIME push $image

image="$IMAGE_REGISTRY/$IMAGE_NAMESPACE/ts-voucher-service-with-opentelemetry:latest"
$OCI_RUNTIME build -f ts-voucher-service/Dockerfile -t $image --platform=linux/amd64
$OCI_RUNTIME push $image

for microservice in ${JAVA_MICROSERVICES[@]}; do
    service="$microservice-service"
    image="$IMAGE_REGISTRY/$IMAGE_NAMESPACE/$service-with-opentelemetry:latest"

    $OCI_RUNTIME build -f Dockerfile.java -t $image --platform=linux/amd64 --target=$service
    $OCI_RUNTIME push $image
done