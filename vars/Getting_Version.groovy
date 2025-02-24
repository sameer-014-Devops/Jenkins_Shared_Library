def call() {
    script {
        echo "The Default Version is: ${env.defaultVersion}"
        try {
            timeout(time: 15, unit: 'SECONDS') {
                // Prompt user to provide a new version number within 15 seconds
                def userVersion = input(
                    message: 'Please Provide The Version Of The Web Application',
                    ok: 'Submit',
                    parameters: [string(defaultValue: env.defaultVersion, description: 'Provide a new version number', name: 'newVer')]
                )
                // If input is provided, set it as the new version
                env.newVer = userVersion
                echo "The New Version Provided is: ${env.newVer}"
            }
        } catch (Exception e) {
            // On timeout or abort, increment the default version number by 0.0.1
            def version = env.defaultVersion.tokenize('.')
            version[2] = (version[2] as Integer) + 1
            env.newVer = version.join('.')
            echo "No Version Provided So The New Version Will: ${env.newVer}"
        }
    }
}
