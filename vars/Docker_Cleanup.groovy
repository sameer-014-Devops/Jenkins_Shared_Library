def call (String dockerUser, String userName, String appName, String tierOne, String tierTwo, String newVersion, String defaultVersion){
    script {
        def cleanupImages = { tier ->
            echo "Starting cleanup for tier: ${tier}"
            def imagePrefix = "${env.dockerUser}/${env.userName}-${env.appName}-${tier}-img"
            
            try {
                // First list all images to verify they exist
                echo "Searching for images with prefix: ${imagePrefix}"
                sh "docker images | grep ${imagePrefix} || true"
                
                def images = [:]
                
                // Add error checking for each command
                try {
                    images.latest = sh(
                        script: "docker images -q ${imagePrefix}:latest",
                        returnStdout: true
                    ).trim()
                    echo "Found latest image ID: ${images.latest ?: 'none'}"
                } catch (Exception e) {
                    echo "Error getting latest image: ${e.message}"
                }
                
                try {
                    images.new = sh(
                        script: "docker images -q ${imagePrefix}:${env.newVersion}",
                        returnStdout: true
                    ).trim()
                    echo "Found new version image ID: ${images.new ?: 'none'}"
                } catch (Exception e) {
                    echo "Error getting new version image: ${e.message}"
                }
                
                try {
                    images.default = sh(
                        script: "docker images -q ${imagePrefix}:${env.defaultVersion}",
                        returnStdout: true
                    ).trim()
                    echo "Found default version image ID: ${images.default ?: 'none'}"
                } catch (Exception e) {
                    echo "Error getting default version image: ${e.message}"
                }
                
                // Remove images if they exist
                images.each { tag, imageId ->
                    if (imageId) {
                        try {
                            echo "Attempting to remove image with ID: ${imageId}"
                            sh """
                                if docker inspect ${imageId} >/dev/null 2>&1; then
                                    docker rmi -f ${imageId} || true
                                    echo "Successfully removed image: ${imageId}"
                                else
                                    echo "Image ${imageId} does not exist"
                                fi
                            """
                        } catch (Exception e) {
                            echo "Failed to remove image ${imageId}: ${e.message}"
                        }
                    }
                }
                
                echo "Cleanup completed for tier: ${tier}"
                
            } catch (Exception e) {
                echo "Major error during cleanup for ${tier}: ${e.message}"
                error "Failed to cleanup Docker images for ${tier}"
            }
        }

        try {
            echo "Starting Docker cleanup process..."
            echo "Parameters received:"
            echo "env.dockerUser: ${env.dockerUser}"
            echo "env.userName: ${env.userName}"
            echo "env.appName: ${env.appName}"
            echo "env.tierOne: ${env.tierOne}"
            echo "env.tierTwo: ${env.tierTwo}"
            echo "env.newVersion: ${env.newVersion}"
            echo "env.defaultVersion: ${env.defaultVersion}"
            
            parallel(
                "cleanup-tier-one": { cleanupImages(env.tierOne) },
                "cleanup-tier-two": { cleanupImages(env.tierTwo) }
            )
            
            echo "Docker cleanup process completed successfully"
            
        } catch (Exception e) {
            echo "Docker cleanup failed with error: ${e.message}"
            error "Docker cleanup failed"
        }
    }
}
