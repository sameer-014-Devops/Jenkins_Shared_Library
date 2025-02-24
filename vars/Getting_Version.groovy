def call(Map config = [:]) {
    def defaultVersion = config.defaultVersion ?: (env.defaultVersion ?: '1.0.0')
    def timeoutValue = config.timeout ?: 30
    def promptMessage = config.message ?: 'Please Provide The Version Of The Web Application'
    
    echo "The Default Version is: ${defaultVersion}"

    try {
        timeout(time: timeoutValue, unit: 'SECONDS') {
            def userVersion = input(
                message: promptMessage,
                ok: 'Submit',
                parameters: [string(defaultValue: defaultVersion, 
                                    description: 'Provide a new version number', 
                                    name: 'newVersion')]
            )

            // If the user provides a version, return it immediately
            if (userVersion?.trim()) {
                echo "The New Version Provided is: ${userVersion}"
                return userVersion
            }
        }
    } catch (hudson.AbortException e) {
        echo "User input was aborted or timed out. Auto-incrementing the version."
    } catch (Exception e) {
        echo "Unexpected error: ${e.message}"
    }

    // Ensure fallback logic only runs if input is not provided
    if (!defaultVersion) {
        echo "Invalid default version. Falling back to 1.0.1"
        return "1.0.1"
    }

    def versionParts = defaultVersion.tokenize('.')
    if (versionParts.size() == 3 && versionParts[2].isInteger()) {
        versionParts[2] = (versionParts[2] as Integer) + 1
    } else {
        echo "Invalid version format detected, using fallback version 1.0.1"
        return "1.0.1"
    }

    def newVersion = versionParts.join('.')
    echo "No Version Provided. Auto-Incremented Version: ${newVersion}"
    return newVersion
}
