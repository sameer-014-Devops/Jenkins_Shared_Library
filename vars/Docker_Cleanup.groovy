def call(String dockerUser, String UserName, String AppName, String TierName, String NewVersion, String DefaultVersion) {
    script {
        echo "User Name: ${UserName}"
        echo "App Name: ${AppName}"
        echo "Tier Name: ${TierName}"
        echo "New Version: ${NewVersion}"
        echo "Default Version: ${DefaultVersion}"

        def latestImageTag = "${env.dockerUser}/${env.UserName}-${env.AppName}-${env.TierName}-img:latest"
        def versionedImageTag = "${env.dockerUser}/${env.UserName}-${env.AppName}-${env.TierName}-img:${env.NewVersion}"
        def defaultversionedImageTag = "${env.dockerUser}/${env.UserName}-${env.AppName}-${env.TierName}-img:${env.DefaultVersion}"

        echo "${env.dockerUser}/${env.UserName}-${env.AppName}-${env.TierName}-img:latest"
        echo "${env.dockerUser}/${env.UserName}-${env.AppName}-${env.TierName}-img:${env.NewVersion}"
        echo "${env.dockerUser}/${env.UserName}-${env.AppName}-${env.TierName}-img:${env.DefaultVersion}"
        def latestImageExists = sh(script: "docker images -q ${latestImageTag}", returnStdout: true).trim()
        
        if (latestImageExists) {
            echo "Docker Image with the latest tag already exists. Removing the existing image..."
            sh "docker rmi -f ${latestImageTag}"
        } else {
            echo "Docker Image with the latest tag does not exist..."
        }

        def versionedImageExists = sh(script: "docker images -q ${versionedImageTag}", returnStdout: true).trim()
        
        if (versionedImageExists) {
            echo "Docker Image with the specified version already exists. Removing the existing image..."
            sh "docker rmi -f ${versionedImageTag}"
        } else {
            echo "Docker Image with the specified version does not exist..."
        }

        def defaultversionedImageExists = sh(script: "docker images -q ${defaultversionedImageTag}", returnStdout: true).trim()
        
        if (defaultversionedImageExists) {
            echo "Docker Image with the specified version already exists. Removing the existing image..."
            sh "docker rmi -f ${defaultversionedImageTag}"
        } else {
            echo "Docker Image with the specified version does not exist..."
        }
    }
    
}
