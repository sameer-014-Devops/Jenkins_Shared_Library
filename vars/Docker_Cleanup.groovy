def call(String userName, String appName, String newVersion, String defaultVersion) {
    def credentialsId = config.credentialsId ?: 'dockerhubCredentials'
    withCredentials([usernamePassword(credentialsId: credentialsId, passwordVariable: 'dockerhubCredentials_Passwd', usernameVariable: 'dockerhubCredentials_User')]) {
        try {
            echo "Checking for the latest Docker image..."
            def latestImageExists = sh(script: """docker images -q ${env.dockerhubCredentials_User}/${userName}-${appName}-img:latest""", returnStdout: true).trim()

            if (latestImageExists) {
                echo 'Docker Image with the latest tag already exists. Removing the existing image...'
                sh """docker rmi -f ${env.dockerhubCredentials_User}/${userName}-${appName}-img:latest"""
            } else {
                echo 'Docker Image with the latest tag does not exist...'
            }

            echo "Checking for the versioned Docker image..."
            def versionedImageExists = sh(script: """docker images -q ${env.dockerhubCredentials_User}/${userName}-${appName}-img:${newVersion}""", returnStdout: true).trim()

            if (versionedImageExists) {
                echo "Docker Image with the ${newVersion} version already exists. Removing the existing image..."
                sh """docker rmi -f ${env.dockerhubCredentials_User}/${userName}-${appName}-img:${newVersion}"""
            } else {
                echo "Docker Image with the ${newVersion} version does not exist..."
            }

            echo "Checking for the default versioned Docker image..."
            def defaultVersionedImageExists = sh(script: """docker images -q ${env.dockerhubCredentials_User}/${userName}-${appName}-img:${defaultVersion}""", returnStdout: true).trim()

            if (defaultVersionedImageExists) {
                echo "Docker Image with the ${defaultVersion} version already exists. Removing the existing image..."
                sh """docker rmi -f ${env.dockerhubCredentials_User}/${userName}-${appName}-img:${defaultVersion}"""
            } else {
                echo "Docker Image with the ${defaultVersion} version does not exist..."
            }
        } catch (Exception e) {
            echo "############################## Docker Cleanup is FAILED ##############################"
            echo "Error: ${e.message}"
            throw e
        }
    }
}
