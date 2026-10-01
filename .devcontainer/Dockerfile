FROM mcr.microsoft.com/devcontainers/base:ubuntu-22.04

# Instalar dependencias del sistema y herramientas de desarrollo (Java 17, Python 3.10, Node.js)
RUN apt-get update && apt-get install -y --no-install-recommends \
    curl \
    git \
    unzip \
    openjdk-17-jdk \
    python3 \
    python3-pip \
    postgresql-client \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*

# Instalar Node.js LTS
RUN curl -fsSL https://deb.nodesource.com/setup_20.x | bash - \
    && apt-get install -y nodejs \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*

# Configurar usuario vscode por defecto
USER vscode
WORKDIR /workspace
