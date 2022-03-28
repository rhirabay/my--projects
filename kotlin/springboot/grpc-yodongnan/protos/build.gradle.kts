// cf. https://github.com/grpc/grpc-kotlin/blob/v1.2.1/examples/stub/build.gradle.kts

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.google.protobuf.gradle.generateProtoTasks
import com.google.protobuf.gradle.id
import com.google.protobuf.gradle.plugins
import com.google.protobuf.gradle.protobuf
import com.google.protobuf.gradle.protoc

plugins {
	id("org.springframework.boot") version "2.6.5"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.6.10"
	kotlin("plugin.spring") version "1.6.10"
	id("com.google.protobuf") version "0.8.18"
	id("java")
	id("maven-publish")
}

group = "rhirabay.grpc.yidongnan"
version = "0.0.1"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

	implementation("io.grpc:grpc-protobuf:${property("grpcVersion")}")
	implementation("io.grpc:grpc-stub:${property("grpcVersion")}")

	implementation("com.google.protobuf:protobuf-java-util:${property("protobufVersion")}")
	implementation("com.google.protobuf:protobuf-kotlin:${property("protobufVersion")}")
	implementation("io.grpc:grpc-kotlin-stub:${property("grpcKotlinVersion")}")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

protobuf {
	protoc {
		artifact = "com.google.protobuf:protoc:${property("protobufVersion")}"
	}
	generatedFilesBaseDir = "$projectDir/build/generated"

	plugins {
		id("grpc") {
			artifact = "io.grpc:protoc-gen-grpc-java:${property("grpcVersion")}"
		}
		id("grpckt") {
			artifact = "io.grpc:protoc-gen-grpc-kotlin:${property("grpcKotlinVersion")}:jdk7@jar"
		}
	}
	generateProtoTasks {
		all().forEach {
			it.plugins {
				id("grpc")
				id("grpckt")
			}
			it.builtins {
				id("kotlin")
			}
		}
	}
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

tasks.jar {
	archiveClassifier.set("")
}

publishing {
	publications {
		create<MavenPublication>("maven") {
			from(components["kotlin"])
		}
	}
}