package org.aziz.quince.service.features;

import java.util.Map;

/**
 * Created by aziz on 19-2-17.
 */
public class EnvService {
    private String POD_GROUP_KEY;
    // Refactoring: Change method signature: POD_GROUP_KEY was given directly to getEnvValue()
    public EnvService(String POD_GROUP_KEY) {
        this.POD_GROUP_KEY = POD_GROUP_KEY;
    }

    public String getEnvValue() {
        Map<String, String> env = System.getenv();
        for (String envName : env.keySet()) {
            if (envName.equals(POD_GROUP_KEY)) {
                return env.get(envName);

            }
        }

        return null;
    }
}