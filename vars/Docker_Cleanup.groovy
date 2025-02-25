def call(String dockerUser, String userName, String appName, String tierName, String newversion, String defaultversion){
    script{
        def latestTag = "${dockerUser}/${userName}-${appName}-${tierName}-img:latest"
        def newversionTag = "${dockerUser}/${userName}-${appName}-${tierName}-img:${newversion}"
        def defaultversionTag = "${dockerUser}/${userName}-${appName}-${tierName}-img:${defaultversion}"

        // Check and Remove latest image if it exists
        def latestImageExists = sh(script: "docker images -q ${latestTag}", returnStdout: true).trim()
        if (latestImageExists) {
            echo "Docker Image ${latestTag} already exists - Removing it Now..."
            sh "docker rmi -f ${latestTag}"
        } else {
            echo "Docker Image ${latestTag} does not exists..." 
        }

        // Check and Remove NewVersion image if it exists
        def newVersionImageExists = sh(script: "docker images -q ${newversionTag}", returnStdout: true).trim()
        if (newVersionImageExists) {
            echo "Docker Image ${newversionTag} already exists - Removing it Now..."
            sh "docker rmi -f ${newversionTag}"
        } else {
            echo "Docker Image ${newversionTag} does not exists..." 
        }

        // Check and Remove defaultVersion image if it exists
        def defaultVersionImageExists = sh(script: "docker images -q ${defaultversionTag}", returnStdout: true).trim()
        if (defaultVersionImageExists) {
            echo "Docker Image ${defaultversionTag} already exists - Removing it Now..."
            sh "docker rmi -f ${defaultversionTag}"
        } else {
            echo "Docker Image ${defaultversionTag} does not exists..." 
        }
    }
}
