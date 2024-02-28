FROM node:20

WORKDIR /app

COPY bin/ bin/
COPY public/ public/
COPY app.js app.js
COPY package.json package.json

RUN npm install
RUN npm install --save @opentelemetry/api \
        @opentelemetry/auto-instrumentations-node

ENV NODE_OPTIONS="--require @opentelemetry/auto-instrumentations-node/register"

EXPOSE 16108

CMD [ "npm", "start" ]