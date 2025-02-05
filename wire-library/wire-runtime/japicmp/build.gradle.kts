import me.champeau.gradle.japicmp.JapicmpTask
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType

buildscript {
  dependencies {
    classpath(libs.pluginz.kotlin)
  }
  repositories {
    mavenCentral()
  }
}

plugins {
  id("java-library")
  kotlin("jvm")
  id("me.champeau.gradle.japicmp")
}

val platformAttr = Attribute.of("org.jetbrains.kotlin.platform.type", KotlinPlatformType::class.java)

val baseline by configurations.creating {
  attributes {
    attribute(platformAttr, KotlinPlatformType.jvm)
  }
}
val latest by configurations.creating {
  attributes {
    attribute(platformAttr, KotlinPlatformType.jvm)
  }
}

dependencies {
  baseline("com.squareup.wire:wire-runtime:3.0.0-alpha01") {
    isTransitive = false
    isForce = true
  }
  latest(projects.wireRuntime)
}

val japicmp by tasks.creating(JapicmpTask::class) {
  dependsOn("jar")
  oldClasspath = baseline
  newClasspath = latest
  isOnlyBinaryIncompatibleModified = true
  isFailOnModification = true
  txtOutputFile = file("$buildDir/reports/japi.txt")
  isIgnoreMissingClasses = true
  isIncludeSynthetic = true
  packageExcludes = listOf(
      "com.squareup.wire.internal"
  )
  classExcludes = listOf(
      "com.squareup.wire.TagHandler"
  )
  methodExcludes = listOf(
      // package protected -> public
      "com.squareup.wire.WireField\$Label#isOneOf()",
      "com.squareup.wire.WireField\$Label#isPacked()",
      "com.squareup.wire.WireField\$Label#isRepeated()",
      // non-final -> final
      "com.squareup.wire.FieldEncoding#rawProtoAdapter()",
      "com.squareup.wire.MessageSerializedForm#readResolve()",
      "com.squareup.wire.ProtoAdapter#newEnumAdapter(java.lang.Class)",
      "com.squareup.wire.ProtoReader#beginMessage()",
      "com.squareup.wire.ProtoReader#endMessage(long)",
      "com.squareup.wire.ProtoReader#forEachTag(com.squareup.wire.TagHandler)",
      "com.squareup.wire.ProtoReader#nextTag()",
      "com.squareup.wire.ProtoReader#peekFieldEncoding()",
      "com.squareup.wire.ProtoReader#readBytes()",
      "com.squareup.wire.ProtoReader#readFixed32()",
      "com.squareup.wire.ProtoReader#readFixed64()",
      "com.squareup.wire.ProtoReader#readString()",
      "com.squareup.wire.ProtoReader#readVarint32()",
      "com.squareup.wire.ProtoReader#readVarint64()",
      "com.squareup.wire.ProtoReader#skip()",
      "com.squareup.wire.ProtoWriter#writeBytes(okio.ByteString)",
      "com.squareup.wire.ProtoWriter#writeFixed32(int)",
      "com.squareup.wire.ProtoWriter#writeFixed64(long)",
      "com.squareup.wire.ProtoWriter#writeString(java.lang.String)",
      "com.squareup.wire.ProtoWriter#writeTag(int, com.squareup.wire.FieldEncoding)",
      "com.squareup.wire.ProtoWriter#writeVarint32(int)",
      "com.squareup.wire.ProtoWriter#writeVarint64(long)",
      // non-abstract -> abstract
      "com.squareup.wire.ProtoAdapter#redact(java.lang.Object)"
  )
}
val check by tasks.getting {
  dependsOn(japicmp)
}
