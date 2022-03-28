import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.6.5"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.6.10"
	kotlin("plugin.spring") version "1.6.10"
}

group = "rhirabay.gprc.yidongnan"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
	mavenLocal()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

	// https://mvnrepository.com/artifact/net.devh/grpc-server-spring-boot-starter
	implementation("net.devh:grpc-server-spring-boot-starter:2.13.1.RELEASE")

//	implementation(project(":protos"))
	implementation("rhirabay.grpc.yidongnan:protos:+")

	implementation("com.google.protobuf:protobuf-java-util:${property("protobufVersion")}")
	implementation("com.google.protobuf:protobuf-kotlin:${property("protobufVersion")}")
	implementation("io.grpc:grpc-kotlin-stub:${property("grpcKotlinVersion")}")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
