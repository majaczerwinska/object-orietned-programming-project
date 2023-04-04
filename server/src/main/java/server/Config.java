/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package server;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Value("${admin.password}")
    private String adminPassword;

    /**
     * the admin password string,
     * as set in application.properties.
     * @return the password string
     */
    @Bean
    public String adminPassword() {
        return adminPassword;
    }


    /**
     * generate a single, unique, random token upon server start
     * distributed to the auth and query endpoints,
     * first sent to the client upon successful authentication
     * and then verified from the query endpoint before running
     * any sql queries received there.
     * @return string token
     */
    @Bean
    public String authToken() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[32];
        random.nextBytes(bytes);

        String token = Base64.getEncoder().encodeToString(bytes);

        return token;
    }


    /**
     * @return random instance
     */
    @Bean
    public Random getRandom() {
        return new Random();
    }
}