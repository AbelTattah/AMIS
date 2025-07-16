<#
.SYNOPSIS
    Builds and runs the AMIS (Atinka Meds Inventory System) application.
.DESCRIPTION
    This script compiles all Java source files and runs the AMIS application.
    It creates necessary directories and handles the classpath automatically.
#>

# Stop on first error
$ErrorActionPreference = "Stop"

# Define directories
$projectRoot = $PSScriptRoot
$srcDir = Join-Path $projectRoot "src"
$outDir = Join-Path $projectRoot "out"
$dataDir = Join-Path $projectRoot "data"

# Create output directory if it doesn't exist
if (-not (Test-Path $outDir)) {
    Write-Host "Creating output directory: $outDir"
    New-Item -ItemType Directory -Path $outDir | Out-Null
}

# Create data directory if it doesn't exist
if (-not (Test-Path $dataDir)) {
    Write-Host "Creating data directory: $dataDir"
    New-Item -ItemType Directory -Path $dataDir | Out-Null
    
    # Create empty data files if they don't exist
    @("drugs.txt", "suppliers.txt", "customers.txt", "purchase_history.txt", "sales_log.txt") | ForEach-Object {
        $filePath = Join-Path $dataDir $_
        if (-not (Test-Path $filePath)) {
            Write-Host "Creating empty data file: $_"
            New-Item -ItemType File -Path $filePath | Out-Null
        }
    }
}

# Find all Java files
$javaFiles = Get-ChildItem -Path $srcDir -Filter *.java -Recurse -File | Select-Object -ExpandProperty FullName

if ($javaFiles.Count -eq 0) {
    Write-Error "No Java source files found in $srcDir"
    exit 1
}

# Compile Java files
Write-Host "Compiling Java files..."
try {
    javac -d $outDir -cp $outDir $javaFiles
    if ($LASTEXITCODE -ne 0) {
        Write-Error "Compilation failed with exit code $LASTEXITCODE"
        exit $LASTEXITCODE
    }
    Write-Host "Compilation completed successfully." -ForegroundColor Green
} catch {
    Write-Error "Error during compilation: $_"
    exit 1
}

# Run the application
Write-Host "`nStarting AMIS Application..." -ForegroundColor Cyan
Write-Host "----------------------------------------" -ForegroundColor Cyan
try {
    java -cp $outDir com.gp27.amis.App
} catch {
    Write-Error "Error running application: $_"
    exit 1
}
