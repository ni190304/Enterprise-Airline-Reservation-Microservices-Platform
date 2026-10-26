if ([string]::IsNullOrEmpty($env:FLIGHT_DB_PASSWORD)) {
    $env:FLIGHT_DB_PASSWORD = [Environment]::GetEnvironmentVariable("FLIGHT_DB_PASSWORD", "User")
}

if ([string]::IsNullOrEmpty($env:FLIGHT_DB_PASSWORD)) {
    throw "FLIGHT_DB_PASSWORD is missing. Set it before starting Flight Ops."
}

mvn spring-boot:run "-Dspring-boot.run.arguments=--spring.datasource.password=$env:FLIGHT_DB_PASSWORD"
