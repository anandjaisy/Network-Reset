plugins {
    id("io.micronaut.application") version "4.5.3"
    id("com.gradleup.shadow") version "8.3.6"
    id("io.micronaut.aot") version "4.5.3"
}

version = "0.1"
group = "ramsay.health"

repositories {
    mavenCentral()
}

dependencies {
    annotationProcessor("io.micronaut:micronaut-http-validation")
    annotationProcessor("io.micronaut.openapi:micronaut-openapi")
    annotationProcessor("io.micronaut.serde:micronaut-serde-processor")
    annotationProcessor("io.micronaut.validation:micronaut-validation-processor")

    implementation("io.micronaut.validation:micronaut-validation")
    implementation("jakarta.validation:jakarta.validation-api")
    implementation("io.micronaut.gcp:micronaut-gcp-logging")
    implementation("io.micronaut.serde:micronaut-serde-jackson")
    implementation("com.google.cloud:google-cloud-firestore:3.31.6")
    implementation("io.micronaut:micronaut-retry")
    implementation("com.nimbusds:nimbus-jose-jwt:10.3")

//    implementation("io.micronaut:micronaut-management")
//    implementation("io.micronaut.micrometer:micronaut-micrometer-core")
//    implementation("io.micronaut.micrometer:micronaut-micrometer-registry-dynatrace")

    implementation("io.micronaut:micronaut-http-client")
    compileOnly("io.micronaut.openapi:micronaut-openapi-annotations")
    runtimeOnly("ch.qos.logback:logback-classic")

    testImplementation("io.micronaut:micronaut-http-client")
}


application {
    mainClass = "ramsay.health.MsAddinApplication"
}
java {
    sourceCompatibility = JavaVersion.toVersion("24")
    targetCompatibility = JavaVersion.toVersion("24")
}


graalvmNative.toolchainDetection = true

micronaut {
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("ramsay.health.*")
    }
    aot {
        // Please review carefully the optimizations enabled below
        // Check https://micronaut-projects.github.io/micronaut-aot/latest/guide/ for more details
        optimizeServiceLoading = true
        convertYamlToJava = true
        precomputeOperations = true
        cacheEnvironment = true
        optimizeClassLoading = true
        deduceEnvironment = true
        optimizeNetty = true
        replaceLogbackXml = true
    }
}
tasks.named<io.micronaut.gradle.docker.NativeImageDockerfile>("optimizedDockerfileNative") {
    jdkVersion = "24"
    baseImage = "artifactory.devops.ramsayhealth-aws.com.au/ramsay-docker-gcr-remote-prod/distroless/base-debian12"
}


