def call(String dockerUser, String userName, String appName, String tierName, String newVersion, String defaultVersion) {
    script {
        // Define all tags at once in a map for better organization and maintenance
        def imageTags = [
            latest: "${dockerUser}/${userName}-${appName}-${tierName}-img:latest",
            newVersion: "${dockerUser}/${userName}-${appName}-${tierName}-img:${newVersion}",
            defaultVersion: "${dockerUser}/${userName}-${appName}-${tierName}-img:${defaultVersion}"
        ]

        try {
            // Use a loop to handle all image checks and removals
            imageTags.each { key, tag ->
                def imageExists = sh(
                    script: "docker images -q ${tag}",
                    returnStdout: true
                ).trim()

                echo "Checking if Docker image ${tag} exists: ${imageExists}"

                if (imageExists) {
                    echo "Docker Image ${tag} already exists - Removing it Now..."
                    sh "docker rmi -f ${tag} || true"  // Added || true to prevent failure if image is in use
                } else {
                    echo "Docker Image ${tag} does not exist..."
                }
            }
        } catch (Exception e) {
            echo "Error during Docker cleanup: ${e.message}"
            currentBuild.result = 'FAILURE'  // Set build status
            error("Docker cleanup failed: ${e.message}")  // Throw error with more context
        }
    }
}
