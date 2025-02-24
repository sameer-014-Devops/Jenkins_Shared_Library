def call(String dockerUser, String userName, String appName, String newversion, String defaultVersion){
    script{
        def latestTag = "${dockerUser}/${userName}-${appName}-img:latest"
        def newversionTag = "${dockerUser}/${userName}-${appName}-img:${newversion}"
        def defaultversionTag = "${dockerUser}/${userName}-${appName}-img:${defaultVersion}"

        // Check and Remove latest image if it exists
        def latestImageExists = sh(script: "docker image ls | grep ${latestTag}", returnStatus: true).trim()
        if(latestImageExists == 0){
            echo "Latest image found () ${latestTag} ) Removing it"
            sh "docker rmi ${latestTag}"
        } else {
            echo "No latest image found"
        }

        // Check and Remove new version image if it exists
        def newversionImageExists = sh(script: "docker image ls | grep ${newversionTag}", returnStatus: true).trim()
        if(newversionImageExists == 0){
            echo "New version image found () ${newversionTag} ) Removing it"
            sh "docker rmi ${newversionTag}"
        } else {
            echo "No new version image found"
        }

        // Check and Remove default version image if it exists
        def defaultversionImageExists = sh(script: "docker image ls | grep ${defaultversionTag}", returnStatus: true).trim()
        if(defaultversionImageExists == 0){
            echo "Default version image found () ${defaultversionTag} ) Removing it"
            sh "docker rmi ${defaultversionTag}"
        } else {
            echo "No default version image found"
        }
    }
}
