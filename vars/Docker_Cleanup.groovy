def call (String dockerUser, String userName, String appName, String tierOne, String tierTwo, String newVersion, String defaultVersion){
    script {
        def cleanupImages = { tier ->
            def imagePrefix = "${dockerUser}/${userName}-${appName}-${tier}-img"
            
            def images = [
                "latest": sh(script: "docker images -q ${imagePrefix}:latest", returnStdout: true).trim(),
                "new": sh(script: "docker images -q ${imagePrefix}:${newVersion}", returnStdout: true).trim(),
                "default": sh(script: "docker images -q ${imagePrefix}:${defaultVersion}", returnStdout: true).trim()
            ]
            
            try {
                images.values().findAll { it != "" }.each { imageId ->
                    sh "docker rmi -f ${imageId} && echo 'Successfully removed image: ${imageId}'"
                }
            } catch (Exception e) {
                echo "Error removing images for ${tier}: ${e.message}"
            }
        }

        parallel(
            "cleanup-tier-one": { cleanupImages(tierOne) },
            "cleanup-tier-two": { cleanupImages(tierTwo) }
        )
    }
}
