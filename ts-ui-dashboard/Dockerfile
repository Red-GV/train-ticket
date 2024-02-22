FROM ubuntu:22.04 as builder

ARG NGINX_VERSION=1.25.3
ARG OPENTELEMETRY_WEBSERVER_VERSION=1.0.4

WORKDIR /workspace

RUN apt-get update -y \
    && apt-get install -y git unzip wget

RUN wget -O opentelemetry-webserver-${OPENTELEMETRY_WEBSERVER_VERSION}.zip https://github.com/open-telemetry/opentelemetry-cpp-contrib/releases/download/webserver%2Fv${OPENTELEMETRY_WEBSERVER_VERSION}/opentelemetry-webserver-sdk-x64-linux.tgz \
    && unzip opentelemetry-webserver-${OPENTELEMETRY_WEBSERVER_VERSION}.zip \
    && tar -zxf opentelemetry-webserver-sdk-x64-linux.tgz 

FROM nginx:1.25.3

WORKDIR /opt

COPY --from=builder /workspace/opentelemetry-webserver-sdk/sdk_lib/lib/ /usr/local/lib/
COPY --from=builder /workspace/opentelemetry-webserver-sdk/logs/ opentelemetry-webserver-sdk/logs/
COPY --from=builder /workspace/opentelemetry-webserver-sdk/conf/ opentelemetry-webserver-sdk/conf/
COPY --from=builder /workspace/opentelemetry-webserver-sdk/WebServerModule/Nginx/${NGINX_VERSION}/ngx_http_opentelemetry_module.so /usr/lib/nginx/modules/ngx_http_opentelemetry_module.so
COPY --from=builder /workspace/opentelemetry-webserver-sdk/install.sh opentelemetry-webserver-sdk/install.sh
COPY --from=builder /workspace/opentelemetry-webserver-sdk/VERSION.txt opentelemetry-webserver-sdk/VERSION.txt

RUN cd opentelemetry-webserver-sdk \
    && ./install.sh \
    && rm install.sh

COPY static/ /usr/share/nginx/html/
COPY nginx.conf /etc/nginx/nginx.conf
COPY opentelemetry_module.conf /etc/nginx/conf.d/opentelemetry_module.conf

RUN ldconfig /usr/local/lib