FROM golang:1.22 as builder

WORKDIR /workspace

COPY go.mod go.mod
COPY go.sum go.sum

RUN go mod download

COPY src/main/main.go main.go
COPY src/main/otel.go otel.go

RUN CGO_ENABLED=0 GOOS=linux GO111MODULE=on go build -mod=readonly -a -o app main.go otel.go

FROM gcr.io/distroless/static:nonroot

WORKDIR /

COPY --from=builder /workspace/app app

EXPOSE 12862

CMD ["/app"]