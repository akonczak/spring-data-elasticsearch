package org.springframework.data.elasticsearch.client.rest;

import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import sun.nio.ch.IOUtil;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Arrays.asList;

public class RestUtil {

    public static String endpoint(String... parts) {
        return asList(parts).stream().filter(StringUtils::hasText).collect(Collectors.joining("/"));
    }

    public static String asString(InputStream stream) throws IOException {
        return StreamUtils.copyToString(stream, UTF_8);
    }

}
