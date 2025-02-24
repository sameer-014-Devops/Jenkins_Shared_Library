def call(Map config = [:]) {
    def defaultVersion = validateVersion(config.defaultVersion ?: env.defaultVersion ?: '1.0.0')
    def timeoutValue = validateTimeout(config.timeout)
    def promptMessage = config.message ?: 'Please Provide The Version Of The Web Application'
    
    echo "The Default Version is: ${defaultVersion}"
    
    try {
        timeout(time: timeoutValue, unit: 'SECONDS') {
            def userVersion = input(
                message: promptMessage,
                ok: 'Submit',
                parameters: [
                    string(
                        defaultValue: defaultVersion, 
                        description: 'Provide a new version number (format: X.Y.Z)', 
                        name: 'newVersion'
                    )
                ]
            )
            
            if (userVersion?.trim()) {
                def validatedVersion = validateVersion(userVersion)
                if (validatedVersion) {
                    echo "The New Version Provided is: ${validatedVersion}"
                    return validatedVersion
                } else {
                    error "Invalid version format provided: ${userVersion}. Expected format: X.Y.Z"
                }
            }
        }
    } catch (org.jenkinsci.plugins.workflow.steps.FlowInterruptedException e) {
        echo "Input timeout reached after ${timeoutValue} seconds. Auto-incrementing version."
    } catch (hudson.AbortException e) {
        echo "User aborted the input. Auto-incrementing version."
    } catch (Exception e) {
        echo "Unexpected error during version input: ${e.message}"
    }
    
    return incrementVersion(defaultVersion)
}

/**
 * Validates the version string format
 * @param version Version string to validate
 * @return Valid version string or null if invalid
 */
private String validateVersion(String version) {
    if (!version?.trim()) return '1.0.0'
    
    def versionPattern = /^\d+\.\d+\.\d+$/
    if (!(version =~ versionPattern)) {
        echo "Warning: Invalid version format '${version}'. Using default version 1.0.0"
        return '1.0.0'
    }
    
    def parts = version.tokenize('.')
    try {
        parts.each { 
            assert it.isInteger() && it.toInteger() >= 0 
        }
        return version
    } catch (AssertionError | NumberFormatException e) {
        echo "Warning: Invalid version numbers in '${version}'. Using default version 1.0.0"
        return '1.0.0'
    }
}

/**
 * Validates and normalizes the timeout value
 * @param timeout Timeout value to validate
 * @return Valid timeout value between 15 and 300 seconds
 */
private int validateTimeout(def timeout) {
    try {
        def timeoutInt = timeout as Integer
        if (timeoutInt < 15) {
            echo "Warning: Timeout value too low. Setting to minimum (15 seconds)"
            return 15
        }
        if (timeoutInt > 300) {
            echo "Warning: Timeout value too high. Setting to maximum (300 seconds)"
            return 300
        }
        return timeoutInt
    } catch (Exception e) {
        echo "Warning: Invalid timeout value. Using default (30 seconds)"
        return 30
    }
}

/**
 * Increments the patch version of a semantic version string
 * @param version Current version to increment
 * @return Incremented version string
 */
private String incrementVersion(String version) {
    def versionParts = version.tokenize('.')
    try {
        def major = versionParts[0] as Integer
        def minor = versionParts[1] as Integer
        def patch = versionParts[2] as Integer
        
        def newVersion = "${major}.${minor}.${patch + 1}"
        echo "Auto-incremented version: ${newVersion}"
        return newVersion
    } catch (Exception e) {
        echo "Error incrementing version: ${e.message}. Falling back to version 1.0.1"
        return "1.0.1"
    }
}
