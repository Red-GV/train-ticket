FROM ibmjava:8-sdk as builder

ARG OPENTELEMETRY_JAVA_AGENT_VERSION=2.1.0

RUN apt-get update -y \
    && apt-get install -y maven wget

WORKDIR /workspace

RUN wget https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/download/v${OPENTELEMETRY_JAVA_AGENT_VERSION}/opentelemetry-javaagent.jar

WORKDIR /opt 

COPY ts-admin-basic-info-service/ ts-admin-basic-info-service/
COPY ts-admin-order-service/ ts-admin-order-service/
COPY ts-admin-route-service/ ts-admin-route-service/
COPY ts-admin-travel-service/ ts-admin-travel-service/
COPY ts-admin-user-service/ ts-admin-user-service/
COPY ts-assurance-service/ ts-assurance-service/
COPY ts-auth-service/ ts-auth-service/
COPY ts-basic-service/ ts-basic-service/
COPY ts-cancel-service/ ts-cancel-service/
COPY ts-common/ ts-common/
COPY ts-config-service/ ts-config-service/
COPY ts-consign-price-service/ ts-consign-price-service/
COPY ts-consign-service/ ts-consign-service/
COPY ts-contacts-service/ ts-contacts-service/
COPY ts-execute-service/ ts-execute-service/
COPY ts-food-map-service/ ts-food-map-service/
COPY ts-food-service/ ts-food-service/
COPY ts-inside-payment-service/ ts-inside-payment-service/
COPY ts-notification-service/ ts-notification-service/
COPY ts-order-other-service/ ts-order-other-service/
COPY ts-order-service/ ts-order-service/
COPY ts-payment-service/ ts-payment-service/
COPY ts-preserve-other-service/ ts-preserve-other-service/
COPY ts-preserve-service/ ts-preserve-service/
COPY ts-price-service/ ts-price-service/
COPY ts-rebook-service/ ts-rebook-service/
COPY ts-route-plan-service/ ts-route-plan-service/
COPY ts-route-service/ ts-route-service/
COPY ts-seat-service/ ts-seat-service/
COPY ts-security-service/ ts-security-service/
COPY ts-station-service/ ts-station-service/
COPY ts-ticketinfo-service/ ts-ticketinfo-service/
COPY ts-train-service/ ts-train-service/
COPY ts-travel-plan-service/ ts-travel-plan-service/
COPY ts-travel-service/ ts-travel-service/
COPY ts-travel2-service/ ts-travel2-service/
COPY ts-user-service/ ts-user-service/
COPY ts-verification-code-service/ ts-verification-code-service/

COPY pom.xml pom.xml

RUN mvn clean package -Dmaven.test.skip=true

FROM ibmjava:8-jre AS service
WORKDIR /opt
COPY --from=builder /workspace/opentelemetry-javaagent.jar opentelemetry-javaagent.jar

FROM service AS ts-admin-basic-info-service
EXPOSE 18767
COPY --from=builder /opt/ts-admin-basic-info-service/target/ts-admin-basic-info-service-1.0.jar app/ts-admin-basic-info-service.jar
CMD ["java", "-javaagent:/opt/opentelemetry-javaagent.jar", "-Xmx200m", "-jar", "/opt/app/ts-admin-basic-info-service.jar"]

FROM service AS ts-admin-order-service
EXPOSE 16112
COPY --from=builder /opt/ts-admin-order-service/target/ts-admin-order-service-1.0.jar app/ts-admin-order-service.jar
CMD ["java", "-javaagent:/opt/opentelemetry-javaagent.jar", "-Xmx200m", "-jar", "/opt/app/ts-admin-order-service.jar"]

FROM service AS ts-admin-route-service
EXPOSE 16113
COPY --from=builder /opt/ts-admin-route-service/target/ts-admin-route-service-1.0.jar app/ts-admin-route-service.jar
CMD ["java", "-javaagent:/opt/opentelemetry-javaagent.jar", "-Xmx200m", "-jar", "/opt/app/ts-admin-route-service.jar"]

FROM service AS ts-admin-travel-service
EXPOSE 16114
COPY --from=builder /opt/ts-admin-travel-service/target/ts-admin-travel-service-1.0.jar app/ts-admin-travel-service.jar
CMD ["java", "-javaagent:/opt/opentelemetry-javaagent.jar", "-Xmx200m", "-jar", "/opt/app/ts-admin-travel-service.jar"]

FROM service AS ts-admin-user-service
EXPOSE 16115
COPY --from=builder /opt/ts-admin-user-service/target/ts-admin-user-service-1.0.jar app/ts-admin-user-service.jar
CMD ["java", "-javaagent:/opt/opentelemetry-javaagent.jar", "-Xmx200m", "-jar", "/opt/app/ts-admin-user-service.jar"]

FROM service AS ts-assurance-service
EXPOSE 18888
COPY --from=builder /opt/ts-assurance-service/target/ts-assurance-service-1.0.jar app/ts-assurance-service.jar
CMD ["java", "-javaagent:/opt/opentelemetry-javaagent.jar", "-Xmx200m", "-jar", "/opt/app/ts-assurance-service.jar"]

FROM service AS ts-auth-service
EXPOSE 12349
COPY --from=builder /opt/ts-auth-service/target/ts-auth-service-1.0.jar app/ts-auth-service.jar
CMD ["java", "-javaagent:/opt/opentelemetry-javaagent.jar", "-Xmx200m", "-jar", "/opt/app/ts-auth-service.jar"]

FROM service AS ts-basic-service
EXPOSE 15680
COPY --from=builder /opt/ts-basic-service/target/ts-basic-service-1.0.jar app/ts-basic-service.jar
CMD ["java", "-javaagent:/opt/opentelemetry-javaagent.jar", "-Xmx200m", "-jar", "/opt/app/ts-basic-service.jar"]

FROM service AS ts-cancel-service
EXPOSE 18885
COPY --from=builder /opt/ts-cancel-service/target/ts-cancel-service-1.0.jar app/ts-cancel-service.jar
CMD ["java", "-javaagent:/opt/opentelemetry-javaagent.jar", "-Xmx200m", "-jar", "/opt/app/ts-cancel-service.jar"]

FROM service AS ts-config-service
EXPOSE 15679
COPY --from=builder /opt/ts-config-service/target/ts-config-service-1.0.jar app/ts-config-service.jar
CMD ["java", "-javaagent:/opt/opentelemetry-javaagent.jar", "-Xmx200m", "-jar", "/opt/app/ts-config-service.jar"]

FROM service AS ts-consign-price-service
EXPOSE 16110
COPY --from=builder /opt/ts-consign-price-service/target/ts-consign-price-service-1.0.jar app/ts-consign-price-service.jar
CMD ["java", "-javaagent:/opt/opentelemetry-javaagent.jar", "-Xmx200m", "-jar", "/opt/app/ts-consign-price-service.jar"]

FROM service AS ts-consign-service
EXPOSE 16111
COPY --from=builder /opt/ts-consign-service/target/ts-consign-service-1.0.jar app/ts-consign-service.jar
CMD ["java", "-javaagent:/opt/opentelemetry-javaagent.jar", "-Xmx200m", "-jar", "/opt/app/ts-consign-service.jar"]

FROM service AS ts-contacts-service
EXPOSE 12347
COPY --from=builder /opt/ts-contacts-service/target/ts-contacts-service-1.0.jar app/ts-contacts-service.jar
CMD ["java", "-javaagent:/opt/opentelemetry-javaagent.jar", "-Xmx200m", "-jar", "/opt/app/ts-contacts-service.jar"]

FROM service AS ts-execute-service
EXPOSE 12386
COPY --from=builder /opt/ts-execute-service/target/ts-execute-service-1.0.jar app/ts-execute-service.jar
CMD ["java", "-javaagent:/opt/opentelemetry-javaagent.jar", "-Xmx200m", "-jar", "/opt/app/ts-execute-service.jar"]

FROM service AS ts-food-map-service
EXPOSE 18855
COPY --from=builder /opt/ts-food-map-service/target/ts-food-map-service-1.0.jar app/ts-food-map-service.jar
CMD ["java", "-javaagent:/opt/opentelemetry-javaagent.jar", "-Xmx200m", "-jar", "/opt/app/ts-food-map-service.jar"]

FROM service AS ts-food-service
EXPOSE 18856
COPY --from=builder /opt/ts-food-service/target/ts-food-service-1.0.jar app/ts-food-service.jar
CMD ["java", "-javaagent:/opt/opentelemetry-javaagent.jar", "-Xmx200m", "-jar", "/opt/app/ts-food-service.jar"]

FROM service AS ts-inside-payment-service
EXPOSE 18673
COPY --from=builder /opt/ts-inside-payment-service/target/ts-inside-payment-service-1.0.jar app/ts-inside-payment-service.jar
CMD ["java", "-javaagent:/opt/opentelemetry-javaagent.jar", "-Xmx200m", "-jar", "/opt/app/ts-inside-payment-service.jar"]

FROM service AS ts-notification-service
EXPOSE 17853
COPY --from=builder /opt/ts-notification-service/target/ts-notification-service-1.0.jar app/ts-notification-service.jar
CMD ["java", "-javaagent:/opt/opentelemetry-javaagent.jar", "-Xmx200m", "-jar", "/opt/app/ts-notification-service.jar"]

FROM service AS ts-order-other-service
EXPOSE 12032
COPY --from=builder /opt/ts-order-other-service/target/ts-order-other-service-1.0.jar app/ts-order-other-service.jar
CMD ["java", "-javaagent:/opt/opentelemetry-javaagent.jar", "-Xmx200m", "-jar", "/opt/app/ts-order-other-service.jar"]

FROM service AS ts-order-service
EXPOSE 12031
COPY --from=builder /opt/ts-order-service/target/ts-order-service-1.0.jar app/ts-order-service.jar
CMD ["java", "-javaagent:/opt/opentelemetry-javaagent.jar", "-Xmx200m", "-jar", "/opt/app/ts-order-service.jar"]

FROM service AS ts-payment-service
EXPOSE 19001
COPY --from=builder /opt/ts-payment-service/target/ts-payment-service-1.0.jar app/ts-payment-service.jar
CMD ["java", "-javaagent:/opt/opentelemetry-javaagent.jar", "-Xmx200m", "-jar", "/opt/app/ts-payment-service.jar"]

FROM service AS ts-preserve-other-service
EXPOSE 14569
COPY --from=builder /opt/ts-preserve-other-service/target/ts-preserve-other-service-1.0.jar app/ts-preserve-other-service.jar
CMD ["java", "-javaagent:/opt/opentelemetry-javaagent.jar", "-Xmx200m", "-jar", "/opt/app/ts-preserve-other-service.jar"]

FROM service AS ts-preserve-service
EXPOSE 14568
COPY --from=builder /opt/ts-preserve-service/target/ts-preserve-service-1.0.jar app/ts-preserve-service.jar
CMD ["java", "-javaagent:/opt/opentelemetry-javaagent.jar", "-Xmx200m", "-jar", "/opt/app/ts-preserve-service.jar"]

FROM service AS ts-price-service
EXPOSE 16579
COPY --from=builder /opt/ts-price-service/target/ts-price-service-1.0.jar app/ts-price-service.jar
CMD ["java", "-javaagent:/opt/opentelemetry-javaagent.jar", "-Xmx200m", "-jar", "/opt/app/ts-price-service.jar"]

FROM service AS ts-rebook-service
EXPOSE 18886
COPY --from=builder /opt/ts-rebook-service/target/ts-rebook-service-1.0.jar app/ts-rebook-service.jar
CMD ["java", "-javaagent:/opt/opentelemetry-javaagent.jar", "-Xmx200m", "-jar", "/opt/app/ts-rebook-service.jar"]

FROM service AS ts-route-plan-service
EXPOSE 14578
COPY --from=builder /opt/ts-route-plan-service/target/ts-route-plan-service-1.0.jar app/ts-route-plan-service.jar
CMD ["java", "-javaagent:/opt/opentelemetry-javaagent.jar", "-Xmx200m", "-jar", "/opt/app/ts-route-plan-service.jar"]

FROM service AS ts-route-service
EXPOSE 11178
COPY --from=builder /opt/ts-route-service/target/ts-route-service-1.0.jar app/ts-route-service.jar
CMD ["java", "-javaagent:/opt/opentelemetry-javaagent.jar", "-Xmx200m", "-jar", "/opt/app/ts-route-service.jar"]

FROM service AS ts-seat-service
EXPOSE 18898
COPY --from=builder /opt/ts-seat-service/target/ts-seat-service-1.0.jar app/ts-seat-service.jar
CMD ["java", "-javaagent:/opt/opentelemetry-javaagent.jar", "-Xmx200m", "-jar", "/opt/app/ts-seat-service.jar"]

FROM service AS ts-security-service
EXPOSE 11188
COPY --from=builder /opt/ts-security-service/target/ts-security-service-1.0.jar app/ts-security-service.jar
CMD ["java", "-javaagent:/opt/opentelemetry-javaagent.jar", "-Xmx200m", "-jar", "/opt/app/ts-security-service.jar"]

FROM service AS ts-station-service
EXPOSE 12345
COPY --from=builder /opt/ts-station-service/target/ts-station-service-1.0.jar app/ts-station-service.jar
CMD ["java", "-javaagent:/opt/opentelemetry-javaagent.jar", "-Xmx200m", "-jar", "/opt/app/ts-station-service.jar"]

FROM service AS ts-ticketinfo-service
EXPOSE 15681
COPY --from=builder /opt/ts-ticketinfo-service/target/ts-ticketinfo-service-1.0.jar app/ts-ticketinfo-service.jar
CMD ["java", "-javaagent:/opt/opentelemetry-javaagent.jar", "-Xmx200m", "-jar", "/opt/app/ts-ticketinfo-service.jar"]

FROM service AS ts-train-service
EXPOSE 14567
COPY --from=builder /opt/ts-train-service/target/ts-train-service-1.0.jar app/ts-train-service.jar
CMD ["java", "-javaagent:/opt/opentelemetry-javaagent.jar", "-Xmx200m", "-jar", "/opt/app/ts-train-service.jar"]

FROM service AS ts-travel-plan-service
EXPOSE 14322
COPY --from=builder /opt/ts-travel-plan-service/target/ts-travel-plan-service-1.0.jar app/ts-travel-plan-service.jar
CMD ["java", "-javaagent:/opt/opentelemetry-javaagent.jar", "-Xmx200m", "-jar", "/opt/app/ts-travel-plan-service.jar"]

FROM service AS ts-travel-service
EXPOSE 12346
COPY --from=builder /opt/ts-travel-service/target/ts-travel-service-1.0.jar app/ts-travel-service.jar
CMD ["java", "-javaagent:/opt/opentelemetry-javaagent.jar", "-Xmx200m", "-jar", "/opt/app/ts-travel-service.jar"]

FROM service AS ts-travel2-service
EXPOSE 16346
COPY --from=builder /opt/ts-travel2-service/target/ts-travel2-service-1.0.jar app/ts-travel2-service.jar
CMD ["java", "-javaagent:/opt/opentelemetry-javaagent.jar", "-Xmx200m", "-jar", "/opt/app/ts-travel2-service.jar"]

FROM service AS ts-user-service
EXPOSE 12346
COPY --from=builder /opt/ts-user-service/target/ts-user-service-1.0.jar app/ts-user-service.jar
CMD ["java", "-javaagent:/opt/opentelemetry-javaagent.jar", "-Xmx200m", "-jar", "/opt/app/ts-user-service.jar"]

FROM service AS ts-verification-code-service
EXPOSE 15678
COPY --from=builder /opt/ts-verification-code-service/target/ts-verification-code-service-1.0.jar app/ts-verification-code-service.jar
CMD ["java", "-javaagent:/opt/opentelemetry-javaagent.jar", "-Xmx200m", "-jar", "/opt/app/ts-verification-code-service.jar"]
