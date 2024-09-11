plugins {
    id("java")
}

group = "ru.eblan"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("com.1stleg:jnativehook:2.1.0")
}

tasks.test {
    useJUnitPlatform()
}