// vars/versionPrompt.groovy
#!/usr/bin/env groovy

/**
 * Prompts the user for a version number with a timeout
 * If no input is received, automatically increments the patch version
 *
 * @param defaultVersion The default version to use if no input is provided
 * @param timeout The timeout in seconds for user input (default: 30)
 * @param message The message to display in the input prompt (optional)
 * @return The selected version (either user input or auto-incremented)
 */
def call(Map config = [:]) {
    def defaultVersion = config.defaultVersion ?: env.defaultVersion ?: '1.0.0'
    def timeoutValue = config.timeout ?: 30
    def promptMessage = config.message ?: 'Please Provide The Version Of The Web Application'
    
    echo "The Default Version is: ${defaultVersion}"
    
    try {
        timeout(time: timeoutValue, unit: 'SECONDS') {
            // Prompt user to provide a new version number within specified timeout
            def userVersion = input(
                message: promptMessage,
                ok: 'Submit',
                parameters: [string(defaultValue: defaultVersion, 
                                    description: 'Provide a new version number', 
                                    name: 'newVersion')]
            )
            
            // If input is provided, set it as the new version
            def newVersion = userVersion
            echo "The New Version Provided is: ${newVersion}"
            return newVersion
        }
    } catch (Exception e) {
        // On timeout or abort, increment the default version number by 0.0.1
        def version = defaultVersion.tokenize('.')
        version[2] = (version[2] as Integer) + 1
        def newVersion = version.join('.')
        echo "No Version Provided So The New Version Will Be: ${newVersion}"
        return newVersion
    }
}
