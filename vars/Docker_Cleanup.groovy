def call(String dockerUser, String userName, String appName, String tierName, String newVersion, String defaultVersion) {
    
    def latestImageTag = "${dockerUser}/${userName}-${appName}-${tierName}-img:latest"
    def versionedImageTag = "${dockerUser}/${userName}-${appName}-${tierName}-img:${newVersion}"
    def defaultversionedImageTag = "${dockerUser}/${userName}-${appName}-${tierName}-img:${defaultVersion}"

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
