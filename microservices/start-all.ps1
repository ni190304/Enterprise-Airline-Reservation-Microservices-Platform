$services = @(
    "user-service",
    "location-service",
    "airline-core-service",
    "flight-ops-service",
    "pricing-service",
    "ancillary-service",
    "booking-service",
    "payment-service",
    "seat-service"
)

foreach ($service in $services) {
    Start-Process powershell -ArgumentList @(
        "-NoExit",
        "-Command",
        "cd '.\services\$service'; mvn spring-boot:run"
    )
}