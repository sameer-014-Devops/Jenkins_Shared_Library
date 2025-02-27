def call(String dockerUser, String userName, String appName, String tierName, String newVersion, String defaultVersion) {
    
    script {
        
        def latestTag = "${dockerUser}/${userName}-${appName}-${tierName}-img:latest"
        def newVersionTag = "${dockerUser}/${userName}-${appName}-${tierName}-img:${newVersion}"
        def defaultVersionTag = "${dockerUser}/${userName}-${appName}-${tierName}-img:${defaultVersion}"

        try {
            // Check and Remove latest image if it exists
            def latestImageExists = sh(script: "docker images -q ${latestTag}", returnStdout: true).trim()

            if (latestImageExists) {
                echo "Docker Image ${latestTag} already exists - Removing it Now..."
                sh "docker rmi -f ${latestTag}"
            } else {
                echo "Docker Image ${latestTag} does not exist..."
            }

            // Check and Remove NewVersion image if it exists
            def newVersionImageExists = sh(script: "docker images -q ${newVersionTag}", returnStdout: true).trim()

            if (newVersionImageExists) {
                echo "Docker Image ${newVersionTag} already exists - Removing it Now..."
                sh "docker rmi -f ${newVersionTag}"
            } else {
                echo "Docker Image ${newVersionTag} does not exist..."
            }

            // Check and Remove defaultVersion image if it exists
            def defaultVersionImageExists = sh(script: "docker images -q ${defaultVersionTag}", returnStdout: true).trim()
            
            if (defaultVersionImageExists) {
                echo "Docker Image ${defaultVersionTag} already exists - Removing it Now..."
                sh "docker rmi -f ${defaultVersionTag}"
            } else {
                echo "Docker Image ${defaultVersionTag} does not exist..."
            }
        } catch (Exception e) {
            echo "Error during Docker cleanup: ${e.message}"
            throw e
        }
        
    }
    
}
