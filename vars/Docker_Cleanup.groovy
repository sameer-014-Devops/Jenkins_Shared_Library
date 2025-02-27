def call(Sting dockerUser){
    script {

        def latestImageTag = "${env.dockerUser}/${env.userName}-${env.appName}-${env.tierOne}-img:latest"
        def versionedImageTag = "${env.dockerUser}/${env.userName}-${env.appName}-${env.tierOne}-img:${env.newVersion}"
        def defaultversionedImageTag = "${env.dockerUser}/${env.userName}-${env.appName}-${env.tierOne}-img:${env.defaultVersion}"

        echo "${env.dockerUser}/${env.userName}-${env.appName}-${env.tierOne}-img:latest"
        echo "${env.dockerUser}/${env.userName}-${env.appName}-${env.tierOne}-img:${env.NewVersion}"
        echo "${env.dockerUser}/${env.userName}-${env.appName}-${env.tierOne}-img:${env.DefaultVersion}"
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
