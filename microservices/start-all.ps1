$root = $PSScriptRoot
Set-Location $root

function Wait-Port($port, $name, $timeoutSec = 180) {
    Write-Host "Waiting for $name on port $port..." -ForegroundColor Yellow
    $deadline = (Get-Date).AddSeconds($timeoutSec)
    while ((Get-Date) -lt $deadline) {
        $client = New-Object System.Net.Sockets.TcpClient
        try {
            $client.Connect("127.0.0.1", $port)
            $client.Close()
            Write-Host "$name is up." -ForegroundColor Green
            return
        } catch { Start-Sleep -Seconds 3 }
    }
    Write-Host "$name did not come up in $timeoutSec s. Check its terminal." -ForegroundColor Red
}

function Start-Svc($path) {
    $name = Split-Path $path -Leaf
    Start-Process powershell -WorkingDirectory (Join-Path $root $path) -ArgumentList @(
        "-NoExit", "-Command",
        "`$Host.UI.RawUI.WindowTitle='$name'; mvn spring-boot:run"
    )
}

# 1. Docker: only the databases, Kafka and Redis (NOT the Docker eureka/config containers)
$containers = @("kafka", "gds-redis-dev",
    "userdb", "locationdb", "airlinecoredb", "flightopsdb", "pricingdb",
    "ancillarydb", "bookingdb", "paymentdb", "seatdb")
docker start $containers
Write-Host "Giving MySQL a few seconds to be ready..." -ForegroundColor Yellow
Start-Sleep -Seconds 15

# 2. Eureka, then 3. Config server
Start-Svc "cloud\service-registry"
Wait-Port 8761 "Eureka"
Start-Svc "cloud\config-server"
Wait-Port 8888 "Config server"

# 4. The 9 services
$services = @(
    "user-service", "location-service", "airline-core-service",
    "flight-ops-service", "pricing-service", "ancillary-service",
    "booking-service", "payment-service", "seat-service"
)
foreach ($service in $services) { Start-Svc "services\$service" }

# 5. Gateway last
Start-Svc "cloud\api-gateway"