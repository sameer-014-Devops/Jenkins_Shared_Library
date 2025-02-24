def call(String userName, String appName, String newVersion, String defaultVersion) {
    
    def latestImageExists = sh(script: """docker images -q $DOCKERHUB_CREDENTIALS_USR/${userName}-${appName}-img:latest""", returnStdout: true).trim()

    if (latestImageExists) {
        echo 'Docker Image with the latest tag already exists. Removing the existing image...'
        sh """docker rmi -f $DOCKERHUB_CREDENTIALS_USR/${userName}-${appName}-img:latest"""
    } else {
        echo 'Docker Image with the latest tag does not exist...'
    }

    def versionedImageExists = sh(script: """docker images -q $DOCKERHUB_CREDENTIALS_USR/${userName}-${appName}-img:${newVersion}""", returnStdout: true).trim()

    if (versionedImageExists) {
        echo "Docker Image with the ${newversion} version already exists. Removing the existing image..."
        sh """docker rmi -f $DOCKERHUB_CREDENTIALS_USR/${userName}-${appName}-img:${newVersion}"""
    } else {
        echo "Docker Image with the ${newversion} version does not exist..."
    }

    def defaultversionedImageExists = sh(script: """docker images -q $DOCKERHUB_CREDENTIALS_USR/${userName}-${appName}-img:${defaultVersion}""", returnStdout: true).trim()

    if (defaultversionedImageExists) {
        echo "Docker Image with the ${defaultVersion} version already exists. Removing the existing image..."
        sh """docker rmi -f $DOCKERHUB_CREDENTIALS_USR/${userName}-${appName}-img:${defaultVersion}"""
    } else {
        echo "Docker Image with the ${defaultVersion} version does not exist..."
    }
}
