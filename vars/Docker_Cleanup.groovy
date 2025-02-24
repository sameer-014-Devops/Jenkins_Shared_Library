def call(String dockerUser, String userName, String appName, String newversion, String defaultVersion){
    script{
        def latestTag = "${dockerUser}/${userName}-${appName}-img:latest"
        def newversionTag = "${dockerUser}/${userName}-${appName}-img:${newversion}"
        def defaultversionTag = "${dockerUser}/${userName}-${appName}-img:${defaultVersion}"

        // Check and Remove latest image if it exists
        withEnv(["LATEST_TAG=${latestTag}"]) {
            def latestImageExists = sh(script: 'docker image ls | grep "$LATEST_TAG"', returnStatus: true)
            if(latestImageExists == 0){
                echo "Latest image found () ${latestTag} ) Removing it"
                sh 'docker rmi "$LATEST_TAG"'
            } else {
                echo "No latest image found"
            }
        }

        // Check and Remove new version image if it exists
        withEnv(["NEW_VERSION_TAG=${newversionTag}"]) {
            def newversionImageExists = sh(script: 'docker image ls | grep "$NEW_VERSION_TAG"', returnStatus: true)
            if(newversionImageExists == 0){
                echo "New version image found () ${newversionTag} ) Removing it"
                sh 'docker rmi "$NEW_VERSION_TAG"'
            } else {
                echo "No new version image found"
            }
        }

        // Check and Remove default version image if it exists
        wihtEnv(["DEFAULT_VERSION_TAG=${defaultversionTag}"]) {
            def defaultversionImageExists = sh(script: 'docker image ls | grep "$DEFAULT_VERSION_TAG"', returnStatus: true)
            if(defaultversionImageExists == 0){
                echo "Default version image found () ${defaultversionTag} ) Removing it"
                sh 'docker rmi "$DEFAULT_VERSION_TAG"'
            } else {
                echo "No default version image found"
            }
        }
    }
}
