package com.sequarius.titan.sample.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * project titan-sample
 *
 * @author Sequarius *
 * @since 01/02/2020
 */
public class JacksonUtil {
    public static ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }

}
