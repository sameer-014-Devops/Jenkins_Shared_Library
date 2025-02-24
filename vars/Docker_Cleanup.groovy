def call(String dockerUser, String userName, String appName, String newVersion, String defaultVersion) {
    script {
        def latestTag = "${dockerUser}/${userName}-${appName}-img:latest"
        def versionedTag = "${dockerUser}/${userName}-${appName}-img:${newVersion}"
        def defaultTag = "${dockerUser}/${userName}-${appName}-img:${defaultVersion}"

        // Check and remove latest image if it exists
        def latestImageExists = docker.imageExists(latestTag).exists()
        if (latestImageExists) {
            echo "Docker image ${latestTag} exists. Removing it..."
            docker.image(latestTag).remove()
        } else {
            echo "Docker image ${latestTag} does not exist."
        }

        // Check and remove versioned image if it exists
        def versionedImageExists = docker.imageExists(versionedTag).exists()
        if (versionedImageExists) {
            echo "Docker image ${versionedTag} exists. Removing it..."
            docker.image(versionedTag).remove()
        } else {
            echo "Docker image ${versionedTag} does not exist."
        }

        // Check and remove default image if it exists
        def defaultImageExists = docker.imageExists(defaultTag).exists()
        if (defaultImageExists) {
            echo "Docker image ${defaultTag} exists. Removing it..."
            docker.image(defaultTag).remove()
        } else {
            echo "Docker image ${defaultTag} does not exist."
        }
    }
}
