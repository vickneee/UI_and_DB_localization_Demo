# Stage
# FROM openjdk:17-jdk-slim
# That image tag used to exist but was recently renamed
FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

# Install GUI + GL libraries AND X Virtual Frame Buffer (Xvfb)
# Xvfb is necessary to run GUI applications (like JavaFX) in a headless environment.
RUN apt-get update && apt-get install -y \
    libx11-6 libxext6 libxrender1 libxtst6 libxi6 \
    libxrandr2 libxinerama1 libgtk-3-0 \
    libgl1-mesa-glx libgl1-mesa-dri mesa-utils \
    fonts-dejavu wget unzip \
    xvfb \
    && rm -rf /var/lib/apt/lists/*

# Download and unzip JavaFX Linux SDK
RUN mkdir -p /javafx-sdk \
    && wget -O javafx.zip https://download2.gluonhq.com/openjfx/21.0.2/openjfx-21.0.2_linux-x64_bin-sdk.zip \
    && unzip javafx.zip -d /javafx-sdk \
    && mv /javafx-sdk/javafx-sdk-21.0.2/lib /javafx-sdk/lib \
    && rm -rf /javafx-sdk/javafx-sdk-21.0.2 javafx.zip

# Copy your JAR (target/app.jar -> used same name as generated.jar file)
COPY target/bmi_calculator.jar /app/app.jar

# Force software rendering (avoid ES2 crash)
ENV JAVAFX_PRISM_SW=true

# Use host X11 display
ENV DISPLAY=:0

# Start Xvfb and then run JavaFX
CMD ["java", "--module-path", "/javafx-sdk/lib", "--add-modules", "javafx.controls,javafx.fxml", "-jar", "app.jar"]