FROM gitpod/workspace-full-vnc

# Install Java 17
USER root
RUN apt-get update && 
    apt-get install -y openjdk-17-jdk && 
    apt-get clean && 
    rm -rf /var/lib/apt/lists/*

ENV JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
ENV PATH="$JAVA_HOME/bin:$PATH"

# Install Android SDK
ARG ANDROID_SDK_ROOT="/home/gitpod/Android/sdk"
ENV ANDROID_SDK_ROOT=${ANDROID_SDK_ROOT}
ENV PATH="$PATH:${ANDROID_SDK_ROOT}/cmdline-tools/latest/bin:${ANDROID_SDK_ROOT}/platform-tools"

RUN mkdir -p ${ANDROID_SDK_ROOT}/cmdline-tools && 
    wget -q https://dl.google.com/android/repository/commandlinetools-linux-8594367_latest.zip -O android-cmdline-tools.zip && 
    unzip -q android-cmdline-tools.zip -d ${ANDROID_SDK_ROOT}/cmdline-tools/latest && 
    rm android-cmdline-tools.zip

# Accept Android SDK licenses and install necessary components
RUN yes | ${ANDROID_SDK_ROOT}/cmdline-tools/latest/bin/sdkmanager --licenses
RUN ${ANDROID_SDK_ROOT}/cmdline-tools/latest/bin/sdkmanager "platform-tools" "platforms;android-34" "build-tools;34.0.0"

# Install Node.js (for Hardhat)
RUN curl -fsSL https://deb.nodesource.com/setup_lts.x | bash - && 
    apt-get install -y nodejs

# Install yarn (optional, but common for Node.js projects)
RUN npm install -g yarn

# Set up global npm for Hardhat
ENV NPM_CONFIG_PREFIX=/home/gitpod/.npm-global
ENV PATH=$NPM_CONFIG_PREFIX/bin:$PATH
RUN npm config set prefix $NPM_CONFIG_PREFIX

# Install web3j CLI (if needed later)
# RUN wget https://github.com/web3j/web3j-cli/releases/download/4.10.0/web3j-cli-shadow-4.10.0.jar -P /usr/local/bin/

USER gitpod
