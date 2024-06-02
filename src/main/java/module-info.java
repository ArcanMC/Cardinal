module Cardinal {
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires com.google.gson;
    requires docker.java.api;
    requires docker.java.core;
    requires docker.java.transport;
    requires docker.java.transport.httpclient5;
    requires java.desktop;
    requires jline;
    requires lombok;
    requires org.jline;
    requires redisson;
    requires snakeyaml;
    requires testcontainers;
}