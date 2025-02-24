def call(String dockerUser, String userName, String appName, String newVersion, String defaultVersion) {
    script {
        def latestTag = "${dockerUser}/${userName}-${appName}-img:latest"
        def versionedTag = "${dockerUser}/${userName}-${appName}-img:${newVersion}"
        def defaultTag = "${dockerUser}/${userName}-${appName}-img:${defaultVersion}"

        // Check and remove latest image if it exists
        def latestImageExists = sh(script: 'docker images -q ${latestTag}', returnStdout: true).trim()
        if (latestImageExists) {
            echo "Docker Image with the latest tag already exists. Removing the existing image..."
            sh 'docker rmi -f ${latestTag}'
        } else {
            echo "Docker Image with the latest tag does not exist."
        }

        // Check and remove versioned image if it exists
        def versionedImageExists = sh(script: 'docker images -q ${versionedTag}', returnStdout: true).trim()
        if (versionedImageExists) {
            echo "Docker Image with the specified version (${newVersion}) already exists. Removing the existing image..."
            sh 'docker rmi -f ${versionedTag}'
        } else {
            echo "Docker Image with the specified version (${newVersion}) does not exist."
        }

        // Check and remove versioned image if it exists
        def defaultversionedImageExists = sh(script: 'docker images -q ${defaultTag}', returnStdout: true).trim()
        if (defaultversionedImageExists) {
            echo "Docker Image with the specified version (${defaultVersion}) already exists. Removing the existing image..."
            sh 'docker rmi -f ${defaultTag}'
        } else {
            echo "Docker Image with the specified version (${defaultVersion}) does not exist."
        }
    }
}
